package com.example.theerthamovers.Pojo;

import android.content.Context;

import java.util.List;

public class GetCompletedTaskOperator {

public String driver_name,vehicle_name,vehicle_num,model_no,category,work_loc,work_date,st_response,phase_id;
int id;

    public GetCompletedTaskOperator() {
    }

    public GetCompletedTaskOperator(int id, String driver_name, String vehicle_name, String vehicle_num, String model_no, String category, String work_loc, String work_date, String st_response, String phase_id) {
        this.id = id;
        this.driver_name = driver_name;
        this.vehicle_name = vehicle_name;
        this.vehicle_num = vehicle_num;
        this.model_no = model_no;
        this.category = category;
        this.work_loc = work_loc;
        this.work_date = work_date;
        this.st_response = st_response;
        this.phase_id = phase_id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getVehicle_name() {
        return vehicle_name;
    }

    public void setVehicle_name(String vehicle_name) {
        this.vehicle_name = vehicle_name;
    }

    public String getVehicle_num() {
        return vehicle_num;
    }

    public void setVehicle_num(String vehicle_num) {
        this.vehicle_num = vehicle_num;
    }

    public String getModel_no() {
        return model_no;
    }

    public void setModel_no(String model_no) {
        this.model_no = model_no;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getPhase_id() {
        return phase_id;
    }

    public void setPhase_id(String phase_id) {
        this.phase_id = phase_id;
    }
}
