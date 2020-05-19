package tellerium.mvcpages.seleniumutils;

public enum SearchApproachType
{
	First,
	FirstAccessible;

	public static final int SIZE = Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static SearchApproachType forValue(int value)
	{
		return values()[value];
	}
}