package com.moataz.salah.propertymanagement.model.customer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ResponseCustomer {
    @SerializedName("data")
    @Expose
    private List<CustomerModel> data = null;
    @SerializedName("message")
    @Expose
    private String message;
    public List<CustomerModel> getData() {
        return data;
    }
    public void setData(List<CustomerModel> data) {
        this.data = data;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
