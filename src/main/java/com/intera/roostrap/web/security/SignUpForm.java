package com.intera.roostrap.web.security;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaFactory;
import net.tanesha.recaptcha.ReCaptchaResponse;
import org.hibernate.validator.constraints.Email;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;

@RooJavaBean
@RooJson
public class SignUpForm {

    @NotNull
    @Size(min = 1, max = 100)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 100)
    private String lastName;

    @NotNull
    @Size(min = 1, max = 100)
    @Email
    private String emailAddress;

    @NotNull
    @Size(min = 5, max = 100)
    private String password;

    private String retypePassword;

    private String recaptchaChallenge;

    private String recaptchaResponse;

    private ReCaptcha reCatcpha;

    private String reCaptchaErrorMessage;

    public SignUpForm() {
        reCatcpha = ReCaptchaFactory.newReCaptcha("6LdfmL8SAAAAAFnT0l3UNPOV8mkpHIown-ysSR1g", "6LdfmL8SAAAAAHKPqUQV5SxrRX9Id6a8cQo-mgpE", false);
    }

    public void setRecaptcha_challenge_field(String recaptchaChallenge) {
        this.recaptchaChallenge = recaptchaChallenge;
    }

    public void setRecaptcha_response_field(String recaptchaResponse) {
        this.recaptchaResponse = recaptchaResponse;
    }

    public String getReCaptchaHtml() {
        return reCatcpha.createRecaptchaHtml(reCaptchaErrorMessage, null);
    }

    @AssertTrue(message = "Wrong captcha")
    public boolean isValidCaptcha() {
        ReCaptchaResponse reCaptchaResponse = reCatcpha.checkAnswer("localhost", getRecaptchaChallenge(), getRecaptchaResponse());
        boolean result = reCaptchaResponse.isValid();
        if (!result) {
            reCaptchaErrorMessage = "Wrong captcha answer";
        }
        return result;
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static SignUpForm fromJsonToSignUpForm(String json) {
        return new JSONDeserializer<SignUpForm>().use(null, SignUpForm.class).deserialize(json);
    }

	public static String toJsonArray(Collection<SignUpForm> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<SignUpForm> fromJsonArrayToSignUpForms(String json) {
        return new JSONDeserializer<List<SignUpForm>>().use(null, ArrayList.class).use("values", SignUpForm.class).deserialize(json);
    }

	public String getFirstName() {
        return this.firstName;
    }

	public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

	public String getLastName() {
        return this.lastName;
    }

	public void setLastName(String lastName) {
        this.lastName = lastName;
    }

	public String getEmailAddress() {
        return this.emailAddress;
    }

	public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

	public String getPassword() {
        return this.password;
    }

	public void setPassword(String password) {
        this.password = password;
    }

	public String getRetypePassword() {
        return this.retypePassword;
    }

	public void setRetypePassword(String retypePassword) {
        this.retypePassword = retypePassword;
    }

	public String getRecaptchaChallenge() {
        return this.recaptchaChallenge;
    }

	public void setRecaptchaChallenge(String recaptchaChallenge) {
        this.recaptchaChallenge = recaptchaChallenge;
    }

	public String getRecaptchaResponse() {
        return this.recaptchaResponse;
    }

	public void setRecaptchaResponse(String recaptchaResponse) {
        this.recaptchaResponse = recaptchaResponse;
    }

	public ReCaptcha getReCatcpha() {
        return this.reCatcpha;
    }

	public void setReCatcpha(ReCaptcha reCatcpha) {
        this.reCatcpha = reCatcpha;
    }

	public String getReCaptchaErrorMessage() {
        return this.reCaptchaErrorMessage;
    }

	public void setReCaptchaErrorMessage(String reCaptchaErrorMessage) {
        this.reCaptchaErrorMessage = reCaptchaErrorMessage;
    }
}
