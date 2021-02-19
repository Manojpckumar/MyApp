package com.example.theerthamovers.Pojo;

public class MonthlyReportPojo {

    String workdate,totalamount,vehnum,operatorname,location;

    public MonthlyReportPojo() {
    }

    public MonthlyReportPojo(String workdate, String totalamount, String vehnum, String operatorname, String location) {
        this.workdate = workdate;
        this.totalamount = totalamount;
        this.vehnum = vehnum;
        this.operatorname = operatorname;
        this.location = location;
    }


    public String getWorkdate() {
        return workdate;
    }

    public void setWorkdate(String workdate) {
        this.workdate = workdate;
    }

    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }

    public String getVehnum() {
        return vehnum;
    }

    public void setVehnum(String vehnum) {
        this.vehnum = vehnum;
    }

    public String getOperatorname() {
        return operatorname;
    }

    public void setOperatorname(String operatorname) {
        this.operatorname = operatorname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
