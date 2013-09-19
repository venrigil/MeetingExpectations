package com.intera.roostrap.controller;

import com.intera.roostrap.domain.Actionitem;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RequestMapping("/actionitems")
@Controller
@RooWebScaffold(path = "actionitems", formBackingObject = Actionitem.class)
@RooWebJson(jsonObject = Actionitem.class)
public class ActionitemController {

    @RequestMapping(value = "/{PrimaryId}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<java.lang.String> showJson(@PathVariable("PrimaryId") String PrimaryId) {
        Actionitem actionitem = Actionitem.findActionitem(PrimaryId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (actionitem == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(actionitem.toJson(), headers, HttpStatus.OK);
    }

    @RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<java.lang.String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<Actionitem> result = Actionitem.findAllActionitems();
        return new ResponseEntity<String>(Actionitem.toJsonArray(result), headers, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> createFromJson(@RequestBody String json) {
        Actionitem actionitem = Actionitem.fromJsonToActionitem(json);
        actionitem.persist();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> createFromJsonArray(@RequestBody String json) {
        for (Actionitem actionitem : Actionitem.fromJsonArrayToActionitems(json)) {
            actionitem.persist();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Actionitem actionitem = Actionitem.fromJsonToActionitem(json);
        if (actionitem.merge() == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (Actionitem actionitem : Actionitem.fromJsonArrayToActionitems(json)) {
            if (actionitem.merge() == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/{PrimaryId}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> deleteFromJson(@PathVariable("PrimaryId") String PrimaryId) {
        Actionitem actionitem = Actionitem.findActionitem(PrimaryId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (actionitem == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        actionitem.remove();
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Actionitem actionitem, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, actionitem);
            return "actionitems/create";
        }
        uiModel.asMap().clear();
        actionitem.persist();
        return "redirect:/actionitems/" + encodeUrlPathSegment(actionitem.getPrimaryId().toString(), httpServletRequest);
    }

    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Actionitem());
        return "actionitems/create";
    }

    @RequestMapping(value = "/{PrimaryId}", produces = "text/html")
    public String show(@PathVariable("PrimaryId") String PrimaryId, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("actionitem", Actionitem.findActionitem(PrimaryId));
        uiModel.addAttribute("itemId", PrimaryId);
        return "actionitems/show";
    }

    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("actionitems", Actionitem.findActionitemEntries(firstResult, sizeNo));
            float nrOfPages = (float) Actionitem.countActionitems() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("actionitems", Actionitem.findAllActionitems());
        }
        addDateTimeFormatPatterns(uiModel);
        return "actionitems/list";
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Actionitem actionitem, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, actionitem);
            return "actionitems/update";
        }
        uiModel.asMap().clear();
        actionitem.merge();
        return "redirect:/actionitems/" + encodeUrlPathSegment(actionitem.getPrimaryId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/{PrimaryId}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("PrimaryId") String PrimaryId, Model uiModel) {
        populateEditForm(uiModel, Actionitem.findActionitem(PrimaryId));
        return "actionitems/update";
    }

    @RequestMapping(value = "/{PrimaryId}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("PrimaryId") String PrimaryId, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Actionitem actionitem = Actionitem.findActionitem(PrimaryId);
        actionitem.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/actionitems";
    }

    void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("actionitem_acicreatedon_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("actionitem_acimodifiedon_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("actionitem_acisyncdate_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("actionitem_aciduedate_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
    }

    void populateEditForm(Model uiModel, Actionitem actionitem) {
        uiModel.addAttribute("actionitem", actionitem);
        addDateTimeFormatPatterns(uiModel);
    }

    String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {
        }
        return pathSegment;
    }
}
