package com.moataz.salah.propertymanagement.model.price;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PriceModel {
    @SerializedName("property_price_id")
    @Expose
    private Integer propertyPriceId;
    @SerializedName("property_id")
    @Expose
    private Integer propertyId;
    @SerializedName("created_user_id")
    @Expose
    private Integer createdUserId;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("price_name")
    @Expose
    private String priceName;
    @SerializedName("active")
    @Expose
    private Integer active;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("updated")
    @Expose
    private String updated;

    public Integer getPropertyPriceId() {
        return propertyPriceId;
    }

    public void setPropertyPriceId(Integer propertyPriceId) {
        this.propertyPriceId = propertyPriceId;
    }

    public Integer getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
    }

    public Integer getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(Integer createdUserId) {
        this.createdUserId = createdUserId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPriceName() {
        return priceName;
    }

    public void setPriceName(String priceName) {
        this.priceName = priceName;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
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
