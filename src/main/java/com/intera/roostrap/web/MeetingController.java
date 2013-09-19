package com.intera.roostrap.web;

import com.intera.roostrap.domain.Meeting;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
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

@RequestMapping("/meetings")
@Controller
@RooWebScaffold(path = "meetings", formBackingObject = Meeting.class)
@RooWebJson(jsonObject = Meeting.class)
@RooWebFinder
public class MeetingController {

    @RequestMapping(params = { "find=ByMcreatedByEquals", "form" }, method = RequestMethod.GET)
    public String findMeetingsByMcreatedByEqualsForm(Model uiModel) {
        return "meetings/findMeetingsByMcreatedByEquals";
    }

    @RequestMapping(params = "find=ByMcreatedByEquals", method = RequestMethod.GET)
    public String findMeetingsByMcreatedByEquals(@RequestParam("mcreatedBy") String mcreatedBy, Model uiModel) {
        uiModel.addAttribute("meetings", Meeting.findMeetingsByMcreatedByEquals(mcreatedBy).getResultList());
        return "meetings/list";
    }

    @RequestMapping(params = { "find=ByMeetingIDEquals", "form" }, method = RequestMethod.GET)
    public String findMeetingsByMeetingIDEqualsForm(Model uiModel) {
        return "meetings/findMeetingsByMeetingIDEquals";
    }

    @RequestMapping(params = "find=ByMeetingIDEquals", method = RequestMethod.GET)
    public String findMeetingsByMeetingIDEquals(@RequestParam("meetingID") String meetingID, Model uiModel) {
        uiModel.addAttribute("meetings", Meeting.findMeetingsByMeetingIDEquals(meetingID).getResultList());
        return "meetings/list";
    }

    @RequestMapping(value = "/{PrimaryId}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<java.lang.String> showJson(@PathVariable("PrimaryId") String PrimaryId) {
        Meeting meeting = Meeting.findMeeting(PrimaryId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (meeting == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(meeting.toJson(), headers, HttpStatus.OK);
    }

    @RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<java.lang.String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<Meeting> result = Meeting.findAllMeetings();
        return new ResponseEntity<String>(Meeting.toJsonArray(result), headers, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> createFromJson(@RequestBody String json) {
        Meeting meeting = Meeting.fromJsonToMeeting(json);
        meeting.persist();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> createFromJsonArray(@RequestBody String json) {
        for (Meeting meeting : Meeting.fromJsonArrayToMeetings(json)) {
            meeting.persist();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Meeting meeting = Meeting.fromJsonToMeeting(json);
        if (meeting.merge() == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (Meeting meeting : Meeting.fromJsonArrayToMeetings(json)) {
            if (meeting.merge() == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/{PrimaryId}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> deleteFromJson(@PathVariable("PrimaryId") String PrimaryId) {
        Meeting meeting = Meeting.findMeeting(PrimaryId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (meeting == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        meeting.remove();
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

    @RequestMapping(params = "find=ByMcreatedByEquals", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<java.lang.String> jsonFindMeetingsByMcreatedByEquals(@RequestParam("mcreatedBy") String mcreatedBy) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        return new ResponseEntity<String>(Meeting.toJsonArray(Meeting.findMeetingsByMcreatedByEquals(mcreatedBy).getResultList()), headers, HttpStatus.OK);
    }

    @RequestMapping(params = "find=ByMeetingIDEquals", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<java.lang.String> jsonFindMeetingsByMeetingIDEquals(@RequestParam("meetingID") String meetingID) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        return new ResponseEntity<String>(Meeting.toJsonArray(Meeting.findMeetingsByMeetingIDEquals(meetingID).getResultList()), headers, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Meeting meeting, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, meeting);
            return "meetings/create";
        }
        uiModel.asMap().clear();
        meeting.persist();
        return "redirect:/meetings/" + encodeUrlPathSegment(meeting.getPrimaryId().toString(), httpServletRequest);
    }

    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Meeting());
        return "meetings/create";
    }

    @RequestMapping(value = "/{PrimaryId}", produces = "text/html")
    public String show(@PathVariable("PrimaryId") String PrimaryId, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("meeting", Meeting.findMeeting(PrimaryId));
        uiModel.addAttribute("itemId", PrimaryId);
        return "meetings/show";
    }

    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("meetings", Meeting.findMeetingEntries(firstResult, sizeNo));
            float nrOfPages = (float) Meeting.countMeetings() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("meetings", Meeting.findAllMeetings());
        }
        addDateTimeFormatPatterns(uiModel);
        return "meetings/list";
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Meeting meeting, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, meeting);
            return "meetings/update";
        }
        uiModel.asMap().clear();
        meeting.merge();
        return "redirect:/meetings/" + encodeUrlPathSegment(meeting.getPrimaryId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/{PrimaryId}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("PrimaryId") String PrimaryId, Model uiModel) {
        populateEditForm(uiModel, Meeting.findMeeting(PrimaryId));
        return "meetings/update";
    }

    @RequestMapping(value = "/{PrimaryId}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("PrimaryId") String PrimaryId, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Meeting meeting = Meeting.findMeeting(PrimaryId);
        meeting.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/meetings";
    }

    void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("meeting_mdate_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("meeting_mplannedstartdatetime_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("meeting_mplannedenddatetime_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("meeting_msyncdate_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("meeting_mcreatedon_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("meeting_mmodifiedon_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
    }

    void populateEditForm(Model uiModel, Meeting meeting) {
        uiModel.addAttribute("meeting", meeting);
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
