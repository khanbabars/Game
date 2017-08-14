package org.aq;

import java.sql.Connection;
import java.sql.SQLException;
import javax.jms.JMSException;
import javax.jms.TopicConnection;
import org.datasource.GetConnFromPool;
import oracle.jms.AQjmsTopicConnectionFactory;
import oracle.ucp.UniversalConnectionPoolException;

public class AqTopicConn {
	
	public  TopicConnection tc_conn;
	public  Connection con;
	private  State state = State.Initialize;
	
	
	public  TopicConnection startConnection () throws ClassNotFoundException, SQLException, UniversalConnectionPoolException, JMSException {
	
		
		
	
	if(state == State.Initialize) {
		
 	 Class.forName("oracle.AQ.AQOracleDriver");
 	 con = GetConnFromPool.returnConnectionFromPool();
 	 System.out.println("Innitiating Connection ... ");
   	 tc_conn =AQjmsTopicConnectionFactory.createTopicConnection(con);
   	 tc_conn.start();
   	 state = State.Active;
	}
	
	else if(state ==State.Active){
		
		System.out.println("State is Active");
	}
   	 return tc_conn;
	}
	
	public   TopicConnection closeConnection () throws ClassNotFoundException, SQLException, UniversalConnectionPoolException, JMSException {
		
	 state = State.Closed;
	 
	 if(state == State.Active) {
		 
		 tc_conn.close();
	 }
   	 return tc_conn;
   	 
	}
	public enum State { Status ,Unknown, Initialize, Active, Closed } 
	 
}

