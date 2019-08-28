package com.taskmanager;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskmanager.model.ProjectDetailBO;
import com.taskmanager.model.ProjectDetails;
import com.taskmanager.model.Status;
import com.taskmanager.service.ProjectDetailService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProjectManagementControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@InjectMocks
	ProjectManagementController prjManagementCtrl;
	
	@MockBean
	ProjectDetailService projectService;
	
	@MockBean
	ProjectDetailBO prjDetail;
	List<ProjectDetailBO> prjDetailList;
	ProjectDetails prjDetails;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(prjManagementCtrl).build();
		prjDetail = new ProjectDetailBO("123", "Test", "TestProject",randomDateGenerator(), randomDateGenerator(),"TestManager", 23);
/*		prjDetail.setProjectId("123");
		prjDetail.setProjectName("Test");*/
		prjDetails = new ProjectDetails(prjDetail.getProjectId(), prjDetail.getProjectName(), randomDateGenerator(), randomDateGenerator(), prjDetail.getManager(), prjDetail.getPriority(), Status.ACTIVE.toString());
		prjDetailList = new ArrayList<ProjectDetailBO>();
		prjDetailList.add(prjDetail);
	}
	
	@Test
	public void createPrjTaskTest() throws Exception {
		when(projectService.addProject(prjDetail)).thenReturn(prjDetail);
		 mockMvc.perform(MockMvcRequestBuilders.post("/api/addprj")
	                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(prjDetail)))
	                .andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void editPrjTaskTest() throws Exception {
		when(projectService.editProject("123", prjDetail)).thenReturn(prjDetail);
		 mockMvc.perform(MockMvcRequestBuilders.put("/api/updateprj/123")
	                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(prjDetail)))
	                .andExpect(status().isAccepted()).andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void getPrjListTest() throws Exception {
		when(projectService.getPrjList()).thenReturn(prjDetailList);
		 mockMvc.perform(MockMvcRequestBuilders.get("/api/getprojects")
	                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(prjDetailList)))
	                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void getPrjByIdTest() throws Exception {
		when(projectService.getProjectById("123")).thenReturn(prjDetail);
		 mockMvc.perform(MockMvcRequestBuilders.get("/api/getproject/123")
	                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(prjDetail)))
	                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void getProjectsTest() throws Exception {
		when(projectService.getPrjList()).thenReturn(prjDetailList);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/getprojects/TestProject")
	                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(prjDetailList)))
	                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void updateProjectStatusTest() throws Exception {
		when(projectService.updateProjectStatus("123")).thenReturn(true);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/updateproject/123")
	                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(prjDetailList)))
	                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
	}
	
	 public String asJsonString(final Object obj) {
	        try {
	            return new ObjectMapper().writeValueAsString(obj);
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
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
