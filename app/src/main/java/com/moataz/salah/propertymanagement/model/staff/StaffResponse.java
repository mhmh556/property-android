package com.moataz.salah.propertymanagement.model.staff;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StaffResponse {
    @SerializedName("data")
    @Expose
    private List<StaffModel> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    public List<StaffModel> getData() {
        return data;
    }

    public void setData(List<StaffModel> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
