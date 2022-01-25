package com.moataz.salah.propertymanagement.model.sign;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("user_full_name")
    @Expose
    private String userFullName;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("user_name")
    @Expose
    private String userName;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
