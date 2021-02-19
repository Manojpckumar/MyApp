package com.example.theerthamovers.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExampleSumByMonth {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Allcompletedinc")
    @Expose
    private List<Allcompletedinc> allcompletedinc = null;

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

    public List<Allcompletedinc> getAllcompletedinc() {
        return allcompletedinc;
    }

    public void setAllcompletedinc(List<Allcompletedinc> allcompletedinc) {
        this.allcompletedinc = allcompletedinc;
    }


}
