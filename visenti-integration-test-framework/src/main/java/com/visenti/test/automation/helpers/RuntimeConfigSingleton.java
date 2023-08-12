package com.visenti.test.automation.helpers;
import static com.visenti.test.automation.constants.FrameworkConstants.COMMON_CONFIG_PROPERTY_FILE_PATH;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class RuntimeConfigSingleton {

	private static RuntimeConfigSingleton runtimeConfigSingleton;
	private String executionEnvironment;
	private String customerName;

	private RuntimeConfigSingleton()  {
		executionEnvironment = System.getProperty("environment");
		customerName = System.getProperty("customer");
		
		if(executionEnvironment==null||customerName==null)
		{
			File f=new File(COMMON_CONFIG_PROPERTY_FILE_PATH);
			Properties p=new Properties();
			try {
				p.load(new FileInputStream(f));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			executionEnvironment=p.getProperty("execution.environment");
			customerName=p.getProperty("customer.name");
			
		}
		Log.info("Execution Environment "+executionEnvironment);
		Log.info("Customer Name "+customerName);
	}

	public static RuntimeConfigSingleton getInstance()  {
		if (runtimeConfigSingleton == null) {
			runtimeConfigSingleton = new RuntimeConfigSingleton();
		}
		return runtimeConfigSingleton;

	}

	public String getExecutionEnvironment() {
		return executionEnvironment;
	}

	public String getCustomerName() {
		return customerName;
	}

}
