package com.example.theerthamovers.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddNewVehicle {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("veh")
    @Expose
    private Veh veh;

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

    public Veh getVeh() {
        return veh;
    }

    public void setVeh(Veh veh) {
        this.veh = veh;
    }
}
