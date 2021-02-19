package com.example.theerthamovers.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Passwordvisibility {

    @SerializedName("data_status")
    @Expose
    private String dataStatus;

    public String getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus;
    }

}
