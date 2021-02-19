package com.example.theerthamovers.Pojo;

public class IncomeByDatePojo {
    String amount,category,drawable;

    public IncomeByDatePojo(String amount, String category, String drawable) {
        this.amount = amount;
        this.category = category;
        this.drawable = drawable;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDrawable() {
        return drawable;
    }

    public void setDrawable(String drawable) {
        this.drawable = drawable;
    }
}
