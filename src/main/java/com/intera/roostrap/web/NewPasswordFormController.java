package com.intera.roostrap.web;

import com.intera.roostrap.web.security.NewPasswordForm;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebJson(jsonObject = NewPasswordForm.class)
@Controller
@RequestMapping("/newpasswordforms")
public class NewPasswordFormController {
}
