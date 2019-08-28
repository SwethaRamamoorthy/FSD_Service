package com.taskmanager.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskmanager.exception.BadRequestException;
import com.taskmanager.model.Status;
import com.taskmanager.model.UserDetailBO;
import com.taskmanager.model.UserDetails;
import com.taskmanager.repository.UserDetailRepository;
import com.taskmanager.service.TaskManagerService;
import com.taskmanager.service.UserDetailService;

@Service("userService")
public class UserDetailServiceImpl implements UserDetailService {

	@Autowired
	UserDetailRepository userDetailRepo;
	
	@Autowired
	TaskManagerService TaskManagerService;

	@Override
	public UserDetailBO addUser(UserDetailBO userDetails) throws BadRequestException {
		UserDetails userex =getUserByEmpId(userDetails.getEmpId());
				if(userex == null) {
					UserDetails user = new UserDetails();
					userDetails.setId(UUID.randomUUID().toString());
					user.setId(userDetails.getId());
					user.setEmpId(userDetails.getEmpId());
					user.setFirstName(userDetails.getFirstName());
					user.setLastName(userDetails.getLastName());
					user.setUserStatus(Status.ACTIVE.toString());
					userDetailRepo.save(user);
				}else {
					throw new BadRequestException("User Already Exists");
				}
		return userDetails; 		
	}

	@Override
	public UserDetailBO editUser(String userId, UserDetailBO userDetails) {
		UserDetails user = new UserDetails();
		user.setId(userId);
		user.setEmpId(userDetails.getEmpId());
		user.setFirstName(userDetails.getFirstName());
		user.setLastName(userDetails.getLastName());
		userDetailRepo.save(user);
		return userDetails;

	}

	@Override
	public UserDetails getUserByEmpId(String userName) {
		UserDetails user = userDetailRepo.findByEmpId(userName);
		return user;
	}

	@Override
	public List<UserDetails> getUserList() {
		List<UserDetails> userList = (List<UserDetails>) userDetailRepo.searchActiveUsers(Status.ACTIVE.toString());
		return userList;
	}

	@Override
	public boolean deleteUser(String id) {
		try {
			userDetailRepo.updateUserStatusById(id, Status.SUSPENDED.toString());
			boolean taskUpdateStatus = TaskManagerService.suspendTaskbyUser(id);
			if(!taskUpdateStatus){
				return false;
			}
		}catch(Exception e){
			return false;
		}
		return true;
	}

	@Override
	public List<UserDetails> searchUserByName(String userName) {
		List<UserDetails> userList = (List<UserDetails>) userDetailRepo.searchUserByName(userName);
		return userList;
	}

}
