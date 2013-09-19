package com.intera.roostrap.web.security;

import com.intera.roostrap.domain.security.Role;
import com.intera.roostrap.domain.security.User;
import com.intera.roostrap.domain.security.UserRole;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.finder.RooWebFinder;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
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

@RequestMapping("/security/assignrole")
@Controller
@RooWebScaffold(path = "security/assignrole", formBackingObject = UserRole.class)
@RooWebJson(jsonObject = UserRole.class)
@RooWebFinder
public class UserRoleController {

	@RequestMapping(params = { "find=ByUser", "form" }, method = RequestMethod.GET)
    public String findUserRolesByUserForm(Model uiModel) {
        uiModel.addAttribute("users", User.findAllUsers());
        return "security/assignrole/findUserRolesByUser";
    }

	@RequestMapping(params = "find=ByUser", method = RequestMethod.GET)
    public String findUserRolesByUser(@RequestParam("user") User user, Model uiModel) {
        uiModel.addAttribute("userroles", UserRole.findUserRolesByUser(user).getResultList());
        return "security/assignrole/list";
    }

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") Long id) {
        UserRole userRole = UserRole.findUserRole(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (userRole == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(userRole.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<UserRole> result = UserRole.findAllUserRoles();
        return new ResponseEntity<String>(UserRole.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        UserRole userRole = UserRole.fromJsonToUserRole(json);
        userRole.persist();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (UserRole userRole: UserRole.fromJsonArrayToUserRoles(json)) {
            userRole.persist();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        UserRole userRole = UserRole.fromJsonToUserRole(json);
        if (userRole.merge() == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (UserRole userRole: UserRole.fromJsonArrayToUserRoles(json)) {
            if (userRole.merge() == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Long id) {
        UserRole userRole = UserRole.findUserRole(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (userRole == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        userRole.remove();
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(params = "find=ByUser", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> jsonFindUserRolesByUser(@RequestParam("user") User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        return new ResponseEntity<String>(UserRole.toJsonArray(UserRole.findUserRolesByUser(user).getResultList()), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid UserRole userRole, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, userRole);
            return "security/assignrole/create";
        }
        uiModel.asMap().clear();
        userRole.persist();
        return "redirect:/security/assignrole/" + encodeUrlPathSegment(userRole.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new UserRole());
        List<String[]> dependencies = new ArrayList<String[]>();
        if (User.countUsers() == 0) {
            dependencies.add(new String[] { "user", "security/users" });
        }
        if (Role.countRoles() == 0) {
            dependencies.add(new String[] { "role", "security/roles" });
        }
        uiModel.addAttribute("dependencies", dependencies);
        return "security/assignrole/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("userrole", UserRole.findUserRole(id));
        uiModel.addAttribute("itemId", id);
        return "security/assignrole/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("userroles", UserRole.findUserRoleEntries(firstResult, sizeNo));
            float nrOfPages = (float) UserRole.countUserRoles() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("userroles", UserRole.findAllUserRoles());
        }
        return "security/assignrole/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid UserRole userRole, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, userRole);
            return "security/assignrole/update";
        }
        uiModel.asMap().clear();
        userRole.merge();
        return "redirect:/security/assignrole/" + encodeUrlPathSegment(userRole.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, UserRole.findUserRole(id));
        return "security/assignrole/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        UserRole userRole = UserRole.findUserRole(id);
        userRole.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/security/assignrole";
    }

	void populateEditForm(Model uiModel, UserRole userRole) {
        uiModel.addAttribute("userRole", userRole);
        uiModel.addAttribute("roles", Role.findAllRoles());
        uiModel.addAttribute("users", User.findAllUsers());
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
