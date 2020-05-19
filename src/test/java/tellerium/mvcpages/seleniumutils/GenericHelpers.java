package tellerium.mvcpages.seleniumutils;

import org.openqa.selenium.WebElement;

public final class GenericHelpers
{
	public static <TInterface> TInterface As(WebElement element)
	{
		TInterface typed = (TInterface)element;
		if (typed == null)
		{
			String errorMessage = String.format("Underlying element does not support this opperation. It should implement %1$s interface", "Generic Class");
			throw new UnsupportedOperationException(errorMessage);
		}
		return typed;
	}
}