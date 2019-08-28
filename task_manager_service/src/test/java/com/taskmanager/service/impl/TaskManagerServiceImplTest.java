package com.taskmanager.service.impl;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.taskmanager.model.Status;
import com.taskmanager.model.Task;
import com.taskmanager.model.TaskListBO;
import com.taskmanager.repository.TaskManagerRepository;

@RunWith(MockitoJUnitRunner.class)
public class TaskManagerServiceImplTest {
	
	@InjectMocks
	TaskManagerServiceImpl taskManagerServiceImpl;
	
	@Mock
	TaskManagerRepository taskManagerRepo;
	
	Optional<Task> options;
	
	Task task; 
	
    @Before
    public void init() {
    	task = new Task();
        MockitoAnnotations.initMocks(this);
        options = Optional.of(task);
    }
	
	
	@Test
	public void addTaskTest() { 
		TaskListBO task = new TaskListBO();
		task.setTaskId("123");
		task.setTaskTitle("TestTitle");
		task.setEndDate(randomDateGenerator());
		task.setStartDate(randomDateGenerator());
		task.setPriority(2);
		TaskListBO parentTask = new TaskListBO();
		task.setParentTaskid("127");
/*		task.setParentTask(parentTask);*/
		taskManagerServiceImpl.addTask(task);
	}
	
	@Test
	public void getTaskList() {
		List<Task> taskList = new ArrayList<Task>();
		Task taskInput = new Task("123", "TestTitle", randomDateGenerator(), randomDateGenerator(), 2, "open", "124", "project");
		Task taskInput1 = new Task("124", "TestTitle1", randomDateGenerator(), randomDateGenerator(), 1, "open", "124", "project");
		List<Task> subTaskList = new ArrayList<Task>();
		subTaskList.add(taskInput);
		subTaskList.add(taskInput1);
		Task taskInput2 = new Task("125", "TestTitle1", randomDateGenerator(), randomDateGenerator(), 1, subTaskList);
		taskList.add(taskInput2);
		taskList.add(taskInput1);
		/*taskList.add(taskInput2);*/
		when(taskManagerRepo.findActiveTasks(Status.SUSPENDED.toString())).thenReturn(taskList);
		List<TaskListBO> taskRes = (List<TaskListBO>) taskManagerServiceImpl.getTaskList();
		assertEquals(3, taskRes.size());
	}
	
/*	@Test(expected = NotFoundException.class)
    public void getTaskListTestFailure() throws NotFoundException {
		List<Task> taskList1 = new ArrayList<Task>();
		when(taskManagerRepo.findAll()).thenThrow(NotFoundException.class);
		List<TaskListBO> taskRes = (List<TaskListBO>) taskManagerServiceImpl.getTaskList();
		assertEquals(taskList1, taskRes);
    }*/
	
	@Test
	public void getTaskbyId() {
		List<Task> taskList = new ArrayList<Task>();
		Task taskInput = new Task("123", "TestTitle", randomDateGenerator(), randomDateGenerator(), 2, "open", "124", "project");
		taskList.add(taskInput);
		when(taskManagerRepo.findByTaskId("123")).thenReturn(taskInput);
		TaskListBO taskRes = taskManagerServiceImpl.getTaskbyId("123");
		assertEquals("123", taskRes.getTaskId());
	}
	
	@Test
	public void updateTask() {
		TaskListBO task = new TaskListBO();
		task.setTaskId("123");
		task.setTaskTitle("TestTitle");
		task.setEndDate(randomDateGenerator());
		task.setStartDate(randomDateGenerator());
		task.setPriority(2);
		TaskListBO parentTask = new TaskListBO();
		task.setParentTaskid("127");
		taskManagerServiceImpl.updateTask("123", task);
	}
	
	@Test
	public void checkSubTaskStatusTest() {
		List<Task> taskList = new ArrayList<Task>();
		Task taskInput = new Task("123", "TestTitle", randomDateGenerator(), randomDateGenerator(), 2, "open", "124", "project");
		Task taskInput1 = new Task("124", "TestTitle1", randomDateGenerator(), randomDateGenerator(), 1, "open", "124", "project");
		List<Task> subTaskList = new ArrayList<Task>();
		subTaskList.add(taskInput);
		subTaskList.add(taskInput1);
		Task taskInput2 = new Task("125", "TestTitle1", randomDateGenerator(), randomDateGenerator(), 1, subTaskList);
		taskList.add(taskInput2);
		when(taskManagerRepo.findByTaskId("125")).thenReturn(taskInput2);
		boolean flag = taskManagerServiceImpl.checkSubTaskStatus("125");
		assertEquals(false, flag);
	}
	
	@Test
	public void endTaskTest() {
		List<Task> taskList = new ArrayList<Task>();
		Task taskInput = new Task("123", "TestTitle", randomDateGenerator(), randomDateGenerator(), 2, "closed", "124", "project");
		Task taskInput1 = new Task("124", "TestTitle1", randomDateGenerator(), randomDateGenerator(), 1, "closed", "124", "project");
		List<Task> subTaskList = new ArrayList<Task>();
		subTaskList.add(taskInput);
		subTaskList.add(taskInput1);
		Task taskInput2 = new Task("125", "TestTitle1", randomDateGenerator(), randomDateGenerator(), 1, subTaskList);
		taskList.add(taskInput2);
		when(taskManagerRepo.findByTaskId("125")).thenReturn(taskInput2);
		boolean flag = taskManagerServiceImpl.endTaskbyId("125");
		assertEquals(true, flag);
		
	}
	
	@Test
	public void getTaskListByProjectTest() {
		List<Task> taskList = new ArrayList<Task>();
		Task taskInput = new Task("123", "TestTitle", randomDateGenerator(), randomDateGenerator(), 2, "open", "124", "project");
		Task taskInput1 = new Task("124", "TestTitle1", randomDateGenerator(), randomDateGenerator(), 1, "open", "124", "project");
		List<Task> subTaskList = new ArrayList<Task>();
		subTaskList.add(taskInput);
		subTaskList.add(taskInput1);
		Task taskInput2 = new Task("125", "TestTitle1", randomDateGenerator(), randomDateGenerator(), 1, subTaskList);
		taskList.add(taskInput2);
		taskList.add(taskInput1);
		/*taskList.add(taskInput2);*/
		when(taskManagerRepo.findTaskByProjectName("projectName")).thenReturn(taskList);
		List<TaskListBO> taskRes = (List<TaskListBO>) taskManagerServiceImpl.getTaskList();
		assertEquals(3, taskRes.size());
	}
	


	
	public static java.sql.Date randomDateGenerator(){
		/*		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date dateStr;*/
				GregorianCalendar gc = new GregorianCalendar();
		        int year = randBetween(1900, 2010);
		        gc.set(gc.YEAR, year);
		        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
		        gc.set(gc.DAY_OF_YEAR, dayOfYear);
		        java.sql.Date dateDB = new java.sql.Date(gc.getTimeInMillis());
		        return dateDB;
			}
			
		    public static int randBetween(int start, int end) {
		        return start + (int)Math.round(Math.random() * (end - start));
		    }
	

}
