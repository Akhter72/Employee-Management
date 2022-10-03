package com.ideas2it.training.model;

import java.util.List;

import java.util.Date;

public class Project {

    private int projectId;
    private String name;
    private String employeeId;
    private String startDate;
    private String description;
    private String projectManager;
    private String clientName;
    private boolean isDeleted;
    private String createdAt;
    private String modifiedAt;
    private List<Employee> employees;
    public Project(){}

    public Project(String name, String startDate, String description, String projectManager, String clientName) {
	this.name = name;
	this.startDate = startDate;
	this.description = description;
	this.projectManager = projectManager;
	this.clientName = clientName;
    }
    public Project(int projectId,String name, String employeeId, String startDate, String description, String projectManager, String clientName) {
	this.projectId = projectId;
	this.name = name;
        this.employeeId = employeeId;
	this.startDate = startDate;
	this.description = description;
	this.projectManager = projectManager;
	this.clientName = clientName;
    }

    public void setName(String name) {
	this.name = name;
    }	

    public void setProjectId(int projectId) {
	this.projectId = projectId;
    }

    public void setEmployeeId(String employeeid) {
	this.employeeId = employeeid;
    }

    public void setStartDate(String startDate) {
	this.startDate = startDate;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public void setProjectManager(String projectManager) {
	this.projectManager = projectManager;
    }

    public void setClientName(String clientName) {
	this.clientName = clientName;
    }

    public void setIsDeleted(boolean isDeleted) {
	this.isDeleted = isDeleted;
    }

    public void setCreatedAt(String createdAt) {
	this.createdAt = createdAt;
    }

    public void setModifiedAt(String modifiedAt) {
	this.modifiedAt = modifiedAt;
    }


    /* Getters */

    public int getProjectId() {
	return projectId;
    }

    public String getName() {
	return name;
    }

    public String getEmployeeId() {
	return employeeId;
    }

    public String getStartDate() {
	return startDate;
    }

    public String getDescription() {
	return description;
    }

    public String getProjectManager() {
	return projectManager;
    }

    public String getClientName() {
	return clientName;
    }


    public boolean getIsDeleted() {
	return isDeleted;
    }

    public String getCreatedAt() {
	return createdAt;
    }

    public String getModifiedAt() {
	return modifiedAt;
    }

    public void setEmployees(List<Employee> employees) {
	this.employees = employees;
    }
		
    public List<Employee> getEmployees() {
	return employees;
    }

}