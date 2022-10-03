package com.ideas2it.training.dao.employee.impl;

import java.sql.*;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import com.ideas2it.training.model.Employee;
import com.ideas2it.training.model.LeaveRecords;
import com.ideas2it.training.model.Project;
import com.ideas2it.training.constants.EmployeeType;
import com.ideas2it.training.constants.Gender;
import com.ideas2it.training.config.DBConnection;
import com.ideas2it.training.utils.UtilDateTime;
import com.ideas2it.training.utils.hibernateUtil.HibernateSession;


import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Query; 
import org.hibernate.Criteria; 
import org.hibernate.criterion.Restrictions;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.ideas2it.training.dao.employee.EmployeeDao;

public class EmployeeDaoImpl implements EmployeeDao {

private static SessionFactory factory;

    private UtilDateTime utilDate = new UtilDateTime();

    @Override
    public boolean addEmployee(Employee employee)  {
	SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    	Session session = sessionFactory.openSession();
	boolean isAdded = false;
        List<Employee> employees = new ArrayList<>();
        try {
            Transaction transaction = session.beginTransaction();
            employee.setCreatedAt(utilDate.getCurDateTime());
	    employee.setModifiedAt(utilDate.getCurDateTime());
	    session.save(employee); 
            transaction.commit();
            isAdded = true;
        } catch (Exception e) {
            System.out.println(e);
        }
	return isAdded;
    }
			
    @Override
    public List<Employee> getEmployees() {
	SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    	Session session = sessionFactory.openSession();

        List<Employee> allEmployees= new ArrayList<Employee>();
        try {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("FROM Employee where isDeleted = false",Employee.class);
            allEmployees = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
	    session.close();
        }
	    
        return allEmployees;  
    
    }

    //@Override
    public Employee getEmployeelll(String employeeId) {
	SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    	Session session = sessionFactory.openSession();
        Employee employee = null;
	List<Object[]> listResult = null;
        try {
            Transaction transaction = session.beginTransaction();
	    Query query = session.createQuery("FROM Employee where isDeleted = false AND employeeId ='"+employeeId+"'",Employee.class);
	    employee = (Employee) query.uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
	    session.close();
	    return employee;
        } 
    }

    
    public Employee getEmployee(String employeeId) {
	SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    	Session session = sessionFactory.openSession();
        Employee employee = null;
	List<Object[]> listResult = null;
        try {
            Transaction transaction = session.beginTransaction();
	    String employeeInfo =  "from Employee as e "
                                  +"inner join LeaveRecords as l on 1=1"
                                  +"where e.employeeId = :employeeId ";
            Query query = session.createQuery(employeeInfo);
            query.setParameter("employeeId",employeeId);
            listResult = query.getResultList();
	    for(int i =0;i<listResult.size();i++) {
		Object[] arr = (Object[]) listResult.get(i);
		Employee employee1 = (Employee)arr[0];
		LeaveRecords  leaves = (LeaveRecords)arr[1];
		System.out.println(employee1);
	        System.out.println(leaves);
            }
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
	    session.close();
	    return employee;
        } 
    }


    @Override
    public String getLastEmployee() {
	SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    	Session session = sessionFactory.openSession();
        String lastId = null;
	Employee employee = null;
        try {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("FROM Employee ORDER BY employeeId DESC",Employee.class).setMaxResults(1);
	    employee = (Employee) query.uniqueResult();
	    //List<Employee> employee = query.getResultList();
	    
            lastId = employee.getEmployeeId();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
	    session.close();
        } 
        return lastId; 
    }


    @Override
    public boolean updateEmployee(Employee employee) {
	SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    	Session session = sessionFactory.openSession();
	boolean isAdded = false;
        try {
            Transaction transaction = session.beginTransaction();
	    employee.setModifiedAt(utilDate.getCurDateTime());
	    session.update(employee); 
            transaction.commit();
            isAdded = true;
        } catch (Exception e) {
            System.out.println(e);
        }
	return isAdded;
    }

    @Override
    public boolean deleteEmployee(Employee employee) {
	SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    	Session session = sessionFactory.openSession();
	boolean isAdded = false;
        try {
            Transaction transaction = session.beginTransaction();
	    employee.setIsDeleted(true);
	    employee.setModifiedAt(utilDate.getCurDateTime());
	    session.update(employee); 
            transaction.commit();
            isAdded = true;
        } catch (Exception e) {
            System.out.println(e);
        }
	return isAdded;
    }   

    public boolean assignProject(String employeeId,int projectId) {
	SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    	Session session = sessionFactory.openSession();
	boolean isAdded = false;
	Employee employee = null;
	Project project = null;


        try {
            Transaction transaction = session.beginTransaction();
	    employee = (Employee) session.get(Employee.class, employeeId);
            //Query query = session.createQuery("FROM Project where P_IS_DELETED = false AND P_ID = "+projectId,Project.class);
            List<Project> projects = employee.getProjects();
	    //List<Project> projects =  session.get(Project.class, projectId);
	    project = (Project) session.get(Project.class, projectId);
	    projects.add(project);
	    employee.setProjects(projects);
	    session.saveOrUpdate(employee);
            isAdded = true;
            transaction.commit();

        } catch (Exception e) {
            System.out.println(e);
        }
	return isAdded;
    
    }

}

    //String hql = "select * from employee where employeeId ( select employeeId from employee_projects where projectId  

