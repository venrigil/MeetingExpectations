package com.intera.roostrap.web;

import com.intera.roostrap.domain.Directory;
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

@RequestMapping("/directorys")
@Controller
@RooWebScaffold(path = "directorys", formBackingObject = Directory.class)
@RooWebJson(jsonObject = Directory.class)
public class DirectoryController {

    @RequestMapping(value = "/{PrimaryId}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<java.lang.String> showJson(@PathVariable("PrimaryId") String PrimaryId) {
        Directory directory = Directory.findDirectory(PrimaryId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (directory == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(directory.toJson(), headers, HttpStatus.OK);
    }

    @RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<java.lang.String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<Directory> result = Directory.findAllDirectorys();
        return new ResponseEntity<String>(Directory.toJsonArray(result), headers, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> createFromJson(@RequestBody String json) {
        Directory directory = Directory.fromJsonToDirectory(json);
        directory.persist();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> createFromJsonArray(@RequestBody String json) {
        for (Directory directory : Directory.fromJsonArrayToDirectorys(json)) {
            directory.persist();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Directory directory = Directory.fromJsonToDirectory(json);
        if (directory.merge() == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (Directory directory : Directory.fromJsonArrayToDirectorys(json)) {
            if (directory.merge() == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/{PrimaryId}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> deleteFromJson(@PathVariable("PrimaryId") String PrimaryId) {
        Directory directory = Directory.findDirectory(PrimaryId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (directory == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        directory.remove();
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Directory directory, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, directory);
            return "directorys/create";
        }
        uiModel.asMap().clear();
        directory.persist();
        return "redirect:/directorys/" + encodeUrlPathSegment(directory.getPrimaryId().toString(), httpServletRequest);
    }

    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Directory());
        return "directorys/create";
    }

    @RequestMapping(value = "/{PrimaryId}", produces = "text/html")
    public String show(@PathVariable("PrimaryId") String PrimaryId, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("directory", Directory.findDirectory(PrimaryId));
        uiModel.addAttribute("itemId", PrimaryId);
        return "directorys/show";
    }

    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("directorys", Directory.findDirectoryEntries(firstResult, sizeNo));
            float nrOfPages = (float) Directory.countDirectorys() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("directorys", Directory.findAllDirectorys());
        }
        addDateTimeFormatPatterns(uiModel);
        return "directorys/list";
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Directory directory, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, directory);
            return "directorys/update";
        }
        uiModel.asMap().clear();
        directory.merge();
        return "redirect:/directorys/" + encodeUrlPathSegment(directory.getPrimaryId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/{PrimaryId}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("PrimaryId") String PrimaryId, Model uiModel) {
        populateEditForm(uiModel, Directory.findDirectory(PrimaryId));
        return "directorys/update";
    }

    @RequestMapping(value = "/{PrimaryId}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("PrimaryId") String PrimaryId, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Directory directory = Directory.findDirectory(PrimaryId);
        directory.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/directorys";
    }

    void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("directory_pcreatedon_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("directory_pmodifiedon_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("directory_psyncdate_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
    }

    void populateEditForm(Model uiModel, Directory directory) {
        uiModel.addAttribute("directory", directory);
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
