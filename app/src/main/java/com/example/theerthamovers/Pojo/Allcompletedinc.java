package com.example.theerthamovers.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Allcompletedinc {

    @SerializedName("total_work_amount")
    @Expose
    private Integer totalWorkAmount;
    @SerializedName("diesel")
    @Expose
    private Integer diesel;
    @SerializedName("food")
    @Expose
    private Integer food;
    @SerializedName("grease")
    @Expose
    private Integer grease;
    @SerializedName("salary")
    @Expose
    private Integer salary;
    @SerializedName("bata")
    @Expose
    private Integer bata;
    @SerializedName("transportation_charge")
    @Expose
    private Integer transportationCharge;
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
    @SerializedName("total_client_amount")
    @Expose
    private Integer totalClientAmount;

    public Integer getTotalWorkAmount() {
        return totalWorkAmount;
    }

    public void setTotalWorkAmount(Integer totalWorkAmount) {
        this.totalWorkAmount = totalWorkAmount;
    }

    public Integer getDiesel() {
        return diesel;
    }

    public void setDiesel(Integer diesel) {
        this.diesel = diesel;
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

    public Integer getTransportationCharge() {
        return transportationCharge;
    }

    public void setTransportationCharge(Integer transportationCharge) {
        this.transportationCharge = transportationCharge;
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

    public Integer getTotalClientAmount() {
        return totalClientAmount;
    }

    public void setTotalClientAmount(Integer totalClientAmount) {
        this.totalClientAmount = totalClientAmount;
    }

}
