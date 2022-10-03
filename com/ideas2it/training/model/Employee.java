package com.ideas2it.training.model;

import java.time.LocalDate;
import java.time.Period;

import java.util.List;

import com.ideas2it.training.model.LeaveRecords;
import com.ideas2it.training.model.Project;

/* program to implement pojo class */
public class Employee {
    private String employeeId;
    private String employeeType;
    private String name;
    private String gender;
    private String birthdate;
    private String designation;
    private String contact;
    private String email;
    private String probationTime;
    private List<LeaveRecords> leaves;
    List<Project> projects;
    private boolean isDeleted;
    private String createdAt;
    private String modifiedAt;

    public Employee(){}

    public Employee(String employeeId, String employeeType, String name, String gender , String birthdate, String designation, String contact,String email, String probationTime) {
	this.employeeId = employeeId;
	this.employeeType = employeeType;
	this.name = name;
	this.gender = gender;
	this.birthdate = birthdate;
	this.designation = designation;
	this.contact = contact;
	this.email = email;
	this.probationTime = probationTime;
    }


    public Employee(String employeeId, String employeeType, String name, String gender , String birthdate, String designation, String contact,String email, String probationTime,String createdAt,String modifiedAt) {
	this.employeeId = employeeId;
	this.employeeType = employeeType;
	this.name = name;
	this.gender = gender;
	this.birthdate = birthdate;
	this.designation = designation;
	this.contact = contact;
	this.email = email;
	this.probationTime = probationTime;
	this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
      
    /* setters */
    public void setEmployeeId(String employeeId) {
	this.employeeId = employeeId;
    }

    public void setEmployeeType(String employeeType) {
	this.employeeType = employeeType;
    }

    public void setName(String name) {
	this.name = name;
    }

    public void setGender(String gender) {
	this.gender = gender;
    }
    public void setBirthdate(String birthdate) {
	this.birthdate = birthdate;
    }
    public void setDesignation(String designation) {
	this.designation = designation;

    }
    public void setContact(String contact) {
	this.contact = contact;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public void setProbationTime(String probationTime) {
	this.probationTime = probationTime;
    }

    public void setLeaves(List<LeaveRecords> leaves) {
	this.leaves = leaves;
    }

    public void setProjects(List<Project> projects) {
	this.projects = projects;
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

       /*getters */

    public String getEmployeeId() {
	return employeeId;
    }

    public String getEmployeeType() {
	return employeeType;
    }
    public String getName() {
	return name;
    }

    public String getGender() {
	return gender;
    }
    public String getDesignation() {
	return designation;
    }
    public String getContact() {
	return contact;
    }

    public String getEmail() {
	return email;
    }

    public String getBirthdate() {
	return birthdate;
    }

    public String getProbationTime() {
	return probationTime;
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


    public List<LeaveRecords> getLeaves() {
	return leaves;
    }

    public List<Project> getProjects() {
	return projects;
    }


    public int getAge(String birthdate) {
	LocalDate dob = LocalDate.parse(birthdate);
	LocalDate curDate = LocalDate.now();
	int age = Period.between(dob,curDate).getYears();
	return age;
    }

    public String toString() {
	//System.out.println("ID\ttype\tname\tdob\tage\tdes\tcontact\temail\tprobat\t");

	String employeeDetails = ""+getEmployeeId()+"\t"+getEmployeeType()+"\t"+getName()+"\t"+getBirthdate()+"\t"+getAge(getBirthdate())+"\t"+getDesignation()+"\t"+getContact()+"\t"+getEmail()+"\t"+getProbationTime()+"\n";

/*
	String employeeDetails = "\n******EMPLOYEE DETAILS OF ID "+getEmployeeId()+" ARE :-******"
	+"\n Employee ID           : "+ getEmployeeId()
	+"\n EmployeeType          : "+ getEmployeeType()
	+"\n Name                  : "+ getName()
	+"\n DOB                   : "+ getBirthdate()
	+"\n Age                   : "+ getAge(getBirthdate())
	+"\n Designation           : "+ getDesignation()
	+"\n Contact               : "+ getContact()
	+"\n Email                 : "+ getEmail()
	+"\n Probation Time        : "+ getProbationTime();
*/
	return employeeDetails;	
    }


}


