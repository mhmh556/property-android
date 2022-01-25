package com.moataz.salah.propertymanagement.model.staff;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddUserResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("user")
    @Expose
    private AddUserModel user;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AddUserModel getUser() {
        return user;
    }

    public void setUser(AddUserModel user) {
        this.user = user;
    }
}
