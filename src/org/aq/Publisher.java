package org.aq;




import java.sql.SQLException;
import javax.jms.*;
import org.aq.AqTopicConn.State;
import org.threads.BaseThread;
import oracle.jms.AQjmsSession;
import oracle.jms.AQjmsTopicPublisher;
import oracle.ucp.UniversalConnectionPoolException;


public class Publisher {
	
	
	 private AqTopicConn aqTopicConn =  new AqTopicConn();
	
	 public Publisher(State state ) throws ClassNotFoundException, SQLException, UniversalConnectionPoolException, JMSException {
		 
		 
		 switch (state) {
		 
		 case  Initialize : 
			 aqTopicConn.tc_conn =  aqTopicConn.startConnection();
			  return;
		  case  Active : 
			  aqTopicConn.tc_conn =  aqTopicConn.startConnection();
			  return;
		  case  Closed : 
			  aqTopicConn.tc_conn =  aqTopicConn.closeConnection();
			  return;
		default:
			break;
			}
	  }
    //public static void main(String[] args) throws Exception {
        
	public void getGameSessionPublisher(int win, int bet, int balance) throws Exception {

		      
    	  	TopicSession jms_sess = aqTopicConn.tc_conn.createTopicSession(true, Session.SESSION_TRANSACTED);
        Topic queueTopic= ((AQjmsSession )jms_sess).getTopic("gameserver","TEST_QUEUE");
     
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
        
  
     
        
        
        BytesMessage messAll = jms_sess.createBytesMessage();
        messAll.writeUTF("  <Gaming_data>\n" + 
        		"    <win> "+win+" </win>\n" + 
        		"    <bet> "+bet+"</bet>\n" + 
        		"    <Balance>"+balance+"</Balance>\n" + 
        		"  </Gaming_data>");
        
        
        //messOnlyForGreen.writeInt(12344);
        publisherAq.publish(messAll);
       // publisherAq.publish(messOnlyForGreen, new AQjmsAgent[]{new AQjmsAgent("GREEN", null)} );
        System.out.println("Finished ");
   //     	con.commit();
        aqTopicConn.con.commit();
//        aqTopicConn.tc_conn.
//        con.close();      
        } 
 		 } 
    } 


