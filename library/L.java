package library;

import java.util.UUID;

public class L 
{
	//Method changes String length to fit integer value length; returns changed String.
	public static String fitToLength(int length, String str)
	{
		if(str.length() > length)
		{
			return str.substring(0, length);
		}
		else
		{
			while(str.length() < length)
			{
				str = str + " ";
			}
			return str;
		}
	}
	
	//Method removes spaces at the end of a String; returns changed String.
	public static String shear(String str)
	{
		for(int i = str.length() - 1; i >= 0; i--)
		{	
			if(str.charAt(i) == 32)
			{
				str = str.substring(0, i);
			}
			else 
			{
				return str;
			}
		}
		return str;
	}
	
	//Method uses UUID to get a random 36 digit hexcode ID; returns ID as a String;
	public static String getID()
	{
		UUID uuid = UUID.randomUUID();
		String id = uuid.toString();
		return id;
	}
}
