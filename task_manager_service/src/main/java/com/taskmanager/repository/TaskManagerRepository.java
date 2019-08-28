package com.taskmanager.repository;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.taskmanager.model.Task;

@Repository
@Transactional
public interface TaskManagerRepository extends CrudRepository<Task,Integer> {
	
	/*
	 * public static final String FIND_PARENT_ID =
	 * "SELECT a.task_id,a.parent_id,b.task_title as parent_task_name, b.status as parent_task_status FROM taskmanager.tasklist a LEFT JOIN taskmanager.tasklist b ON b.task_id = a.parent_id where a.task_id = ?1"
	 * ;
	 */
	
	public static final String UPDATE_TASK_STATUS = "UPDATE taskmanager.tasklist SET status=?2 WHERE task_id=?1";
	
	public static final String SEARCH_TASK_PROJECT = "SELECT a.task_id, a.task_title, a.parent_id,a.start_date,a.end_date,a.priority, a.status, a.project_id, a.project_name,a.user_id,a.status FROM tasklist a WHERE LOWER(a.project_name) LIKE CONCAT('%',:searchTerm,'%')";
	
	public static final String SEARCH_TASK_PROJECTS = "SELECT a.task_id, a.task_title, a.parent_id,a.start_date,a.end_date,a.priority, a.status, a.project_id, a.project_name,a.user_id,a.status FROM tasklist a WHERE a.project_id IN (:projectName)";
	
	public static final String UPDATE_TASK_STATUS_BY_USER = "UPDATE taskmanager.tasklist SET status=?2 WHERE user_id=?1";
	
	public static final String SELECT_ACTIVE_TASKS = "SELECT a.task_id, a.task_title, a.parent_id,a.start_date,a.end_date,a.priority, a.status, a.project_id, a.project_name,a.user_id,a.status FROM tasklist a WHERE a.status<>?1";
	
	public static final String UPDATE_TASK_STATUS_BY_PROJECT = "UPDATE taskmanager.tasklist SET status=?2 WHERE project_id=?1";

	Task findByTaskId(String taskId);
	
/*	@Query(value = FIND_PARENT_ID, nativeQuery = true)
	public List<Object[]> findParentTask(String taskId);
	*/
    @Modifying
    @Transactional
	@Query(value = UPDATE_TASK_STATUS, nativeQuery = true)
	void updateTaskStatus(String taskId, String status);
    
    @Transactional
	@Query(value = SEARCH_TASK_PROJECT, nativeQuery = true)
    List<Task> findTaskByProjectName(@Param("searchTerm") String projectName);
    
    List<Task> findByProjectId(String projectId);
    
    @Query(value = SEARCH_TASK_PROJECTS, nativeQuery = true)
    List<Task> findTaskByProjectNames(@Param("projectName") Set<String> projectName);
    
    @Modifying
    @Transactional
    @Query(value = UPDATE_TASK_STATUS_BY_USER, nativeQuery = true)
    void updateTaskStatusByUserId(String userId, String status);
    
    @Query(value=SELECT_ACTIVE_TASKS, nativeQuery = true)
    List<Task> findActiveTasks(String status);
    
    @Modifying
    @Transactional
    @Query(value = UPDATE_TASK_STATUS_BY_PROJECT, nativeQuery = true)
    void updateTaskStatusByProjectId(String projectId, String status);
    
/*    @Query(nativeQuery = true, value = "SELECT DISTINCT a.task_id, a.task_title, a.parent_id,a.start_date,a.end_date,a.priority,a.status FROM taskmanager.tasklist a LEFT JOIN taskmanager.tasklist b ON b.task_id = a.parent_id")
    List<Task> findAll();*/
    

	
}
