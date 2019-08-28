package com.taskmanager;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
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
import com.taskmanager.model.UserDetailBO;
import com.taskmanager.model.UserDetails;
import com.taskmanager.service.UserDetailService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserManagementControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@InjectMocks
	UserManagementController userManagementCtrl;
	
	@MockBean
	UserDetailService userService;
	
	@MockBean
	UserDetailBO user;
	List<UserDetails> userList;
	UserDetails userDetails;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userManagementCtrl).build();
		user = new UserDetailBO();
		user.setEmpId("TestUser");
		user.setFirstName("TestFirstName");
		user.setLastName("TestLastName");
		userDetails = new UserDetails("123", user.getEmpId(), user.getFirstName(),user.getLastName(),Status.ACTIVE.toString());
		userList = new ArrayList<UserDetails>();
		userList.add(userDetails);
	}
	
	@Test
	public void createUserTest() throws Exception {
		when(userService.addUser(user)).thenReturn(user);
		 mockMvc.perform(MockMvcRequestBuilders.post("/api/adduser")
	                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
	                .andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void editUserTest() throws Exception {
		when(userService.editUser("123", user)).thenReturn(user);
		 mockMvc.perform(MockMvcRequestBuilders.put("/api/updateuser/123")
	                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
	                .andExpect(status().isAccepted()).andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void getUserListTest() throws Exception {
		when(userService.getUserList()).thenReturn(userList);
		 mockMvc.perform(MockMvcRequestBuilders.get("/api/getusers")
	                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(userList)))
	                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void getUserByIdTest() throws Exception {
		when(userService.getUserByEmpId("TestUser")).thenReturn(userDetails);
		 mockMvc.perform(MockMvcRequestBuilders.get("/api/getuser/123")
	                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(userDetails)))
	                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
	}
	
	
	
	@Test
	public void deleteUserByIdTest() throws Exception {
		when(userService.deleteUser("TestUser")).thenReturn(true);
		 mockMvc.perform(MockMvcRequestBuilders.put("/api/deleteuser/123")
	                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(userDetails)))
	                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void deleteUserByIdTestFailure() throws Exception {
		when(userService.deleteUser("TestUser")).thenReturn(false);
		 mockMvc.perform(MockMvcRequestBuilders.put("/api/deleteuser/123")
	                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(userDetails)))
	                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
	}
	
	 public String asJsonString(final Object obj) {
	        try {
	            return new ObjectMapper().writeValueAsString(obj);
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
	    }
	
}
