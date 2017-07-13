package org.log;



import org.apache.log4j.Logger;
import java.io.IOException;

public class Logging {
	  private static final Logger logger = Logger.getLogger(Logging.class);
	   public static void main(String[] args)  throws IOException {
		   
		   //BasicConfigurator.configure();
	     
	      logger.info("Starting Application Server");
	      System.out.println("logs are printing");
	      /*
	      logger.debug("DEBUG");
	      logger.error("ERROR");
	      logger.warn("WARN");
	      logger.fatal("FATAL");
	      */
	   }
	}


