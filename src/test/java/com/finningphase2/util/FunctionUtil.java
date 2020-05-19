package com.finningphase2.util;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.BaseTest;
import com.google.common.base.Function;

import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.finningphase2.basepages.basicpages.BasicPage.FindElementWait;

/**
 * this class contains the methods not about the driver,but about something like to deal with string 
 * the methods in this class are generally working at back end , they do not deal with the webelements directly.
 * @author icey.qi
 *
 */
public class FunctionUtil {
	
	static int MAX_WAIT_FOR_ELEMENT = 30;
	/*
	 * Modified by Tripp 2015/08/10 get the value between the from valueFromText
	 * between String A and String B
	 * 
	 * @author Xin.Fang
	 */
	public static String getTheValueBetweenStringAAndB(String valueFromText, String A, String B) {
		String regex = String.format("([\\s\\S]*)%s([\\s\\S]*)%s([\\s\\S]*)", A, B);
		Matcher m = Pattern.compile(regex).matcher(valueFromText);
		m.find();
		return m.group(2);
	}

	/**
	 * Get a dateRange based on the current date and the months need verify
	 *
	 * @param months
	 * @return DateRange
	 */
	public static Date[] getDateRange(int months) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 2);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR, -12);
		calendar.set(Calendar.SECOND, 0);
		Date dateRangeMax = calendar.getTime();
		calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR, -12);
		calendar.add(Calendar.MONTH, -months + 1);
		calendar.set(Calendar.SECOND, 0);
		calendar.add(Calendar.SECOND, -1);
		Date dateRangeMin = calendar.getTime();
		Date[] dates = { dateRangeMax, dateRangeMin };
		return dates;
	}
	
	/**
	 * Objective: Clear certain files under desired Folder
	 * 
	 * @param desiredFolderPath
	 *            : Known folder Path
	 * @throws Exception
	 */
	public static void clearCertainFileUnderFolder(String desiredFolderPath) throws Exception {

		File downloadFile = new File(desiredFolderPath);
		if (downloadFile.exists())
			downloadFile.delete();
	}	
	
	  /**
	    * Objective: fill out input field
	    * @param element : WebElement object
	    * @param inputValue: string value
	 * @throws Exception 
	    */
	static public void input(WebElement element, String inputValue) throws Exception{  
		FindElementWait(element, 5);
		element.clear();
//		 customAssertion.assertFalse(inputValue.isEmpty(), "Desired to enter string is Empty:" + element);
	     element.sendKeys(inputValue);    
	   }
	
	  /**
	    * Objective: wait For Page load complete
	 * @throws Exception 
	    */ 
	   public static void waitForPageLoadComplete() {
	        WebDriverWait wait = new WebDriverWait(BaseTest.driver.get(), 200000);
	        wait.until(isPageLoaded());
	    }
	   
	   /**
	    * Objective: Verify Page load completed or not
	    * @return Function<WebDriver, Boolean>
	 * @throws Exception 
	    */  
	   public static Function<WebDriver, Boolean> isPageLoaded() {
		   return new Function<WebDriver, Boolean>() {
	            public Boolean apply(WebDriver driver) {
	                return ((JavascriptExecutor) BaseTest.driver.get()).executeScript("return document.readyState").equals("complete");
	            }
	        };
	   }
	   
	   /**
	    * wait until the element visible in max wait time setting which is set for all test cases
	    * if not visible at last, throw ElementNotVisibleException to the operations 
	    * @param element: the WebElement to be judged 
	 * @throws Exception 
	    */
	   static public void waitUtilElementVisible(WebElement element) throws Exception {   
			 waitUtilElementVisible(element, MAX_WAIT_FOR_ELEMENT);  
	       }  
	   
	   /**
	     * wait until the element visible in max wait time setting
	     * if not visible at last, throw ElementNotVisibleException to the operations   
	     * @param element:  the WebElement to be judged 
	     * @param timeout: timeout setting in seconds 
	     */
	    static public void waitUtilElementVisible(WebElement element, int timeout) {  
	    	
	    	
	           long start = System.currentTimeMillis();  
	           boolean isDisplayed = false;  
	           while (!isDisplayed && ((System.currentTimeMillis() - start) < timeout * 1000)) {  
	        	   
	               isDisplayed = (element == null)? false : element.isDisplayed();  
	           }  
	           if (!isDisplayed){  
	               throw new ElementNotVisibleException("the element is not visible in " + timeout + "seconds!");
	               }         
	       }  
	    
	    /**
		    * Objective: Verify if element is exist or not
		    * @author bella.ding
		    * @param webElement
		    * @return
		 * @throws Exception 
		    */
		   public static boolean isElementExist(WebElement webElement){
			   BaseTest.driver.get().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			   try{
				   webElement.isDisplayed();
				   return true;
			   }catch(NoSuchElementException ex){
				   return false;
			   }catch(StaleElementReferenceException ex){
				   return false;
			   }
			
		   }
		   
		   /**
		    * Objective: get specific window by title of window
		    * @author bella.ding
		    * @param windowIndexNo
		    * @return
		    * @throws InterruptedException
		    */
		   
		    public static void switchToWindow(int windowIndexNo) throws InterruptedException {
		        // Initialize parameters
		  		boolean switchedWindow = false;
		  		//Get the total size of windows in current application
		  		System.out.println("4444" + BaseTest.driver.get().getWindowHandles().size());
		        int total_Size = BaseTest.driver.get().getWindowHandles().size();
		        //Switch to desired window when assign the Valid Window Index
		        if(windowIndexNo < total_Size && windowIndexNo >= 0){ 
		        	//Switch to desired window by Index No.
		        	System.out.println("33333");
		        	Object[] windows=BaseTest.driver.get().getWindowHandles().toArray();
		        	BaseTest.driver.get().switchTo().window((String) windows[windowIndexNo]);
		        	Thread.sleep(1000);
		        	switchedWindow = true;
		        }else{
//		        	CustomAssertion.assertFalse(switchedWindow, "Assigned Window Index Invalid, should be less than:" + total_Size);
		        }                                          
		    }
		    
		    
		    /**
			 * click the element by JS
			 *
			 * @author mandy.chen
			 * @param element
			 * @throws Exception
			 */
			public static void clickByJavaScripts(WebElement element) throws Exception {
				FunctionUtil.waitForElementExist(element);
				((JavascriptExecutor) BaseTest.driver.get()).executeScript(
						"arguments[0].click();", element);
				Thread.sleep(1000);
			}
			
			/**
			 * Objective:Waiting for the Element be displayed by Loop when it did not exist first
			 * Return:
			 * @throws Exception
			 * @throws Exception
			 */
			public static void waitForElementExist(WebElement element) throws Exception{
				boolean elementIsExsit = isElementExist(element);
				int loopCount = 0;
				while(!elementIsExsit){
					Thread.sleep(3000);
					loopCount = loopCount + 1;
					elementIsExsit = isElementExist(element);
					if(loopCount == 10){
//						CustomAssertion.assertTrueAndExit(elementIsExsit, "Element not exist after waiting for 60 Seconds" + TestCaseBase.threadDriver.get().getTitle());
					}
				}
			}
		   
}
