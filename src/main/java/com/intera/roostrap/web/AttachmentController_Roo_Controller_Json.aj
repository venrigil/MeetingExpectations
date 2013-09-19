// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.intera.roostrap.web;

import com.intera.roostrap.domain.Attachment;
import com.intera.roostrap.web.AttachmentController;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

privileged aspect AttachmentController_Roo_Controller_Json {
    
    @RequestMapping(value = "/{PrimaryId}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> AttachmentController.showJson(@PathVariable("PrimaryId") String PrimaryId) {
        Attachment attachment = Attachment.findAttachment(PrimaryId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (attachment == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(attachment.toJson(), headers, HttpStatus.OK);
    }
    
    @RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> AttachmentController.listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<Attachment> result = Attachment.findAllAttachments();
        return new ResponseEntity<String>(Attachment.toJsonArray(result), headers, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> AttachmentController.createFromJson(@RequestBody String json) {
        Attachment attachment = Attachment.fromJsonToAttachment(json);
        attachment.persist();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> AttachmentController.createFromJsonArray(@RequestBody String json) {
        for (Attachment attachment: Attachment.fromJsonArrayToAttachments(json)) {
            attachment.persist();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    
    @RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> AttachmentController.updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Attachment attachment = Attachment.fromJsonToAttachment(json);
        if (attachment.merge() == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> AttachmentController.updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (Attachment attachment: Attachment.fromJsonArrayToAttachments(json)) {
            if (attachment.merge() == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{PrimaryId}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> AttachmentController.deleteFromJson(@PathVariable("PrimaryId") String PrimaryId) {
        Attachment attachment = Attachment.findAttachment(PrimaryId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (attachment == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        attachment.remove();
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
    
}
