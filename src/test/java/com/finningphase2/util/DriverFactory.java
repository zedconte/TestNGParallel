package com.finningphase2.util;

import bsh.org.objectweb.asm.Constants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class DriverFactory {

    static Properties PROPERTIES_RESOURCES = SystemUtil.loadPropertiesResources("/testdata_common.properties");

    public static WebDriver createInstance(String browserType, String onGrid) throws MalformedURLException {
        WebDriver driver = null;

        if (browserType.toLowerCase().contains("chrome")) {
            switch (onGrid.toLowerCase()) {
                case "false":
                    driver = createLocalChromeDriver();
                    break;
                case "true":
                    driver = createRemoteChromeDriver();
                    break;
                default:
                    //Log.error("  !!! The value for Constants.RUNNER is not correct; Valid options are: localhost, remote");
                    Assert.fail();
            }
        }
        return driver;
    }

    private static WebDriver createLocalChromeDriver() {
        File chromeDriver = new File("./lib/chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", chromeDriver.getAbsolutePath());


        ChromeOptions options = new ChromeOptions();
        options.addExtensions(new File("lib/extension_2_12_2_0.crx"));
        options.addArguments("--test-type");
        options.addArguments("disable-geolocation");
        options.addArguments("start-maximized");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().deleteAllCookies();
        return driver;
    }

    private static WebDriver createRemoteChromeDriver() throws MalformedURLException {
        File chromeDriver = new File("./lib/chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", chromeDriver.getAbsolutePath());
        String host = PROPERTIES_RESOURCES.getProperty("host");
        String port = PROPERTIES_RESOURCES.getProperty("port");

        ChromeOptions options = new ChromeOptions();
        options.addExtensions(new File("lib/extension_2_12_2_0.crx"));
        options.addArguments("--test-type");
        options.addArguments("disable-geolocation");
        options.addArguments("start-maximized");

        WebDriver driver = new RemoteWebDriver(new URL("http://" + host + ":" + port + "/wd/hub"), options);
        driver.manage().deleteAllCookies();
        return driver;
    }
}