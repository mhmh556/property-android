package com.moataz.salah.propertymanagement.model.reserve;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReservationListResponse {
    @SerializedName("data")
    @Expose
    private List<ReservationModel> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    public List<ReservationModel> getData() {
        return data;
    }

    public void setData(List<ReservationModel> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
