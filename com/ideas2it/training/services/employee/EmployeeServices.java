package com.ideas2it.training.services.employee;

import com.ideas2it.training.model.Employee;
import com.ideas2it.training.model.Project;
import com.ideas2it.training.model.LeaveRecords;

import java.util.List;
import java.util.ArrayList;

import com.ideas2it.training.constants.EmployeeType;
import com.ideas2it.training.constants.Gender;
 
public interface EmployeeServices {

    /**
    * this method is used to take Employee details as input, creates employee Object.
    * then Doa Layer is called with object as paramenter.
    * doa Layer adds the object into employee DataBase.   
    * @param EmployeeId , to set ID for Employee.
    * @param employeeType , to set ID for Employee.
    * @param name , to set name of Employee.
    * @param designation , to set designation of Employee.
    * @param contact , to set contact of Employee
    * @param email , to set email of Employee
    * @param probationTime , to set probationTime of Employee
    */

    public boolean addEmployee(Employee employee);

    /**
    * this methods is used to get list of Trainees in Trainees Database.
    * deosnot accept any parameter from controller
    * calls Dao Layer to get the all set of trainees from DataBase.
    * @return List Containig details of all Trainees. 
    */
    public List<Employee> getEmployees();

    /**
    * this method is used to take Employee id as input to find whether Employee is present in data base or not
    * if user in database, return details of the given empoyee id which is inturn returned from Dao layer.
    * @return , if trainee in Db, Trainne obj with details will be returned , else null.
    */

    public Employee getEmployee(String EmployeeId);

    /**
    * this method is used to update the probationTime of the employee
    * Dao layer is called and object passed as parameter.
    * Dao layer will update the field for us using by object passed.
    * @param employee of type Employee, its object with new probationTime to set for the trainee.
    */
    public boolean updateEmployee(Employee employee);

    /**
    * this method is used to remove the employee
    * Dao layer is called and object passed as parameter.
    * Dao layer will remove the Object for us using by object passed.
    * @param employee of type Employee,
    */
    public boolean deleteEmployee(Employee employee);

    public String getLastEmployee();

    public String generateEmployeeId();

    public boolean assignProject(String employeeId,int projectId);


}