package com.example.theerthamovers.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Veh {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("vehicle_name")
    @Expose
    private String vehicleName;
    @SerializedName("model_no")
    @Expose
    private String modelNo;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("point_time")
    @Expose
    private Integer pointTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getPointTime() {
        return pointTime;
    }

    public void setPointTime(Integer pointTime) {
        this.pointTime = pointTime;
    }
}
