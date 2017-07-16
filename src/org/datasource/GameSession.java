package org.datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.threads.BaseThread;

public class GameSession {

	public static void main(String args[]) throws SQLException {
	}

	public static void getGameSession(int win, int bet, int balance) throws SQLException {
	
		BaseThread threadObj = new BaseThread(win, bet, balance);
		Thread startThread = new Thread(threadObj);
		System.out.println("Starting Thread "+startThread.getName());
		startThread.start();

		// get connection from JdbcConnectionPool
		Connection conn = JdbcConnectionPool.poolFactoryPrimary().getConnection();
		PreparedStatement insertSql = conn
				.prepareStatement("insert into game_session(win, bet, balance) values(?,?,?)");
		insertSql.setInt(1, win);
		insertSql.setInt(2, bet);
		insertSql.setInt(3, balance);
		insertSql.executeUpdate();
		System.out.println("Starting Thread " +startThread.getName());
		 //conn.close();
		

		/*
		  No need to close connection because connection pool removes idle connections
		  conn.close();
		 System.out.println("New row inserted ");2
		 */
	

	}

}
