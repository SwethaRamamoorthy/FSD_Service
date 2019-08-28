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
import com.taskmanager.model.Task;
import com.taskmanager.model.TaskListBO;
import com.taskmanager.service.TaskManagerService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TaskManagerControllerTest {

	@Autowired
	private MockMvc mockMvc;
	

	@InjectMocks
	TaskManagerController taskManagerController;

	@MockBean
	TaskManagerService taskService;
	@MockBean
	private TaskListBO taskListBO;
	private Task task;
	private List<TaskListBO> taskList;


	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(taskManagerController).build();
		taskListBO = new TaskListBO();
		taskListBO.setTaskId("123");
		taskListBO.setTaskTitle("TestTitle");
		taskListBO.setEndDate(randomDateGenerator());
		taskListBO.setStartDate(randomDateGenerator());
		taskListBO.setPriority(2);
		TaskListBO parentTask = new TaskListBO();
		taskListBO.setParentTaskid("127");
		task = new Task("123", "TestTitle", randomDateGenerator(), randomDateGenerator(), 2, "open","124", "project");
		taskList = new ArrayList<TaskListBO>();
		taskList.add(taskListBO);

	}

	
	@Test
	public void createTaskTest() throws Exception {
        when(taskService.addTask(taskListBO)).thenReturn(taskListBO);
        System.out.println(asJsonString(taskListBO));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/addtask")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(taskListBO)))
                .andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void updateTaskTest() throws Exception {
        when(taskService.updateTask("123",taskListBO)).thenReturn(taskListBO);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/updatetask/123")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(taskListBO)))
                .andExpect(status().isAccepted()).andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void getTaskTest() throws Exception {
        when(taskService.getTaskbyId("123")).thenReturn(taskListBO);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/gettaskbyId/123")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(task)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void getTaskListTest() throws Exception {
        when(taskService.getTaskList()).thenReturn(taskList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/gettask")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(taskList)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void getTaskListByProjectListTest() throws Exception {
        when(taskService.getTaskListByProject("TestProject")).thenReturn(taskList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/gettaskbyproject/TestProject")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(taskList)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void endTaskTest() throws Exception {
        when(taskService.endTaskbyId("123")).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/endtaskbyId/123")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(taskListBO)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
	}

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    
    @Test
    public void getTaskListByProjectFailureTest() throws Exception{
    	List<TaskListBO> emptyList = new ArrayList<TaskListBO>();
    	 when(taskService.getTaskListByProject("TestProject")).thenReturn(emptyList);
         mockMvc.perform(MockMvcRequestBuilders.get("/api//gettaskbyproject//TestProject")
                 .contentType(MediaType.APPLICATION_JSON).content(asJsonString(taskList)))
                 .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }
    
    @Test
    public void getTaskListByPrjIdTest() throws Exception {
    	 when(taskService.getTaskListByProject("TestProject")).thenReturn(taskList);
         mockMvc.perform(MockMvcRequestBuilders.get("/api//gettaskbyprjId/123")
                 .contentType(MediaType.APPLICATION_JSON).content(asJsonString(taskList)))
                 .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
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
