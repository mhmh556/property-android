package com.moataz.salah.propertymanagement.model.staff;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StaffModel implements Serializable {
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("admin")
    @Expose
    private Integer admin;
    @SerializedName("app_active")
    @Expose
    private Integer appActive;
    @SerializedName("user_full_name")
    @Expose
    private String userFullName;
    @SerializedName("user_job")
    @Expose
    private String userJob;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("user_phone")
    @Expose
    private double userPhone;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("user_address")
    @Expose
    private String userAddress;
    @SerializedName("image")
    @Expose
    private String userImage;
    @SerializedName("user_email")
    @Expose
    private String userEmail;
    @SerializedName("user_national_id")
    @Expose
    private Integer userNationalId;
    @SerializedName("user_activation")
    @Expose
    private String userActivation;
    @SerializedName("user_api_key")
    @Expose
    private String userApiKey;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("updated")
    @Expose
    private String updated;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAdmin() {
        return admin;
    }

    public void setAdmin(Integer admin) {
        this.admin = admin;
    }

    public Integer getAppActive() {
        return appActive;
    }

    public void setAppActive(Integer appActive) {
        this.appActive = appActive;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserJob() {
        return userJob;
    }

    public void setUserJob(String userJob) {
        this.userJob = userJob;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(double userPhone) {
        this.userPhone = userPhone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Integer getUserNationalId() {
        return userNationalId;
    }

    public void setUserNationalId(Integer userNationalId) {
        this.userNationalId = userNationalId;
    }

    public String getUserActivation() {
        return userActivation;
    }

    public void setUserActivation(String userActivation) {
        this.userActivation = userActivation;
    }

    public String getUserApiKey() {
        return userApiKey;
    }

    public void setUserApiKey(String userApiKey) {
        this.userApiKey = userApiKey;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }
}
