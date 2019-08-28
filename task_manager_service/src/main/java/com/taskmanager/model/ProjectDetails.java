package com.taskmanager.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "project_details")
public class ProjectDetails {

	@Column(name="project_name")
	String projectName;
	@Column(name="start_date")
	Date startDate;
	@Column(name="end_date")
	Date endDate;
	@Column(name="manager")
	String manager;
	@Column(name="priority")
	int priority;
	@Column(name="status")
	String status;
	@Column(name="project_id")
	String projectId;

	
	public ProjectDetails() {
		
	}
	
	
	public ProjectDetails(String projectId, String projectName, Date startDate,Date endDate,String manager,int priority,String status) {
		super();
		this.projectId = projectId;
		this.projectName = projectName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.manager = manager;
		this.priority = priority;
		this.status = status;
	}
	
	@Id
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


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}

}
