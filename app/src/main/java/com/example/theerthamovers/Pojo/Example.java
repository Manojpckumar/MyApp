package com.example.theerthamovers.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Example {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("statusinfocompleted")
    @Expose
    private List<Statusinfocompleted> statusinfocompleted = null;

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

    public List<Statusinfocompleted> getStatusinfocompleted() {
        return statusinfocompleted;
    }

    public void setStatusinfocompleted(List<Statusinfocompleted> statusinfocompleted) {
        this.statusinfocompleted = statusinfocompleted;
    }


}
