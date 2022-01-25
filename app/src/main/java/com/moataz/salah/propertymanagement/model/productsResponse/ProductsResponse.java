package com.moataz.salah.propertymanagement.model.productsResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductsResponse {
    @SerializedName("data")
    @Expose
    private List<ProductsModel> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    public List<ProductsModel> getData() {
        return data;
    }

    public void setData(List<ProductsModel> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
