package org.hikariCP;

import javax.sql.DataSource;
import org.hikariCP.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.typesafe.config.Config;
import org.hikariCP.Configs;

// {{start:pools}}
public class ConnectionPools {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionPools.class);
    /*
     * Normally we would be using the app config but since this is an example
     * we will be using a localized example config.
     */
    private static final Config conf = new Configs.Builder()
                                                  .withResource("properties/pools.conf")
                                                  .build();
    /*
     *  This pool is made for short quick transactions that the web application uses.
     *  Using enum singleton pattern for lazy singletons
     */
    private enum Transactional {
        INSTANCE(ConnectionPool.getDataSourceFromConfig(conf.getConfig("pools.transactional"), Metrics.registry(), HealthChecks.getHealthCheckRegistry()));
        private final DataSource dataSource;
        private Transactional(DataSource dataSource) {
            this.dataSource = dataSource;
        }
        public DataSource getDataSource() {
            return dataSource;
        }
    }
    public static DataSource getTransactional() {
        return Transactional.INSTANCE.getDataSource();
    }

    /*
     *  This pool is designed for longer running transactions / bulk inserts / background jobs
     *  Basically if you have any multithreading or long running background jobs
     *  you do not want to starve the main applications connection pool.
     *
     *  EX.
     *  You have an endpoint that needs to insert 1000 db records
     *  This will queue up all the connections in the pool
     *
     *  While this is happening a user tries to log into the site.
     *  If you use the same pool they may be blocked until the bulk insert is done
     *  By splitting pools you can give transactional queries a much higher chance to
     *  run while the other pool is backed up.
     */
    private enum Processing {
        INSTANCE(ConnectionPool.getDataSourceFromConfig(conf.getConfig("pools.processing"), Metrics.registry(), HealthChecks.getHealthCheckRegistry()));
        private final DataSource dataSource;
        private Processing(DataSource dataSource) {
            this.dataSource = dataSource;
        }
        public DataSource getDataSource() {
            return dataSource;
        }
    }

    public static DataSource getProcessing() {
        return Processing.INSTANCE.getDataSource();
    }

    public static void main(String[] args) {
        logger.debug("starting");
        @SuppressWarnings("unused")
		DataSource processing = ConnectionPools.getProcessing();
        logger.debug("processing started");
        @SuppressWarnings("unused")
		DataSource transactional = ConnectionPools.getTransactional();
        logger.debug("transactional started");
        logger.debug("done");
    }
}
// {{end:pools}}
