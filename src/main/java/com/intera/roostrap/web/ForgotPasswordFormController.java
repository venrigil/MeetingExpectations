package com.intera.roostrap.web;

import com.intera.roostrap.web.security.ForgotPasswordForm;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebJson(jsonObject = ForgotPasswordForm.class)
@Controller
@RequestMapping("/forgotpasswordforms")
public class ForgotPasswordFormController {
}
