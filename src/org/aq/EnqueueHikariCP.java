package org.aq;




import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.jms.*;
import org.aq.AqTopicConn.State;
import org.threads.BaseThread;
import oracle.jms.AQjmsSession;
import oracle.jms.AQjmsTopicPublisher;
import oracle.ucp.UniversalConnectionPoolException;


public class EnqueueHikariCP {
	

		
		 private AqTopicConnHikariCP aqTopicConn =  new AqTopicConnHikariCP();
		
		 public EnqueueHikariCP(State state ) throws ClassNotFoundException, SQLException, UniversalConnectionPoolException, JMSException {
			 
			 
			 switch (state) {
			 
			 case  Status : 

					if (aqTopicConn.con == null){
						 aqTopicConn.tc_conn =  aqTopicConn.startConnection();
					}
					else if (aqTopicConn.con != null){
						 System.out.print("gsdb status is active");
					}
					return;
					
			  case  Initialize : 
				  aqTopicConn.tc_conn =  aqTopicConn.startConnection();
				  return;
			  case  Active : 
				  System.out.print("gsdb connection is active");
				  return;
			  case  Closed : 
				  aqTopicConn.tc_conn =  aqTopicConn.closeConnection();
				  return;
			  case  Unknown : 
				  System.err.print("GSDB connection is in unknown State");
				  return;
			default:
				break;
				}
		  }
	    //public static void ,iubliilmain(String[] args) throws Exception {
	        
		public void getGameSessionPublisher(int win, int bet, int balance) throws Exception {

			      
	    	  	TopicSession jms_sess = aqTopicConn.tc_conn.createTopicSession(true, Session.SESSION_TRANSACTED);
	        Topic queueTopic= ((AQjmsSession )jms_sess).getTopic("gameserver","TEST_QUEUE");
	     
	        AQjmsTopicPublisher publisherAq = (AQjmsTopicPublisher)jms_sess.createPublisher(queueTopic);
	        TopicSubscriber deQueue =  (TopicSubscriber)((AQjmsSession) jms_sess).createDurableSubscriber(queueTopic, "DEQUEUE");
	 
	        //Disabled multiple Publishers and multiple Subscribers
			
	     
	        
	        // attach an object to this method and pass with synchronized threading
	 		Object lockOnGameSession = new Object();
	 		synchronized(lockOnGameSession){
	 			
	 			//Calcuate thread  execution time, start time of thread
	 			long lStartTimeThread = System.currentTimeMillis();
	 			System.err.println(lStartTimeThread);
	 			
	 			
	 			//Run the rest of the program
	 			
	 			BaseThread threadObj = new BaseThread(win, bet, balance);
	 			Thread startThread = new Thread(threadObj);
	 			startThread.start();
	 			System.out.println("started "+startThread.getName());
	 			
	 			//--------------------------------------//
	       // BytesMessage messOnlyForGreen = jms_sess.createBytesMessage();
	 			
	        System.out.println("Data Enqueue for" +startThread.getName());
	        
	  
	     
	        
	        
	        BytesMessage messAll = jms_sess.createBytesMessage();
	        System.err.println( " Request Game test-Dragon "+" Balance "+balance+" win "+win+ " bet "+bet);
	        messAll.writeUTF("  <Gaming_data>\n" + 
	        		"    <win> "+win+" </win>\n" + 
	        		"    <bet> "+bet+"</bet>\n" + 
	        		"    <Balance>"+balance+"</Balance>\n" + 
	        		"  </Gaming_data>");
	        
	       
	       
	         
	         
	        //messOnlyForGreen.writeInt(12344);
	        publisherAq.publish(messAll);
	       // publisherAq.publish(messOnlyForGreen, new AQjmsAgent[]{new AQjmsAgent("GREEN", null)} );
	       
	        
	      //Enqueue processing time of thread
	        long lEnqueueTime = System.currentTimeMillis();
	        System.err.println(lEnqueueTime);
				long msEnqueueDiff =   lEnqueueTime -lStartTimeThread;
				System.err.println(msEnqueueDiff+"ms"); 
				  System.err.println("Message is Equeued in "+ msEnqueueDiff+"ms on gsdb");
	        Message msg = deQueue.receive(10);
	       
	        System.err.println("Response Start Dequeuing of message for "+startThread.getName());
	        while(msg != null){
	            String deQueueMessage = ( ((BytesMessage)msg).readUTF());
	           
	              PreparedStatement insertSql = aqTopicConn.con.prepareStatement("insert into GAMESESSION_UTF(text) values(?)");
	      		insertSql.setString(1, deQueueMessage);
	      		msg = deQueue.receive(10); 
	      		insertSql.executeUpdate();
		      	 
	      		System.out.println(deQueueMessage);
		      	
	        
	             
	        }
	     // Dequeue Processing time of thread
	        
	        
	        long lDequeueTime = System.currentTimeMillis();
	        System.err.println(lEnqueueTime);
				long msDequeueDiff =   lDequeueTime -lEnqueueTime;
				System.err.println(msDequeueDiff+"ms"); 
				System.err.println("Message is Equeued in "+ msDequeueDiff+"ms on gsdb");
	        
	   //     	con.commit();
	       aqTopicConn.con.commit();
//	        aqTopicConn.tc_conn.
//	        con.close();      
	        } 
	 		 } 
	    } 





