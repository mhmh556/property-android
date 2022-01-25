package com.moataz.salah.propertymanagement.model.price;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddPriceResponse {
    @SerializedName("data")
    @Expose
    private List<PriceModel> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    public List<PriceModel> getData() {
        return data;
    }

    public void setData(List<PriceModel> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
