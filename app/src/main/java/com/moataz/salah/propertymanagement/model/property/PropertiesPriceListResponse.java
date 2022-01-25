package com.moataz.salah.propertymanagement.model.property;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PropertiesPriceListResponse {
    @SerializedName("data")
    @Expose
    private List<PropertyPriceModel> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    public List<PropertyPriceModel> getData() {
        return data;
    }

    public void setData(List<PropertyPriceModel> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
