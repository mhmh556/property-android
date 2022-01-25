package com.moataz.salah.propertymanagement.model.task;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TaskModel implements Serializable {
    @SerializedName("task_id")
    @Expose
    private Integer taskId;
    @SerializedName("task_user_id")
    @Expose
    private Integer taskUserId;
    @SerializedName("task_job_id")
    @Expose
    private Integer taskJobId;
    @SerializedName("tasks_created_user_id")
    @Expose
    private Integer tasksCreatedUserId;
    @SerializedName("tasks_updated_user_id")
    @Expose
    private Object tasksUpdatedUserId;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("tasks_tasks_created_user_id")
    @Expose
    private Integer tasksTasksCreatedUserId;
    @SerializedName("tasks_created")
    @Expose
    private String tasksCreated;
    @SerializedName("tasks_updated")
    @Expose
    private String tasksUpdated;
    @SerializedName("task_job_name")
    @Expose
    private String taskJobName;
    @SerializedName("user_full_name")
    @Expose
    private String userFullName;

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getTaskUserId() {
        return taskUserId;
    }

    public void setTaskUserId(Integer taskUserId) {
        this.taskUserId = taskUserId;
    }

    public Integer getTaskJobId() {
        return taskJobId;
    }

    public void setTaskJobId(Integer taskJobId) {
        this.taskJobId = taskJobId;
    }

    public Integer getTasksCreatedUserId() {
        return tasksCreatedUserId;
    }

    public void setTasksCreatedUserId(Integer tasksCreatedUserId) {
        this.tasksCreatedUserId = tasksCreatedUserId;
    }

    public Object getTasksUpdatedUserId() {
        return tasksUpdatedUserId;
    }

    public void setTasksUpdatedUserId(Object tasksUpdatedUserId) {
        this.tasksUpdatedUserId = tasksUpdatedUserId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTasksTasksCreatedUserId() {
        return tasksTasksCreatedUserId;
    }

    public void setTasksTasksCreatedUserId(Integer tasksTasksCreatedUserId) {
        this.tasksTasksCreatedUserId = tasksTasksCreatedUserId;
    }

    public String getTasksCreated() {
        return tasksCreated;
    }

    public void setTasksCreated(String tasksCreated) {
        this.tasksCreated = tasksCreated;
    }

    public String getTasksUpdated() {
        return tasksUpdated;
    }

    public void setTasksUpdated(String tasksUpdated) {
        this.tasksUpdated = tasksUpdated;
    }

    public String getTaskJobName() {
        return taskJobName;
    }

    public void setTaskJobName(String taskJobName) {
        this.taskJobName = taskJobName;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

}
