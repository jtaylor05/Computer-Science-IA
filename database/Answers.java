package database;

import java.io.*;
import java.nio.file.Files;

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
	private final static int END_OF_PATH = END_OF_ID + LENGTH_OF_PATH;
	private final static int LENGTH_OF_GRADE = 10;
	private final static int LENGTH_OF_FILE = END_OF_PATH + LENGTH_OF_GRADE + 1;
	
	//following is a tester method; REMOVE FOR FINAL PRODUCT.
	public static void main(String[] args)
	{
		//addAnswer(Accounts.getID(0), Questions.getID(1), "images/answer_1", 7);
		//addAnswer(Accounts.getID(1), Questions.getID(0), "images/answer_2", 4);
		int i = 0;
		String uID = getUserID(i);
		String qID = getQID(i);
		while(uID != null)
		{
			System.out.println(uID + " " + qID);
			
			i = i + 1;
			uID = getUserID(i);
			qID = getQID(i);
		}
		
		System.out.println(findAnswer(getUserID(1), getQID(1)));

	}
	
	//Method adds answer data to file "answer"; returns void.
	public static void addAnswer(String userID, String qID, String filePath, int points)
	{
		String id = userID + qID;
		String fixedPath = L.fitToLength(LENGTH_OF_PATH, filePath);
		String fixedPoints = L.fitToLength(LENGTH_OF_GRADE, "" + points);
		
		try
		{
			fw = new FileWriter(DATABASE_FILE_PATH, true);
			fw.write(id + fixedPath + fixedPoints + "\n");
			fw.close();
		}
		catch(Exception e)
		{
			System.out.println("exception");
		}		
	}
	
	//Method adds answer data to file "answer"; returns void.
	public static void addAnswer(String userID, String qID, String filePath)
	{
		String id = userID + qID;
		String fixedPath = L.fitToLength(LENGTH_OF_PATH, filePath);
		String fixedPoints = L.fitToLength(LENGTH_OF_GRADE, "-1");
			
		try
		{
			fw = new FileWriter(DATABASE_FILE_PATH, true);
			fw.write(id + fixedPath + fixedPoints + "\n");
			fw.close();
		}
		catch(Exception e)
		{
			System.out.println("exception");
		}		
	}
	
	public static void removeAnswer(int index)
	{
		File temp = new File("database/temp");
		File database = new File(DATABASE_FILE_PATH);
		try
		{
			raf = new RandomAccessFile(database, "r");
			fw = new FileWriter(temp, true);
			int count;
			int length = (int)raf.length()/LENGTH_OF_FILE;
			String line = raf.readLine();
			for(count = 0; count < length; count++)
			{
				if(count != index)
				{
					fw.write(line + "\n");
				}
				line = raf.readLine();
			}
			raf.close();
			fw.close();
			
			Files.delete(database.toPath());
			temp.renameTo(database);
			
		}
		catch(Exception e)
		{
			System.out.println("exception: " + e);
		}		
	}
	
	//Method uses a question and user ID to find a matching answer
	public static int findAnswer(String userID, String QID)
	{
		int userIndex = -1;
		do
		{
			userIndex = getUserIDIndex(userID, userIndex + 1);
			if(QID.equals(getQID(userIndex)))
			{
				return userIndex;
			}
		}
		while(userIndex > -1);
		
		return -1;
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
				raf.close();
				return null;
			}
			
			String line = raf.readLine();
			ID = line.substring(0, LENGTH_OF_USERID);
			raf.close();
		}
		catch(Exception e)
		{
			System.out.println("error on getUserID " + e);
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
				raf.close();
				return null;
			}
				
			String line = raf.readLine();
			ID = line.substring(LENGTH_OF_USERID, END_OF_ID);
			raf.close();
		}
		catch(Exception e)
		{
			System.out.println("error on getQID " + e);
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
				raf.close();
				return null;
			}
			
			String line = raf.readLine();
			filePath = line.substring(END_OF_ID, END_OF_PATH);
			raf.close();
		}
		catch(Exception e)
		{
			System.out.println("error " + e);
		}
		
		return filePath;
	}
	
	public static boolean changeFilePath(int index, String newPath)
	{
		try
		{
			raf = new RandomAccessFile(DATABASE_FILE_PATH, "rw");
			if(LENGTH_OF_FILE * index < raf.length())
			{
				raf.seek(LENGTH_OF_FILE * index + END_OF_ID);
			}
			else
			{
				raf.close();
				return false;
			}
			String path = L.fitToLength(LENGTH_OF_PATH, "" + newPath);
			
			raf.writeBytes(path);
			raf.close();
			return true;
		}
		catch(Exception e)
		{
			System.out.println("error " + e);
		}
		return false;
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
					raf.close();
					return index;
				}
					
				raf.seek(LENGTH_OF_FILE * (index + 1));
			}
			raf.close();
		}
		catch(Exception e)
		{
			System.out.println("error " + e);
		}
			
		return -1;
	}
	
	//method uses looks for index of searchterm "searchUserID"; returns index of where "searchUserID"
	//was found or -1 if not found.
	public static int getUserIDIndex(String searchUserID, int minIndex)
	{
		String id = "";
		int index = minIndex;
		try
		{
			raf = new RandomAccessFile(DATABASE_FILE_PATH, "rw");
			raf.seek(LENGTH_OF_FILE * index);
			int length = (int)raf.length();
			while(raf.getFilePointer() < length)
			{
					
				String line = raf.readLine();
				id = line.substring(0, LENGTH_OF_USERID);
					
				if(id.equals(searchUserID))
				{
					raf.close();
					return index;
				}
				index = index + 1;
				raf.seek(LENGTH_OF_FILE * index);
			}
			raf.close();
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
					raf.close();
					return index;
				}
						
				raf.seek(LENGTH_OF_FILE * (index + 1));
			}
			raf.close();
		}
		catch(Exception e)
		{
			System.out.println("error " + e);
		}
				
		return -1;
	}
	
	//method uses looks for index of searchterm "searchQID"; returns index of where "searchQID"
	//was found or -1 if not found.
	public static int getQIDIndex(String searchQID, int minIndex)
	{
		String id = "";
		int index = minIndex;
		try
		{
			raf = new RandomAccessFile(DATABASE_FILE_PATH, "rw");
			raf.seek(LENGTH_OF_FILE * index);
			int length = (int)raf.length();
			while(raf.getFilePointer() < length)
			{			
				String line = raf.readLine();
				id = line.substring(LENGTH_OF_USERID, END_OF_ID);
					
				if(id.equals(searchQID))
				{
					raf.close();
					return index;
				}
				
				index = index + 1;
				raf.seek(LENGTH_OF_FILE * (index + 1));
			}
			raf.close();
		}
		catch(Exception e)
		{
			System.out.println("error " + e);
		}
				
		return -1;
	}
	
	//To find the int value of the total number of points for question at index;
	//returns the int value found.
	public static int getPoints(int index)
	{
		String points = "-1";
		try
		{
			raf = new RandomAccessFile(DATABASE_FILE_PATH, "rw");
			if(LENGTH_OF_FILE * index < raf.length())
			{
				raf.seek(LENGTH_OF_FILE * index);
			}
			else
			{
				raf.close();
				return -1;
			}
			
			String line = raf.readLine();
			points = line.substring(END_OF_PATH, LENGTH_OF_FILE - 1);
			raf.close();
		}
		catch(Exception e)
		{
			System.out.println("error " + e);
		}
		
		points = L.shear(points);
		int p = Integer.parseInt(points);
		return p;
	}
	
	
	public static boolean changePoints(int index, int newPoints)
	{
		try
		{
			raf = new RandomAccessFile(DATABASE_FILE_PATH, "rw");
			if(LENGTH_OF_FILE * index < raf.length())
			{
				raf.seek(LENGTH_OF_FILE * index + END_OF_PATH);
			}
			else
			{
				raf.close();
				return false;
			}
			String points = L.fitToLength(LENGTH_OF_GRADE, "" + newPoints);
			
			raf.writeBytes(points);
			raf.close();
			return true;
		}
		catch(Exception e)
		{
			System.out.println("error " + e);
		}
		return false;
	}
	
	public static void update()
	{
		int index = 0;
		String userID = getUserID(index);
		while(userID != null)
		{
			if(Accounts.getIDIndex(userID) == -1)
			{
				removeAnswer(index);
				userID = getUserID(index);
			}
			else
			{
				index++;
				userID = getUserID(index);
			}
		}
		
		index = 0;
		String QID = getQID(index);
		while(QID != null)
		{
			if(Questions.getIDIndex(QID) == -1)
			{
				removeAnswer(index);
				QID = getQID(index);
			}
			else
			{
				index++;
				QID = getQID(index);
			}
		}
	}
}