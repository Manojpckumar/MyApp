package com.example.theerthamovers.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExamplePasswordVisibility {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("passwordvisibility")
    @Expose
    private List<Passwordvisibility> passwordvisibility = null;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Passwordvisibility> getPasswordvisibility() {
        return passwordvisibility;
    }

    public void setPasswordvisibility(List<Passwordvisibility> passwordvisibility) {
        this.passwordvisibility = passwordvisibility;
    }

}
