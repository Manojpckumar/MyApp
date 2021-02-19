package com.example.theerthamovers.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExampleStartedNew {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Allassignedtasksss")
    @Expose
    private List<AllassignedtasksStarted> allassignedtasksss = null;

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

    public List<AllassignedtasksStarted> getAllassignedtasksss() {
        return allassignedtasksss;
    }

    public void setAllassignedtasksss(List<AllassignedtasksStarted> allassignedtasksss) {
        this.allassignedtasksss = allassignedtasksss;
    }
}
