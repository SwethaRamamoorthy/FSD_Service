package com.taskmanager.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.taskmanager.model.Status;
import com.taskmanager.model.Task;
import com.taskmanager.model.UserDetailBO;
import com.taskmanager.model.UserDetails;
import com.taskmanager.repository.UserDetailRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailServiceImplTest {

	@InjectMocks
	UserDetailServiceImpl userDetailServiceImpl;
	
	@Mock
	UserDetailRepository userDetailRepo; 
	
	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void addUserTest(){
		UserDetailBO user = new UserDetailBO();
		user.setEmpId("TestUser");
		user.setFirstName("TestFirstName");
		user.setLastName("TestLastName");
		userDetailServiceImpl.addUser(user);
	}
	
	@Test
	public void getUserByIdTest() {
		UserDetails user = new UserDetails("123","143247","TestFirstName","TestLastName",Status.ACTIVE.toString());
		when(userDetailRepo.findByEmpId("TestUser")).thenReturn(user);
		UserDetails userRes = userDetailServiceImpl.getUserByEmpId("TestUser");
		assertEquals(user, userRes);
	}
	
	@Test
	public void getUserListTest() {
		UserDetails user = new UserDetails("123","143247","TestFirstName","TestLastName",Status.ACTIVE.toString());
		UserDetails user1 = new UserDetails("123","143247","TestFirstName","TestLastName",Status.ACTIVE.toString());
		List<UserDetails> userList = new ArrayList<UserDetails>();
		userList.add(user);
		userList.add(user1);
		when(userDetailRepo.searchActiveUsers(Status.ACTIVE.toString())).thenReturn(userList);
		List<UserDetails> userRes = (List<UserDetails>)userDetailServiceImpl.getUserList();
		assertEquals(2, userRes.size());
	}
	
	
	@Test
	public void editUserTest(){
		UserDetailBO user = new UserDetailBO();
		user.setEmpId("TestUser");
		user.setFirstName("TestFirstName");
		user.setLastName("TestLastName");
		userDetailServiceImpl.editUser("TestUser", user);
	}
	
	@Test
	public void deleteUserTest() {
		UserDetails user = new UserDetails("123","143247","TestFirstName","TestLastName",Status.ACTIVE.toString());
		UserDetails user1 = new UserDetails("123","143247","TestFirstName","TestLastName",Status.ACTIVE.toString());
		List<UserDetails> userList = new ArrayList<UserDetails>();
		userList.add(user);
		userList.add(user1);
		userDetailServiceImpl.deleteUser("123");
		
	}
	
	
}
