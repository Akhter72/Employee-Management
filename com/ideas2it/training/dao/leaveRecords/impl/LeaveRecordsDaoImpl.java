package com.ideas2it.training.dao.leaveRecords.impl;

import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ideas2it.training.config.DBConnection;
import com.ideas2it.training.model.LeaveRecords;
import com.ideas2it.training.model.Employee;
import com.ideas2it.training.dao.leaveRecords.LeaveRecordsDao;
import com.ideas2it.training.constants.LeaveType;
import com.ideas2it.training.utils.UtilDateTime;

import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Query; 
import org.hibernate.Criteria; 
import org.hibernate.criterion.Restrictions;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class LeaveRecordsDaoImpl implements LeaveRecordsDao {

    private Connection con;
    private UtilDateTime utilDate = new UtilDateTime();

    private List<LeaveRecords> employees;



    @Override
    public boolean addLeaveRecords(String employeeId , LeaveRecords leaveRecord)  {
	SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    	Session session = sessionFactory.openSession();
        boolean isAdded = false;
	leaveRecord.setCreatedAt(utilDate.getCurDateTime());
	leaveRecord.setModifiedAt(utilDate.getCurDateTime());
        try {
            Transaction transaction = session.beginTransaction();
            Employee employee = (Employee) session.get(Employee.class, employeeId);
	    leaveRecord.setEmployee(employee);
            
            session.persist(leaveRecord); 
	
            transaction.commit();
            isAdded = true;
        } catch (Exception e) {
            System.out.println(e); 
        } finally {
	    session.close();
	}
        return isAdded;  
    }


    public List<LeaveRecords> getLeaveRecords() {
	SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    	Session session = sessionFactory.openSession();

        List<LeaveRecords> leaveRecords = new ArrayList<>();
        try {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("FROM LeaveRecords where L_IS_DELETED = false",LeaveRecords.class);
            leaveRecords = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
	    session.close();
        }
	    
        return leaveRecords;  
    
    }

    public List<LeaveRecords> getLeaveRecord(String employeeId) {
	SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    	Session session = sessionFactory.openSession();
        List<LeaveRecords> leaveRecords = new ArrayList<>();
        try {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("FROM LeaveRecords WHERE E_ID ='"+employeeId+"' AND L_IS_DELETED = false ",LeaveRecords.class);
            leaveRecords =query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
	    session.close();
        } 
        return leaveRecords; 
    }

    public boolean updateLeaveRecord(String employeeId,LeaveRecords leaveRecord) {

	SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    	Session session = sessionFactory.openSession();
	boolean isAdded = false;
        try {
            Transaction transaction = session.beginTransaction();
	    leaveRecord.setModifiedAt(utilDate.getCurDateTime());
            Employee employee = (Employee) session.get(Employee.class, employeeId);
	    leaveRecord.setEmployee(employee);
	    session.update(leaveRecord); 
            transaction.commit();
            isAdded = true;
        } catch (Exception e) {
            System.out.println(e);
        }
	return isAdded;
    }

/*
	boolean status = false;
	String query = "UPDATE leaveRecords SET leave_type = '"+leaveRecord.getLeaveType()+"', modified_at = '"+utilDate.getCurDateTime()+"' where leave_id = "+leaveRecord.getLeaveId()+" AND deleted = "+NOT_DELETED;//   "SELECT * FROM leaverecords where employee_id ='"+employeeId+"'";
	try {  
	    con = DBConnection.getConnection();
	    Statement statement = con.createStatement();
	    statement.execute(query);
	    status = true;
	} catch(Exception e) {
            status = false;
        } finally {
	    DBConnection.closeConnection(con);
	}
	return status;

*/    
    @Override
    public boolean deleteLeaveRecord(String employeeId,LeaveRecords leaveRecord) {

	SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    	Session session = sessionFactory.openSession();
	boolean isAdded = false;
        try {
            Transaction transaction = session.beginTransaction();
	    leaveRecord.setIsDeleted(true);
	    leaveRecord.setModifiedAt(utilDate.getCurDateTime());
	    Employee employee = (Employee) session.get(Employee.class, employeeId);
	    leaveRecord.setEmployee(employee);
	    session.update(leaveRecord); 
            transaction.commit();
            isAdded = true;
        } catch (Exception e) {
            System.out.println(e);
        }
	return isAdded;
    } 

/*
	boolean status = false;

	String query = "UPDATE leaverecords SET deleted = "+DELETED+" WHERE project_id = '"+leaveRecord.getLeaveId()+"'";
				   				  						
	try {
		con = DBConnection.getConnection();  
	        Statement statement = con.createStatement();
		statement.execute(query);
		status = true;
	} catch(Exception e) {
            System.out.println(e);
        } finally {
	    DBConnection.closeConnection(con);
	}
	return status;
*/  

}
