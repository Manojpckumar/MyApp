package com.example.theerthamovers.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterDriver {

    @SerializedName("username")
    @Expose
    String username;

    @SerializedName("mobile")
    @Expose
    String mobile;

    @SerializedName("email")
    @Expose
    String email;

    @SerializedName("password")
    @Expose
    String password;

    public RegisterDriver(String uname, String umobile, String uemail, String upass) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
