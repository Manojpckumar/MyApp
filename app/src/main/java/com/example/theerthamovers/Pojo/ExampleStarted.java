package com.example.theerthamovers.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExampleStarted {


    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("statusinfostarted")
    @Expose
    private List<StartedStatusInfo> statusinfostarted = null;

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

    public List<StartedStatusInfo> getStatusinfostarted() {
        return statusinfostarted;
    }

    public void setStatusinfostarted(List<StartedStatusInfo> statusinfostarted) {
        this.statusinfostarted = statusinfostarted;
    }
}
