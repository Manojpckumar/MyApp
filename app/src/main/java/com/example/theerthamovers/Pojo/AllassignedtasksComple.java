package com.example.theerthamovers.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllassignedtasksComple {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("driver_name")
    @Expose
    private String driverName;
    @SerializedName("vehicle_name")
    @Expose
    private String vehicleName;
    @SerializedName("vehicle_number")
    @Expose
    private String vehicleNumber;
    @SerializedName("model_no")
    @Expose
    private String modelNo;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("work_location")
    @Expose
    private String workLocation;
    @SerializedName("work_date")
    @Expose
    private String workDate;
    @SerializedName("started_response")
    @Expose
    private String startedResponse;
    @SerializedName("phaseid")
    @Expose
    private Integer phaseid;
    @SerializedName("bill_status")
    @Expose
    private String billStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
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

    public String getWorkLocation() {
        return workLocation;
    }

    public void setWorkLocation(String workLocation) {
        this.workLocation = workLocation;
    }

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }

    public String getStartedResponse() {
        return startedResponse;
    }

    public void setStartedResponse(String startedResponse) {
        this.startedResponse = startedResponse;
    }

    public Integer getPhaseid() {
        return phaseid;
    }

    public void setPhaseid(Integer phaseid) {
        this.phaseid = phaseid;
    }

    public String getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus;
    }

}
