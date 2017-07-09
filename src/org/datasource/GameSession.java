package org.datasource;

import java.sql.PreparedStatement;

import java.sql.SQLException;




public class GameSession {
	
	

	public static void main(String args[]) throws SQLException {
	//getGameSession(win, bet, balance);

	}
	
	
	public static void getGameSession(int win, int bet, int balance) throws SQLException {

PreparedStatement insertSql =JdbcDataSource.getDbConnections().prepareStatement("insert into game_session(win, bet, balance) values(?,?,?)");
insertSql.setInt(1, win);
insertSql.setInt(2, bet);
insertSql.setInt(3, balance);
insertSql.executeUpdate();

JdbcDataSource.getDbConnections().close();

//System.out.println("New row inserted ");


}
	}
