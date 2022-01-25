package com.moataz.salah.propertymanagement.model.electrical;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ElectricalResponse {
    @SerializedName("data")
    @Expose
    private List<ElectricalModel> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    public List<ElectricalModel> getData() {
        return data;
    }

    public void setData(List<ElectricalModel> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
