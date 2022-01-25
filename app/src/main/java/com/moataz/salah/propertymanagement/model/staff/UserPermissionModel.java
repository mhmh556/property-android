package com.moataz.salah.propertymanagement.model.staff;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserPermissionModel implements Serializable {
    @SerializedName("user_app_permission_id")
    @Expose
    private Integer userAppPermissionId;
    @SerializedName("user_property_permission_created_user_id")
    @Expose
    private Integer userPropertyPermissionCreatedUserId;
    @SerializedName("user_property_permission_updated_user_id")
    @Expose
    private Object userPropertyPermissionUpdatedUserId;
    @SerializedName("user_property_permission_user_api_key")
    @Expose
    private String userPropertyPermissionUserApiKey;
    @SerializedName("user_property_permission_app_api_key")
    @Expose
    private String userPropertyPermissionAppApiKey;
    @SerializedName("property_view")
    @Expose
    private Integer propertyView;
    @SerializedName("property_add")
    @Expose
    private Integer propertyAdd;
    @SerializedName("property_edit")
    @Expose
    private Integer propertyEdit;
    @SerializedName("property_delete")
    @Expose
    private Integer propertyDelete;
    @SerializedName("cleaning_view")
    @Expose
    private Integer cleaningView;
    @SerializedName("cleaning_add")
    @Expose
    private Integer cleaningAdd;
    @SerializedName("cleaning_edit")
    @Expose
    private Integer cleaningEdit;
    @SerializedName("cleaning_delete")
    @Expose
    private Integer cleaningDelete;
    @SerializedName("product_view")
    @Expose
    private Integer productView;
    @SerializedName("product_add")
    @Expose
    private Integer productAdd;
    @SerializedName("product_edit")
    @Expose
    private Integer productEdit;
    @SerializedName("product_delete")
    @Expose
    private Integer productDelete;
    @SerializedName("custmer_view")
    @Expose
    private Integer custmerView;
    @SerializedName("custmer_add")
    @Expose
    private Integer custmerAdd;
    @SerializedName("custmer_edit")
    @Expose
    private Integer custmerEdit;
    @SerializedName("custmer_delete")
    @Expose
    private Integer custmerDelete;
    @SerializedName("reservation_view")
    @Expose
    private Integer reservationView;
    @SerializedName("reservation_add")
    @Expose
    private Integer reservationAdd;
    @SerializedName("reservation_edit")
    @Expose
    private Integer reservationEdit;
    @SerializedName("reservation_delete")
    @Expose
    private Integer reservationDelete;
    @SerializedName("purchase_view")
    @Expose
    private Integer purchaseView;
    @SerializedName("purchase_add")
    @Expose
    private Integer purchaseAdd;
    @SerializedName("purchase_edit")
    @Expose
    private Integer purchaseEdit;
    @SerializedName("purchase_delete")
    @Expose
    private Integer purchaseDelete;
    @SerializedName("device_meter_view")
    @Expose
    private Integer deviceMeterView;
    @SerializedName("device_meter_add")
    @Expose
    private Integer deviceMeterAdd;
    @SerializedName("device_meter_edit")
    @Expose
    private Integer deviceMeterEdit;
    @SerializedName("device_meter_delete")
    @Expose
    private Integer deviceMeterDelete;
    @SerializedName("customer_view")
    @Expose
    private Integer customerView;
    @SerializedName("customer_add")
    @Expose
    private Integer customerAdd;
    @SerializedName("customer_edit")
    @Expose
    private Integer customerEdit;
    @SerializedName("customer_delete")
    @Expose
    private Integer customerDelete;
    @SerializedName("report_view")
    @Expose
    private Integer reportView;
    @SerializedName("app_permission_created")
    @Expose
    private String appPermissionCreated;
    @SerializedName("app_permission_updated")
    @Expose
    private String appPermissionUpdated;

    public Integer getUserAppPermissionId() {
        return userAppPermissionId;
    }

    public void setUserAppPermissionId(Integer userAppPermissionId) {
        this.userAppPermissionId = userAppPermissionId;
    }

    public Integer getUserPropertyPermissionCreatedUserId() {
        return userPropertyPermissionCreatedUserId;
    }

    public void setUserPropertyPermissionCreatedUserId(Integer userPropertyPermissionCreatedUserId) {
        this.userPropertyPermissionCreatedUserId = userPropertyPermissionCreatedUserId;
    }

    public Object getUserPropertyPermissionUpdatedUserId() {
        return userPropertyPermissionUpdatedUserId;
    }

    public void setUserPropertyPermissionUpdatedUserId(Object userPropertyPermissionUpdatedUserId) {
        this.userPropertyPermissionUpdatedUserId = userPropertyPermissionUpdatedUserId;
    }

    public String getUserPropertyPermissionUserApiKey() {
        return userPropertyPermissionUserApiKey;
    }

    public void setUserPropertyPermissionUserApiKey(String userPropertyPermissionUserApiKey) {
        this.userPropertyPermissionUserApiKey = userPropertyPermissionUserApiKey;
    }

    public String getUserPropertyPermissionAppApiKey() {
        return userPropertyPermissionAppApiKey;
    }

    public void setUserPropertyPermissionAppApiKey(String userPropertyPermissionAppApiKey) {
        this.userPropertyPermissionAppApiKey = userPropertyPermissionAppApiKey;
    }

    public Integer getPropertyView() {
        return propertyView;
    }

    public void setPropertyView(Integer propertyView) {
        this.propertyView = propertyView;
    }

    public Integer getPropertyAdd() {
        return propertyAdd;
    }

    public void setPropertyAdd(Integer propertyAdd) {
        this.propertyAdd = propertyAdd;
    }

    public Integer getPropertyEdit() {
        return propertyEdit;
    }

    public void setPropertyEdit(Integer propertyEdit) {
        this.propertyEdit = propertyEdit;
    }

    public Integer getPropertyDelete() {
        return propertyDelete;
    }

    public void setPropertyDelete(Integer propertyDelete) {
        this.propertyDelete = propertyDelete;
    }

    public Integer getCleaningView() {
        return cleaningView;
    }

    public void setCleaningView(Integer cleaningView) {
        this.cleaningView = cleaningView;
    }

    public Integer getCleaningAdd() {
        return cleaningAdd;
    }

    public void setCleaningAdd(Integer cleaningAdd) {
        this.cleaningAdd = cleaningAdd;
    }

    public Integer getCleaningEdit() {
        return cleaningEdit;
    }

    public void setCleaningEdit(Integer cleaningEdit) {
        this.cleaningEdit = cleaningEdit;
    }

    public Integer getCleaningDelete() {
        return cleaningDelete;
    }

    public void setCleaningDelete(Integer cleaningDelete) {
        this.cleaningDelete = cleaningDelete;
    }

    public Integer getProductView() {
        return productView;
    }

    public void setProductView(Integer productView) {
        this.productView = productView;
    }

    public Integer getProductAdd() {
        return productAdd;
    }

    public void setProductAdd(Integer productAdd) {
        this.productAdd = productAdd;
    }

    public Integer getProductEdit() {
        return productEdit;
    }

    public void setProductEdit(Integer productEdit) {
        this.productEdit = productEdit;
    }

    public Integer getProductDelete() {
        return productDelete;
    }

    public void setProductDelete(Integer productDelete) {
        this.productDelete = productDelete;
    }

    public Integer getCustmerView() {
        return custmerView;
    }

    public void setCustmerView(Integer custmerView) {
        this.custmerView = custmerView;
    }

    public Integer getCustmerAdd() {
        return custmerAdd;
    }

    public void setCustmerAdd(Integer custmerAdd) {
        this.custmerAdd = custmerAdd;
    }

    public Integer getCustmerEdit() {
        return custmerEdit;
    }

    public void setCustmerEdit(Integer custmerEdit) {
        this.custmerEdit = custmerEdit;
    }

    public Integer getCustmerDelete() {
        return custmerDelete;
    }

    public void setCustmerDelete(Integer custmerDelete) {
        this.custmerDelete = custmerDelete;
    }

    public Integer getReservationView() {
        return reservationView;
    }

    public void setReservationView(Integer reservationView) {
        this.reservationView = reservationView;
    }

    public Integer getReservationAdd() {
        return reservationAdd;
    }

    public void setReservationAdd(Integer reservationAdd) {
        this.reservationAdd = reservationAdd;
    }

    public Integer getReservationEdit() {
        return reservationEdit;
    }

    public void setReservationEdit(Integer reservationEdit) {
        this.reservationEdit = reservationEdit;
    }

    public Integer getReservationDelete() {
        return reservationDelete;
    }

    public void setReservationDelete(Integer reservationDelete) {
        this.reservationDelete = reservationDelete;
    }

    public Integer getPurchaseView() {
        return purchaseView;
    }

    public void setPurchaseView(Integer purchaseView) {
        this.purchaseView = purchaseView;
    }

    public Integer getPurchaseAdd() {
        return purchaseAdd;
    }

    public void setPurchaseAdd(Integer purchaseAdd) {
        this.purchaseAdd = purchaseAdd;
    }

    public Integer getPurchaseEdit() {
        return purchaseEdit;
    }

    public void setPurchaseEdit(Integer purchaseEdit) {
        this.purchaseEdit = purchaseEdit;
    }

    public Integer getPurchaseDelete() {
        return purchaseDelete;
    }

    public void setPurchaseDelete(Integer purchaseDelete) {
        this.purchaseDelete = purchaseDelete;
    }

    public Integer getDeviceMeterView() {
        return deviceMeterView;
    }

    public void setDeviceMeterView(Integer deviceMeterView) {
        this.deviceMeterView = deviceMeterView;
    }

    public Integer getDeviceMeterAdd() {
        return deviceMeterAdd;
    }

    public void setDeviceMeterAdd(Integer deviceMeterAdd) {
        this.deviceMeterAdd = deviceMeterAdd;
    }

    public Integer getDeviceMeterEdit() {
        return deviceMeterEdit;
    }

    public void setDeviceMeterEdit(Integer deviceMeterEdit) {
        this.deviceMeterEdit = deviceMeterEdit;
    }

    public Integer getDeviceMeterDelete() {
        return deviceMeterDelete;
    }

    public void setDeviceMeterDelete(Integer deviceMeterDelete) {
        this.deviceMeterDelete = deviceMeterDelete;
    }

    public Integer getCustomerView() {
        return customerView;
    }

    public void setCustomerView(Integer customerView) {
        this.customerView = customerView;
    }

    public Integer getCustomerAdd() {
        return customerAdd;
    }

    public void setCustomerAdd(Integer customerAdd) {
        this.customerAdd = customerAdd;
    }

    public Integer getCustomerEdit() {
        return customerEdit;
    }

    public void setCustomerEdit(Integer customerEdit) {
        this.customerEdit = customerEdit;
    }

    public Integer getCustomerDelete() {
        return customerDelete;
    }

    public void setCustomerDelete(Integer customerDelete) {
        this.customerDelete = customerDelete;
    }

    public Integer getReportView() {
        return reportView;
    }

    public void setReportView(Integer reportView) {
        this.reportView = reportView;
    }

    public String getAppPermissionCreated() {
        return appPermissionCreated;
    }

    public void setAppPermissionCreated(String appPermissionCreated) {
        this.appPermissionCreated = appPermissionCreated;
    }

    public String getAppPermissionUpdated() {
        return appPermissionUpdated;
    }

    public void setAppPermissionUpdated(String appPermissionUpdated) {
        this.appPermissionUpdated = appPermissionUpdated;
    }
}
