package com.ideas2it.training.config;

//package com.jdbc.util;
  
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
  
public class DBConnection {
  
    private static Connection connection = null;
  
        String url = "jdbc:mysql://localhost:3306/training";
        String user = "root";
        String pass = "1234";

   private DBConnection() {

        try {
            //Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, pass);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() throws Exception 
    {
	if (connection == null || connection.isClosed()) {
	    DBConnection dbConnection = new DBConnection();
	   
        }
        return connection;
    }

    public static void closeConnection(Connection con) {
	try{
	    con.close();
	} catch(Exception e) {
	    System.out.println(e);
	}
    }
}