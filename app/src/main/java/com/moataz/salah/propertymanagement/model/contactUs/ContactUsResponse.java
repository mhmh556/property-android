package com.moataz.salah.propertymanagement.model.contactUs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContactUsResponse {
    @SerializedName("data")
    @Expose
    private List<ContactUsModel> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    public List<ContactUsModel> getData() {
        return data;
    }

    public void setData(List<ContactUsModel> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
