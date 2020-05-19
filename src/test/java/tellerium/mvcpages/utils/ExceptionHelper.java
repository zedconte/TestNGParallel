package tellerium.mvcpages.utils;

public final class ExceptionHelper
{
	public static String GetFullExceptionMessage(RuntimeException exception)
	{
		if (exception == null)
		{
			return "";
		}

//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var innerExceptionMessage = exception.getCause().getMessage();
		return String.format("%1$s\r\n%2$s: %3$s\r\n%4$s", innerExceptionMessage, exception.getClass().getName(), exception.getMessage(), exception.getStackTrace()).trim();
	}

	public static <T> T SwallowException(tangible.Func0Param<T> func, T defaultValue)
	{
		try
		{
			return func.invoke();
		}
		catch (Exception e)
		{
			return defaultValue;
		}
	}
	public static void SwallowException(tangible.Action0Param func)
	{
		try
		{
			func.invoke();
		}
		catch (Exception e)
		{

		}
	}
}