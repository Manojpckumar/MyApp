package com.example.theerthamovers.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExampleAllOperator {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Alloperators")
    @Expose
    private List<Alloperator> alloperators = null;

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

    public List<Alloperator> getAlloperators() {
        return alloperators;
    }

    public void setAlloperators(List<Alloperator> alloperators) {
        this.alloperators = alloperators;
    }

}
