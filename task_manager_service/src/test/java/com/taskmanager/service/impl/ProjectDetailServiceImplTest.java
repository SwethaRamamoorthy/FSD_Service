package com.taskmanager.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.taskmanager.model.ProjectDetailBO;
import com.taskmanager.model.ProjectDetails;
import com.taskmanager.model.Status;
import com.taskmanager.model.Task;
import com.taskmanager.repository.ProjectDetailRepository;
import com.taskmanager.repository.TaskManagerRepository;

@RunWith(MockitoJUnitRunner.class)
public class ProjectDetailServiceImplTest {
	
	@InjectMocks
	ProjectDetailServiceImpl prjDetailServiceImpl;
	
	@Mock
	ProjectDetailRepository prjDetailRepo;
	
	@Mock
	TaskManagerRepository taskManagerRepo;
	
	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void addPrjTest() {
		ProjectDetailBO prjDetail = new ProjectDetailBO();
		prjDetail.setProjectName("Test");
		prjDetail.setProjectDescription("TestProjectDescription");
		prjDetail.setManager("testManager");
		prjDetail.setPriority(1);
		prjDetail.setStartDate(randomDateGenerator());
		prjDetail.setEndDate(randomDateGenerator());
		prjDetailServiceImpl.addProject(prjDetail);
	}
	
	@Test
	public void editPrjTest() {
		ProjectDetailBO prjDetail = new ProjectDetailBO();
		prjDetail.setProjectName("Test");
		prjDetailServiceImpl.editProject("123", prjDetail);
	}
	
	@Test
	public void getPrjListTest() {
		ProjectDetails prjdetail = new ProjectDetails("123", "TestProject", randomDateGenerator(), randomDateGenerator(), "TestManager",1,Status.ACTIVE.toString());
		Task taskInput1 = new Task("124", "TestTitle1", randomDateGenerator(), randomDateGenerator(), 1, "open", "123", "TestProject");
		Task taskInput2 = new Task("125", "TestTitle2", randomDateGenerator(), randomDateGenerator(), 1, "closed", "123", "TestProject");
		List<Task> taskres = new ArrayList<Task>();
		taskres.add(taskInput1);
		taskres.add(taskInput2);
		Set<String> prjIds = new HashSet<String>();
		prjIds.add("123");
		when(taskManagerRepo.findTaskByProjectNames(prjIds)).thenReturn(taskres);
		List<ProjectDetails> prjList = new ArrayList<ProjectDetails>();
		prjList.add(prjdetail);
		when(prjDetailRepo.findAll()).thenReturn(prjList);
		List<ProjectDetailBO> userResList = prjDetailServiceImpl.getPrjList();
		assertEquals(1, userResList.size());
	}
	
	@Test
	public void getProjectByIdTest() {
		ProjectDetails prjdetails = new ProjectDetails("123", "TestProject", randomDateGenerator(), randomDateGenerator(), "TestManager",1,Status.ACTIVE.toString());
		when(prjDetailRepo.findByProjectId("123")).thenReturn(prjdetails);
		ProjectDetailBO prjDetailRes = prjDetailServiceImpl.getProjectById("123");
	}
	
	@Test
	public void updateProjectStatusTest() {
		ProjectDetails prjdetails = new ProjectDetails("123", "TestProject", randomDateGenerator(), randomDateGenerator(), "TestManager",1,Status.ACTIVE.toString());
		prjDetailServiceImpl.updateProjectStatus("123");
	}
	
	@Test
	public void searchByProjectNameTest() {
		ProjectDetails prjdetails = new ProjectDetails("123", "TestProject", randomDateGenerator(), randomDateGenerator(), "TestManager",1,Status.ACTIVE.toString());
		List<ProjectDetails> prjList = new ArrayList<ProjectDetails>();
		prjList.add(prjdetails);
		when(prjDetailRepo.searchByProjectName("TestProject")).thenReturn(prjList);
		List<ProjectDetailBO> prjDetList = prjDetailServiceImpl.searchByProjectName("TestProject");
		assertEquals(1, prjDetList.size());
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
