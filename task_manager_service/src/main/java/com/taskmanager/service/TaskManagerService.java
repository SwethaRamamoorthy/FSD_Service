package com.taskmanager.service;

import java.util.List;

import com.taskmanager.exception.NotFoundException;
import com.taskmanager.model.Task;
import com.taskmanager.model.TaskListBO;


public interface TaskManagerService {
	
	TaskListBO addTask(TaskListBO task);
	
	List<TaskListBO> getTaskList() throws NotFoundException;
	
	TaskListBO getTaskbyId(String taskId);
	
	TaskListBO updateTask(String taskId, TaskListBO taskinp);
	
/*	List<Object[]> getParentTaskList(String taskId);*/
	
	boolean endTaskbyId(String taskId);
	
	boolean suspendTaskbyUser(String userId);
	
	boolean suspendTaskByProject(String projectId);
	
	List<TaskListBO> getTaskListByProject(String projectName);

	List<TaskListBO> getTaskListByPrjId(String prjId);

}
