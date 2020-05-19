package tellerium.mvcpages.seleniumutils;

import org.openqa.selenium.*;
import tellerium.mvcpages.utils.XPathHelpers;

import java.util.List;

public class ByText extends By
{
	public static ByText FromPartial(String text)
	{
		String xpathLiteral = XPathHelpers.ToXPathLiteral(text.trim());
		String xpathToFind = String.format(".//*[contains(text(), %1$s) or ((@type='submit' or  @type='reset') and contains(@value,%1$s)) or contains(@title,%1$s)]", xpathLiteral);
		return new ByText(xpathToFind, String.format("With partial text: '%1$s'", text));
	}

	public static ByText From(String text)
	{
		String xpathLiteral = XPathHelpers.ToXPathLiteral(text.trim());
		String xpathToFind = String.format(".//*[((normalize-space(.) = %1$s) and (count(*)=0) ) or (normalize-space(text()) = %1$s) or ((@type='submit' or  @type='reset') and @value=%1$s) or (@title=%1$s)]", xpathLiteral);
		return new ByText(xpathToFind, String.format("With text: '%1$s'", text));
	}

	private ByText(String xpathToFind, String description)
	{
		//super.findElements() = context -> ((FindsByXPath)context).FindElementByXPath(xpathToFind);
		//super.findElements() = context -> ((FindsByXPath)context).FindElementsByXPath(xpathToFind);
		//Description = description;
	}

	@Override
	public List<WebElement> findElements(SearchContext searchContext) {
		return null;
	}
}