package com.example.theerthamovers.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExampleVehicleNumber {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Allvehiclenumber")
    @Expose
    private List<AllVehicleNumber> allvehiclenumber = null;

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

    public List<AllVehicleNumber> getAllvehiclenumber() {
        return allvehiclenumber;
    }

    public void setAllvehiclenumber(List<AllVehicleNumber> allvehiclenumber) {
        this.allvehiclenumber = allvehiclenumber;
    }
}
