package com.intera.roostrap.web.security;

import com.intera.roostrap.domain.security.User;
import com.intera.roostrap.domain.security.attribute.CreateAttributes;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.Validator;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.roo.addon.web.mvc.controller.finder.RooWebFinder;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

@RequestMapping("/security/users")
@Controller
@RooWebScaffold(path = "security/users", formBackingObject = User.class)
@RooWebJson(jsonObject = User.class)
@RooWebFinder
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private transient MailSender mailSender;

    @Autowired
    @Qualifier("accountActivationMailTemplate")
    private SimpleMailMessage mailTemplate;

    @Autowired
    private Validator validator;

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid User user, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        validator.validate(user, CreateAttributes.class);
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, user);
            return "security/users/create";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.persist();
        return "redirect:/security/users/" + encodeUrlPathSegment(user.getId().toString(), httpServletRequest);
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid User user, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, user);
            return "security/users/update";
        }
        User storedUser = User.findUser(user.getId());
        user.setPassword(storedUser.getPassword());
        if (user.getEnabled() && !storedUser.getEnabled()) {
            user.setActivationDate(new Date());
            try {
                sendActivationMail(user);
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }
        uiModel.asMap().clear();
        user.merge();
        return "redirect:/security/users/" + encodeUrlPathSegment(user.getId().toString(), httpServletRequest);
    }

    private void sendActivationMail(User user) throws IOException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        String msgBody = "...";
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("somu@rigil.com", "RigilAppME Admin"));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress("venkata@rigil.com", "Mr. User"));
            msg.setSubject("Mail Activation");
            msg.setText(msgBody);
            Transport.send(msg);
        } catch (AddressException addressException) {
        } catch (MessagingException messageException) {
        }
    }

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") Long id) {
        User user = User.findUser(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (user == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(user.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<User> result = User.findAllUsers();
        return new ResponseEntity<String>(User.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        User user = User.fromJsonToUser(json);
        user.persist();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (User user: User.fromJsonArrayToUsers(json)) {
            user.persist();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        User user = User.fromJsonToUser(json);
        if (user.merge() == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (User user: User.fromJsonArrayToUsers(json)) {
            if (user.merge() == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Long id) {
        User user = User.findUser(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (user == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        user.remove();
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(params = "find=ByEmailAddress", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> jsonFindUsersByEmailAddress(@RequestParam("emailAddress") String emailAddress) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        return new ResponseEntity<String>(User.toJsonArray(User.findUsersByEmailAddress(emailAddress).getResultList()), headers, HttpStatus.OK);
    }

	@RequestMapping(params = { "find=ByEmailAddress", "form" }, method = RequestMethod.GET)
    public String findUsersByEmailAddressForm(Model uiModel) {
        return "security/users/findUsersByEmailAddress";
    }

	@RequestMapping(params = "find=ByEmailAddress", method = RequestMethod.GET)
    public String findUsersByEmailAddress(@RequestParam("emailAddress") String emailAddress, Model uiModel) {
        uiModel.addAttribute("users", User.findUsersByEmailAddress(emailAddress).getResultList());
        return "security/users/list";
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new User());
        return "security/users/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("user", User.findUser(id));
        uiModel.addAttribute("itemId", id);
        return "security/users/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("users", User.findUserEntries(firstResult, sizeNo));
            float nrOfPages = (float) User.countUsers() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("users", User.findAllUsers());
        }
        addDateTimeFormatPatterns(uiModel);
        return "security/users/list";
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, User.findUser(id));
        return "security/users/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        User user = User.findUser(id);
        user.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/security/users";
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("user_activationdate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

	void populateEditForm(Model uiModel, User user) {
        uiModel.addAttribute("user", user);
        addDateTimeFormatPatterns(uiModel);
    }

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
}
