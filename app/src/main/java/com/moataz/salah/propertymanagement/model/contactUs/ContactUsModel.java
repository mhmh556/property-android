package com.moataz.salah.propertymanagement.model.contactUs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ContactUsModel implements Serializable {
    @SerializedName("contact_us_id")
    @Expose
    private Integer contactUsId;
    @SerializedName("contact_type_id")
    @Expose
    private Integer contactTypeId;
    @SerializedName("contact_us_contact_us_id")
    @Expose
    private Integer contactUsContactUsId;
    @SerializedName("contact_us_updated_user_id")
    @Expose
    private Integer contactUsUpdatedUserId;
    @SerializedName("contact_user_id")
    @Expose
    private Integer contactUserId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("contact_text")
    @Expose
    private String contactText;
    @SerializedName("contact_us_created")
    @Expose
    private String contactUsCreated;
    @SerializedName("contact_us_updated")
    @Expose
    private String contactUsUpdated;
    @SerializedName("contact_type_name")
    @Expose
    private String contactTypeName;

    public Integer getContactUsId() {
        return contactUsId;
    }

    public void setContactUsId(Integer contactUsId) {
        this.contactUsId = contactUsId;
    }

    public Integer getContactTypeId() {
        return contactTypeId;
    }

    public void setContactTypeId(Integer contactTypeId) {
        this.contactTypeId = contactTypeId;
    }

    public Integer getContactUsContactUsId() {
        return contactUsContactUsId;
    }

    public void setContactUsContactUsId(Integer contactUsContactUsId) {
        this.contactUsContactUsId = contactUsContactUsId;
    }

    public Integer getContactUsUpdatedUserId() {
        return contactUsUpdatedUserId;
    }

    public void setContactUsUpdatedUserId(Integer contactUsUpdatedUserId) {
        this.contactUsUpdatedUserId = contactUsUpdatedUserId;
    }

    public Integer getContactUserId() {
        return contactUserId;
    }

    public void setContactUserId(Integer contactUserId) {
        this.contactUserId = contactUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContactText() {
        return contactText;
    }

    public void setContactText(String contactText) {
        this.contactText = contactText;
    }

    public String getContactUsCreated() {
        return contactUsCreated;
    }

    public void setContactUsCreated(String contactUsCreated) {
        this.contactUsCreated = contactUsCreated;
    }

    public String getContactUsUpdated() {
        return contactUsUpdated;
    }

    public void setContactUsUpdated(String contactUsUpdated) {
        this.contactUsUpdated = contactUsUpdated;
    }

    public String getContactTypeName() {
        return contactTypeName;
    }

    public void setContactTypeName(String contactTypeName) {
        this.contactTypeName = contactTypeName;
    }
}
