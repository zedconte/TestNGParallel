package tellerium.mvcpages.utils;

public final class XPathHelpers
{
	//INFO: http://stackoverflow.com/questions/1341847/special-character-in-xpath-query
	public static String ToXPathLiteral(String value)
	{
		// if the value contains only single or double quotes, construct
		// an XPath literal
		if (!value.contains("\""))
		{
			return "\"" + value + "\"";
		}
		if (!value.contains("'"))
		{
			return "'" + value + "'";
		}

		// if the value contains both single and double quotes, construct an
		// expression that concatenates all non-double-quote substrings with
		// the quotes, e.g.:
		//
		//    concat("foo", '"', "bar")
		StringBuilder sb = new StringBuilder();
		sb.append("concat(");
		String[] substrings = value.split("\"", -1);
		for (int i = 0; i < substrings.length; i++)
		{
			boolean needComma = (i > 0);
			if (!substrings[i].equals(""))
			{
				if (i > 0)
				{
					sb.append(", ");
				}
				sb.append("\"");
				sb.append(substrings[i]);
				sb.append("\"");
				needComma = true;
			}
			if (i < substrings.length - 1)
			{
				if (needComma)
				{
					sb.append(", ");
				}
				sb.append("'\"'");
			}

		}
		sb.append(")");
		return sb.toString();
	}
}