package com.intera.roostrap.controller;

import com.intera.roostrap.domain.Agendaitem;
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

@RequestMapping("/agendaitems")
@Controller
@RooWebScaffold(path = "agendaitems", formBackingObject = Agendaitem.class)
@RooWebJson(jsonObject = Agendaitem.class)
public class AgendaitemController {

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Agendaitem agendaitem, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, agendaitem);
            return "agendaitems/create";
        }
        uiModel.asMap().clear();
        agendaitem.persist();
        return "redirect:/agendaitems/" + encodeUrlPathSegment(agendaitem.getPrimaryId().toString(), httpServletRequest);
    }

    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Agendaitem());
        return "agendaitems/create";
    }

    @RequestMapping(value = "/{PrimaryId}", produces = "text/html")
    public String show(@PathVariable("PrimaryId") String PrimaryId, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("agendaitem", Agendaitem.findAgendaitem(PrimaryId));
        uiModel.addAttribute("itemId", PrimaryId);
        return "agendaitems/show";
    }

    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("agendaitems", Agendaitem.findAgendaitemEntries(firstResult, sizeNo));
            float nrOfPages = (float) Agendaitem.countAgendaitems() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("agendaitems", Agendaitem.findAllAgendaitems());
        }
        addDateTimeFormatPatterns(uiModel);
        return "agendaitems/list";
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Agendaitem agendaitem, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, agendaitem);
            return "agendaitems/update";
        }
        uiModel.asMap().clear();
        agendaitem.merge();
        return "redirect:/agendaitems/" + encodeUrlPathSegment(agendaitem.getPrimaryId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/{PrimaryId}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("PrimaryId") String PrimaryId, Model uiModel) {
        populateEditForm(uiModel, Agendaitem.findAgendaitem(PrimaryId));
        return "agendaitems/update";
    }

    @RequestMapping(value = "/{PrimaryId}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("PrimaryId") String PrimaryId, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Agendaitem agendaitem = Agendaitem.findAgendaitem(PrimaryId);
        agendaitem.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/agendaitems";
    }

    void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("agendaitem_agiactualstart_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("agendaitem_agiactualend_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("agendaitem_agicreatedon_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("agendaitem_agimodifiedon_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("agendaitem_agisyncdate_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
    }

    void populateEditForm(Model uiModel, Agendaitem agendaitem) {
        uiModel.addAttribute("agendaitem", agendaitem);
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

    @RequestMapping(value = "/{PrimaryId}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<java.lang.String> showJson(@PathVariable("PrimaryId") String PrimaryId) {
        Agendaitem agendaitem = Agendaitem.findAgendaitem(PrimaryId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (agendaitem == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(agendaitem.toJson(), headers, HttpStatus.OK);
    }

    @RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<java.lang.String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<Agendaitem> result = Agendaitem.findAllAgendaitems();
        return new ResponseEntity<String>(Agendaitem.toJsonArray(result), headers, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> createFromJson(@RequestBody String json) {
        Agendaitem agendaitem = Agendaitem.fromJsonToAgendaitem(json);
        agendaitem.persist();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> createFromJsonArray(@RequestBody String json) {
        for (Agendaitem agendaitem : Agendaitem.fromJsonArrayToAgendaitems(json)) {
            agendaitem.persist();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Agendaitem agendaitem = Agendaitem.fromJsonToAgendaitem(json);
        if (agendaitem.merge() == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (Agendaitem agendaitem : Agendaitem.fromJsonArrayToAgendaitems(json)) {
            if (agendaitem.merge() == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/{PrimaryId}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> deleteFromJson(@PathVariable("PrimaryId") String PrimaryId) {
        Agendaitem agendaitem = Agendaitem.findAgendaitem(PrimaryId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (agendaitem == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        agendaitem.remove();
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
}
