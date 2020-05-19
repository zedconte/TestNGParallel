package com.finningphase2.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;



public class SystemUtil {
	
    static public Properties resource = null;
	
	/**
	 * load the test data in properties file
	 * @param fileName : The file name to load
	 * 
	 * 
	 */
	static public Properties loadPropertiesResources(String fileName) {

		
		//InputStream data_input = SystemUtil.class.getResourceAsStream(fileName);
		resource = new Properties();
			try {
				File file=new File("./testdata/"+fileName);	
				InputStream data_input = new FileInputStream(file);
				resource.load(data_input);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Error:Not found properties file"+ fileName);
				e.printStackTrace();
			}
		
		return resource;
	}
	
	
	
	
	public static void driverKiller() throws Exception
	{
	  final String KILL = "taskkill /IM ";
	  String processName = "IEDriverServer.exe"; //IE process
	  Runtime.getRuntime().exec(KILL + processName); 
	  Thread.sleep(1000);
	  String processName2 = "chromedriver.exe"; //chrome process
	  Runtime.getRuntime().exec(KILL + processName2); 
	  Thread.sleep(1000);
	}
	
	
}
