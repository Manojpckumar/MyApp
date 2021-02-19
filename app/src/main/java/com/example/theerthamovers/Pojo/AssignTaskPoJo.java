package com.example.theerthamovers.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssignTaskPoJo {

    @SerializedName("driver_name")
    @Expose
    String opname;

    @SerializedName("vehicle_name")
    @Expose
    String vehname;

    @SerializedName("vehicle_number")
    @Expose
    String vehnum;

    @SerializedName("model_no")
    @Expose
    String vehmodel;

    @SerializedName("category")
    @Expose
    String vehcate;

    @SerializedName("work_location")
    @Expose
    String work_loc;

    @SerializedName("work_date")
    @Expose
    String work_date;

    @SerializedName("started_response")
    @Expose
    String st_response;

    public String getOpname() {
        return opname;
    }

    public void setOpname(String opname) {
        this.opname = opname;
    }

    public String getVehname() {
        return vehname;
    }

    public void setVehname(String vehname) {
        this.vehname = vehname;
    }

    public String getVehnum() {
        return vehnum;
    }

    public void setVehnum(String vehnum) {
        this.vehnum = vehnum;
    }

    public String getVehmodel() {
        return vehmodel;
    }

    public void setVehmodel(String vehmodel) {
        this.vehmodel = vehmodel;
    }

    public String getVehcate() {
        return vehcate;
    }

    public void setVehcate(String vehcate) {
        this.vehcate = vehcate;
    }

    public String getWork_loc() {
        return work_loc;
    }

    public void setWork_loc(String work_loc) {
        this.work_loc = work_loc;
    }

    public String getWork_date() {
        return work_date;
    }

    public void setWork_date(String work_date) {
        this.work_date = work_date;
    }

    public String getSt_response() {
        return st_response;
    }

    public void setSt_response(String st_response) {
        this.st_response = st_response;
    }
}
