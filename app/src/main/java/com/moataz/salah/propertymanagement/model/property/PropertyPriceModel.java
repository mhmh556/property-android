package com.moataz.salah.propertymanagement.model.property;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class PropertyPriceModel implements Serializable {
    @SerializedName("property_price_id")
    @Expose
    private Integer propertyPriceId;
    @SerializedName("property_id")
    @Expose
    private Integer propertyId;
    @SerializedName("created_user_id")
    @Expose
    private Integer createdUserId;
    @SerializedName("updated_user_id")
    @Expose
    private Object updatedUserId;
    @SerializedName("app_api_key")
    @Expose
    private String appApiKey;
    @SerializedName("price")
    @Expose
    private Integer price;
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
    private Object updated;

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

    public Object getUpdatedUserId() {
        return updatedUserId;
    }

    public void setUpdatedUserId(Object updatedUserId) {
        this.updatedUserId = updatedUserId;
    }

    public String getAppApiKey() {
        return appApiKey;
    }

    public void setAppApiKey(String appApiKey) {
        this.appApiKey = appApiKey;
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

    public Object getUpdated() {
        return updated;
    }

    public void setUpdated(Object updated) {
        this.updated = updated;
    }
}
