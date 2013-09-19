package com.intera.roostrap.web.security;

import com.intera.roostrap.domain.security.User;

import javax.persistence.TypedQuery;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author '<a href="mailto:ichsan@gmail.com">Muhammad Ichsan</a>'
 *
 */
@RequestMapping("/passwd")
@Controller
public class ChangePasswordController {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@RequestMapping
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new ChangePasswordForm());
		return "change_password/form";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String submit(
			@Valid @ModelAttribute("form") ChangePasswordForm form,
			BindingResult bindingResult, Model uiModel) {
		validateMore(form, bindingResult);

		if (!bindingResult.hasErrors()) {
			UserDetails userDetails = (UserDetails) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			String newPassword = form.getNewPassword();
			TypedQuery<User> query = User.findUsersByEmailAddress(userDetails
					.getUsername());

			try {
				User user = query.getSingleResult();

				if (passwordEncoder.matches(form.getOldPassword(),
						user.getPassword())) {
					user.setPassword(passwordEncoder.encode(newPassword));
					user.merge();
					return "redirect:/passwd/thanks";

				} else {
					bindingResult.rejectValue("oldPassword",
							"field_invalid_password");
				}
			} catch (EmptyResultDataAccessException e) {
				bindingResult
						.rejectValue("emailAddress", "error_no_such_email");
			}
		}

		populateEditForm(uiModel, form);
		return "change_password/form";
	}

	@RequestMapping("/thanks")
	public String thanks() {
		return "change_password/thanks";
	}

	private void validateMore(ChangePasswordForm form,
			BindingResult bindingResult) {

		if (form.getNewPassword() != null
				&& !form.getNewPassword().equals(form.getRetypeNewPassword())) {
			bindingResult.rejectValue("newPassword", "field_password_mismatch");
		}
	}

	private void populateEditForm(Model uiModel, ChangePasswordForm form) {
		uiModel.addAttribute("form", form);
	}
}
