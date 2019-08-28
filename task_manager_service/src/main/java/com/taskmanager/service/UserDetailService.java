package com.taskmanager.service;

import java.util.List;

import com.taskmanager.model.UserDetailBO;
import com.taskmanager.model.UserDetails;

public interface UserDetailService {
	
	UserDetailBO addUser(UserDetailBO userDetails);
	
	UserDetailBO editUser(String userId, UserDetailBO userDetails);
	
	UserDetails getUserByEmpId(String empId);
	
    List<UserDetails> getUserList();
    
    boolean deleteUser(String id);
    
    List<UserDetails> searchUserByName(String userName);

}
