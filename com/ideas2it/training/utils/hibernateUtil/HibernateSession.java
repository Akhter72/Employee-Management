package com.ideas2it.training.utils.hibernateUtil;

import java.util.Date;
import java.util.Iterator; 
import java.util.List; 

import org.hibernate.cfg.Configuration;
import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class HibernateSession {

    private static Session session;

    public static Session getSession() {

        try {
            SessionFactory factory = new Configuration().configure().buildSessionFactory();
	    session = factory.openSession();
        } catch (Exception ex) { 
            System.out.println(ex);
        }
        return session;
    }

    public static void closeSession() {
	session.close();
    }

	
}