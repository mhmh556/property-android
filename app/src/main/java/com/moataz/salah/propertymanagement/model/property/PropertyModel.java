package com.moataz.salah.propertymanagement.model.property;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PropertyModel implements Serializable {
    @SerializedName("property_id")
    @Expose
    private Integer propertyId;
    @SerializedName("apt_type_id")
    @Expose
    private Integer aptTypeId;
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
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("reserve_until")
    @Expose
    private Object reserveUntil;
    @SerializedName("available")
    @Expose
    private String available;
    @SerializedName("properties_created")
    @Expose
    private String propertiesCreated;
    @SerializedName("properties_updated")
    @Expose
    private String propertiesUpdated;
    @SerializedName("apt_area")
    @Expose
    private String aptArea;
    @SerializedName("property_price_id")
    @Expose
    private Integer propertyPriceId;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("price_name")
    @Expose
    private String priceName;
    @SerializedName("properties_prices_created")
    @Expose
    private String propertiesPricesCreated;
    @SerializedName("properties_prices_updated")
    @Expose
    private String propertiesPricesUpdated;
    @SerializedName("created_user_id")
    @Expose
    private Integer createdUserId;
    @SerializedName("updated_user_id")
    @Expose
    private Object updatedUserId;
    @SerializedName("active")
    @Expose
    private Integer active;
    @SerializedName("price_created_user_id")
    @Expose
    private Integer priceCreatedUserId;
    @SerializedName("price_updated_user_id")
    @Expose
    private Object priceUpdatedUserId;
    @SerializedName("date_create_price")
    @Expose
    private String dateCreatePrice;
    @SerializedName("date_update_price")
    @Expose
    private String dateUpdatePrice;
    @SerializedName("property_created_user_id")
    @Expose
    private Integer propertyCreatedUserId;
    @SerializedName("property_updated_user_id")
    @Expose
    private Object propertyUpdatedUserId;
    @SerializedName("date_create_property")
    @Expose
    private String dateCreateProperty;
    @SerializedName("date_update_property")
    @Expose
    private String dateUpdateProperty;
    @SerializedName("electrical_account_id")
    @Expose
    private Integer electricalAccountId;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("apt_type_name")
    @Expose
    private String aptTypeName;
    @SerializedName("future_reservation")
    @Expose
    private List<FutureReservation> futureReservation = null;

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

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Object getReserveUntil() {
        return reserveUntil;
    }

    public void setReserveUntil(Object reserveUntil) {
        this.reserveUntil = reserveUntil;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getPropertiesCreated() {
        return propertiesCreated;
    }

    public void setPropertiesCreated(String propertiesCreated) {
        this.propertiesCreated = propertiesCreated;
    }

    public String getPropertiesUpdated() {
        return propertiesUpdated;
    }

    public void setPropertiesUpdated(String propertiesUpdated) {
        this.propertiesUpdated = propertiesUpdated;
    }

    public String getAptArea() {
        return aptArea;
    }

    public void setAptArea(String aptArea) {
        this.aptArea = aptArea;
    }

    public Integer getPropertyPriceId() {
        return propertyPriceId;
    }

    public void setPropertyPriceId(Integer propertyPriceId) {
        this.propertyPriceId = propertyPriceId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getPriceName() {
        return priceName;
    }

    public void setPriceName(String priceName) {
        this.priceName = priceName;
    }

    public String getPropertiesPricesCreated() {
        return propertiesPricesCreated;
    }

    public void setPropertiesPricesCreated(String propertiesPricesCreated) {
        this.propertiesPricesCreated = propertiesPricesCreated;
    }

    public String getPropertiesPricesUpdated() {
        return propertiesPricesUpdated;
    }

    public void setPropertiesPricesUpdated(String propertiesPricesUpdated) {
        this.propertiesPricesUpdated = propertiesPricesUpdated;
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

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getPriceCreatedUserId() {
        return priceCreatedUserId;
    }

    public void setPriceCreatedUserId(Integer priceCreatedUserId) {
        this.priceCreatedUserId = priceCreatedUserId;
    }

    public Object getPriceUpdatedUserId() {
        return priceUpdatedUserId;
    }

    public void setPriceUpdatedUserId(Object priceUpdatedUserId) {
        this.priceUpdatedUserId = priceUpdatedUserId;
    }

    public String getDateCreatePrice() {
        return dateCreatePrice;
    }

    public void setDateCreatePrice(String dateCreatePrice) {
        this.dateCreatePrice = dateCreatePrice;
    }

    public String getDateUpdatePrice() {
        return dateUpdatePrice;
    }

    public void setDateUpdatePrice(String dateUpdatePrice) {
        this.dateUpdatePrice = dateUpdatePrice;
    }

    public Integer getPropertyCreatedUserId() {
        return propertyCreatedUserId;
    }

    public void setPropertyCreatedUserId(Integer propertyCreatedUserId) {
        this.propertyCreatedUserId = propertyCreatedUserId;
    }

    public Object getPropertyUpdatedUserId() {
        return propertyUpdatedUserId;
    }

    public void setPropertyUpdatedUserId(Object propertyUpdatedUserId) {
        this.propertyUpdatedUserId = propertyUpdatedUserId;
    }

    public String getDateCreateProperty() {
        return dateCreateProperty;
    }

    public void setDateCreateProperty(String dateCreateProperty) {
        this.dateCreateProperty = dateCreateProperty;
    }

    public String getDateUpdateProperty() {
        return dateUpdateProperty;
    }

    public void setDateUpdateProperty(String dateUpdateProperty) {
        this.dateUpdateProperty = dateUpdateProperty;
    }

    public Integer getElectricalAccountId() {
        return electricalAccountId;
    }

    public void setElectricalAccountId(Integer electricalAccountId) {
        this.electricalAccountId = electricalAccountId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAptTypeName() {
        return aptTypeName;
    }

    public void setAptTypeName(String aptTypeName) {
        this.aptTypeName = aptTypeName;
    }

    public List<FutureReservation> getFutureReservation() {
        return futureReservation;
    }

    public void setFutureReservation(List<FutureReservation> futureReservation) {
        this.futureReservation = futureReservation;
    }
}
