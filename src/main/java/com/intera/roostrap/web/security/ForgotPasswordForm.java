package com.intera.roostrap.web.security;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;

@RooJavaBean
@RooJson
public class ForgotPasswordForm {

    @NotNull
    @Email
    private String emailAddress;

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static ForgotPasswordForm fromJsonToForgotPasswordForm(String json) {
        return new JSONDeserializer<ForgotPasswordForm>().use(null, ForgotPasswordForm.class).deserialize(json);
    }

	public static String toJsonArray(Collection<ForgotPasswordForm> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<ForgotPasswordForm> fromJsonArrayToForgotPasswordForms(String json) {
        return new JSONDeserializer<List<ForgotPasswordForm>>().use(null, ArrayList.class).use("values", ForgotPasswordForm.class).deserialize(json);
    }

	public String getEmailAddress() {
        return this.emailAddress;
    }

	public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
