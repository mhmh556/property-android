package com.moataz.salah.propertymanagement.model.addFlat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FlatResponseModel implements Serializable {
    @SerializedName("property_id")
    @Expose
    private Integer propertyId;
    @SerializedName("apt_type_id")
    @Expose
    private Integer aptTypeId;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("electrical_account_id")
    @Expose
    private Integer electricalAccountId;
    @SerializedName("created_user_id")
    @Expose
    private Integer createdUserId;
    @SerializedName("app_api_key")
    @Expose
    private String appApiKey;
    @SerializedName("apt_num")
    @Expose
    private String aptNum;
    @SerializedName("num_rooms")
    @Expose
    private Integer numRooms;
    @SerializedName("num_bath")
    @Expose
    private Integer numBath;
    @SerializedName("num_living_room")
    @Expose
    private Integer numLivingRoom;
    @SerializedName("num_floor")
    @Expose
    private Integer numFloor;
    @SerializedName("apt_area")
    @Expose
    private String aptArea;

    public Integer getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
    }

    public Integer getAptTypeId() {
        return aptTypeId;
    }

    public void setAptTypeId(Integer aptTypeId) {
        this.aptTypeId = aptTypeId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getElectricalAccountId() {
        return electricalAccountId;
    }

    public void setElectricalAccountId(Integer electricalAccountId) {
        this.electricalAccountId = electricalAccountId;
    }

    public Integer getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(Integer createdUserId) {
        this.createdUserId = createdUserId;
    }

    public String getAppApiKey() {
        return appApiKey;
    }

    public void setAppApiKey(String appApiKey) {
        this.appApiKey = appApiKey;
    }

    public String getAptNum() {
        return aptNum;
    }

    public void setAptNum(String aptNum) {
        this.aptNum = aptNum;
    }

    public Integer getNumRooms() {
        return numRooms;
    }

    public void setNumRooms(Integer numRooms) {
        this.numRooms = numRooms;
    }

    public Integer getNumBath() {
        return numBath;
    }

    public void setNumBath(Integer numBath) {
        this.numBath = numBath;
    }

    public Integer getNumLivingRoom() {
        return numLivingRoom;
    }

    public void setNumLivingRoom(Integer numLivingRoom) {
        this.numLivingRoom = numLivingRoom;
    }

    public Integer getNumFloor() {
        return numFloor;
    }

    public void setNumFloor(Integer numFloor) {
        this.numFloor = numFloor;
    }

    public String getAptArea() {
        return aptArea;
    }

    public void setAptArea(String aptArea) {
        this.aptArea = aptArea;
    }
}
