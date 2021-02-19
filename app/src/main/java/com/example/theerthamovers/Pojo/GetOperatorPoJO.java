package com.example.theerthamovers.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetOperatorPoJO {

    @SerializedName("username")
    @Expose
    String username;

    @SerializedName("mobile")
    @Expose
    String mobile;

    @SerializedName("email")
    @Expose
    String email;



    public GetOperatorPoJO() {
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
