package com.intera.roostrap.web.security;

import com.intera.roostrap.domain.security.User;

import java.util.Date;
import java.io.IOException;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.persistence.TypedQuery;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author '<a href="mailto:ichsan@gmail.com">Muhammad Ichsan</a>'
 *
 */
@RequestMapping("/signup")
@Controller
public class SignUpController {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private transient MailSender mailSender;

	@Autowired
	@Qualifier("accountActivationMailTemplate")
	private SimpleMailMessage mailTemplate;

	private boolean isAccountAutoEnabled;

	
	@RequestMapping(method = RequestMethod.GET)
	public String createSignUpForm(Model uiModel) {
		populateEditForm(uiModel, new SignUpForm());
		return "signup/form";
	}

	
	@RequestMapping(method = RequestMethod.POST)
	public String submitSignUpForm(
			@Valid @ModelAttribute("form") SignUpForm form,
			BindingResult bindingResult, Model uiModel) {

		validateMore(form, bindingResult);

		if (!bindingResult.hasErrors()) {
			User user = new User();
			user.setActivationDate(null);
			user.setEmailAddress(form.getEmailAddress());
			user.setFirstName(form.getFirstName());
			user.setLastName(form.getLastName());
			user.setPassword(passwordEncoder.encode(form.getPassword()));
			user.setEnabled(false);
			user.setLocked(false);
			user.persist();

			String activationKey = createActivationKey(user);

			if (isAccountAutoEnabled) {
				try {
				sendActivationMail(activationKey, user);
				 } catch(IOException ie) {
		                ie.printStackTrace();
		            }
			}

			return "redirect:/signup/thanks";
		}

		populateEditForm(uiModel, form);
		return "signup/form";
	}

	private void validateMore(SignUpForm form, BindingResult bindingResult) {
		if (form.getPassword() != null
				&& !form.getPassword().equals(form.getRetypePassword())) {
			bindingResult.rejectValue("password", "field_password_mismatch");
		}
	}

	
	@RequestMapping(params = "k", method = RequestMethod.GET)
	public String activateUser(
			@RequestParam(value = "k", required = true) String activationKey,
			@RequestParam(value = "e", required = true) String emailAddress,
			Model uiModel) {
		TypedQuery<User> query = User.findUsersByEmailAddress(emailAddress);

		try {
			User user = query.getSingleResult();

			if (createActivationKey(user).equals(activationKey)) {
				user.setActivationDate(new Date());
				user.setEnabled(true);
				user.merge();
				return "redirect:/login";
			}

		} catch (EmptyResultDataAccessException e) {
			
		}

		return "signup/error";
	}

	@RequestMapping("/thanks")
	public String thanks(Model uiModel) {
		uiModel.addAttribute("accountAutoEnabled", isAccountAutoEnabled);
		return "signup/thanks";
	}

	private void sendActivationMail(String activationKey, User user) 
		
		/**
		 * {
		SimpleMailMessage mailMessage = new SimpleMailMessage(
				mailTemplate);
		mailMessage.setTo(user.getEmailAddress());

		mailMessage.setText(mailMessage.getText()
				.replace("{{FIRST_NAME}}", user.getFirstName())
				.replace("{{LAST_NAME}}", user.getLastName())
				.replace("{{EMAIL_ADDRESS}}", user.getEmailAddress())
				.replace("{{ACTIVATION_KEY}}", activationKey));

		mailSender.send(mailMessage);
	
		**/
	    throws IOException 
	    	{
	    	Properties props = new Properties();
	    	Session session = Session.getDefaultInstance(props, null);
	    	try {
	    		Message msg = new MimeMessage(session);
		
	    		
	    		
	    		
	    		msg.addRecipient(Message.RecipientType.TO,
	    				new InternetAddress( "venkata@rigil.com"));
	    		
	    		
	    		
	    			
	    			
	    		
	    		msg.setSubject("Mail Activation");
	    		/**
	    		 * msg.setText( msg.getText()
	    		 
	    				.replace("{{FIRST_NAME}}", user.getFirstName())
	    				.replace("{{LAST_NAME}}", user.getLastName())
	    				.replace("{{EMAIL_ADDRESS}}", user.getEmailAddress())
	    				.replace("{{ACTIVATION_KEY}}", activationKey));
	    				*/
	    		
	    		Transport.send(msg);
	    	} 	catch (AddressException addressException) {
	    		
	    	}
	    	catch (MessagingException messageException) {
	    		
	    	}
	}

	private String createActivationKey(User user) {
		return user.getPassword().substring(0, 5);
	}

	private void populateEditForm(Model uiModel, SignUpForm form) {
		uiModel.addAttribute("form", form);
		uiModel.addAttribute("captcha_form", form.getReCaptchaHtml());
	}

	/* Accessors ************************************************************ */

	public void setAccountAutoEnabled(boolean isAccountAutoEnabled) {
		this.isAccountAutoEnabled = isAccountAutoEnabled;
	}
}
