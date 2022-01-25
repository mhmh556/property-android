package com.moataz.salah.propertymanagement.model.reserve;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class ReservationModel implements Serializable {
    @SerializedName("reservation_id")
    @Expose
    private Integer reservationId;
    @SerializedName("property_id")
    @Expose
    private Integer propertyId;
    @SerializedName("property_price_id")
    @Expose
    private Integer propertyPriceId;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("created_user_id")
    @Expose
    private Integer createdUserId;
    @SerializedName("updated_user_id")
    @Expose
    private Object updatedUserId;
    @SerializedName("num_person")
    @Expose
    private Integer numPerson;
    @SerializedName("check_in_date")
    @Expose
    private String checkInDate;
    @SerializedName("check_out_date")
    @Expose
    private String checkOutDate;
    @SerializedName("real_check_out_date")
    @Expose
    private Object realCheckOutDate;
    @SerializedName("real_check_in_date")
    @Expose
    private Object realCheckInDate;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("updated")
    @Expose
    private String updated;
    @SerializedName("apt_num")
    @Expose
    private String aptNum;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("reservations_sales")
    @Expose
    private List<ReservationsSale> reservationsSales = null;

    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public Integer getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
    }

    public Integer getPropertyPriceId() {
        return propertyPriceId;
    }

    public void setPropertyPriceId(Integer propertyPriceId) {
        this.propertyPriceId = propertyPriceId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(Integer createdUserId) {
        this.createdUserId = createdUserId;
    }

    public Object getUpdatedUserId() {
        return updatedUserId;
    }

    public void setUpdatedUserId(Object updatedUserId) {
        this.updatedUserId = updatedUserId;
    }

    public Integer getNumPerson() {
        return numPerson;
    }

    public void setNumPerson(Integer numPerson) {
        this.numPerson = numPerson;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Object getRealCheckOutDate() {
        return realCheckOutDate;
    }

    public void setRealCheckOutDate(Object realCheckOutDate) {
        this.realCheckOutDate = realCheckOutDate;
    }

    public Object getRealCheckInDate() {
        return realCheckInDate;
    }

    public void setRealCheckInDate(Object realCheckInDate) {
        this.realCheckInDate = realCheckInDate;
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

    public String getAptNum() {
        return aptNum;
    }

    public void setAptNum(String aptNum) {
        this.aptNum = aptNum;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<ReservationsSale> getReservationsSales() {
        return reservationsSales;
    }

    public void setReservationsSales(List<ReservationsSale> reservationsSales) {
        this.reservationsSales = reservationsSales;
    }
}
