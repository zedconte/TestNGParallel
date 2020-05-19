package tellerium.mvcpages.seleniumutils.exceptions;

import org.openqa.selenium.*;

public class ElementIsNotClickableException extends CannotInteractWithElementException
{
	public ElementIsNotClickableException(WebElement element)
	{
		super(String.format("Element %1$s is not clickable", element.getTagName()));

	}
}