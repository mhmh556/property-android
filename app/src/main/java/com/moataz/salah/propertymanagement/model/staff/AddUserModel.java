package com.moataz.salah.propertymanagement.model.staff;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddUserModel implements Serializable {
    @SerializedName("admin")
    @Expose
    private Integer admin;
    @SerializedName("admin_api_key")
    @Expose
    private String adminApiKey;
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
    private Integer userPhone;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("user_address")
    @Expose
    private String userAddress;
    @SerializedName("user_api_key")
    @Expose
    private String userApiKey;
    @SerializedName("user_email")
    @Expose
    private String userEmail;
    @SerializedName("user_national_id")
    @Expose
    private Integer userNationalId;
    @SerializedName("user_activation")
    @Expose
    private String userActivation;

    public Integer getAdmin() {
        return admin;
    }

    public void setAdmin(Integer admin) {
        this.admin = admin;
    }

    public String getAdminApiKey() {
        return adminApiKey;
    }

    public void setAdminApiKey(String adminApiKey) {
        this.adminApiKey = adminApiKey;
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

    public Integer getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(Integer userPhone) {
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

    public String getUserApiKey() {
        return userApiKey;
    }

    public void setUserApiKey(String userApiKey) {
        this.userApiKey = userApiKey;
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
}
