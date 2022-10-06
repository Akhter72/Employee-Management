package com.ideas2it.training.services.employee.impl;

import java.util.*;
import java.util.Scanner;

import com.ideas2it.training.model.Employee;
import com.ideas2it.training.dao.employee.EmployeeDao;
import com.ideas2it.training.dao.employee.impl.EmployeeDaoImpl;
import com.ideas2it.training.services.employee.EmployeeServices;
import com.ideas2it.training.constants.EmployeeType;
import com.ideas2it.training.constants.Gender;

public class EmployeeServicesImpl implements EmployeeServices {

    private EmployeeDao employeeDao = new EmployeeDaoImpl();

    public boolean addEmployee(Employee employee) {
	//Employee employee = new Employee(employeeId,employeeType,name,gender,birthdate,designation,contact,email,probationTime);
	return employeeDao.addEmployee(employee);
			
    }


    public List<Employee> getEmployees() {
	return employeeDao.getEmployees();	
    }


   @Override 
    public Employee getEmployee(String employeeId) {
        return employeeDao.getEmployee(employeeId);		
    }

   @Override 
    public Employee getEmployeeLeaves(String employeeId) {
        return employeeDao.getEmployeeLeaves(employeeId);		
    }

   @Override 
    public Employee getEmployeeProjects(String employeeId) {
        return employeeDao.getEmployeeProjects(employeeId);		
    }

    @Override
    public boolean updateEmployee(Employee employee) {
	return employeeDao.updateEmployee(employee);
    }

    @Override
    public boolean deleteEmployee(Employee employee) {
	//leaveDao.deleteLeaveRecord(employee.getEmployeeId());
	//projectDao.deleteProject(employee.getEmployeeId());
	return employeeDao.deleteEmployee(employee);
    }

    public String getLastEmployee() {
	return employeeDao.getLastEmployee();
    }

    public String generateEmployeeId() {
	String employeeId;
	String prefixId = "I2IT";
	String postfixId = "";

	String lastId = getLastEmployee();
	if (lastId == null) {
	    employeeId = prefixId + 100;
	} else {
	    for(int i = prefixId.length() ; i < lastId.length() ; i++) 
		postfixId +=lastId.charAt(i); 
	    int num = Integer.parseInt(postfixId) + 1;
	    employeeId = prefixId+num;	    
	}
        return employeeId;
    }

    public boolean assignProject(String employeeId,int projectId) {
	return employeeDao.assignProject(employeeId,projectId);
    }

}

