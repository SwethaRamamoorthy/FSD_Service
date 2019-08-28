package com.taskmanager;

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

import com.taskmanager.model.ProjectDetailBO;
import com.taskmanager.service.ProjectDetailService;

@RestController
@RequestMapping("/api")
public class ProjectManagementController {

	@Autowired
	ProjectDetailService projectService;

	@CrossOrigin
	@RequestMapping(value = "/addprj", method = RequestMethod.POST)
	public ResponseEntity<ProjectDetailBO> addPrj(@RequestBody ProjectDetailBO prjDetails) {
		ProjectDetailBO prjDetailRes =  projectService.addProject(prjDetails);
		return new ResponseEntity<ProjectDetailBO>(prjDetailRes, HttpStatus.CREATED);
	}

	@CrossOrigin
	@RequestMapping(value = "/updateprj/{projectId}", method = RequestMethod.PUT)
	public ResponseEntity<ProjectDetailBO> updatePrj(@PathVariable String projectId, @RequestBody ProjectDetailBO prjDetails) {
		ProjectDetailBO prjDetailRes =	projectService.editProject(projectId, prjDetails);
		return new ResponseEntity<ProjectDetailBO>(prjDetailRes, HttpStatus.ACCEPTED);
	}

	@CrossOrigin
	@RequestMapping(value = "/getproject/{projectId}", method = RequestMethod.GET)
	public ResponseEntity<ProjectDetailBO> getPrjById(@PathVariable String projectId) throws Exception {
		ProjectDetailBO prjDetails = projectService.getProjectById(projectId);
		return new ResponseEntity<ProjectDetailBO>(prjDetails, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/getprojects", method = RequestMethod.GET)
	public ResponseEntity<List<ProjectDetailBO>> getProjects() throws Exception {
		List<ProjectDetailBO> prjList = projectService.getPrjList();
		return new ResponseEntity<List<ProjectDetailBO>>(prjList, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/getprojects/{prjName}", method = RequestMethod.GET)
	public ResponseEntity<List<ProjectDetailBO>> getProjects(@PathVariable String prjName) throws Exception {
		List<ProjectDetailBO> prjList = projectService.searchByProjectName(prjName);
		return new ResponseEntity<List<ProjectDetailBO>>(prjList, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/updateproject/{prjId}", method = RequestMethod.PUT)
	public ResponseEntity<ProjectDetailBO> updateProjectStatus(@PathVariable String prjId) throws Exception {
		ProjectDetailBO projectDetailBO = new ProjectDetailBO();
		boolean updateStatus = projectService.updateProjectStatus(prjId);
		if(updateStatus) {
			projectDetailBO.setMessage("Project Status update is succesfull");
		}else {
			projectDetailBO.setMessage("Project suspension is not done as expected, validate the request");
		}
		return new ResponseEntity<ProjectDetailBO>(projectDetailBO, HttpStatus.OK);
	}

}
