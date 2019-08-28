package com.taskmanager.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.net.ssl.SSLEngineResult.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskmanager.model.ProjectDetailBO;
import com.taskmanager.model.ProjectDetails;
import com.taskmanager.model.Task;
import com.taskmanager.repository.ProjectDetailRepository;
import com.taskmanager.repository.TaskManagerRepository;
import com.taskmanager.service.ProjectDetailService;
import com.taskmanager.service.TaskManagerService;


@Service("projectService")
public class ProjectDetailServiceImpl implements ProjectDetailService {

	@Autowired
	ProjectDetailRepository prjDetailRepo;
	
	@Autowired
	TaskManagerRepository taskManagerRep;
	
	@Autowired
	TaskManagerService taskManagerService;
	
	@Override
	public ProjectDetailBO addProject(ProjectDetailBO prjDetails) {
		ProjectDetails prjDetail = new ProjectDetails();
		UUID prjId = UUID.randomUUID();
		prjDetail.setProjectId(prjId.toString());
		prjDetail.setProjectName(prjDetails.getProjectName());
		prjDetail.setStartDate(new java.sql.Date(prjDetails.getStartDate().getTime()));
		prjDetail.setEndDate(new java.sql.Date(prjDetails.getEndDate().getTime()));
		prjDetail.setManager(prjDetails.getManager());
		prjDetail.setPriority(prjDetails.getPriority());
		prjDetail.setStatus(com.taskmanager.model.Status.ACTIVE.toString());
		prjDetailRepo.save(prjDetail);
		prjDetails.setProjectId(prjId.toString());
		return prjDetails; 
	}

	@Override
	public ProjectDetailBO editProject(String projectId, ProjectDetailBO prjDetails) {
		ProjectDetails prjDetail = new ProjectDetails();
		prjDetail.setProjectId(projectId);
		prjDetail.setProjectName(prjDetails.getProjectName());
		prjDetailRepo.save(prjDetail);
		return prjDetails;

	}

	@Override
	public List<ProjectDetailBO> getPrjList() {

		List<ProjectDetailBO> prjListRes = new ArrayList<ProjectDetailBO>();
		List<ProjectDetails> prjList = (List<ProjectDetails>)prjDetailRepo.findActiveProjects(com.taskmanager.model.Status.SUSPENDED.toString());
		prjListRes = processProjectDetails(prjList);
		return prjListRes; 
	}

	@Override
	public ProjectDetailBO getProjectById(String projectId) {
		ProjectDetailBO prjDetailBO = new ProjectDetailBO();
		int completedTotal = 0;
		int taskTotal = 0;
		ProjectDetails projectDetails = prjDetailRepo.findByProjectId(projectId);
		Set<String> prjNameSet = new HashSet<String>();
		prjNameSet.add(projectDetails.getProjectId());
		List<Task> taskListRes = taskManagerRep.findTaskByProjectNames(prjNameSet);
		Map<String,List<Task>> prjStatusCountMap = taskListRes.stream().collect(Collectors.groupingBy(Task::getStatus));
		if(prjStatusCountMap.get(projectDetails.getProjectId())!=null && prjStatusCountMap.get(Status.CLOSED.toString())!=null){
			completedTotal = prjStatusCountMap.get(Status.CLOSED.toString()).size();
		}
		if(prjStatusCountMap.get(projectDetails.getProjectId())!=null) {
			taskTotal = taskListRes.size();
		}
		prjDetailBO.setProjectId(projectDetails.getProjectId());
		prjDetailBO.setProjectName(projectDetails.getProjectName());
		prjDetailBO.setStartDate(projectDetails.getStartDate());
		prjDetailBO.setEndDate(projectDetails.getEndDate());
		prjDetailBO.setTotalCompleted(completedTotal);
		prjDetailBO.setTotalTask(taskTotal);
		prjDetailBO.setPriority(projectDetails.getPriority());
		prjDetailBO.setManager(projectDetails.getManager());
		return prjDetailBO;
	}

	
	@Override
	public List<ProjectDetailBO> searchByProjectName(String prjName) {
		List<ProjectDetailBO> prjListRes = new ArrayList<ProjectDetailBO>();
		List<ProjectDetails> prjList = (List<ProjectDetails>)prjDetailRepo.searchByProjectName(prjName);
		prjListRes = processProjectDetails(prjList);
		return prjListRes; 
	}
	
	private List<ProjectDetailBO> processProjectDetails(List<ProjectDetails> prjList) {
		List<ProjectDetailBO> prjListRes = new ArrayList<ProjectDetailBO>();
		ProjectDetailBO prjDetailBO;
		int completedTotal = 0;
		int taskTotal = 0;
		if(prjList.size() > 0){
			Set<String> prjNameSet = prjList.stream().map(prj -> prj.getProjectId()).collect(Collectors.toSet());
			List<Task> taskListRes = taskManagerRep.findTaskByProjectNames(prjNameSet);
			Map<String,Map<String,List<Task>>> prjStatusCountMap = taskListRes.stream().collect(Collectors.groupingBy(Task::getProjectId,Collectors.groupingBy(Task::getStatus)));
			Map<String,List<Task>> prjTaskCount = taskListRes.stream().collect(Collectors.groupingBy(Task::getProjectId));
			for(ProjectDetails prjDetails : prjList){
				prjDetailBO = new ProjectDetailBO();
				if(prjStatusCountMap.get(prjDetails.getProjectId())!=null && prjStatusCountMap.get(prjDetails.getProjectId()).get(Status.CLOSED.toString())!=null){
					completedTotal = prjStatusCountMap.get(prjDetails.getProjectId()).get(Status.CLOSED.toString()).size();
				}
				if(prjTaskCount.get(prjDetails.getProjectId())!=null) {
					taskTotal = prjTaskCount.get(prjDetails.getProjectId()).size();
				}
				prjDetailBO.setProjectId(prjDetails.getProjectId());
				prjDetailBO.setProjectName(prjDetails.getProjectName());
				prjDetailBO.setStartDate(prjDetails.getStartDate());
				prjDetailBO.setEndDate(prjDetails.getEndDate());
				prjDetailBO.setTotalCompleted(completedTotal);
				prjDetailBO.setTotalTask(taskTotal);
				prjDetailBO.setPriority(prjDetails.getPriority());
				prjDetailBO.setManager(prjDetails.getManager());
				prjListRes.add(prjDetailBO);
			}
		}

		return prjListRes;
	}

	@Override
	public boolean updateProjectStatus(String projectId) {
	try {
		prjDetailRepo.updateProjectStatus(projectId, com.taskmanager.model.Status.SUSPENDED.toString());
		boolean updateTaskStatus = taskManagerService.suspendTaskByProject(projectId);
		if(!updateTaskStatus) {
			return false;
		}
	}catch(Exception e){
		return false;
	}
		return true;
	}
}
