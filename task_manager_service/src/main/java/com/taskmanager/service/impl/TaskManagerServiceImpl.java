package com.taskmanager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskmanager.exception.BadRequestException;
import com.taskmanager.exception.NotFoundException;
import com.taskmanager.model.Status;
import com.taskmanager.model.Task;
import com.taskmanager.model.TaskListBO;
import com.taskmanager.repository.TaskManagerRepository;
import com.taskmanager.service.TaskManagerService;

@Service("taskService")
public class TaskManagerServiceImpl implements TaskManagerService {
	

	
	@Autowired
	TaskManagerRepository taskManagerRep;

	@Override
	@Transactional
	public TaskListBO addTask(TaskListBO taskInp) {
		Task task = new Task();
		UUID idOne = UUID.randomUUID();
		taskInp.setTaskId(idOne.toString());	
		task.setTaskId(taskInp.getTaskId());
		task.setTaskTitle(taskInp.getTaskTitle());
		task.setEndDate(new java.sql.Date(taskInp.getEndDate().getTime()));
		task.setStartDate(new java.sql.Date(taskInp.getStartDate().getTime()));
		task.setStatus("Open");
		task.setPriority(taskInp.getPriority());
		task.setProjectId(taskInp.getProjectId());
		task.setProjectName(taskInp.getProjectName());
		task.setUserId(taskInp.getUserId());
		if(taskInp.getParentTaskid()!=null){
			Task parentTask = taskManagerRep.findByTaskId(taskInp.getParentTaskid());
			parentTask.setTaskId(taskInp.getParentTaskid());
			task.setParentTask(parentTask);
/*			List<Task> subTaskList = new ArrayList<Task>();
			subTaskList.add(task);
			parentTask.setSubTask(subTaskList);
			taskManagerRep*/
		}
		taskManagerRep.save(task);
		return taskInp;

	}

	@Override
	public List<TaskListBO> getTaskList() throws NotFoundException {

		List<TaskListBO> taskListRes; 
		Map<String, TaskListBO> taskMap = new HashMap<String, TaskListBO>(); 
		List<Task> taskList= (List<Task>)taskManagerRep.findActiveTasks(Status.SUSPENDED.toString());
		if(taskList.isEmpty()) {
			throw new NotFoundException("No record found");
		} 
		taskMap = processTaskListResponse(taskList);
		taskListRes = new ArrayList<TaskListBO>(taskMap.values()); 
		return taskListRes;
	}

	@Override
	public TaskListBO getTaskbyId(String taskId) {
		TaskListBO taskres = new TaskListBO();
		Task task = taskManagerRep.findByTaskId(taskId);
		taskres.setTaskId(task.getTaskId());
		taskres.setTaskTitle(task.getTaskTitle());
		taskres.setEndDate(task.getEndDate());
		taskres.setStartDate(task.getStartDate());
		taskres.setPriority(task.getPriority());
		taskres.setProjectId(task.getProjectId());
		taskres.setProjectName(task.getProjectName());
		taskres.setUserId(task.getUserId());
		taskres.setParentTaskid(task.getParentTask().getTaskId());
		return taskres;
	}

	@Override
	public TaskListBO updateTask(String taskId, TaskListBO taskinp) {
		Task task = new Task();
		task.setTaskId(taskId);
		task.setTaskTitle(taskinp.getTaskTitle());
		task.setEndDate(new java.sql.Date(taskinp.getEndDate().getTime()));
		task.setStartDate(new java.sql.Date(taskinp.getStartDate().getTime()));
		task.setPriority(taskinp.getPriority());
		task.setStatus(Status.OPEN.toString());
		task.setStatus("Open");
		task.setPriority(taskinp.getPriority());
		task.setProjectId(taskinp.getProjectId());
		task.setProjectName(taskinp.getProjectName());
		task.setUserId(taskinp.getUserId());
		if(taskinp.getParentTaskid()!=null){
			Task parentTask = taskManagerRep.findByTaskId(taskinp.getParentTaskid());
			parentTask.setTaskId(taskinp.getParentTaskid());
			task.setParentTask(parentTask);
		}
		taskManagerRep.save(task);
		return taskinp;
	}
	
/*	private Date dateFormatter(String inpDate) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-mm-yyyy");
		java.util.Date date;
		java.sql.Date sqlDate = null;
		try {
			date = sdf1.parse(inpDate);
			sqlDate = new java.sql.Date(date.getTime());

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sqlDate;
	}*/
	
/*	private String dateToString(Date dateInput) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-mm-yyyy");
		String date_to_string = sdf1.format(dateInput);
		return date_to_string;
	}*/

/*	@Override
	public List<Object[]> getParentTaskList(String taskId) {
		List<Object[]> task = taskManagerRep.findParentTask(taskId);
		return null;
	}*/

	@Override
	public boolean endTaskbyId(String taskId) {
		if(!checkSubTaskStatus(taskId)){
			throw new BadRequestException("Please close sub task before parent task");
		}
		try {
			taskManagerRep.updateTaskStatus(taskId, Status.CLOSED.toString());
		}catch(Exception e){
			throw new BadRequestException("Please provide the valid task id");
		}
		return true;
	}

	public boolean checkSubTaskStatus(String taskId){
		Task parenttask = taskManagerRep.findByTaskId(taskId);
		if(!parenttask.getSubTask().isEmpty()) {
			List<Task> subTaskList = parenttask.getSubTask();
/*			for (Task task : subTaskList) {
				if(task.getStatus().equalsIgnoreCase("Open")) {
					return false;
				}
			}*/
			Optional<Task> taskOpen = subTaskList.stream().filter(t-> Status.OPEN.toString().equalsIgnoreCase(t.getStatus())).findFirst();
			if(taskOpen.isPresent()) {
				return false;
			}
		}
		return true;
	}
	
	private Map<String, TaskListBO>  processTaskListResponse(List<Task> taskList) {
		TaskListBO taskres;
		Map<String, TaskListBO> taskMap = new HashMap<String, TaskListBO>(); 
		for (Task task : taskList){

			if(task.getSubTask()!= null && !task.getSubTask().isEmpty()) {
				for(Task subTask : task.getSubTask()) {
					taskres = new TaskListBO();
					taskres.setTaskId(subTask.getTaskId());
					taskres.setTaskTitle(subTask.getTaskTitle());
					taskres.setEndDate(new java.util.Date(subTask.getEndDate().getTime()));
					taskres.setStartDate(new java.util.Date(subTask.getStartDate().getTime()));
					taskres.setParentTaskid(task.getTaskId());
					taskres.setParentTaskTitle(task.getTaskTitle());
					taskres.setPriority(subTask.getPriority());
					taskres.setProjectId(subTask.getProjectId());
					taskres.setProjectName(subTask.getProjectName());
					taskres.setUserId(subTask.getUserId());
					taskres.setStatus(subTask.getStatus());
					taskMap.put(subTask.getTaskId(), taskres);
					if(!taskMap.containsKey(task.getTaskId())) {
						TaskListBO parentTaskBO = new TaskListBO();
						parentTaskBO.setTaskId(task.getTaskId());
						parentTaskBO.setTaskTitle(task.getTaskTitle());
						parentTaskBO.setEndDate(new java.util.Date(subTask.getEndDate().getTime()));
						parentTaskBO.setStartDate(new java.util.Date(subTask.getStartDate().getTime()));
						parentTaskBO.setPriority(task.getPriority());
						parentTaskBO.setProjectId(task.getProjectId());
						parentTaskBO.setProjectName(task.getProjectName());
						parentTaskBO.setUserId(task.getUserId());
						taskMap.put(task.getTaskId(), parentTaskBO);
					}

				}
			} else if(task.getSubTask() == null || task.getSubTask().isEmpty()) {
				taskres = new TaskListBO();
				taskres.setTaskId(task.getTaskId());
				taskres.setTaskTitle(task.getTaskTitle());
				taskres.setEndDate(new java.util.Date(task.getEndDate().getTime()));
				taskres.setStartDate(new java.util.Date(task.getStartDate().getTime()));
				taskres.setPriority(task.getPriority());
				taskres.setProjectId(task.getProjectId());
				taskres.setProjectName(task.getProjectName());
				taskres.setUserId(task.getUserId());
				taskres.setStatus(task.getStatus());
				if(!taskMap.containsKey(task.getTaskId())) {
					taskMap.put(task.getTaskId(), taskres);
				}
			}
		}
		return taskMap;
	}

	@Override
	public List<TaskListBO> getTaskListByProject(String projectName) {
		List<TaskListBO> taskListRes; 
		Map<String, TaskListBO> taskMap = new HashMap<String, TaskListBO>(); 
		List<Task> taskList = taskManagerRep.findTaskByProjectName(projectName.toLowerCase());
		taskMap = processTaskListResponse(taskList);
		taskListRes = new ArrayList<TaskListBO>(taskMap.values()); 
		return taskListRes;
	}

	@Override
	public List<TaskListBO> getTaskListByPrjId(String prjId) {
		List<TaskListBO> taskListRes; 
		Map<String, TaskListBO> taskMap = new HashMap<String, TaskListBO>(); 
		List<Task> taskList = taskManagerRep.findByProjectId(prjId);
		taskMap = processTaskListResponse(taskList);
		taskListRes = new ArrayList<TaskListBO>(taskMap.values()); 
		return taskListRes;
	}

	@Override
	public boolean suspendTaskbyUser(String userId) {
		try {
			taskManagerRep.updateTaskStatusByUserId(userId, Status.SUSPENDED.toString());
		}catch(Exception e){
			return false;
		}
		return true;
	}

	@Override
	public boolean suspendTaskByProject(String projectId) {
		try {
			taskManagerRep.updateTaskStatusByProjectId(projectId, Status.SUSPENDED.toString());
		}catch(Exception e){
			return false;
		}
		return true;
	}


}
