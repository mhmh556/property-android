package com.moataz.salah.propertymanagement.model.addFlat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddNewFlatResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private FlatResponseModel data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FlatResponseModel getData() {
        return data;
    }

    public void setData(FlatResponseModel data) {
        this.data = data;
    }
}
