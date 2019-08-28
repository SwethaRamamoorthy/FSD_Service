package com.taskmanager.model;

import java.util.Date;

public class ProjectDetailBO {
	
	String projectId;
	String projectName;
	String projectDescription;
	Date startDate;
	Date endDate;
	String manager;
	int priority;
	int totalTask;
	int totalCompleted;
	String message;
	
	public ProjectDetailBO() {
		
	}
	
	public ProjectDetailBO(String projectName, String projectDescription,String projectId, Date startDate,Date endDate,String manager,int priority ) {
		this.projectName = projectName;
		this.projectDescription = projectDescription;
		this.projectId = projectId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.manager = manager;
		this.priority = priority;
	}
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectDescription() {
		return projectDescription;
	}
	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
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

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getTotalTask() {
		return totalTask;
	}

	public void setTotalTask(int totalTask) {
		this.totalTask = totalTask;
	}

	public int getTotalCompleted() {
		return totalCompleted;
	}

	public void setTotalCompleted(int totalCompleted) {
		this.totalCompleted = totalCompleted;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
