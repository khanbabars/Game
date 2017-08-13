package org.aq;

import org.datasource.GetConnFromPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.jms.*;
import oracle.jms.AQjmsSession;
import oracle.jms.AQjmsTopicConnectionFactory;




public class Subscriber {
	
	

	    public static void main(String[] args) throws Exception {
	
	    	
	    		Class.forName("oracle.AQ.AQOracleDriver");
	        Connection con = GetConnFromPool.returnConnectionFromPool(); 
	        TopicConnection tc_conn = AQjmsTopicConnectionFactory.createTopicConnection(con);
	        TopicSession jms_sess = tc_conn.createTopicSession(true, Session.SESSION_TRANSACTED);
	        tc_conn.start();
	       
	        
	        Topic queueTopic = ((AQjmsSession) jms_sess).getTopic("gameserver","TEST_QUEUE");
	        TopicSubscriber subGreen =  (TopicSubscriber)((AQjmsSession) jms_sess).createDurableSubscriber(queueTopic, "GREEN");
	       // TopicSubscriber subRed =  (TopicSubscriber)((AQjmsSession) jms_sess).createDurableSubscriber(queueTopic, "RED");        
	        Message msg = subGreen.receive(10);
	     
	        
	        //
	        System.err.println("Start receiving message for green subscriber");
	        while(msg != null){
	            String deQueue = ( ((BytesMessage)msg).readUTF());
	           
	              PreparedStatement insertSql = con
	      				.prepareStatement("insert into GAMESESSION_UTF(text) values(?)");
	      		insertSql.setString(1, deQueue);
	      	  msg = subGreen.receive(10); 
	      		insertSql.executeUpdate();
		      	 
	      		System.out.println(deQueue);
		      	
	        
	             
	        }
	        System.err.println("End receiving message for green subscriber");
	       
	         System.err.println("  ");        
	        
	         
	         
	         /* System.err.println("Start receiving message for red subscriber");
	        @SuppressWarnings("unused")
	        BytesMessage byteMsg = (BytesMessage)msg;
	        msg = subRed.receive(10);
	        while(msg != null){
	              System.err.println("     RED recive message "+ ((BytesMessage)msg).readUTF());
	              msg = subRed.receive(10); // receive with timeout;
	        }
	           System.err.println("End receiving message for red subscriber");
	          */ 
	        con.commit();
	        tc_conn.close();
	        con.close();

	    }
	    
	    
	    }
	    
	


