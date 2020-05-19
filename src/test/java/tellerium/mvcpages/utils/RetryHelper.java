package tellerium.mvcpages.utils;

import tangible.Func0Param;

public final class RetryHelper
{
	public static boolean Retry(int numberOfRetries, tangible.Func0Param<Boolean> action)
	{
		int currentNumberOfRetries = numberOfRetries;
		boolean success;
		do
		{
			if (currentNumberOfRetries < numberOfRetries)
			{
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			success = action.invoke();
		} while (currentNumberOfRetries-- > 0 && success == false);

		return success;
	}


/*	public static RetryResult RetryWithExceptions(int numberOfRetries, Func0Param<Boolean> action)
	{
		return tellurium.tellerium.mvcpages.utils.RetryHelper.<RuntimeException>RetryWithExceptions(numberOfRetries, action);
	}*/

	public static <T extends RuntimeException> RetryResult RetryWithExceptions(int numberOfRetries, Func0Param<Boolean> action) throws T
	{
		RuntimeException lastException = null;
		boolean success = Retry(numberOfRetries, () ->
		{
				//try
				//{
					return action.invoke();
				//}
/*				catch (T ex)
				{
					lastException = ex;
					return false;
				}*/
		});
		RetryResult tempVar = new RetryResult();
		tempVar.setSuccess(success);
		tempVar.setLastException(lastException);
		return tempVar;
	}
}