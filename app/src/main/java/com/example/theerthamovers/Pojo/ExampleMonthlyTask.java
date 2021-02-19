package com.example.theerthamovers.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExampleMonthlyTask {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Allcompletedbetweentasks")
    @Expose
    private List<Allcompletedbetweentask> allcompletedbetweentasks = null;

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

    public List<Allcompletedbetweentask> getAllcompletedbetweentasks() {
        return allcompletedbetweentasks;
    }

    public void setAllcompletedbetweentasks(List<Allcompletedbetweentask> allcompletedbetweentasks) {
        this.allcompletedbetweentasks = allcompletedbetweentasks;
    }

}
