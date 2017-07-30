package org.datasource;


import java.sql.*;
import oracle.ucp.jdbc.PoolDataSourceFactory;
import oracle.ucp.UniversalConnectionPoolAdapter;
import oracle.ucp.UniversalConnectionPoolException;
import oracle.ucp.admin.UniversalConnectionPoolManager;
import oracle.ucp.admin.UniversalConnectionPoolManagerImpl;
import oracle.ucp.jdbc.PoolDataSource;

/*
 Creating JDBC connection pool 
 Two methods
 poolFactoryPrimary
 poolFactorySecondary

*/
public class JdbcConnectionPool {
	
		public Connection conn;
		private static PoolDataSource poolDsFactory;
		public static PoolDataSource poolFactoryPrimary() throws SQLException, UniversalConnectionPoolException {
		try {
			
			
			// Creating a pool-enabled data source
			UniversalConnectionPoolManager mgr = UniversalConnectionPoolManagerImpl.getUniversalConnectionPoolManager();
			
			
			PoolDataSource poolDsFactory = PoolDataSourceFactory.getPoolDataSource();
			
			//poolDsFactory.setConnectionPoolName("GAMESERVER_DB");
			poolDsFactory.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
			poolDsFactory.setURL("jdbc:oracle:thin:@//10.0.1.28:1521/XE");
			poolDsFactory.setUser("gameserver");
			poolDsFactory.setPassword("gameserver");
			mgr.createConnectionPool((UniversalConnectionPoolAdapter)poolDsFactory);
			
			
			System.out.println("Checking Pool factory parameters");
			//Pool Properties
			poolDsFactory.setInitialPoolSize(5);
			poolDsFactory.setMinPoolSize(10);
			poolDsFactory.setMaxPoolSize(100);
			poolDsFactory.setMaxConnectionReuseCount(100);
			poolDsFactory.setConnectionWaitTimeout(10);
			poolDsFactory.setInactiveConnectionTimeout(10);
			
			//Caching enable
			poolDsFactory.setMaxStatements(50);
			return poolDsFactory;
		

		} catch (SQLException e) {
			System.out.println("\nAn SQL exception occurred : " + e.getMessage());
		}
		return poolDsFactory;
	}

	
	
	
	
	//Second Pool Factory
	
	public static PoolDataSource poolFactorySecondary() throws SQLException, UniversalConnectionPoolException {
		try {
	
	
	// Creating a pool-enabled data source
	UniversalConnectionPoolManager mgr = UniversalConnectionPoolManagerImpl.getUniversalConnectionPoolManager();
	
	
	PoolDataSource poolDsFactory = PoolDataSourceFactory.getPoolDataSource();
	
	//poolDsFactory.setConnectionPoolName("GAMESERVER_DB");
	poolDsFactory.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
	poolDsFactory.setURL("jdbc:oracle:thin:@//10.0.1.28:1521/XE");
	poolDsFactory.setUser("gameserver");
	poolDsFactory.setPassword("gameserver");
	mgr.createConnectionPool((UniversalConnectionPoolAdapter)poolDsFactory);
	
	
	
	//Pool Properties
	poolDsFactory.setInitialPoolSize(5);
	poolDsFactory.setMinPoolSize(10);
	poolDsFactory.setMaxPoolSize(100);
	poolDsFactory.setMaxConnectionReuseCount(100);
	poolDsFactory.setConnectionWaitTimeout(10);
	poolDsFactory.setInactiveConnectionTimeout(10);
	System.out.println("Enabling Session factory");
	//Caching enable
	poolDsFactory.setMaxStatements(10);
	return poolDsFactory;


} catch (SQLException e) {
	System.out.println("\nAn SQL exception occurred : " + e.getMessage());
}
return poolDsFactory;
}
	}
