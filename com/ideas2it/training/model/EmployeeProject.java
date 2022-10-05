package com.ideas2it.training.model;

import java.io.Serializable;

public class EmployeeProject implements Serializable{

    private String employeeId;
    private int projectId;

    public EmployeeProject(){}
    
    public void setEmployeeId(String employeeId) {
	this.employeeId = employeeId;
    }
    
    public void setProjectId(int projectId) {
	this.projectId = projectId;
    }

    public String getEmployeeId() {
	return employeeId;
    }
    
    public int getProjectId() {
	return projectId;
    }
}

