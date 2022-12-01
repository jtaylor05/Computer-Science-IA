package library;

import java.util.*;
import java.util.UUID;

import database.Accounts;

import java.awt.*;

/**
 * Library class for IA project. Includes general methods.
 */
public class L 
{
	public static final Color PRIME_BLUE = new Color(100, 211, 255);
	public static final Color DARK_BLUE = new Color(52, 143, 179);
	public static final Color LIGHT_BLUE = new Color(125, 218, 255);
	public static final Color DARK_BROWN = new Color(179, 110, 34);
	public static final Color LIGHT_BROWN = new Color(255, 181, 99);
	
	/**
	 * @param length length to fit to
	 * @param str String to fit to length
	 * @return new String with length equal to parameter length
	 */
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
	
	/**
	 * @param str String to be sheared of all trailing spaces
	 * @return sheared String
	 */
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
	
	/**
	 * @return new random UUID
	 */
	public static String getID()
	{
		UUID uuid = UUID.randomUUID();
		String id = uuid.toString();
		return id;
	}
	
	/**
	 * @return List of all teacher IDs
	 */
	public static LinkedList<String> makeTeacherList()
	{
		int index = 0;
		String id = Accounts.getID(index);
		LinkedList<String> ids = new LinkedList<>();
		while(id != null)
		{
			if(Accounts.isTeacher(index))
			{
				ids.add(id);
			}
			
			index = index + 1;
			id = Accounts.getID(index);
		}
		
		return ids;
	}
}
