package org.aq;


import java.sql.Connection;
import javax.jms.*;
import org.datasource.GetConnFromPool;
import oracle.jms.AQjmsAgent;
import oracle.jms.AQjmsSession;
import oracle.jms.AQjmsTopicConnectionFactory;
import oracle.jms.AQjmsTopicPublisher;


public class Publisher {

    public static void main(String[] args) throws Exception {
        Class.forName("oracle.AQ.AQOracleDriver");
        Connection con = GetConnFromPool.returnConnectionFromPool();
        TopicConnection tc_conn =AQjmsTopicConnectionFactory.createTopicConnection(con);
        tc_conn.start();
        TopicSession jms_sess = tc_conn.createTopicSession(true, Session.SESSION_TRANSACTED);
        Topic queueTopic= ((AQjmsSession )jms_sess).getTopic("JMSUSER","AQ_QUEUE5");
        AQjmsTopicPublisher publisherAq = (AQjmsTopicPublisher)jms_sess.createPublisher(queueTopic);
        BytesMessage messAll = jms_sess.createBytesMessage();
        BytesMessage messOnlyForGreen = jms_sess.createBytesMessage();;
        messAll.writeUTF("  '<Gaming_data>\n" + 
        		"    <win>0</win>\n" + 
        		"    <bet>20</bet>\n" + 
        		"    <Balance>100</Balance>\n" + 
        		"  </Gaming_data>'");
        messOnlyForGreen.writeInt(12344);
        publisherAq.publish(messAll);
        publisherAq.publish(messOnlyForGreen, new AQjmsAgent[]{new AQjmsAgent("GREEN", null)} );
        con.commit();
        tc_conn.close();
        con.close();                
    } 
}
