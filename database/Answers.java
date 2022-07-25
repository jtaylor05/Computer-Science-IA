package database;

import java.io.*;
import library.L;

public class Answers 
{
	private static FileWriter fw;
	private static RandomAccessFile raf;
	private final static String DATABASE_FILE_PATH = "database/answers";
	
	private final static int LENGTH_OF_USERID = 37;
	private final static int LENGTH_OF_QID = 36;
	private final static int END_OF_ID = LENGTH_OF_USERID + LENGTH_OF_QID;
	private final static int LENGTH_OF_PATH = 48;
	private final static int LENGTH_OF_FILE = END_OF_ID + LENGTH_OF_PATH + 1;
	
	//following is a tester method; REMOVE FOR FINAL PRODUCT.
	public static void main(String[] args)
	{
		//addAnswer(Accounts.getID(0), Questions.getID(1), "images/answer_1");
		//addAnswer(Accounts.getID(1), Questions.getID(0), "images/answer_2");
		
		int index1 = getUserIDIndex(Accounts.getID(1));
		int index2 = getQIDIndex(Questions.getID(1));
		String userID = getUserID(index2);
		String QID = getQID(index1);
		
		System.out.println(index1 + " " + index2);
		System.out.println(userID + " " + QID);
	}
	
	//Method adds answer data to file "answer"; returns void.
	public static void addAnswer(String userID, String qID, String filePath)
	{
		String id = userID + qID;
		String fixedPath = L.fitToLength(LENGTH_OF_PATH, filePath);
		
		try
		{
			fw = new FileWriter(DATABASE_FILE_PATH, true);
			fw.write(id + fixedPath + "\n");
			fw.close();
		}
		catch(Exception e)
		{
			System.out.println("exception");
		}		
	}
	
	//Method finds user ID of answer at index; returns found user ID.
	public static String getUserID(int index)
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
			ID = line.substring(0, LENGTH_OF_USERID);
		}
		catch(Exception e)
		{
			System.out.println("error " + e);
		}
		
		return ID;
	}
	
	//Method finds question ID of answer at index; returns found question ID.
	public static String getQID(int index)
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
			ID = line.substring(LENGTH_OF_USERID, END_OF_ID);
		}
		catch(Exception e)
		{
			System.out.println("error " + e);
		}			
		return ID;
	}
	
	//method finds file path of answer image at index; returns found file path.
	public static String getFilePath(int index)
	{
		String filePath = "";
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
			filePath = line.substring(END_OF_ID, LENGTH_OF_FILE);
		}
		catch(Exception e)
		{
			System.out.println("error " + e);
		}
		
		return filePath;
	}
	
	//method uses looks for index of searchterm "searchUserID"; returns index of where "searchUserID"
	//was found or -1 if not found.
	public static int getUserIDIndex(String searchUserID)
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
				id = line.substring(0, LENGTH_OF_USERID);
				
				if(id.equals(searchUserID))
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
	
	//method uses looks for index of searchterm "searchQID"; returns index of where "searchQID"
	//was found or -1 if not found.
	public static int getQIDIndex(String searchQID)
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
				id = line.substring(LENGTH_OF_USERID, END_OF_ID);
				
				if(id.equals(searchQID))
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
}