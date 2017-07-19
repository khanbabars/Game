package org.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import oracle.ucp.UniversalConnectionPoolException;

public class GetConnFromPool {
	public static Connection returnConnectionFromPool() throws SQLException, UniversalConnectionPoolException {
	
	Connection conn = JdbcConnectionPool.poolFactoryPrimary().getConnection();
	return conn;
		
	}
}
