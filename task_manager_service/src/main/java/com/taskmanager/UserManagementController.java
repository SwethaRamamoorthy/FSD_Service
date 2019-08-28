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
import com.taskmanager.model.UserDetailBO;
import com.taskmanager.model.UserDetails;
import com.taskmanager.service.UserDetailService;

@RestController
@RequestMapping("/api")
public class UserManagementController {
	
	@Autowired
	UserDetailService userService;
	
	@CrossOrigin
	@RequestMapping(value = "/adduser", method = RequestMethod.POST)
	public ResponseEntity<UserDetailBO> addUser(@RequestBody UserDetailBO userDetails) {
		UserDetailBO userRes = new UserDetailBO();
		try {
			userRes = userService.addUser(userDetails);
		}catch(BadRequestException e) {
			userRes.setStatusMsg("User Already Exists");
			/*return new ResponseEntity<UserDetailBO>(userRes, HttpStatus.BAD_REQUEST);*/
		}
		return new ResponseEntity<UserDetailBO>(userRes, HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/updateuser/{id}", method = RequestMethod.PUT)
	public ResponseEntity<UserDetailBO> updateUser(@PathVariable String id, @RequestBody UserDetailBO userDetails) {
		UserDetailBO userRes =  userService.editUser(id, userDetails);
		return new ResponseEntity<UserDetailBO>(userRes, HttpStatus.ACCEPTED);
		
	}
	
	@CrossOrigin
	@RequestMapping(value = "/getuser/{empId}", method = RequestMethod.GET)
	public  ResponseEntity<UserDetails> getUserByEmpId(@PathVariable String empId) {
		UserDetails user = userService.getUserByEmpId(empId);
		return new ResponseEntity<UserDetails>(user, HttpStatus.OK);
	}
	
	
	@CrossOrigin
	@RequestMapping(value = "/getusers", method = RequestMethod.GET)
	public ResponseEntity<List<UserDetails>> getUserList() throws Exception {
		List<UserDetails> userList = new ArrayList<UserDetails>();
			userList = userService.getUserList();
		return new ResponseEntity<List<UserDetails>>(userList, HttpStatus.OK);
	}
	
	
	@CrossOrigin
	@RequestMapping(value = "/searchusers/{name}", method = RequestMethod.GET)
	public ResponseEntity<List<UserDetails>> searchUser(@PathVariable String name) throws Exception {
		List<UserDetails> userList = new ArrayList<UserDetails>();
			userList = userService.searchUserByName(name);
		return new ResponseEntity<List<UserDetails>>(userList, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/deleteuser/{id}", method = RequestMethod.PUT)
	public ResponseEntity<UserDetailBO> deleteuser(@PathVariable String id) throws Exception {
		UserDetailBO userDetailResponse = new UserDetailBO();
			boolean updateStatus =   userService.deleteUser(id);
			if(updateStatus) {
				userDetailResponse.setStatusMsg("User got suspended succesfully");
			}else {
				userDetailResponse.setStatusMsg("User suspension is not done as expected, validate the request");
			}
		return new ResponseEntity<UserDetailBO>(userDetailResponse, HttpStatus.OK);
	}

}
