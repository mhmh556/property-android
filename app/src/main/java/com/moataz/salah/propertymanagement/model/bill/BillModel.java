package com.moataz.salah.propertymanagement.model.bill;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BillModel implements Serializable {
    @SerializedName("electical_bill_id")
    @Expose
    private Integer electicalBillId;
    @SerializedName("electrical_account_id")
    @Expose
    private Integer electricalAccountId;
    @SerializedName("created_user_id")
    @Expose
    private Object createdUserId;
    @SerializedName("date_bill")
    @Expose
    private String dateBill;
    @SerializedName("last_reading")
    @Expose
    private Integer lastReading;
    @SerializedName("current_reading")
    @Expose
    private Integer currentReading;
    @SerializedName("total_price")
    @Expose
    private Integer totalPrice;
    @SerializedName("consumption")
    @Expose
    private Integer consumption;

    public Integer getElecticalBillId() {
        return electicalBillId;
    }

    public void setElecticalBillId(Integer electicalBillId) {
        this.electicalBillId = electicalBillId;
    }

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

    public String getDateBill() {
        return dateBill;
    }

    public void setDateBill(String dateBill) {
        this.dateBill = dateBill;
    }

    public Integer getLastReading() {
        return lastReading;
    }

    public void setLastReading(Integer lastReading) {
        this.lastReading = lastReading;
    }

    public Integer getCurrentReading() {
        return currentReading;
    }

    public void setCurrentReading(Integer currentReading) {
        this.currentReading = currentReading;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getConsumption() {
        return consumption;
    }

    public void setConsumption(Integer consumption) {
        this.consumption = consumption;
    }
}
