package org.datasource;


import java.sql.*;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
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
	
	public static final Logger logger = Logger.getLogger(JdbcConnectionPool.class);
	public static PoolDataSource poolFactoryPrimary() throws SQLException, UniversalConnectionPoolException {
		try {
			
			BasicConfigurator.configure();
			
			
			String getInsanceName = "GameServer Database";
			// Creating a pool-enabled data source
			 logger.info("Enabling Session factory");
			 
			UniversalConnectionPoolManager mgr = UniversalConnectionPoolManagerImpl.getUniversalConnectionPoolManager();
			PoolDataSource poolDsFactory = PoolDataSourceFactory.getPoolDataSource();
			
			//poolDsFactory.setConnectionPoolName("GAMESERVER_DB");
			poolDsFactory.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
			poolDsFactory.setURL("jdbc:oracle:thin:@//10.0.1.28:1521/XE");
			poolDsFactory.setUser("gameserver");
			poolDsFactory.setPassword("gameserver");
			mgr.createConnectionPool((UniversalConnectionPoolAdapter)poolDsFactory);
			
			
			
			//Pool Properties
			poolDsFactory.setInitialPoolSize(20);
			poolDsFactory.setMinPoolSize(20);
			poolDsFactory.setMaxPoolSize(40);
			poolDsFactory.setMaxConnectionReuseCount(100);
			poolDsFactory.setConnectionWaitTimeout(10);
			poolDsFactory.setInactiveConnectionTimeout(10);
			
			//Caching enable
			poolDsFactory.setMaxStatements(10);
			
			
			Connection conn = poolDsFactory.getConnection();
			
			
			// Checking the number of available and borrowed connections again
			int avlConnCount = poolDsFactory.getAvailableConnectionsCount();
			logger.info("\nAvailable connections: " + avlConnCount);
			int brwConnCount = poolDsFactory.getBorrowedConnectionsCount();
			logger.info("\nBorrowed connections: " + brwConnCount);
			
			//Database Instance Name
			logger.info("\nConnected as: " + getInsanceName);
		
			conn.close();
			conn = null;
			return poolDsFactory;

		} catch (SQLException e) {
			System.out.println("\nAn SQL exception occurred : " + e.getMessage());
		}
		return null;
	}

	
	
	
	//Second Pool Factory
	
	public static PoolDataSource poolFactorySecondary() throws SQLException, UniversalConnectionPoolException {
try {
			
			BasicConfigurator.configure();
			
			
			String getInsanceName = "GameServer Database";
			// Creating a pool-enabled data source
			 logger.info("Enabling Session factory");
			 
			UniversalConnectionPoolManager mgr = UniversalConnectionPoolManagerImpl.getUniversalConnectionPoolManager();
			PoolDataSource poolDsFactory = PoolDataSourceFactory.getPoolDataSource();
			
			//poolDsFactory.setConnectionPoolName("GAMESERVER_DB");
			poolDsFactory.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
			poolDsFactory.setURL("jdbc:oracle:thin:@//10.0.1.28:1521/XE");
			poolDsFactory.setUser("gameserver");
			poolDsFactory.setPassword("gameserver");
			mgr.createConnectionPool((UniversalConnectionPoolAdapter)poolDsFactory);
			
			
			
			//Pool Properties
			poolDsFactory.setInitialPoolSize(20);
			poolDsFactory.setMinPoolSize(20);
			poolDsFactory.setMaxPoolSize(40);
			poolDsFactory.setMaxConnectionReuseCount(100);
			poolDsFactory.setConnectionWaitTimeout(10);
			poolDsFactory.setInactiveConnectionTimeout(10);
			
			//Caching enable
			poolDsFactory.setMaxStatements(10);
			
			
			Connection conn = poolDsFactory.getConnection();
			
			
			// Checking the number of available and borrowed connections again
			int avlConnCount = poolDsFactory.getAvailableConnectionsCount();
			logger.info("\nAvailable connections: " + avlConnCount);
			int brwConnCount = poolDsFactory.getBorrowedConnectionsCount();
			logger.info("\nBorrowed connections: " + brwConnCount);
			
			//Database Instance Name
			logger.info("\nConnected as: " + getInsanceName);
		
			conn.close();
			conn = null;
			return poolDsFactory;

		} catch (SQLException e) {
			System.out.println("\nAn SQL exception occurred : " + e.getMessage());
		}
		return null;
	}
	}
