package com.moataz.salah.propertymanagement.model.expenses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetExpensesResponse {
    @SerializedName("data")
    @Expose
    private List<ExpensesModel> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    public List<ExpensesModel> getData() {
        return data;
    }

    public void setData(List<ExpensesModel> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
