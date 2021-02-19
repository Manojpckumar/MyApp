package com.example.theerthamovers.Pojo;

public class Vehicle {


    String Point_Time;
    String Vehicle_number;
    String Category;
    String Model_no;

    public Vehicle(String vehicle_number, String category, String model_no,String point_time) {
        Vehicle_number = vehicle_number;
        Category = category;
        Model_no = model_no;
        Point_Time = point_time;
    }

    public String getPoint_Time() {
        return Point_Time;
    }

    public void setPoint_Time(String point_Time) {
        Point_Time = point_Time;
    }

    public String getVehicle_number() {
        return Vehicle_number;
    }

    public void setVehicle_number(String vehicle_number) {
        Vehicle_number = vehicle_number;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getModel_no() {
        return Model_no;
    }

    public void setModel_no(String model_no) {
        Model_no = model_no;
    }

}
