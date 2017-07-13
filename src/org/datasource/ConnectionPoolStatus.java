package org.datasource;

import java.sql.*;
import oracle.ucp.jdbc.PoolDataSourceFactory;
import oracle.ucp.jdbc.PoolDataSource;

/*
 Check JDBC Pool Factory running status 
 check how many connections are in used etc
 */
public class ConnectionPoolStatus {

	public static Connection getPoolStatus() throws SQLException {

		PoolDataSource pds = PoolDataSourceFactory.getPoolDataSource();
		// Setting connection properties of the data source
		pds.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
		Connection conn = JdbcConnectionPool.poolFactoryPrimary().getConnection();
		System.out.println("\nConnection borrowed from the pool");
		// Checking the number of available and borrowed connections
		int avlConnCount = PoolDataSourceFactory.getPoolDataSource().getAvailableConnectionsCount();
		System.out.println("\nAvailable connections: " + avlConnCount);
		int brwConnCount = PoolDataSourceFactory.getPoolDataSource().getBorrowedConnectionsCount();
		System.out.println("\nBorrowed connections: " + brwConnCount);
		// Working with the connection
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select user from dual");
		while (rs.next())
			System.out.println("\nConnected as: " + rs.getString(1));
		rs.close();
		// Returning the connection to the pool
		conn.close();
		conn = null;
		System.out.println("\nConnection returned to the pool");
		// Checking the number of available and borrowed connections again
		avlConnCount = PoolDataSourceFactory.getPoolDataSource().getAvailableConnectionsCount();
		System.out.println("\nAvailable connections: " + avlConnCount);
		brwConnCount = PoolDataSourceFactory.getPoolDataSource().getBorrowedConnectionsCount();
		System.out.println("\nBorrowed connections: " + brwConnCount);
		return conn;
	}

}
