package com.moataz.salah.propertymanagement.model.task;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TaskResponse {
    @SerializedName("data")
    @Expose
    private List<TaskModel> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    public List<TaskModel> getData() {
        return data;
    }

    public void setData(List<TaskModel> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
