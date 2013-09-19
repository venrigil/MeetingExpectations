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
public class ChangePasswordForm {

    @NotNull
    private String oldPassword;

    @NotNull
    @Size(min = 5, max = 100)
    private String newPassword;

    private String retypeNewPassword;

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static ChangePasswordForm fromJsonToChangePasswordForm(String json) {
        return new JSONDeserializer<ChangePasswordForm>().use(null, ChangePasswordForm.class).deserialize(json);
    }

	public static String toJsonArray(Collection<ChangePasswordForm> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<ChangePasswordForm> fromJsonArrayToChangePasswordForms(String json) {
        return new JSONDeserializer<List<ChangePasswordForm>>().use(null, ArrayList.class).use("values", ChangePasswordForm.class).deserialize(json);
    }

	public String getOldPassword() {
        return this.oldPassword;
    }

	public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
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
