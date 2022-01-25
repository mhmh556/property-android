package com.moataz.salah.propertymanagement.model.apt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AptTypeResponse {
    @SerializedName("data")
    @Expose
    private List<AptTypeModel> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    public List<AptTypeModel> getData() {
        return data;
    }

    public void setData(List<AptTypeModel> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
