package org.log;


import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import java.io.IOException;


	
	

	public class LogDef {
	  private static final Logger logger = Logger.getLogger(LogDef.class);
	   public static void main(String args[]) throws IOException {
		   
		   //BasicConfigurator.configure();
	      
	      logger.info("INFO");
	    
	   }
	}


