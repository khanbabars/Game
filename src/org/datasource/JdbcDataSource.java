package org.datasource;

import java.sql.DriverManager;
import java.sql.Connection;


public class JdbcDataSource {
	

	    public static Connection getDbConnections() {
//System.out.println("-------- Oracle JDBC Connection Testing ------");
	        try{  
	     	Class.forName("oracle.jdbc.driver.OracleDriver");  
	    Connection con=DriverManager.getConnection( "jdbc:oracle:thin:@10.0.1.28:1521:xe","gameserver","gameserver");  
	          return con;
 // con.close(); 
	        }catch(Exception e)
	        { System.out.println(e);}  
return null;
	        }  
	          
	         
	        
}

//adding code to git

	        


