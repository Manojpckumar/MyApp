package com.example.theerthamovers.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExampleMobile {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Adminmobile")
    @Expose
    private List<Adminmobile> adminmobile = null;

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

    public List<Adminmobile> getAdminmobile() {
        return adminmobile;
    }

    public void setAdminmobile(List<Adminmobile> adminmobile) {
        this.adminmobile = adminmobile;
    }
}
