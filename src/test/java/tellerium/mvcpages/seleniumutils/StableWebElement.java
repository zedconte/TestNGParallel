package tellerium.mvcpages.seleniumutils;

import org.openqa.selenium.*;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
import tellerium.mvcpages.seleniumutils.exceptions.CannotFindElementByException;
import tellerium.mvcpages.utils.RetryHelper;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class StableWebElement implements IStableWebElement
{
	private SearchContext parent;
	private WebElement element;
	private By locator;
	private SearchApproachType searchApproach = SearchApproachType.values()[0];

	public StableWebElement(SearchContext parent, WebElement element, By locator, SearchApproachType searchApproach)
	{
		this.parent = parent;
		this.element = element;
		this.locator = locator;
		this.searchApproach = searchApproach;
	}

	public final void RegenerateElement()
	{
		IStableWebElement stableParent = parent instanceof IStableWebElement ? (IStableWebElement)parent : null;
		if (stableParent != null && stableParent.IsStale())
		{
			stableParent.RegenerateElement();
		}
		try
		{
			switch (searchApproach)
			{
				case First:
					this.element = this.parent.findElement(locator);
					break;
				case FirstAccessible:
					this.element = SeleniumFinderExtensions.FindFirstAccessibleElement(this.parent, locator);
					break;
				default:
					throw new IndexOutOfBoundsException("searchApproach");
			}
		}
		catch (RuntimeException ex)
		{
			throw new CannotFindElementByException(locator, parent, ex);
		}
	}

	public final boolean IsStale()
	{
		try
		{
			//INFO: If element is stale accessing any property should throw exception
			// ReSharper disable once UnusedVariable
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
			var tagName = this.element.getTagName();
			return false;
		}
		catch (StaleElementReferenceException e)
		{
			return true;
		}
	}

	public final String GetDescription()
	{
		IStableWebElement stableParent = parent instanceof IStableWebElement ? (IStableWebElement)parent : null;
		String parentDescription = stableParent != null ? stableParent.GetDescription() : null;
		String thisDescription = String.format("'%1$s'", this.locator);
		return parentDescription == null ? thisDescription : String.format("%1$s inside %2$s", thisDescription, parentDescription);
	}

	public final WebElement Unwrap()
	{
		if (IsStale())
		{
			RegenerateElement();
		}
		return element;
	}

	private <T> T Execute(tangible.Func0Param<T> function)
	{
		AtomicReference<T> result = null;
		Execute(() ->
		{
				result.set(function.invoke());
		});
		return result.get();
	}

	private void Execute(tangible.Action0Param action)
	{
		boolean success = RetryHelper.Retry(3, () ->
		{
				try
				{
					action.invoke();
					return true;
				}
				catch (StaleElementReferenceException e)
				{
					RegenerateElement();
					return false;
				}
		});
		if (success == false)
		{
			throw new WebElementNotFoundException("Element is no longer accessible");
		}
	}

	public final WebElement findElement(By by)
	{
		return Execute(() -> element.findElement(by));
	}

	public final List<WebElement> findElements(By by)
	{
		return Execute(() -> element.findElements(by));
	}

	public final void clear()
	{
		Execute(() -> element.clear());
	}

	public final void sendKeys(String text)
	{
		Execute(() -> element.sendKeys(text));
	}

	public final void submit()
	{
		Execute(() -> element.submit());
	}

	@Override
	public void sendKeys(CharSequence... charSequences) {

	}

	public final void click()
	{
		Execute(() -> element.click());
	}

	public final String getAttribute(String attributeName)
	{
		return Execute(() -> element.getAttribute(attributeName));
	}

/*	public final String GetProperty(String propertyName)
	{
		return Execute(() -> element.getProperty(propertyName));
	}*/

	public final String getCssValue(String propertyName)
	{
		return Execute(() -> element.getCssValue(propertyName));
	}

	public final String getTagName()
	{
		return Execute(() -> element.getTagName());
	}
	public final String getText()
	{
		return Execute(() -> element.getText());
	}
	public final boolean isEnabled()
	{
		return Execute(() -> element.isEnabled());
	}
	public final boolean isSelected()
	{
		return Execute(() -> element.isSelected());
	}
	public final Point getLocation()
	{
		return Execute(() -> element.getLocation());
	}
	public final Dimension getSize()
	{
		return Execute(() -> element.getSize());
	}

	@Override
	public Rectangle getRect() {
		return null;
	}

	public final boolean isDisplayed()
	{
		return Execute(() -> element.isDisplayed());
	}

	public final Point getLocationOnScreenOnceScrolledIntoView()
	{
		return Execute(() -> GenericHelpers.<Locatable>As(element).getCoordinates().inViewPort());
	}

	public final Coordinates getCoordinates()
	{
		return Execute(() -> GenericHelpers.<Locatable>As(element).getCoordinates());
	}

	public final <X> X getScreenshotAs(OutputType<X> outputType)
	{
		return Execute(() -> GenericHelpers.<TakesScreenshot>As(element).getScreenshotAs(outputType));
	}

	public final WebElement getWrappedElement()
	{
		return Unwrap();
	}

	public final WebDriver getWrappedDriver()
	{
		boolean tempVar = element instanceof WrapsDriver;
		WrapsDriver driverWrapper = tempVar ? (WrapsDriver)element : null;
		if (tempVar)
		{
			return driverWrapper.getWrappedDriver();
		}
		throw new UnsupportedOperationException(String.format("Element %1$s does not have information about driver", this.GetDescription()));
	}
}