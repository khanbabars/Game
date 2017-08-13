package org.aq;


import java.sql.Connection;
import javax.jms.*;
import org.datasource.GetConnFromPool;
import org.threads.BaseThread;
import oracle.jms.AQjmsSession;
import oracle.jms.AQjmsTopicConnectionFactory;
import oracle.jms.AQjmsTopicPublisher;


public class Publisher {

    //public static void main(String[] args) throws Exception {
        
	public static void getGameSessionPublisher(int win, int bet, int balance) throws Exception {
    		
		
	 		
     	 Class.forName("oracle.AQ.AQOracleDriver");
     	 Connection con = GetConnFromPool.returnConnectionFromPool();
     	 System.out.println("Innitiating Connection ... ");
       	 TopicConnection tc_conn =AQjmsTopicConnectionFactory.createTopicConnection(con);
         tc_conn.start();

    	  	TopicSession jms_sess = tc_conn.createTopicSession(true, Session.SESSION_TRANSACTED);
        Topic queueTopic= ((AQjmsSession )jms_sess).getTopic("jmsuser","AQ_QUEUE5");
     
        AQjmsTopicPublisher publisherAq = (AQjmsTopicPublisher)jms_sess.createPublisher(queueTopic);
     
        
       
        
        //Disabled multiple Publishers and multiple Subscribers

     
        
        // attach an object to this method and pass with synchronized threading
 		Object lockOnGameSession = new Object();
 		synchronized(lockOnGameSession){
    	
 			BaseThread threadObj = new BaseThread(win, bet, balance);
 			Thread startThread = new Thread(threadObj);
 			startThread.start();
 			System.out.println("Thread started "+startThread.getName());
 			
 			//--------------------------------------//
       // BytesMessage messOnlyForGreen = jms_sess.createBytesMessage();
 			
        System.out.println("Data Enqueue " +startThread.getName());
        
  
        if (publisherAq != null) {
        
        
        BytesMessage messAll = jms_sess.createBytesMessage();
        messAll.writeUTF("  <Gaming_data>\n" + 
        		"    <win> "+win+" </win>\n" + 
        		"    <bet> "+bet+"</bet>\n" + 
        		"    <Balance>"+balance+"</Balance>\n" + 
        		"  </Gaming_data>");
        
        
        //messOnlyForGreen.writeInt(12344);
        publisherAq.publish(messAll);
        //publisherAq.publish(messOnlyForGreen, new AQjmsAgent[]{new AQjmsAgent("GREEN", null)} );
        System.out.println("Finished ");
        con.commit();
        tc_conn.close();
        con.close();      
        } 
 		 } 
    } 
} 

