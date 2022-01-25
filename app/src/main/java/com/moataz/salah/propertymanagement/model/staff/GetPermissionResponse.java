package com.moataz.salah.propertymanagement.model.staff;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetPermissionResponse {
    @SerializedName("data")
    @Expose
    private UserPermissionModel data;
    @SerializedName("message")
    @Expose
    private String message;

    public UserPermissionModel getData() {
        return data;
    }

    public void setData(UserPermissionModel data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
