package com.taskmanager.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.taskmanager.model.UserDetails;

@Repository
@Transactional
public interface UserDetailRepository extends CrudRepository<UserDetails,Integer> {
	
	
	public static final String SELECT_ACTIVE_USERS = "SELECT u.id,u.emp_id,u.first_name,u.last_name,u.user_status FROM user_details u WHERE u.user_status=?1";
	
	public static final String UPDATE_USER_STATUS = "UPDATE taskmanager.user_details SET user_status=?2 WHERE id=?1";
	
	public static final String SEARCH_USERS= "SELECT u.id,u.emp_id,u.first_name,u.last_name,u.user_status FROM user_details u WHERE LOWER(u.first_name) LIKE CONCAT('%',:searchTerm,'%') OR LOWER(u.last_name) LIKE CONCAT('%',:searchTerm,'%')";
	
	UserDetails findByEmpId(String empId);
	
	@Transactional
	@Query(value = SELECT_ACTIVE_USERS, nativeQuery = true)
	List<UserDetails> searchActiveUsers(String user_status);
	
	@Modifying
	@Transactional
	@Query(value = UPDATE_USER_STATUS,nativeQuery = true)
	void updateUserStatusById(String id, String user_status);
	
    @Transactional
	@Query(value = SEARCH_USERS, nativeQuery = true)
    List<UserDetails> searchUserByName(@Param("searchTerm") String userName);
	
	
	
	
	

}
