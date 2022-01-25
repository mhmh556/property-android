package com.moataz.salah.propertymanagement.model.taskJob;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TaskJobModel {
    @SerializedName("task_job_id")
    @Expose
    private Integer taskJobId;
    @SerializedName("created_user_id")
    @Expose
    private Object createdUserId;
    @SerializedName("updated_user_id")
    @Expose
    private Object updatedUserId;
    @SerializedName("task_job_name")
    @Expose
    private String taskJobName;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("updated")
    @Expose
    private String updated;

    public Integer getTaskJobId() {
        return taskJobId;
    }

    public void setTaskJobId(Integer taskJobId) {
        this.taskJobId = taskJobId;
    }

    public Object getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(Object createdUserId) {
        this.createdUserId = createdUserId;
    }

    public Object getUpdatedUserId() {
        return updatedUserId;
    }

    public void setUpdatedUserId(Object updatedUserId) {
        this.updatedUserId = updatedUserId;
    }

    public String getTaskJobName() {
        return taskJobName;
    }

    public void setTaskJobName(String taskJobName) {
        this.taskJobName = taskJobName;
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
