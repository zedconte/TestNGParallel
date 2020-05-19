package tellerium.mvcpages.seleniumutils.exceptions;

import tellerium.mvcpages.seleniumutils.SeleniumExtensions;
import tellerium.mvcpages.seleniumutils.WebElementNotFoundException;
import org.openqa.selenium.*;
import org.openqa.selenium.By;

public class CannotFindElementByException extends WebElementNotFoundException
{
	private By By;
	public final By getBy()
	{
		return By;
	}

	private SearchContext Context;
	public final SearchContext getContext()
	{
		return Context;
	}


	public CannotFindElementByException(By by, SearchContext context)
	{
		this(by, context, null);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public CannotFindElementByException(By @by, ISearchContext context, Exception originalException = null)
	public CannotFindElementByException(By by, SearchContext context, RuntimeException originalException)
	{
		super(String.format("Cannot find element %1$s inside %2$s", by, SeleniumExtensions.GetElementDescription(context)), originalException);
		By = by;
		Context = context;
	}
}