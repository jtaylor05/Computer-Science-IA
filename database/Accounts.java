package database;

import java.io.*;
import library.L;

public class Accounts 
{
	private static FileWriter fw;
	private static RandomAccessFile raf;
	private final static String DATABASE_FILE_PATH = "database/accounts";
	
	
	private final static int LENGTH_OF_ID = 37;
	private final static int LENGTH_OF_NAME = 23;
	private final static int END_OF_NAME = LENGTH_OF_ID + LENGTH_OF_NAME;
	private final static int LENGTH_OF_PASS = 28;
	private final static int END_OF_PASS = END_OF_NAME + LENGTH_OF_PASS;
	private final static int LENGTH_OF_EMAIL = 48;
	private final static int LENGTH_OF_FILE = END_OF_PASS + LENGTH_OF_EMAIL + 1;
	
	//following is a tester method; REMOVE FOR FINAL PRODUCT.
	public static void main(String[] args)
	{
		//addAccount("JacksonT", "p4ssWord!", "jtaylor@gmail.com", false);
		//addAccount("LeslieT", "12345678", "ltaylor@gmail.com", true);
		//int index = getUsernameIndex("JacksonT");
		
		//System.out.println(L.shear(getPassword(index)));
		//System.out.println(getID(index));
		//System.out.println(isTeacher(index));
		
	}
	
	//Method adds account data to file "accounts"; returns void.
	public static void addAccount(String username, String password, String email, boolean isTeacher)
	{
		String fixedName = L.fitToLength(LENGTH_OF_NAME, username);
		String fixedPass = L.fitToLength(LENGTH_OF_PASS, password);
		String fixedEmail = L.fitToLength(LENGTH_OF_EMAIL, email);
		String id = L.getID();
		String teacher = "0";
		if(isTeacher)
		{
			teacher = "1";
		}
		
		try
		{
			fw = new FileWriter(DATABASE_FILE_PATH, true);
			fw.write(id + teacher + fixedName + fixedPass + fixedEmail + "\n");
			fw.close();
		}
		catch(Exception e)
		{
			System.out.println("exception");
		}		
	}
	
	//Method searches for search term "username" of all usernames in file 
	//"accounts"; returns int index of account with "username".
	public static int getUsernameIndex(String username)
	{
		String fixedName = L.fitToLength(LENGTH_OF_NAME, username);
		String name = "";
		int index = -1;
		try
		{
			raf = new RandomAccessFile(DATABASE_FILE_PATH, "rw");
			int length = (int)raf.length();
			while(raf.getFilePointer() < length)
			{
				index = index + 1;
				
				String line = raf.readLine();
				name = line.substring(LENGTH_OF_ID, END_OF_NAME);
				
				if(name.equals(fixedName))
				{
					return index;
				}
				
				raf.seek(LENGTH_OF_FILE * (index + 1));
			}
		}
		catch(Exception e)
		{
			System.out.println("Line 87 error " + e);
		}
		
		return -1;
	}
	
	//To retrieve username from certain index in database; returns found username.
	public static String getUsername(int index)
	{
		String username = "";
		try
		{
			raf = new RandomAccessFile(DATABASE_FILE_PATH, "rw");
			if(LENGTH_OF_FILE * index < raf.length())
			{
				raf.seek(LENGTH_OF_FILE * index);
			}
			else
			{
				return null;
			}
			
			String line = raf.readLine();
			username = line.substring(LENGTH_OF_ID, END_OF_NAME);
		}
		catch(Exception e)
		{
			System.out.println("Line 114 error " + e);
		}
		
		return username;
	}
	
	//Method searches for search term "email" of all email in file 
	//"accounts"; returns int index of account with "email".
	public static int getEmailIndex(String email)
	{
		String fixedEmail = L.fitToLength(LENGTH_OF_EMAIL, email);
		String mail = "";
		int index = -1;
		try
		{
			raf = new RandomAccessFile(DATABASE_FILE_PATH, "rw");
			int length = (int)raf.length();
			while(raf.getFilePointer() < length)
			{
				index = index + 1;
				
				String line = raf.readLine();
				mail = line.substring(END_OF_PASS);
				
				if(mail.equals(fixedEmail))
				{
					return index;
				}
				
				raf.seek(LENGTH_OF_FILE * (index + 1));
			}
		}
		catch(Exception e)
		{
			System.out.println("Line 148 error " + e);
		}
		
		return -1;
	}
	
	//Method searches for search term "password" of all passwords in file 
	//"passwords"; returns int index of account with "password".
	public static int getPasswordIndex(String password)
	{
		String fixedPass = L.fitToLength(LENGTH_OF_PASS, password);
		String pass = "";
		int index = -1;
		try
		{
			raf = new RandomAccessFile(DATABASE_FILE_PATH, "rw");
			int length = (int)raf.length();
			while(raf.getFilePointer() < length)
			{
				index = index + 1;
					
				String line = raf.readLine();
				pass = line.substring(END_OF_NAME, END_OF_PASS);
					
				if(pass.equals(fixedPass))
				{
					return index;
				}
					
				raf.seek(LENGTH_OF_FILE * (index + 1));
			}
		}
		catch(Exception e)
		{
			System.out.println("Line 182 error " + e);
		}
			
		return -1;
	}
	
	//Method finds password of account at index; returns found password of account.
	public static String getPassword(int index)
	{
		String password = "";
		try
		{
			raf = new RandomAccessFile(DATABASE_FILE_PATH, "rw");
			if(LENGTH_OF_FILE * index < raf.length())
			{
				raf.seek(LENGTH_OF_FILE * index);
			}
			else
			{
				return null;
			}
			
			String line = raf.readLine();
			password = line.substring(END_OF_NAME, END_OF_PASS);
		}
		catch(Exception e)
		{
			System.out.println("error " + e);
		}
		
		return password;
	}
	
	//Method finds ID of account at index; returns found ID of account.
	public static String getID(int index)
	{
		String ID = "";
		try
		{
			raf = new RandomAccessFile(DATABASE_FILE_PATH, "rw");
			if(LENGTH_OF_FILE * index < raf.length())
			{
				raf.seek(LENGTH_OF_FILE * index);
			}
			else
			{
				return null;
			}
			
			String line = raf.readLine();
			ID = line.substring(0, LENGTH_OF_ID);
		}
		catch(Exception e)
		{
			System.out.println("Line 236 error " + e);
		}
		
		return ID;
	}
	
	//method uses looks for index of searchterm "searchID"; returns index of where "searchID"
	//was found or -1 if not found.
	public static int getIDIndex(String searchID)
	{
		String id = "";
		int index = -1;
		try
		{
			raf = new RandomAccessFile(DATABASE_FILE_PATH, "rw");
			int length = (int)raf.length();
			while(raf.getFilePointer() < length)
			{
				index = index + 1;
					
				String line = raf.readLine();
				id = line.substring(0, LENGTH_OF_ID);
						
				if(id.equals(searchID))
				{
					return index;
				}
					
				raf.seek(LENGTH_OF_FILE * (index + 1));
			}
		}
		catch(Exception e)
		{
			System.out.println("error " + e);
		}
				
		return -1;
	}
	
	//Method uses last value of ID to check whether teacher or student;
	//returns boolean value true if a teacher, false if not;
	public static boolean isTeacher(int index)
	{
		boolean isTeacher = false;
		try
		{
			raf = new RandomAccessFile(DATABASE_FILE_PATH, "rw");
			if(LENGTH_OF_FILE * index < raf.length())
			{
				raf.seek(LENGTH_OF_FILE * index);
			}
			else
			{
				return false;
			}
			
			String line = raf.readLine();
			String str = line.substring(LENGTH_OF_ID - 1, LENGTH_OF_ID);
			if(str.equals("1"))
			{ isTeacher = true; } 
		}
		catch(Exception e)
		{
			System.out.println("Line 266 error " + e);
		}
		
		return isTeacher;
	}
}
