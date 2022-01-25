package com.moataz.salah.propertymanagement.model.taskJob;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TaskJobResponse {
    @SerializedName("data")
    @Expose
    private List<TaskJobModel> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    public List<TaskJobModel> getData() {
        return data;
    }

    public void setData(List<TaskJobModel> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
