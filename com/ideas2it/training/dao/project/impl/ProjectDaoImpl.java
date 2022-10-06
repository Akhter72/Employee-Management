 package com.ideas2it.training.dao.project.impl;

import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ideas2it.training.config.DBConnection;
import com.ideas2it.training.model.Project;
import com.ideas2it.training.model.Employee;
import com.ideas2it.training.dao.project.ProjectDao;
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

public class ProjectDaoImpl implements ProjectDao {
    private final int DELETED = 1;
    private final int NOT_DELETED = 0;
    private Connection con;
    private UtilDateTime utilDate = new UtilDateTime();

    @Override
    public boolean addProject(Project project)  {


	SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    	Session session = sessionFactory.openSession();
        boolean isAdded = false;
	project.setCreatedAt(utilDate.getCurDateTime());
	project.setModifiedAt(utilDate.getCurDateTime());
        try {
            Transaction transaction = session.beginTransaction();

            session.persist(project); 
	
            transaction.commit();
            isAdded = true;
        } catch (Exception e) {
            System.out.println(e); 
        } finally {
	    session.close();
	}
        return isAdded;  
    }


    @Override
    public List<Project> getProjects() {
        List<Project> projects = new ArrayList<>();
	Session session = null;
        try {
	    session = HibernateSession.getSession();
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("FROM Project where isDeleted = false",Project.class);
            projects = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
	    session.close();
        }  
        return projects;  
    
    }

    public Project getProjectEmployees(int projectId) {
	List<Object[]> joinedList = new ArrayList<>();
	List<Employee> employees = new ArrayList<>();
        Project project = null;
	Session session = null;
        try {
            session = HibernateSession.getSession();
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("From Project p "
					     +"INNER JOIN Employee e "
					     +"ON e.employeeId IN (select ep.employeeId from EmployeeProject ep where ep.projectId = :projectId) " 
					     + "where p.projectId = :projectId");
	    query.setParameter("projectId",projectId);
            joinedList =  query.getResultList();
	    if ( joinedList.size() > 0) {
		for(int i=0; i<joinedList.size(); i++) {
		    Object[] arr = joinedList.get(i);
		    project = (Project) arr[0];
		    Employee employee = (Employee) arr[1];
		    if(employee != null){
			employees.add(employee);
		    }
		}
		project.setEmployees(employees);
	    }
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
	    session.close();
        }
	    
        return project;  
    
    }

    public Project getProject(int projectId) {
    	Session session = null;
        Project project = null;
        try {
            session = HibernateSession.getSession();
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("FROM Project where isDeleted = false AND projectId ="+projectId,Project.class);
            project = (Project) query.uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
	    session.close();
        }
	    
        return project;  
    
    }

    public boolean updateProject(Project project) {
	boolean status = false;
	String query = "UPDATE projects SET employee_id = '"+project.getEmployeeId()+"' , modified_at = '"+utilDate.getCurDateTime()+"' where project_id = "+project.getProjectId()+" AND deleted="+NOT_DELETED;//   "SELECT * FROM leaverecords where employee_id ='"+employeeId+"'";
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
    }

    @Override
    public boolean deleteProject(Project project) {
	boolean status = false;

	String query = "UPDATE projects SET deleted = "+DELETED+" WHERE project_id = '"+project.getProjectId()+"'";
				   				  						
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
    }

}
