package com.visenti.test.automation.helpers;

import com.visenti.test.automation.constants.FrameworkConstants;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;


public class Log {

    private static final Logger Log = LogManager.getLogger(Log.class);

    public static void configureLoggerToReadFromLog4JProperties()
    {
        final LoggerContext context = (LoggerContext) LogManager.getContext(false);
        final Configuration config = context.getConfiguration();
        config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME).setLevel(Level.INFO);
        context.updateLoggers(config);

        // Further implementation to read the logs from log4j.properties file needs to be done

    	/*String log4jPropertiesFullPath = FrameworkConstants.LOG4J_FILE_PATH;
        File file = new File(log4jPropertiesFullPath);
        context.setConfigLocation(file.toURI());*/
    }

    public static void startLog(String sTestCaseName){
        Log.info("******* Started test case " + sTestCaseName + " *******");
    }


    public static void endLog(String sTestCaseName){
        Log.info("******* Ended test case " + sTestCaseName  + " *******");
    }

    //Info Level Logs
    public static void info(String message)
    {
        Log.info(message);
    }

    //Warn Level Logs
    public static void warn (String message) {
        Log.warn(message);
    }

    //Error Level Logs
    public static void error (String message) {
        Log.error(message);
    }

    //Fatal Level Logs
    public static void fatal (String message) {
        Log.fatal(message);
    }

    //Debug Level Logs
    public static void debug (String message) {
        Log.debug(message);
    }

}