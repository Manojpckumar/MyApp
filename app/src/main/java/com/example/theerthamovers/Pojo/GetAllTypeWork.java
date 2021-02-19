package com.example.theerthamovers.Pojo;

public class GetAllTypeWork {

    int id;
    String drivername,veh_name,veh_no,model_no,work_loc,work_date,cate,phid,billstatus;

    public GetAllTypeWork() {
    }

    public GetAllTypeWork(int id, String drivername, String veh_name, String veh_no, String model_no, String work_loc, String work_date, String cate, String phid, String billstatus) {
        this.id = id;
        this.drivername = drivername;
        this.veh_name = veh_name;
        this.veh_no = veh_no;
        this.model_no = model_no;
        this.work_loc = work_loc;
        this.work_date = work_date;
        this.cate = cate;
        this.phid = phid;
        this.billstatus = billstatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDrivername() {
        return drivername;
    }

    public void setDrivername(String drivername) {
        this.drivername = drivername;
    }

    public String getVeh_name() {
        return veh_name;
    }

    public void setVeh_name(String veh_name) {
        this.veh_name = veh_name;
    }

    public String getVeh_no() {
        return veh_no;
    }

    public void setVeh_no(String veh_no) {
        this.veh_no = veh_no;
    }

    public String getModel_no() {
        return model_no;
    }

    public void setModel_no(String model_no) {
        this.model_no = model_no;
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

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public String getPhid() {
        return phid;
    }

    public void setPhid(String phid) {
        this.phid = phid;
    }

    public String getBillstatus() {
        return billstatus;
    }

    public void setBillstatus(String billstatus) {
        this.billstatus = billstatus;
    }
}
