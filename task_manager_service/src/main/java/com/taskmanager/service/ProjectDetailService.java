package com.taskmanager.service;

import java.util.List;

import com.taskmanager.model.ProjectDetailBO;
import com.taskmanager.model.ProjectDetails;

public interface ProjectDetailService {
	
	ProjectDetailBO addProject(ProjectDetailBO prjDetails);
	
	ProjectDetailBO editProject(String projectId, ProjectDetailBO prjDetails);
	
	List<ProjectDetailBO> getPrjList();
	
	ProjectDetailBO getProjectById(String projectId);
	
	List<ProjectDetailBO> searchByProjectName(String prjName);
	
	boolean updateProjectStatus(String projectId);
	
}
                           