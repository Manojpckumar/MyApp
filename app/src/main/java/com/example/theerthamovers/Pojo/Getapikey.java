package com.example.theerthamovers.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Getapikey {

    @SerializedName("getapikey")
    @Expose
    private String getapikey;

    public String getGetapikey() {
        return getapikey;
    }

    public void setGetapikey(String getapikey) {
        this.getapikey = getapikey;
    }
}
