package com.example.theerthamovers.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllVehicle {

    @SerializedName("0")
    @Expose
    private String _0;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("1")
    @Expose
    private String _1;
    @SerializedName("vehicle_name")
    @Expose
    private String vehicleName;
    @SerializedName("2")
    @Expose
    private String _2;
    @SerializedName("vehicle_number")
    @Expose
    private String vehicleNumber;
    @SerializedName("3")
    @Expose
    private String _3;
    @SerializedName("model_no")
    @Expose
    private String modelNo;
    @SerializedName("4")
    @Expose
    private String _4;
    @SerializedName("category")
    @Expose
    private String category;

    public String get0() {
        return _0;
    }

    public void set0(String _0) {
        this._0 = _0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String get1() {
        return _1;
    }

    public void set1(String _1) {
        this._1 = _1;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String get2() {
        return _2;
    }

    public void set2(String _2) {
        this._2 = _2;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String get3() {
        return _3;
    }

    public void set3(String _3) {
        this._3 = _3;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public String get4() {
        return _4;
    }

    public void set4(String _4) {
        this._4 = _4;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
