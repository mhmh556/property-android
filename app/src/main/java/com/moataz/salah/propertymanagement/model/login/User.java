package com.moataz.salah.propertymanagement.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.moataz.salah.propertymanagement.model.application.ApplicationModel;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("user_api_key")
    @Expose
    private String userApiKey;
    @SerializedName("admin")
    @Expose
    private Integer admin;
    @SerializedName("user_job")
    @Expose
    private String userJob;
    @SerializedName("user_full_name")
    @Expose
    private String userFullName;
    @SerializedName("user_phone")
    @Expose
    private Long userPhone;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("user_email")
    @Expose
    private String userEmail;
    @SerializedName("user_national_id")
    @Expose
    private Integer userNationalId;
    @SerializedName("user_activation")
    @Expose
    private String userActivation;
    @SerializedName("applications")
    @Expose
    private List<ApplicationModel> applications = null;
    @SerializedName("access_token")
    @Expose
    private String accessToken;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserApiKey() {
        return userApiKey;
    }

    public void setUserApiKey(String userApiKey) {
        this.userApiKey = userApiKey;
    }

    public Integer getAdmin() {
        return admin;
    }

    public void setAdmin(Integer admin) {
        this.admin = admin;
    }

    public String getUserJob() {
        return userJob;
    }

    public void setUserJob(String userJob) {
        this.userJob = userJob;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public Long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(Long userPhone) {
        this.userPhone = userPhone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public List<ApplicationModel> getApplications() {
        return applications;
    }

    public void setApplications(List<ApplicationModel> applications) {
        this.applications = applications;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
