package com.example.theerthamovers.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExampleGetApi {


    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("getapikey")
    @Expose
    private List<Getapikey> getapikey = null;

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

    public List<Getapikey> getGetapikey() {
        return getapikey;
    }

    public void setGetapikey(List<Getapikey> getapikey) {
        this.getapikey = getapikey;
    }

}
