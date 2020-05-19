package tellerium.mvcpages.seleniumutils;

import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.interactions.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;

public interface IStableWebElement extends WebElement, Locatable, TakesScreenshot, WrapsElement, WrapsDriver
{
	void RegenerateElement();
	boolean IsStale();
	String GetDescription();
}