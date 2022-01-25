package com.moataz.salah.propertymanagement.model.electrical;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ElectricalModel implements Serializable {
    @SerializedName("electrical_account_id")
    @Expose
    private Integer electricalAccountId;
    @SerializedName("created_user_id")
    @Expose
    private Object createdUserId;
    @SerializedName("updated_user_id")
    @Expose
    private Object updatedUserId;
    @SerializedName("device_num")
    @Expose
    private Integer deviceNum;
    @SerializedName("account_num")
    @Expose
    private Integer accountNum;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("multiplication_factor")
    @Expose
    private Integer multiplicationFactor;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("updated")
    @Expose
    private Object updated;

    public Integer getElectricalAccountId() {
        return electricalAccountId;
    }

    public void setElectricalAccountId(Integer electricalAccountId) {
        this.electricalAccountId = electricalAccountId;
    }

    public Object getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(Object createdUserId) {
        this.createdUserId = createdUserId;
    }

    public Object getUpdatedUserId() {
        return updatedUserId;
    }

    public void setUpdatedUserId(Object updatedUserId) {
        this.updatedUserId = updatedUserId;
    }

    public Integer getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(Integer deviceNum) {
        this.deviceNum = deviceNum;
    }

    public Integer getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(Integer accountNum) {
        this.accountNum = accountNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getMultiplicationFactor() {
        return multiplicationFactor;
    }

    public void setMultiplicationFactor(Integer multiplicationFactor) {
        this.multiplicationFactor = multiplicationFactor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Object getUpdated() {
        return updated;
    }

    public void setUpdated(Object updated) {
        this.updated = updated;
    }
}
