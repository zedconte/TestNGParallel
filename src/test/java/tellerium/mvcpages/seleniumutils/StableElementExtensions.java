package tellerium.mvcpages.seleniumutils;

import org.openqa.selenium.*;

public final class StableElementExtensions
{
	public static IStableWebElement FindStableElement(SearchContext context, By by)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var element = context.findElement(by);
		return new StableWebElement(context, element, by, SearchApproachType.First);
	}

	public static IStableWebElement TryFindStableElement(SearchContext context, By by)
	{
		WebElement element = SeleniumFinderExtensions.TryFindElement(context, by);
		if (element == null)
		{
			return null;
		}
		return new StableWebElement(context, element, by, SearchApproachType.First);
	}
}