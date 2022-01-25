package com.moataz.salah.propertymanagement.model.property;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PropertyResponse {
    @SerializedName("data")
    @Expose
    private List<PropertyModel> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    public List<PropertyModel> getData() {
        return data;
    }

    public void setData(List<PropertyModel> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
