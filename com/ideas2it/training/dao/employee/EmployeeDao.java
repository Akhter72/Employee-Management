package com.ideas2it.training.dao.employee;

import java.util.List;

import com.ideas2it.training.model.Employee;

public interface EmployeeDao {

    public List<Employee> getEmployees();
    public Employee getEmployee(String employeeId);
    public Employee getEmployeeLeaves(String employeeId);
    public Employee getEmployeeProjects(String employeeId);
    public boolean updateEmployee(Employee employee);
    public boolean deleteEmployee(Employee employee);
    public boolean addEmployee(Employee employee);
    public String getLastEmployee();
    public boolean assignProject(String employeeId,int projectId);
    
}