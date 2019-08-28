package com.taskmanager.model;

import java.util.Date;

public class TaskListBO {
	
	String taskId;
	String taskTitle;
	String parentTaskid;
	String parentTaskTitle;
	Date startDate;
	Date endDate;
	int priority;
	String message;
	String projectId;
	String projectName;
	String userId;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	String status;

	public TaskListBO() {
		
	}

	public TaskListBO(String taskId, String taskTitle, String parentTaskid, String parentTaskTitle, Date startDate,
			Date endDate, int priority,String projectId, String projectName) {
		super();
		this.taskId = taskId;
		this.taskTitle = taskTitle;
		this.parentTaskid = parentTaskid;
		this.parentTaskTitle = parentTaskTitle;
		this.startDate = startDate;
		this.endDate = endDate;
		this.priority = priority;
		this.projectId = projectId;
		this.projectName = projectName;
	}
	
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskTitle() {
		return taskTitle;
	}
	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}
	public String getParentTaskid() {
		return parentTaskid;
	}
	public void setParentTaskid(String parentTaskid) {
		this.parentTaskid = parentTaskid;
	}
	public String getParentTaskTitle() {
		return parentTaskTitle;
	}
	public void setParentTaskTitle(String parentTaskTitle) {
		this.parentTaskTitle = parentTaskTitle;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
