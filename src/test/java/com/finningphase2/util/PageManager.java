package com.finningphase2.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import com.BaseTest;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Updated by Icey.Qi  fix some problems like print error issue.
 */
public class PageManager extends BaseTest {

	private WebDriver driver;
	protected String browserFlag;
	protected Log log = LogFactory.getLog(this.getClass());

	public PageManager(WebDriver driverRemote, String browser) {
		driver = driverRemote;
		browserFlag = browser;
		PageFactory.initElements(driver, this);
	}

	public WebDriver getDriver() {
		return driver;
	}

	public String getBrowserFlag() {
		return browserFlag;
	}

	public void back() throws Exception{
		try{
			driver.navigate().back();
		} catch (Exception e) {
			printError(e);
		}
	}

	public void navigate(String url) {
		try {
			driver.navigate().to(url);
		} catch (NoSuchElementException e) {
			printError(e);
		} catch (WebDriverException e) {
			printError(e);
		}
	}

	/**
	 * scroll windows to view the element
	 * @author icey.qi
	 * @param element
	 * @throws Exception 
	 */
	public void scrollToViewElement(WebElement element, String elementName) throws Exception{
		try{
			Thread.sleep(2000);
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].scrollIntoView(true);",element);
			log.info( "Windows is scrolled to view " + elementName +" element .");
		}catch (NoSuchElementException | NullPointerException var3) {
			String path = snapshot((TakesScreenshot) driver);
			log.info(ErrorType.ELEMENT_NOTFOUND  + elementName +"element is not visible. " + "Screencast below: " );
		} catch (StaleElementReferenceException var4) {
			String path = snapshot((TakesScreenshot) driver);
			log.info(ErrorType.ELEMENT_STALE + elementName + "element is not visible. " + "Screencast below: " );
		}
	}

	public void clickByJS(WebElement element,String elementName) throws Exception{
		try{
			Thread.sleep(3000);
			((JavascriptExecutor) driver).executeScript("arguments[0].click()",element);
			log.info(element.toString() + " is clicked.");
		} catch (NoSuchElementException | NullPointerException var3) {
			String path = snapshot((TakesScreenshot) driver);
			log.info(ErrorType.ELEMENT_NOTFOUND  + elementName +"element is not visible. " + "Screencast below: " );
		} catch (StaleElementReferenceException var4) {
			String path = snapshot((TakesScreenshot) driver);
			log.info(ErrorType.ELEMENT_STALE + elementName + "element is not visible. " + "Screencast below: " );
		}
	}

	public void inputByJS(String element,String elementName, String value) throws Exception{
		try{
			Thread.sleep(3000);
			((JavascriptExecutor) driver).executeScript(element+".innerText='"+value+"'");
			log.info(element.toString() + " is supplied with an input.");
		} catch (NoSuchElementException | NullPointerException var3) {
			String path = snapshot((TakesScreenshot) driver);
			log.info(ErrorType.ELEMENT_NOTFOUND  + elementName +"element is not visible. " + "Screencast below: " );
		} catch (StaleElementReferenceException var4) {
			String path = snapshot((TakesScreenshot) driver);
			log.info(ErrorType.ELEMENT_STALE + elementName + "element is not visible. " + "Screencast below: " );
		}
	}


	//For those elements identified with Javascript
	public void clickJS(String element, String elementName) throws Exception{
		try{
			Thread.sleep(2000);
			((JavascriptExecutor) driver).executeScript(element+".click();");
			log.info(element.toString() + " is clicked.");
		} catch (NoSuchElementException | NullPointerException var3) {
			String path = snapshot((TakesScreenshot) driver);
			log.info(ErrorType.ELEMENT_NOTFOUND  + elementName + "element is not visible. " + "Screencast below: " );
		} catch (StaleElementReferenceException var4) {
			String path = snapshot((TakesScreenshot) driver);
			log.info(ErrorType.ELEMENT_STALE + elementName + "element is not visible. " + "Screencast below: " );
		}
	}
	
	//For those elements identified with Javascript
		public void executeJS(String script, String elementName) throws Exception{
			try{
				Thread.sleep(2000);
				((JavascriptExecutor) driver).executeScript(script);
				log.info(script + " is executed.");
			} catch (NoSuchElementException | NullPointerException var3) {
				String path = snapshot((TakesScreenshot) driver);
				log.info(ErrorType.ELEMENT_NOTFOUND  + elementName + "element is not visible. " + "Screencast below: " );
			} catch (StaleElementReferenceException var4) {
				String path = snapshot((TakesScreenshot) driver);
				log.info(ErrorType.ELEMENT_STALE + elementName + "element is not visible. " + "Screencast below: " );
			}
		}

	public String getTitle() {
		String t = null;
		try {
			t = driver.getTitle();
			log.info("Page Title is " + t);
		} catch (NoSuchElementException e) {
			printError(e);
		} catch (WebDriverException e) {
			printError(e);
		}
		return t;
	}

	public List<WebElement> findAllElement(String xpath){
		try {
			return driver.findElements(By.xpath(xpath));
		} catch (NoSuchElementException | NoSuchFrameException e) {
			printError(e);
		} catch (WebDriverException e) {
			printError(e);
		}catch (Exception e) {
			printError(e);
		}
		return null;
	}

	public WebElement findElement(String xpath){
		try {
			return driver.findElement(By.xpath(xpath));
		} catch (NoSuchElementException | NoSuchFrameException e) {
			printError(e);
		} catch (WebDriverException e) {
			printError(e);
		}catch (Exception e) {
			printError(e);
		}
		return null;
	}

	public WebElement findElement(By by){
		try {
			return driver.findElement(by);
		} catch (NoSuchElementException | NoSuchFrameException e) {
			printError(e);
		} catch (WebDriverException e) {
			printError(e);
		}catch (Exception e) {
			printError(e);
		}
		return null;
	}

	public void switchToDefaultContent() {
		try {
			driver.switchTo().defaultContent();
			log.info("Switched to Default Content Frame " );
		} catch (NoSuchElementException | NoSuchFrameException e) {
			printError(e);
		} catch (WebDriverException e) {
			printError(e);
		}
	}

	public void switchToFrame(WebElement frame) {
		try {
			driver.switchTo().frame(frame);
			log.info("Switched to Frame " + frame.toString());
		} catch (NoSuchElementException | NoSuchFrameException e) {
			printError(e);
		} catch (WebDriverException e) {
			printError(e);
		}
	}
	
	public void switchToNewTab() throws Exception 
	{
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
	}

	//to switch to a new window
	public void switchToNewWindow() {
		try {
			// Switch to new window opened
			for (String winHandle : driver.getWindowHandles()) {
				driver.switchTo().window(winHandle);
			}
			log.info("Switched to a new window.");
		} catch (NoSuchElementException | NoSuchWindowException e) {
			printError(e);
		} catch (WebDriverException e) {
			printError(e);
		}

	}

	public void switchToOriginWindow() {
		try {
			Set<String> winHandels = driver.getWindowHandles(); 
			List<String> it = new ArrayList<String>(winHandels);
			driver.switchTo().window(it.get(0));
			log.info("Switched to original window.");
		} catch (NoSuchElementException | NoSuchWindowException e) {
			printError(e);
		} catch (WebDriverException e) {
			printError(e);
		}

	}

	public void click(WebElement element) throws Exception {
		try {
			String elementInfomation = element.toString();
			element.click();
			log.info("Clicked " + elementInfomation);
		} catch (NoSuchElementException e) {
			printError(e, element);
		} catch (StaleElementReferenceException e) {
			printError(e, element);
		} catch (WebDriverException e) {
			printError(e);
		}
	}

	public void sendKeys(WebElement element, String keys) throws Exception {
		try {
			Thread.sleep(2000);
			element.sendKeys(keys);
			log.info("Sent " + keys + " to " + element.toString());
		} catch (NoSuchElementException e) {
			printError(e, element);
		} catch (StaleElementReferenceException e) {
			printError(e, element);
		} catch (WebDriverException e) {
			printError(e);
		}
	}

	public void hoverOver(WebElement element){
		try {
			until(element);
			Actions action = new Actions(driver);
			action.moveToElement(element).perform();
			Thread.sleep(2000);
		} catch (NoSuchElementException e) {
			printError(e, element);
		} catch (StaleElementReferenceException e) {
			printError(e, element);
		} catch (WebDriverException e) {
			printError(e);
		} catch (InterruptedException e) {
			printError(e);
		}
	}

	public String getText(WebElement element) {
		String t = null;
		try {
			t = element.getText();
			log.info("Got Text " + t + " of " + element.toString());
		} catch (NoSuchElementException e) {
			printError(e, element);
		} catch (StaleElementReferenceException e) {
			printError(e, element);
		} catch (WebDriverException e) {
			printError(e);
		}
		return t;
	}

	public void clickByAction(WebElement element) {
		try {
			Actions actions = new Actions(driver);
			actions.moveToElement(element).click().build().perform();
			log.info("clicked By Action " + element.toString());
		} catch (NoSuchElementException e) {
			printError(e, element);
		} catch (StaleElementReferenceException e) {
			printError(e, element);
		} catch (WebDriverException e) {
			printError(e);
		}
	}

	public void maximizeBrowser() {
		try {
			driver.manage().window().maximize();
		} catch (NoSuchElementException e) {
			printError(e);
		} catch (WebDriverException e) {
			printError(e);
		}
	}

	public void until(WebElement element, int timeout) {
		try {
			WebDriverWait wait = new WebDriverWait(driver,
					timeout);
			wait.until(ExpectedConditions.visibilityOf(element));
			log.info("Waited until " + element.toString() + "is visible.");
		} catch (NoSuchWindowException | NoSuchElementException e) {
			printError(e, element);
		} catch (StaleElementReferenceException e) {
			printError(e, element);
		} catch (TimeoutException e) {
			printError(e, element);
		} catch (WebDriverException e) {
			printError(e);
		}
	}

	public void until(WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver,
					10);
			wait.until(ExpectedConditions.visibilityOf(element));
			log.info("Waited until " + element.toString() + "is visible.");
		} catch (UnreachableBrowserException e) {
			printError(e);
		} catch (NoSuchWindowException | NoSuchElementException e) {
			printError(e, element);
		} catch (StaleElementReferenceException e) {
			printError(e, element);
		} catch (TimeoutException e) {
			printError(e, element);
		} catch (WebDriverException e) {
			printError(e);
		}
		if (!element.isDisplayed()) {
			printError("Element is not displayed'", element);
		}
	}

	public void untilClickable(WebElement element, int timeout) {
		try {
			WebDriverWait wait = new WebDriverWait(driver,
					timeout);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			log.info("Waited until " + element.toString() + "is clickable.");
		} catch (NoSuchWindowException | NoSuchElementException e) {
			printError(e, element);
		} catch (StaleElementReferenceException e) {
			printError(e, element);
		} catch (TimeoutException e) {
			printError(e, element);
		} catch (WebDriverException e) {
			printError(e);
		}
		if (!element.isEnabled()) {
			printError("Element is not clickable'", element);
		}
	}

	public void select(WebElement element, String value) throws Exception {
		Thread.sleep(2000);
		try {
			if (browserFlag.equals("firefox") || browserFlag.equals("safari")) {
				element.sendKeys(value); // firefox
				log.info("Selected " + value + "from option list " + element.toString());
			} else if (browserFlag.equals("ie") || browserFlag.equals("chrome")) {
				// element.click();
				List<WebElement> optionList = element.findElements(By
						.tagName("option"));
				for (WebElement option : optionList) {
					if (option.getText().trim().equalsIgnoreCase(value)) {
						option.click();
						log.info("Selected " + value + "from option list " + element.toString());
						break;
					}
				}

			}
		} catch (NoSuchElementException e) {
			printError(e, element);
		} catch (StaleElementReferenceException e) {
			printError(e, element);
		} catch (WebDriverException e) {
			printError(e, element);
		}

	}

	/**
	 * updated. Please do NOT use this function if you want to verify an element is not visible
	 * @author icey.qi 
	 * @param element
	 * @return
	 * @throws IOException
	 */
	public boolean isVisible(WebElement element,String elementName) throws IOException {
		try {
			element.isDisplayed();
			log.info(element.toString() + " is visible.");
		} catch (NoSuchElementException | NullPointerException var3) {
			String path = snapshot((TakesScreenshot) driver);
			log.info(ErrorType.ELEMENT_NOTFOUND  + elementName +"element is not visible. " + "Screencast below: " );
			return false;
		} catch (StaleElementReferenceException var4) {
			String path = snapshot((TakesScreenshot) driver);
			log.info(ErrorType.ELEMENT_STALE + elementName + "element is not visible. " + "Screencast below: " );
			return false;
		}
		return element.isDisplayed();
	}

	/**
	 * updated. Please use this function if you want to verify an element is not visible. and you use @FindBy to define the element
	 * @author icey.qi 
	 * @param element
	 * @return true:element is not visible. false : element is visible
	 * @throws Exception
	 */
	public boolean isNotVisible(WebElement element,String elementName) throws Exception {
		try {
			element.isDisplayed();
		} catch (NullPointerException var3) {
			log.info(elementName + " element is not visible.");
			return true;
		} 
		catch (NoSuchElementException var3) {
			log.info(elementName + " element is not visible.");
			return true;
		}catch (StaleElementReferenceException var4) {
			log.info(elementName + " element is not visible.");
			return true;
		}
		String path = snapshot((TakesScreenshot) driver);
		log.info(ErrorType.ELEMENT_STALE + elementName + " element is not visible. " + "Screencast below: " );
		return false;
	}

	public boolean isElementPresent(By by, String elementName){
		try{
			driver.findElement(by);
			log.info(elementName + " element is present");
			return true;
		}catch (NoSuchElementException e){
			return false;
		}
	}

	/**
	 * updated. Please use this function if you want to verify an element is not visible.And if you use xpath to find element
	 * @author icey.qi 
	 //* @param element
	 * @return true:element is not visible. false : element is visible
	 * @throws Exception
	 */
	public boolean isNotVisible(String xpath,String elementName) throws Exception {
		try {
			driver.findElement(By.xpath(xpath));
		} catch (NullPointerException var3) {
			log.info(elementName + " element is not visible.");
			return true;
		} catch (NoSuchElementException var3) {
			log.info(elementName + " element is not visible.");
			return true;
		}catch (StaleElementReferenceException var4) {
			log.info(elementName + " element is not visible.");
			return true;
		}
		String path = snapshot((TakesScreenshot) driver);
		log.info(ErrorType.ELEMENT_STALE + elementName + " element is not visible. " + "Screencast below: " );
		return false;
	}

	public String snapshot(TakesScreenshot drivername) {
		String currentPath = "./reports/errorimages";
		String returnPath = "./errorImages";
		File scrFile = drivername.getScreenshotAs(OutputType.FILE);
		try {
			log.info("save snapshot path is:" + currentPath + "/"
					+ getDatetime() + ".png");
			FileUtils
			.copyFile(scrFile, new File(currentPath + "\\" + getDatetime() + ".png"));
			FileUtils
			.copyFile(scrFile, new File(returnPath + "\\" + getDatetime() + ".png"));
		} catch (IOException e) {
			System.out.println("Can't save screenshot");
			return "";
		} finally {
			System.out.println("screen shot finished, it's in " + currentPath
					+ " folder");
			return returnPath + "/" + getDatetime() + ".png";
		}
	}

	public static String getDatetime() {

		SimpleDateFormat date = new SimpleDateFormat("yyyymmdd_hhmmss");

		return date.format(new Date());

	}
	// select a value from a selector

	public void printError(Object e) {
		//   log.error(e);
		String path = snapshot((TakesScreenshot) driver);
		log.info(e + "\nScreencast below: " );
	}

	public void printError(Object e, WebElement element) {
		//    log.error(e);
		String path = snapshot((TakesScreenshot) driver);
		log.info(element.toString() + "\n" + e + "\nScreencast below: " );
	}

	public void printError( WebDriverException e) {
		//   log.error("webdriver is not reachable");
		log.info("Webdriver is not reachable: "+e);
	}

	public enum ErrorType {
		ELEMENT_NOTFOUND("Element was not found, "),
		ELEMENT_STALE("Element was no longer located in the DOM and has become stale, "),
		WAIT_TIMEOUT("Wait timeout occured, ");
		private final String errorMsg;

		private ErrorType(String errorMsg) {
			this.errorMsg = errorMsg;
		}
	}

	public void dragDrop (WebElement from, WebElement to) throws Exception{
		Thread.sleep(2000);
		try{
			Actions builder = new Actions(driver);
			Action dragAndDrop = builder.clickAndHold(from)
					.moveToElement(to)
					.release(to)
					.build();
			dragAndDrop.perform();
			log.info(from+ " element dragged and dropped");
		}catch(NoSuchElementException var3){
			log.info(from+ " element not visible.");
		}
	}

	//This is the reporting function for the Excel Reports. This  Sets the values into a linked hash map that is then written into the Excel file in IOUTIL
	public void report(int caseNum, int stepNo, String Status, String Exception) throws Exception{
		LinkedHashMap<String, String> resultStep = new LinkedHashMap<String,String>();
		resultStep.put("TestCase Name", TestData.getTitle(caseNum).toString());
		resultStep.put("Step Number", "Step "+stepNo);
		resultStep.put("Status", Status);
		resultStep.put("Step Description", TestData.getDesc(caseNum, stepNo));
		resultStep.put("Expected Result", TestData.getExp(caseNum, stepNo));
		resultStep.put("Exception", Exception);
		resultStep.put("Browser", BaseTest.browserName);
		results.add(resultStep);

	}

}
