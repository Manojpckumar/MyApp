package com.example.theerthamovers.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExampleAddop {


    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("operators")
    @Expose
    private Operators operators;

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

    public Operators getOperators() {
        return operators;
    }

    public void setOperators(Operators operators) {
        this.operators = operators;
    }

}
