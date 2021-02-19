package com.example.theerthamovers.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExampleCompletedNew {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Allassignedtaskssss")
    @Expose
    private List<AllassignedtasksComple> allassignedtasksssses = null;

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

    public List<AllassignedtasksComple> getAllassignedtasksssses() {
        return allassignedtasksssses;
    }

    public void setAllassignedtasksssses(List<AllassignedtasksComple> allassignedtasksssses) {
        this.allassignedtasksssses = allassignedtasksssses;
    }

}
