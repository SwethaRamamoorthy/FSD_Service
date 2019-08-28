package com.taskmanager.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "tasklist")
public class Task {

	@Column(name="task_id")
	String taskId;
	@Column(name="task_title")
	String taskTitle;
	@Column(name = "start_date")
	Date startDate;
	@Column(name = "end_date")
	Date endDate;
	@Column(name = "priority")
	int priority;
	@Column(name = "status")
	String status;
	@Column(name = "project_id")
	String projectId;
	@Column(name = "project_name")
	String projectName;
	@Column(name = "user_id")
	String userId;
	
/*	String parentId;*/
	

    private	Task parentTask;
	
	
	private List<Task> subTask;
	
	public Task(String taskId, String taskTitle, Date startDate, Date endDate, int priority,String status, String projectId,String projectName) {
		super();
		this.taskId = taskId;
		this.taskTitle = taskTitle;
		this.startDate = startDate;
		this.endDate = endDate;
		this.priority = priority;
		this.status = status;
		this.projectId = projectId;
		this.projectName = projectName;
	}
	
	public Task() {
		
	}
	
	public Task(String taskId, String taskTitle, Date startDate, Date endDate, int priority,List<Task> subTask) {
		super();
		this.taskId = taskId;
		this.taskTitle = taskTitle;
		this.startDate = startDate;
		this.endDate = endDate;
		this.priority = priority;
/*		this.parentTask = parentTask;*/
		this.subTask = subTask;

	}
	
	
	@Id
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
	

/*	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}*/
	

    @ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name = "parent_id")
	public Task getParentTask() {
		return parentTask;
	}

	public void setParentTask(Task parentTask) {
		this.parentTask = parentTask;
	}
	
	@OneToMany(mappedBy = "parentTask")
	public List<Task> getSubTask() {
		return subTask;
	}
	public void setSubTask(List<Task> subTask) {
		this.subTask = subTask;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
