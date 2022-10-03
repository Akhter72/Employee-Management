package com.ideas2it.training.view.employee;

import com.ideas2it.training.model.Employee;

import java.util.List;

public class EmployeeView {

    public void viewEmployees(List<Employee> employees) {
        System.out.print("+----------+---------+-------------------------+--------+");
        System.out.print("------------+-----+----------------+");
        System.out.print("--------------+----------------------+--------------+\n");
        System.out.printf("| %-8s | %-7s | %-23s | %-6s |","ID","TYPE","NAME","GENDER");
        System.out.printf(" %-10s | %-3s | %-14s |","DOB","AGE","DESIGNATION");
        System.out.printf(" %-12s | %-20s |","CONTACT","EMAIL");
        System.out.printf(" %-12s | \n","PROBATION");
        System.out.print("+----------+---------+-------------------------+--------+");
        System.out.print("------------+-----+----------------+");
        System.out.print("--------------+----------------------+--------------+\n");
        for(Employee employee:employees) {  
            System.out.printf("| %-8s ", employee.getEmployeeId());
            System.out.printf("| %-7s ", employee.getEmployeeType());
            System.out.printf("| %-23s ", employee.getName());
            System.out.printf("| %-6s ", employee.getGender());
            System.out.printf("| %-10s ", employee.getBirthdate());
            System.out.printf("| %-3s ", employee.getAge(employee.getBirthdate()));
            System.out.printf("| %-14s ", employee.getDesignation());
            System.out.printf("| %-12s ", employee.getContact());
            System.out.printf("| %-20s ", employee.getEmail());
            System.out.printf("| %-12s |", employee.getProbationTime());
	    System.out.printf("\n");

        }
        System.out.print("+----------+---------+-------------------------+--------+");
        System.out.print("------------+-----+----------------+");
        System.out.print("--------------+----------------------+--------------+\n");  
    }


    public void viewEmployee(Employee employee) {
        System.out.print("+----------+---------+-------------------------+--------+");
        System.out.print("------------+-----+----------------+");
        System.out.print("--------------+----------------------+--------------+\n");
        System.out.printf("| %-8s | %-7s | %-23s | %-6s |","ID","TYPE","NAME","GENDER");
        System.out.printf(" %-10s | %-3s | %-14s |","DOB","AGE","DESIGNATION");
        System.out.printf(" %-12s | %-20s |","CONTACT","EMAIL");
        System.out.printf(" %-12s | \n","PROBATION");
        System.out.print("+----------+---------+-------------------------+--------+");
        System.out.print("------------+-----+----------------+");
        System.out.print("--------------+----------------------+--------------+\n");
          
        System.out.printf("| %-8s ", employee.getEmployeeId());
        System.out.printf("| %-7s ", employee.getEmployeeType());
        System.out.printf("| %-23s ", employee.getName());
        System.out.printf("| %-6s ", employee.getGender());
        System.out.printf("| %-10s ", employee.getBirthdate());
        System.out.printf("| %-3s ", employee.getAge(employee.getBirthdate()));
        System.out.printf("| %-14s ", employee.getDesignation());
        System.out.printf("| %-12s ", employee.getContact());
        System.out.printf("| %-20s ", employee.getEmail());
        System.out.printf("| %-12s |", employee.getProbationTime());
	System.out.printf("\n");

    
        System.out.print("+----------+---------+-------------------------+--------+");
        System.out.print("------------+-----+----------------+");
        System.out.print("--------------+----------------------+--------------+\n");  
    }

}