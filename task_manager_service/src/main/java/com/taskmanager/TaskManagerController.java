package com.taskmanager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanager.exception.BadRequestException;
import com.taskmanager.model.ProjectDetails;
import com.taskmanager.model.Task;
import com.taskmanager.model.TaskListBO;
import com.taskmanager.service.ProjectDetailService;
import com.taskmanager.service.TaskManagerService;

@RestController
@RequestMapping("/api")
public class TaskManagerController {

	@Autowired
	TaskManagerService taskService;

	@Autowired
	ProjectDetailService projectService;

	@CrossOrigin
	@RequestMapping(value = "/addtask", method = RequestMethod.POST)
	public ResponseEntity<TaskListBO> addTask(@RequestBody TaskListBO task) {
		TaskListBO taskres = taskService.addTask(task);
		return new ResponseEntity<TaskListBO>(taskres, HttpStatus.CREATED);
	}

	@CrossOrigin
	@RequestMapping(value = "/updatetask/{id}", method = RequestMethod.PUT)
	public ResponseEntity<TaskListBO> updateTask(@PathVariable String id, @RequestBody TaskListBO task) {
		taskService.updateTask(id, task);
		return new ResponseEntity<TaskListBO>(task, HttpStatus.ACCEPTED);

	}

	@CrossOrigin
	@RequestMapping(value = "/gettask", method = RequestMethod.GET)
	public ResponseEntity<List<TaskListBO>> getTaskList() throws Exception {
		List<TaskListBO> taskList = new ArrayList<TaskListBO>();

		taskList = taskService.getTaskList();

		return new ResponseEntity<List<TaskListBO>>(taskList, HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/gettaskbyId/{id}", method = RequestMethod.GET)
	public ResponseEntity<TaskListBO> getTaskById(@PathVariable String id) {
		TaskListBO task = taskService.getTaskbyId(id);
		return new ResponseEntity<TaskListBO>(task, HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/endtaskbyId/{id}", method = RequestMethod.PUT)
	public ResponseEntity<TaskListBO> endTaskById(@PathVariable String id) {
		String status = null;
		TaskListBO taskRes = new TaskListBO();
		try {
			boolean task = taskService.endTaskbyId(id);
			taskRes.setMessage("End task is succesfully done");
		} catch (BadRequestException e) {
			taskRes.setMessage(e.getMessage());
			return new ResponseEntity<TaskListBO>(taskRes, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<TaskListBO>(taskRes, HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/gettaskbyproject/{projectName}", method = RequestMethod.GET)
	public ResponseEntity<List<TaskListBO>> getTaskListByProject(@PathVariable String projectName) throws Exception {

		List<TaskListBO> taskList = new ArrayList<TaskListBO>();

		taskList = taskService.getTaskListByProject(projectName);
		if(taskList.isEmpty()){
			TaskListBO taskBO = new TaskListBO();
			taskBO.setMessage("No Project Found for search term");
			taskList.add(taskBO);
		}

		return new ResponseEntity<List<TaskListBO>>(taskList, HttpStatus.OK);
	}
	
	
	@CrossOrigin
	@RequestMapping(value = "/gettaskbyprjId/{prjId}", method = RequestMethod.GET)
	public ResponseEntity<List<TaskListBO>> getTaskListByPrjId(@PathVariable String prjId) throws Exception {

		List<TaskListBO> taskList = new ArrayList<TaskListBO>();

		taskList = taskService.getTaskListByProject(prjId);

		return new ResponseEntity<List<TaskListBO>>(taskList, HttpStatus.OK);
	}
	
}
