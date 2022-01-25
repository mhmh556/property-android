package com.moataz.salah.propertymanagement.model.application;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddApplicationResponse {
    @SerializedName("data")
    @Expose
    private List<ApplicationModel> data = null;
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("message")
    @Expose
    private String message;

    public List<ApplicationModel> getData() {
        return data;
    }

    public void setData(List<ApplicationModel> data) {
        this.data = data;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
