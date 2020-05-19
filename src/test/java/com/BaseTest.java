package com;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import com.finningphase2.util.DriverFactory;
import com.finningphase2.util.DriverManager;
import com.finningphase2.util.PageManager;
import com.finningphase2.util.TestData;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseTest {

    //Declare ThreadLocal Driver (ThreadLocalMap) for ThreadSafe Tests
    public static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();
    public CapabilityFactory capabilityFactory = new CapabilityFactory();
    public LinkedList<LinkedHashMap<String, String>> results = new LinkedList<LinkedHashMap<String, String>>();
    public static String browserName ="chrome";
    public static String URL;
    protected PageManager pageManager;
    public String browserFlag;
    protected WebDriver driver_original;
    protected EventFiringWebDriver driverEventFiring;


    @BeforeClass
    @Parameters(value={"browser"})
    public void setup (String browser) throws MalformedURLException {
        //Set Browser to ThreadLocalMap
        browserFlag = browser;
        driver_original = DriverFactory.createInstance(browserName, "true");
        DriverManager.setWebDriver(driver_original);
        driverEventFiring = new EventFiringWebDriver(driver_original);

//        driver.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilityFactory.getCapabilities(browser)));
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method caller) {
        String[] classes = caller.getDeclaringClass().getName().split("\\.");
        String className = classes[classes.length - 1];
        pageManager = new PageManager(driverEventFiring,browserFlag);
        pageManager.navigate(URL);
    }

    public WebDriver getDriver() {
        //Get driver from ThreadLocalMap
        return driver.get();
    }

    @AfterMethod
    public void tearDown() {
        getDriver().quit();
    }

    @AfterClass void terminate () {
        //Remove the ThreadLocalMap element
        driver.remove();
    }

    private void setDefaultTestData() {
        //		String s = this.getClass().getName();
        String filename = "testdata_finningphase2.properties";
		/*String filename = ("testdata_" + s.split("\\.")[s.split("\\.").length - 1] + ".properties");
		log.info("Setting...filename=" + filename);*/
        TestData.load(filename);

    }
}