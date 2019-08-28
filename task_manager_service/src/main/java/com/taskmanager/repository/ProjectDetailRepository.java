package com.taskmanager.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.taskmanager.model.ProjectDetails;

@Repository
@Transactional
public interface ProjectDetailRepository extends CrudRepository<ProjectDetails,Integer> {
	
	
	public static final String SEARCH_PROJECTS = "SELECT prj.project_id, prj.project_name, prj.start_date,prj.end_date,prj.priority,prj.manager,prj.status FROM project_details prj WHERE LOWER(prj.project_name) LIKE CONCAT('%',:searchTerm,'%')";
	
	public static final String SELECT_ACTIVE_PROJECTS = "SELECT prj.project_id, prj.project_name, prj.start_date,prj.end_date,prj.priority,prj.manager,prj.status FROM project_details prj WHERE prj.status<>?1";
	
	public static final String UPDATE_PROJECT_STATUS = "UPDATE taskmanager.project_details SET status=?2 WHERE project_id=?1";
	
	ProjectDetails findByProjectId(String projectId);
	
    @Transactional
	@Query(value = SEARCH_PROJECTS, nativeQuery = true)
    List<ProjectDetails> searchByProjectName(@Param("searchTerm") String projectName);
    
    @Transactional
   	@Query(value = SELECT_ACTIVE_PROJECTS, nativeQuery = true)
    List<ProjectDetails> findActiveProjects(String projectStatus);
    
    
    @Modifying
    @Transactional
	@Query(value = UPDATE_PROJECT_STATUS, nativeQuery = true)
	void updateProjectStatus(String projectId, String status);
    
    

}
