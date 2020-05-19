package tellerium.mvcpages.seleniumutils;

public class WebElementNotFoundException extends RuntimeException
{

	public WebElementNotFoundException(String message)
	{
		this(message, null);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public WebElementNotFoundException(string message, Exception innerException = null)
	public WebElementNotFoundException(String message, RuntimeException innerException)
	{
		super(message, innerException);
	}
}