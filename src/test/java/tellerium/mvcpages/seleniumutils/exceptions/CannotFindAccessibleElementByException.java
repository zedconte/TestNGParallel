package tellerium.mvcpages.seleniumutils.exceptions;

import org.openqa.selenium.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class CannotFindAccessibleElementByException extends CannotFindElementByException
{
	private ArrayList<String> candidatesDescriptions;

	private List<WebElement> candidates;
	public final List<WebElement> getCandidates()
	{
		return candidates;
	}


	public CannotFindAccessibleElementByException(By by, SearchContext context, AtomicReference<List<WebElement>> candidates)
	{
		this(by, context, candidates, null);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public CannotFindAccessibleElementByException(By @by, ISearchContext context, IReadOnlyList<IWebElement> candidates, Exception originalException = null)
	public CannotFindAccessibleElementByException(By by, SearchContext context, AtomicReference<List<WebElement>>candidates, RuntimeException originalException)
	{
		super(by, context, originalException);
		this.candidates = candidates.get();
		this.candidatesDescriptions = GetCandidatesDescriptions();
	}

	@Override
	public String getMessage()
	{
		if (getCandidates() == null || getCandidates().size() == 0)
		{
			return super.getMessage();
		}
		return String.format("%1$s which meets accessibility criteria. Potential candidates:%2$s", super.getMessage(), tangible.StringHelper.join("", candidatesDescriptions.toArray(new String[0])));
	}

	private ArrayList<String> GetCandidatesDescriptions()
	{
		//return getCandidates() == null ? null :
/*		return getCandidates().stream().filter(x ->
		{
				//try
				//{
					 String.format("\r\n\t- %1$s[Displayed=%2$s, Enabled=%3$s]", x.getTagName(), x.isDisplayed(), x.isEnabled());
				//}
				*//*catch (Exception e)
				{
					return null;
				}*//*

			//return false;
		}).collect(Collectors.toList());*/
		return (ArrayList<String>) getCandidates().stream().filter(
				element ->!tangible.StringHelper.isNullOrWhiteSpace(element.getTagName())
		).map(element -> String.format("\r\n\t- %1$s[Displayed=%2$s, Enabled=%3$s]", element.getTagName(), element.isDisplayed(), element.isEnabled())
		)
				//{
				//	if(
							//!tangible.StringHelper.isNullOrWhiteSpace(String.format("\r\n\t- %1$s[Displayed=%2$s, Enabled=%3$s]", element.getTagName(), element.isDisplayed(), element.isEnabled()))){
						//return String.format("\r\n\t- %1$s[Displayed=%2$s, Enabled=%3$s]", element.getTagName(), element.isDisplayed(), element.isEnabled());
					//}

					//return null;
				//}

		.collect(Collectors.toList());
		//.collect(Collectors.joining(","))
	}
}