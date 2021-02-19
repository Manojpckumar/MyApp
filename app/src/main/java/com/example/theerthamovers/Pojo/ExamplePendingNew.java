package com.example.theerthamovers.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExamplePendingNew {


    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Allassignedtaskss")
    @Expose
    private List<AllAssignedTasksNew> allassignedtaskss = null;

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

    public List<AllAssignedTasksNew> getAllassignedtaskss() {
        return allassignedtaskss;
    }

    public void setAllassignedtaskss(List<AllAssignedTasksNew> allassignedtaskss) {
        this.allassignedtaskss = allassignedtaskss;
    }

}
