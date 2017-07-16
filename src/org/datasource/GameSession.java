package org.datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.threads.BaseThread;

import oracle.ucp.UniversalConnectionPoolException;

public class GameSession {

	public static void main(String args[]) throws SQLException {
		
	}

	public static void getGameSession(int win, int bet, int balance) throws SQLException {
		// attach an object to this method and pass with synchronized
		Object lockOnGameSession = new Object();
		synchronized(lockOnGameSession){
		
			BaseThread threadObj = new BaseThread(win, bet, balance);
		Thread startThread = new Thread(threadObj);
		startThread.start();
		System.out.println("Thread started"+startThread.getName());

		// get connection from JdbcConnectionPool
		Connection conn = null;
		try {
			conn = JdbcConnectionPool.poolFactoryPrimary().getConnection();
		} catch (UniversalConnectionPoolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PreparedStatement insertSql = conn
				.prepareStatement("insert into game_session(win, bet, balance) values(?,?,?)");
		insertSql.setInt(1, win);
		insertSql.setInt(2, bet);
		insertSql.setInt(3, balance);
		insertSql.executeUpdate();
		System.out.println("Record'Updated for " +startThread.getName());
		 //conn.close();
		

		/*
		  No need to close connection because connection pool removes idle connections
		  conn.close();
		 System.out.println("New row inserted ");2
		 */
		 }

	}

}
