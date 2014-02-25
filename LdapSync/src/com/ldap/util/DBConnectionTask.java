package com.ldap.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnectionTask{

	
    String url = "jdbc:mysql://localhost:3306/DB123?user=root&password=root";
    Connection con;


	public Connection getConnection() {
		// TODO Auto-generated method stub
	    try {
	    	   Class.forName("com.mysql.jdbc.Driver");
	      }
	      catch( Exception e ) {
	        System.out.println("Failed to load mSQL driver.");
	        return null;
	      }
	      try { // Setup the connection with the DB
	          con = DriverManager
	          .getConnection(url);
	}catch(Exception ex){
		ex.printStackTrace();
	}
	return con;

}
}
