package tellerium.mvcpages.utils;

public class RetryResult
{
	private boolean Success;
	public final boolean getSuccess()
	{
		return Success;
	}
	public final void setSuccess(boolean value)
	{
		Success = value;
	}
	private RuntimeException LastException;
	public final RuntimeException getLastException()
	{
		return LastException;
	}
	public final void setLastException(RuntimeException value)
	{
		LastException = value;
	}
}