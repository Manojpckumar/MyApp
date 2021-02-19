package com.example.theerthamovers.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdminLoginPoJo {

    @SerializedName("username")
    @Expose
    String username;

    @SerializedName("mobile")
    @Expose
    String mobile;

    @SerializedName("email")
    @Expose
    String email;

    public AdminLoginPoJo(String username, String mobile, String email) {
        this.username = username;
        this.mobile = mobile;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
