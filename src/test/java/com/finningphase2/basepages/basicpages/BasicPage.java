package com.finningphase2.basepages.basicpages;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;

import com.finningphase2.util.PageManager;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class BasicPage extends AbstractBasePage {

    protected Log log = LogFactory.getLog(this.getClass());
    protected static PageManager pageManager;
    protected String pageName;
    protected String pageUrl;
    
	public BasicPage(PageManager pm){
		pageManager = pm;
		PageFactory.initElements(pageManager.getDriver(), this);
	}

	@Override
	public void verifyDisplayed() throws Exception {
		// TODO Auto-generated method stub
	}

	public static WebElement FindElementWait(WebElement element) {
		return FindElementWait(element, 1);
	}
	public static WebElement FindElementWait(WebElement element, int timeoutInSeconds) {
		By by = getByFromElement(element);
		AtomicReference<List<WebElement>> foundElements = new AtomicReference<>();
		if (timeoutInSeconds > 0) {
			WebDriverWait wait = new WebDriverWait(pageManager.getDriver(), timeoutInSeconds);
			wait.ignoring(StaleElementReferenceException.class);
			wait.until( (WebDriver d) ->
					{
						try {
							foundElements.set(
									d.findElements(by)
											.stream()
											.filter(WebElement::isDisplayed)
											.collect(Collectors.toList())
							);
							var isFound = foundElements.get().stream().findAny();

							//poke the first element's property to make sure it is not stale
							if (!isFound.isPresent()) ((List<WebElement>) foundElements.get()).stream().findFirst().get().getText();
							return true;
						}
						catch (StaleElementReferenceException sere) {
							return false;
						}
						catch (NoSuchElementException nsee) {
							return false;
						}
						catch (TimeoutException toe) {
							return false;
						}
					}
			);
		}
		WebElement foundElement = foundElements.get().stream().findFirst().orElse(null);
		WebElement output = (  (((WrapsElement)foundElement).getWrappedElement()));

		return output;
	}

	public static List<WebElement> FindElementsWait(List<WebElement> elements) {
		return FindElementsWait(elements, 1);
	}

	public static List<WebElement> FindElementsWait(List<WebElement> elements, int timeoutInSeconds) {
		By by = getByFromElement(elements.get(0));
		AtomicReference<List<WebElement>> foundElements = new AtomicReference<>();
		if (timeoutInSeconds > 0) {
			//WebElement wer = new WebDriverWait(this.pageManager.getDriver(), 5).until((WebDriver dr1) -> dr1.findElement(By.id("q")));
			WebDriverWait wait = new WebDriverWait(pageManager.getDriver(), timeoutInSeconds);
			//.until((WebDriver dr1) -> dr1.findElement(By.id("q")));
			wait.ignoring(StaleElementReferenceException.class);
			wait.until( (WebDriver d) ->
					{
						try {
							foundElements.set(
									d.findElements(by)
											.stream()
											.filter(WebElement::isDisplayed)
											.collect(Collectors.toList())
							);
							var isFound = foundElements.get().stream().findAny();

							//poke the first element's property to make sure it is not stale
							if (!isFound.isPresent()) ((List<WebElement>) foundElements.get()).stream().findFirst().get().getText();
							return true;
						}
						catch (StaleElementReferenceException sere) {
							return false;
						}
						catch (NoSuchElementException nsee) {
							return false;
						}
						catch (TimeoutException toe) {
							return false;
						}
					}
			);
		}
		List<WebElement> foundElementList = foundElements.get().stream().collect(Collectors.toList());

		return foundElementList;
	}
	private static By getByFromElement(WebElement element) {

		By by = null;
		String[] pathVariables = (element.toString().split("->")[1].replaceFirst("(?s)(.*)\\]", "$1" + "")).split(":");

		String selector = pathVariables[0].trim();
		String value = pathVariables[1].trim();

		switch (selector) {
			case "id":
				by = By.id(value);
				break;
			case "className":
				by = By.className(value);
				break;
			case "tagName":
				by = By.tagName(value);
				break;
			case "xpath":
				by = By.xpath(value);
				break;
			case "cssSelector":
				by = By.cssSelector(value);
				break;
			case "linkText":
				by = By.linkText(value);
				break;
			case "name":
				by = By.name(value);
				break;
			case "partialLinkText":
				by = By.partialLinkText(value);
				break;
			default:
				throw new IllegalStateException("locator : " + selector + " not found!!!");
		}
		return by;
	}
}
