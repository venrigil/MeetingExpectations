package com.intera.roostrap.web;

import com.intera.roostrap.domain.Attachment;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/attachments")
@Controller
@RooWebScaffold(path = "attachments", formBackingObject = Attachment.class)
@RooWebJson(jsonObject = Attachment.class)
public class AttachmentController {
}
