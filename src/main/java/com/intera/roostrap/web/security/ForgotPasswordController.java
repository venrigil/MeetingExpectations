package com.intera.roostrap.web.security;

import static com.intera.roostrap.util.EncryptionUtil.decrypt;
import static com.intera.roostrap.util.EncryptionUtil.encrypt;
import com.intera.roostrap.domain.security.User;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.util.Calendar;
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
@RequestMapping("/forgot")
@Controller
public class ForgotPasswordController {

	@Autowired
	private transient MailSender mailSender;

	@Autowired
	@Qualifier("accountRecoveryMailTemplate")
	private SimpleMailMessage mailTemplate;

	private String encryptionKey = "miN6CHar4cters!";

	private int hoursToExpire = 24 * 2;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@RequestMapping
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new ForgotPasswordForm());
		return "forgot_password/form";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String submit(
			@Valid @ModelAttribute("form") ForgotPasswordForm form,
			BindingResult bindingResult, Model uiModel)
			throws UnsupportedEncodingException, InvalidKeyException {

		if (!bindingResult.hasErrors()) {
			TypedQuery<User> userQuery = User.findUsersByEmailAddress(form
					.getEmailAddress());
			try {
				User user = userQuery.getSingleResult();

				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.HOUR, hoursToExpire);

				String key = encrypt(encryptionKey, form.getEmailAddress()
						+ ":" + cal.getTimeInMillis());
				try {
					sendAccountRecoveryRequestMail(user, key);
					 } catch(IOException ie) {
			                ie.printStackTrace();
			            }
				
				return "redirect:/forgot/thanks";

			} catch (EmptyResultDataAccessException e) {
				bindingResult.rejectValue("emailAddress",
						"field_email_unregistered");

			}

		}

		populateEditForm(uiModel, form);
		return "forgot_password/form";
	}

	@RequestMapping(value = "/set", params = "k")
	public String createFormSetNewPassword(
			@RequestParam(value = "k") String key, Model uiModel) {
		populateEditForm(uiModel, new NewPasswordForm());
		return "forgot_password/set_new";
	}

	@RequestMapping(value = "/set", params = "k", method = RequestMethod.POST)
	public String submitSetNewPassword(@RequestParam(value = "k") String key,
			@Valid @ModelAttribute("form") NewPasswordForm form,
			BindingResult bindingResult, Model uiModel) {

		if (!form.getNewPassword().equals(form.getRetypeNewPassword())) {
			bindingResult.rejectValue("newPassword", "field_password_mismatch");
		}

		if (!bindingResult.hasErrors()) {
			String decrypted;
			try {
				decrypted = decrypt(encryptionKey, key);
			} catch (InvalidKeyException e) {
				throw new RuntimeException("Invalid encryption key!", e);
			}

			String[] splits = decrypted.split(":");
			String email = splits[0];
			long expireTime = Long.parseLong(splits[1]);

			TypedQuery<User> userQuery = User.findUsersByEmailAddress(email);

			try {
				User user = userQuery.getSingleResult();

				if (System.currentTimeMillis() < expireTime) {
					user.setPassword(passwordEncoder.encode(form
							.getNewPassword()));
					user.merge();
					return "redirect:/login";
				} else {
					bindingResult.reject("error_key_expired");
				}

			} catch (EmptyResultDataAccessException e) {
				bindingResult.reject("field_email_unregistered");
			}

		}

		
		
		populateEditForm(uiModel, form);
		return "forgot_password/set_new";
	}

	@RequestMapping(value = "/thanks")
	public String thanks() {
		return "forgot_password/thanks";
	}

	private void populateEditForm(Model uiModel, ForgotPasswordForm form) {
		uiModel.addAttribute("form", form);
	}

	private void populateEditForm(Model uiModel, NewPasswordForm form) {
		uiModel.addAttribute("form", form);
	}

	/**
	private void sendAccountRecoveryRequestMail(User user, String key)
			throws UnsupportedEncodingException {
		SimpleMailMessage mailMessage = new SimpleMailMessage(mailTemplate);
		mailMessage.setTo(user.getEmailAddress());

		mailMessage.setText(mailMessage.getText()
				.replace("{{FIRST_NAME}}", user.getFirstName())
				.replace("{{LAST_NAME}}", user.getLastName())
				.replace("{{KEY}}", URLEncoder.encode(key, "UTF-8")));

		mailSender.send(mailMessage);
	}

**/
	
	private void sendAccountRecoveryRequestMail(User user, String key)
	/**
			throws UnsupportedEncodingException {
		SimpleMailMessage mailMessage = new SimpleMailMessage(mailTemplate);
		mailMessage.setTo(user.getEmailAddress());

		mailMessage.setText(mailMessage.getText()
				.replace("{{FIRST_NAME}}", user.getFirstName())
				.replace("{{LAST_NAME}}", user.getLastName())
				.replace("{{KEY}}", URLEncoder.encode(key, "UTF-8")));

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

	
	/* Accessors************************************************************* */

	public void setHoursToExpire(int hoursToExpire) {
		this.hoursToExpire = hoursToExpire;
	}

	public void setEncryptionKey(String encryptionKey) {
		this.encryptionKey = encryptionKey;
	}

}

