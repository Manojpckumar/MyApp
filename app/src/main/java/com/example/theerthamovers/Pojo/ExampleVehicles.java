package com.example.theerthamovers.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExampleVehicles {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Getvehicles")
    @Expose
    private List<Getvehicle> getvehicles = null;

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

    public List<Getvehicle> getGetvehicles() {
        return getvehicles;
    }

    public void setGetvehicles(List<Getvehicle> getvehicles) {
        this.getvehicles = getvehicles;
    }
}
