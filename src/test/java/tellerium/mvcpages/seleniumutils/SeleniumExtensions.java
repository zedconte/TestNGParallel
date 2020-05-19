package tellerium.mvcpages.seleniumutils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import tellerium.mvcpages.seleniumutils.exceptions.ElementIsNotClickableException;
import tellerium.mvcpages.utils.*;

import java.util.HashMap;

public final class SeleniumExtensions
{
	public static final int PageLoadTimeout = 120;

	/** 
	 Get rid of focus from currently focused element
	 
	 @param driver Selenium webdriver
	*/
	public static void Blur(RemoteWebDriver driver)
	{
		driver.switchTo().defaultContent();
		RetryHelper.Retry(3, () ->
		{
				ExecuteBlur(driver);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return IsThereElementWithFocus(driver) == false;
		});
	}

	private static void ExecuteBlur(RemoteWebDriver driver)
	{
		driver.executeScript("(function(){" + "\r\n" +
"                        var f= document.querySelector(':focus'); " + "\r\n" +
"                        if(f!=undefined){" + "\r\n" +
"                            f.blur();" + "\r\n" +
"                        }" + "\r\n" +
"                       })();");
	}

	public static void MoveMouseOffTheScreen(RemoteWebDriver driver)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var body = driver.findElementByTagName("body");
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var scrollY = SeleniumExtensions.GetScrollY(driver);
		(new Actions(driver)).moveToElement(body, 0, scrollY + 1).perform();
	}

	/** 
	 Tyoe text into field
	 
	 @param input Field
	 @param text Text to tyoe
	 //@param speed Speed of typing (chars per minute). 0 means default selenium speed
	*/

	public static void Type(WebElement input, String text)
	{
		Type(input, text, 0);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public static void Type(this IWebElement input, string text, int speed = 0)
	public static void Type(WebElement input, String text, int speed)
	{
		if (speed == 0)
		{
			input.sendKeys(text);
		}
		else
		{
			int delay = (1000 * 60) / speed;
			for (char charToType : text.toCharArray())
			{
				input.sendKeys(String.valueOf(charToType));
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/** 
	 Clear textual input without generating change event
	 
	 @param input Input element
	*/
	public static void ClearTextualInput(WebElement input)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var oldValue = input.getAttribute("value");
		if (tangible.StringHelper.isNullOrEmpty(oldValue) == false)
		{
			input.sendKeys(Keys.END);
			input.sendKeys(tangible.StringHelper.padLeft("", oldValue.length(), Keys.BACK_SPACE.charAt(0)));
		}
	}

	public static void Focus(WebElement input)
	{
		input.sendKeys("");
	}

	public static int GetVerticalScrollWidth(RemoteWebDriver driver)
	{
		//INFO: It's hard to get scrollbar width using JS. 17 its default size of scrollbar on Ms Windows platform
		return 17;
	}

	public static int GetWindowHeight(RemoteWebDriver driver)
	{
		return (int)(long)driver.executeScript("return window.innerHeight");
	}

	private static boolean IsElementInteractable(RemoteWebDriver driver, WebElement element)
	{
		//INFO: There is a few case when the mouse click can ommit actual element
		// 1) Element has rounded corners
		// 2) In inline-element space between lines is not clickable
		// 3) Given element has child elements
		return (boolean)driver.executeScript("" + "\r\n" +
"                    return (function(element)" + "\r\n" +
"                    {" + "\r\n" +
"                        function belongsToElement(subElement)" + "\r\n" +
"                        {" + "\r\n" +
"                            return element == subElement || element.contains(subElement);" + "\r\n" +
"                        }" + "\r\n" +
"                        var rec = element.getBoundingClientRect();" + "\r\n" +
"                        var elementAtPosition1 = document.elementFromPoint(rec.left, rec.top);" + "\r\n" +
"                        var elementAtPosition2 = document.elementFromPoint(rec.left+rec.width/2, rec.top+rec.height/2);" + "\r\n" +
"                        var elementAtPosition3 = document.elementFromPoint(rec.left+rec.width/3, rec.top+rec.height/3);" + "\r\n" +
"                        return belongsToElement(elementAtPosition1) || belongsToElement(elementAtPosition2) || belongsToElement(elementAtPosition3);" + "\r\n" +
"                    })(arguments[0]);" + "\r\n" +
"                ", element);
	}

	public static int GetPageHeight(RemoteWebDriver driver)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var scriptResult = driver.executeScript("return Math.max(document.body.scrollHeight, document.body.offsetHeight, document.documentElement.clientHeight, document.documentElement.scrollHeight, document.documentElement.offsetHeight);");
		return (int)(long)scriptResult;
	}


	public static HashMap<String, Object> GetPageDimensions(RemoteWebDriver driver)
	{
		HashMap<String, Object> obj = (HashMap<String, Object>)driver.executeScript("return {" + "\r\n" +
"                            width: Math.max(document.body.scrollWidth, document.body.offsetWidth, document.documentElement.clientWidth, document.documentElement.scrollWidth, document.documentElement.offsetWidth), " + "\r\n" +
"                            height: Math.max(document.body.scrollHeight, document.body.offsetHeight, document.documentElement.clientHeight, document.documentElement.scrollHeight, document.documentElement.offsetHeight)" + "\r\n" +
"                        };");
		return new HashMap((int)(long)obj.get("width"), (int)(long)obj.get("height"));
	}

	public static void ScrollToY(RemoteWebDriver driver, int y)
	{
		driver.executeScript(String.format("window.scrollTo(0,%1$s)", y));
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static int GetScrollY(RemoteWebDriver driver)
	{
		return (int)(long)driver.executeScript("" + "\r\n" +
"                if(typeof window.scrollY != 'undefined'){" + "\r\n" +
"                    return window.scrollY;" + "\r\n" +
"                }else if(typeof document.documentElement.scrollTop != 'undefined'){" + "\r\n" +
"                    return document.documentElement.scrollTop;" + "\r\n" +
"                }" + "\r\n" +
"                return 0;" + "\r\n" +
"");
	}

	/** 
	 Check if any element has currently focus
	 
	 @param driver Selenium webdriver
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Pure] private static bool IsThereElementWithFocus(RemoteWebDriver driver)
	private static boolean IsThereElementWithFocus(RemoteWebDriver driver)
	{
		return (boolean)driver.executeScript("return document.querySelector(':focus')!=undefined;");
	}

	public static final By ParentSelector = By.xpath("..");

	/** 
	 Return parent of given web element
	 
	 @param node Child element
	*/
	public static WebElement GetParent(WebElement node)
	{
		return node.findElement(ParentSelector);
	}

	/** 
	 Return type of input represented by the given web element
	 
	 @param inputElement Web element
	*/
	public static String GetInputType(WebElement inputElement)
	{
		String inputType = inputElement.getAttribute("type") != null ? inputElement.getAttribute("type") : "";
		return inputType.toLowerCase();
	}

	/** 
	 Click on any element with given text
	 
	 @param driver Selenium driver
	 @param scope Scope element to narrow link search
	 @param linkText Element tekst
	*/
	public static void ClickOnElementWithText(RemoteWebDriver driver, SearchContext scope, String linkText, boolean isPartialText)
	{
		IStableWebElement expectedElement = SeleniumFinderExtensions.GetStableElementWithText(driver, scope, linkText, isPartialText);
		ClickOn(driver, expectedElement);
	}

	public static void HoverOnElementWithText(RemoteWebDriver driver, SearchContext scope, String linkText, boolean isPartialText)
	{
		IStableWebElement expectedElement = SeleniumFinderExtensions.GetStableElementWithText(driver, scope, linkText, isPartialText);
		HoverOn(driver, expectedElement);
	}

	public static void ClickOn(RemoteWebDriver driver, WebElement expectedElement)
	{
		try
		{
			expectedElement.click();
		}
		catch (RuntimeException ex)
		{
			//INFO: Different driver throws different exception
			if (ex instanceof IllegalStateException  == false && ex instanceof WebDriverException == false)
			{
				throw ex;
			}

			Integer originalScrollPosition = null;
			if (expectedElement.getLocation().y > SeleniumExtensions.GetWindowHeight(driver))
			{
				originalScrollPosition = SeleniumExtensions.GetScrollY(driver);
				SeleniumExtensions.ScrollToY(driver, expectedElement.getLocation().y + expectedElement.getSize().height);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			try
			{
				SeleniumFinderExtensions.WaitUntil(driver, SeleniumFinderExtensions.SearchElementDefaultTimeout, (d) -> SeleniumExtensions.IsElementInteractable(driver, expectedElement));
			}
			catch (TimeoutException e)
			{
				throw new ElementIsNotClickableException(expectedElement);
			}

			expectedElement.click();
			if (originalScrollPosition != null)
			{
				SeleniumExtensions.ScrollToY(driver, originalScrollPosition.intValue());
			}
		}
	}

	public static void HoverOn(RemoteWebDriver driver, WebElement elementToHover)
	{
		Actions action = new Actions(driver);
		action.moveToElement(elementToHover).perform();
	}

	public static void AppendHtml(RemoteWebDriver driver, WebElement element, String html)
	{
		driver.executeScript("" + "\r\n" +
"                (function(parent,text){" + "\r\n" +
"                    var wrapper = document.createElement(\"div\");" + "\r\n" +
"                    wrapper.innerHTML = text;" + "\r\n" +
"                    for(var i=0; i< wrapper.childNodes.length; i++)" + "\r\n" +
"                    {" + "\r\n" +
"                        parent.appendChild(wrapper.childNodes[i]);" + "\r\n" +
"                    }" + "\r\n" +
"                })(arguments[0],arguments[1]);", element, html);
	}

	public static boolean IsPageLoaded(RemoteWebDriver driver)
	{
		return (boolean)driver.executeScript("return document && document.readyState == 'complete';");
	}

	public static void WaitUntilPageLoad(RemoteWebDriver driver)
	{
		SeleniumFinderExtensions.WaitUntil(driver, PageLoadTimeout, a -> SeleniumExtensions.IsPageLoaded(driver));
	}

	public static WebElement GetActiveElement(RemoteWebDriver driver)
	{
		return (WebElement)driver.executeScript("return document.activeElement;");
	}

	public static String GetElementDescription(SearchContext element)
	{
		IStableWebElement stableElement = element instanceof IStableWebElement ? (IStableWebElement)element : null;
		String tempVar = stableElement.GetDescription();
		return stableElement == null ? null : tempVar != null ? tempVar : "";
	}

	public static RemoteWebDriver GetWebDriver(WebElement webElement)
	{
		return ((WrapsDriver)webElement).getWrappedDriver() instanceof RemoteWebDriver ? (RemoteWebDriver)((WrapsDriver)webElement).getWrappedDriver() : null;
	}
}