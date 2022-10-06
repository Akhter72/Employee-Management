package com.ideas2it.training.services.project;

import com.ideas2it.training.model.Project;

import java.util.List;
import java.util.ArrayList;

 
public interface ProjectServices {

    public boolean addProject(Project project);

    public List<Project> getProjects();
	
    public Project getProjectEmployees(int projectId);

    public Project getProject(int projectId);

    public boolean updateProject(Project project);

    public boolean deleteProject(Project project);

}