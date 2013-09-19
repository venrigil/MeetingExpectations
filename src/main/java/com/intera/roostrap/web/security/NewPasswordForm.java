package com.intera.roostrap.web.security;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;

@RooJavaBean
@RooJson
public class NewPasswordForm {

    @NotNull
    @Size(min = 5)
    private String newPassword;

    private String retypeNewPassword;

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static NewPasswordForm fromJsonToNewPasswordForm(String json) {
        return new JSONDeserializer<NewPasswordForm>().use(null, NewPasswordForm.class).deserialize(json);
    }

	public static String toJsonArray(Collection<NewPasswordForm> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<NewPasswordForm> fromJsonArrayToNewPasswordForms(String json) {
        return new JSONDeserializer<List<NewPasswordForm>>().use(null, ArrayList.class).use("values", NewPasswordForm.class).deserialize(json);
    }

	public String getNewPassword() {
        return this.newPassword;
    }

	public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

	public String getRetypeNewPassword() {
        return this.retypeNewPassword;
    }

	public void setRetypeNewPassword(String retypeNewPassword) {
        this.retypeNewPassword = retypeNewPassword;
    }
}
