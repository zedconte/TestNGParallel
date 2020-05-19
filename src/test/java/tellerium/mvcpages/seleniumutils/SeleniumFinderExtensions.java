package tellerium.mvcpages.seleniumutils;

import org.openqa.selenium.remote.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.*;
import tellerium.mvcpages.seleniumutils.exceptions.CannotFindAccessibleElementByException;
import tellerium.mvcpages.seleniumutils.exceptions.CannotFindElementByException;
import tellerium.mvcpages.utils.ExceptionHelper;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

public final class SeleniumFinderExtensions
{
	public static final int SearchElementDefaultTimeout = 30;

	/** 
	 Search for element with given id
	 
	 @param driver Selenium driver
	 @param elementId Id of expected element
	 //@param timeout Timout for element serch
	*/

	public static IStableWebElement GetStableAccessibleElementById(RemoteWebDriver driver, String elementId)
	{
		return GetStableAccessibleElementById(driver, elementId, SearchElementDefaultTimeout);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public static IStableWebElement GetStableAccessibleElementById(this RemoteWebDriver driver, string elementId, int timeout = SearchElementDefaultTimeout)
	public static IStableWebElement GetStableAccessibleElementById(RemoteWebDriver driver, String elementId, int timeout)
	{
		By by = By.id(elementId);
		return GetStableAccessibleElementByInScope(driver, by, driver, timeout);
	}


	public static IStableWebElement GetStableAccessibleElementByInScope(RemoteWebDriver driver, By by, SearchContext scope)
	{
		return GetStableAccessibleElementByInScope(driver, by, scope, SearchElementDefaultTimeout);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: internal static IStableWebElement GetStableAccessibleElementByInScope(this RemoteWebDriver driver, By @by, ISearchContext scope, int timeout = SearchElementDefaultTimeout)
	public static IStableWebElement GetStableAccessibleElementByInScope(RemoteWebDriver driver, By by, SearchContext scope, int timeout)
	{
		WebElement foundElement = GetFirstAccessibleElement(driver, by, scope, timeout);
		return new StableWebElement(scope, foundElement, by, SearchApproachType.FirstAccessible);
	}

	public static WebElement FindFirstAccessibleElement(SearchContext scope, By locator)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var candidates = scope.findElements(locator);
		AtomicReference<List<WebElement>> candidatesAtomic =
				new AtomicReference<List<WebElement>>(candidates);
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		WebElement foundElement = candidates.stream().findFirst().orElse(null);
		if (foundElement == null)
		{
			throw new CannotFindAccessibleElementByException(locator, scope, candidatesAtomic);
		}
		return foundElement;
	}

	private static WebElement GetFirstAccessibleElement(RemoteWebDriver driver, By by, SearchContext scope, int timeout)
	{
		AtomicReference<List<WebElement>> candidates = null;
		try
		{
			return SeleniumFinderExtensions.WaitUntil(driver, timeout, (a) ->
			{
					candidates.set(scope.findElements(by));
					return FirstAccessibleOrDefault(candidates.get());
			});
		}
		catch (TimeoutException ex)
		{
			throw new CannotFindAccessibleElementByException(by, scope, candidates, ex);
		}
	}

	private static boolean isElementAccessible(WebElement element)
	{
		return element != null && element.isDisplayed() && element.isEnabled();
	}

	private static WebElement FirstAccessibleOrDefault(List<WebElement> elements)
	{
		return elements.stream().filter(element ->
				{
					try
					{
						return isElementAccessible(element);
					}
					catch (StaleElementReferenceException e)
					{
						return false;
					}
				}
		).findFirst().get();
	}


	public static IStableWebElement GetStableElementById(RemoteWebDriver driver, String elementId)
	{
		return GetStableElementById(driver, elementId, SearchElementDefaultTimeout);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public static IStableWebElement GetStableElementById(this RemoteWebDriver driver, string elementId, int timeout = SearchElementDefaultTimeout)
	public static IStableWebElement GetStableElementById(RemoteWebDriver driver, String elementId, int timeout)
	{
		By by = By.id(elementId);
		return GetStableElementByInScope(driver, driver, by, timeout);
	}


	public static IStableWebElement GetStableElementByInScope(RemoteWebDriver driver, SearchContext scope, By by)
	{
		return GetStableElementByInScope(driver, scope, by, 30);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public static IStableWebElement GetStableElementByInScope(this RemoteWebDriver driver, ISearchContext scope, By by, int timeout = 30)
	public static IStableWebElement GetStableElementByInScope(RemoteWebDriver driver, SearchContext scope, By by, int timeout)
	{
		WebElement foundElement = GetFirstElement(driver, scope, by, timeout);
		return new StableWebElement(scope, foundElement, by, SearchApproachType.First);
	}

	private static WebElement GetFirstElement(RemoteWebDriver driver, SearchContext scope, By by, int timeout)
	{
		try
		{
			return SeleniumFinderExtensions.WaitUntil(driver, timeout, d -> scope.findElement(by));
		}
		catch (TimeoutException ex)
		{
			throw new CannotFindElementByException(by, scope, ex);
		}
	}

	public static <TResult> TResult WaitUntil(RemoteWebDriver driver, int timeout, Function<WebDriver, TResult> waitPredictor)
	{
		WebDriverWait waiter = new WebDriverWait(driver, Duration.ofSeconds(timeout).toSeconds());
		return waiter.until(waitPredictor);
	}

	public static IStableWebElement GetStableElementWithText(RemoteWebDriver driver, SearchContext scope, String text, boolean isPartialText)
	{
		By by = BuildLocatorByContainedText(text, isPartialText);
		return GetStableAccessibleElementByInScope(driver, by, scope);
	}

	private static By BuildLocatorByContainedText(String text, boolean isPartialText)
	{
		return isPartialText ? ByText.FromPartial(text) : ByText.From(text);
	}

	public static WebElement TryFindElement(SearchContext context, By by)
	{
		return 	ExceptionHelper.SwallowException(() -> context.findElement(by), null);
	}
}