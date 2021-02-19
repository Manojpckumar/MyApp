package com.example.theerthamovers.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Allcompletedbetweentask {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("driver_name")
    @Expose
    private String driverName;
    @SerializedName("vehicle_number")
    @Expose
    private String vehicleNumber;
    @SerializedName("model_number")
    @Expose
    private String modelNumber;
    @SerializedName("category_type")
    @Expose
    private String categoryType;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("start_kms")
    @Expose
    private Integer startKms;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_kms")
    @Expose
    private Integer endKms;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("diesel")
    @Expose
    private Integer diesel;
    @SerializedName("salary")
    @Expose
    private Integer salary;
    @SerializedName("bata")
    @Expose
    private Integer bata;
    @SerializedName("food")
    @Expose
    private Integer food;
    @SerializedName("grease")
    @Expose
    private Integer grease;
    @SerializedName("normal_oil")
    @Expose
    private Integer normalOil;
    @SerializedName("hydraulic_oil")
    @Expose
    private Integer hydraulicOil;
    @SerializedName("spare_parts")
    @Expose
    private Integer spareParts;
    @SerializedName("others")
    @Expose
    private Integer others;
    @SerializedName("work_date")
    @Expose
    private String workDate;

    @SerializedName("total_work_amount")
    @Expose
    private Integer totalWorkAmount;

    @SerializedName("total_client_amount")
    @Expose
    private Integer totalClientAmount;

    @SerializedName("respstatus")
    @Expose
    private String respstatus;

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

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getStartKms() {
        return startKms;
    }

    public void setStartKms(Integer startKms) {
        this.startKms = startKms;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Integer getEndKms() {
        return endKms;
    }

    public void setEndKms(Integer endKms) {
        this.endKms = endKms;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getDiesel() {
        return diesel;
    }

    public void setDiesel(Integer diesel) {
        this.diesel = diesel;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Integer getBata() {
        return bata;
    }

    public void setBata(Integer bata) {
        this.bata = bata;
    }

    public Integer getFood() {
        return food;
    }

    public void setFood(Integer food) {
        this.food = food;
    }

    public Integer getGrease() {
        return grease;
    }

    public void setGrease(Integer grease) {
        this.grease = grease;
    }

    public Integer getNormalOil() {
        return normalOil;
    }

    public void setNormalOil(Integer normalOil) {
        this.normalOil = normalOil;
    }

    public Integer getHydraulicOil() {
        return hydraulicOil;
    }

    public void setHydraulicOil(Integer hydraulicOil) {
        this.hydraulicOil = hydraulicOil;
    }

    public Integer getSpareParts() {
        return spareParts;
    }

    public void setSpareParts(Integer spareParts) {
        this.spareParts = spareParts;
    }

    public Integer getOthers() {
        return others;
    }

    public void setOthers(Integer others) {
        this.others = others;
    }

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }

    public Integer getTotalWorkAmount() {
        return totalWorkAmount;
    }

    public void setTotalWorkAmount(Integer totalWorkAmount) {
        this.totalWorkAmount = totalWorkAmount;
    }

    public String getRespstatus() {
        return respstatus;
    }

    public void setRespstatus(String respstatus) {
        this.respstatus = respstatus;
    }

    public Integer getTotalClientAmount() {
        return totalClientAmount;
    }

    public void setTotalClientAmount(Integer totalClientAmount) {
        this.totalClientAmount = totalClientAmount;
    }
}
