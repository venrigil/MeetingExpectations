package com.intera.roostrap.web;

import com.intera.roostrap.web.security.ChangePasswordForm;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebJson(jsonObject = ChangePasswordForm.class)
@Controller
@RequestMapping("/changepasswordforms")
public class ChangePasswordFormController {
}
