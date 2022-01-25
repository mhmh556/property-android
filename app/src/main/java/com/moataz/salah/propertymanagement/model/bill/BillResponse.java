package com.moataz.salah.propertymanagement.model.bill;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BillResponse {
    @SerializedName("data")
    @Expose
    private List<BillModel> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    public List<BillModel> getData() {
        return data;
    }

    public void setData(List<BillModel> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
