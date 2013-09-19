// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.intera.roostrap.web;

import com.intera.roostrap.domain.Attachment;
import com.intera.roostrap.web.AttachmentController;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect AttachmentController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String AttachmentController.create(@Valid Attachment attachment, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, attachment);
            return "attachments/create";
        }
        uiModel.asMap().clear();
        attachment.persist();
        return "redirect:/attachments/" + encodeUrlPathSegment(attachment.getPrimaryId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String AttachmentController.createForm(Model uiModel) {
        populateEditForm(uiModel, new Attachment());
        return "attachments/create";
    }
    
    @RequestMapping(value = "/{PrimaryId}", produces = "text/html")
    public String AttachmentController.show(@PathVariable("PrimaryId") String PrimaryId, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("attachment", Attachment.findAttachment(PrimaryId));
        uiModel.addAttribute("itemId", PrimaryId);
        return "attachments/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String AttachmentController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("attachments", Attachment.findAttachmentEntries(firstResult, sizeNo));
            float nrOfPages = (float) Attachment.countAttachments() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("attachments", Attachment.findAllAttachments());
        }
        addDateTimeFormatPatterns(uiModel);
        return "attachments/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String AttachmentController.update(@Valid Attachment attachment, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, attachment);
            return "attachments/update";
        }
        uiModel.asMap().clear();
        attachment.merge();
        return "redirect:/attachments/" + encodeUrlPathSegment(attachment.getPrimaryId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{PrimaryId}", params = "form", produces = "text/html")
    public String AttachmentController.updateForm(@PathVariable("PrimaryId") String PrimaryId, Model uiModel) {
        populateEditForm(uiModel, Attachment.findAttachment(PrimaryId));
        return "attachments/update";
    }
    
    @RequestMapping(value = "/{PrimaryId}", method = RequestMethod.DELETE, produces = "text/html")
    public String AttachmentController.delete(@PathVariable("PrimaryId") String PrimaryId, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Attachment attachment = Attachment.findAttachment(PrimaryId);
        attachment.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/attachments";
    }
    
    void AttachmentController.addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("attachment_acreatedon_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("attachment_amodifiedon_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
    }
    
    void AttachmentController.populateEditForm(Model uiModel, Attachment attachment) {
        uiModel.addAttribute("attachment", attachment);
        addDateTimeFormatPatterns(uiModel);
    }
    
    String AttachmentController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
