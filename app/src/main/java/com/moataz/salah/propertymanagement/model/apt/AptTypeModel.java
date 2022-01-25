package com.moataz.salah.propertymanagement.model.apt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AptTypeModel {
    @SerializedName("apt_type_id")
    @Expose
    private Integer aptTypeId;
    @SerializedName("apt_type_name")
    @Expose
    private String aptTypeName;

    public Integer getAptTypeId() {
        return aptTypeId;
    }

    public void setAptTypeId(Integer aptTypeId) {
        this.aptTypeId = aptTypeId;
    }

    public String getAptTypeName() {
        return aptTypeName;
    }

    public void setAptTypeName(String aptTypeName) {
        this.aptTypeName = aptTypeName;
    }
}
