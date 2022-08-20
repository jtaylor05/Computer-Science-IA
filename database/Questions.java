package database;

import java.io.*;
import library.L;

public class Questions 
{
	private static FileWriter fw;
	private static RandomAccessFile raf;
	private final static String DATABASE_FILE_PATH = "database/questions";
	
	private final static int LENGTH_OF_ID = 36;
	private final static int LENGTH_OF_NAME = 23;
	private final static int END_OF_NAME = LENGTH_OF_ID + LENGTH_OF_NAME;
	private final static int LENGTH_OF_PATH = 48;
	private final static int END_OF_PATH = END_OF_NAME + LENGTH_OF_PATH;
	private final static int LENGTH_OF_GRADE = 10;
	private final static int LENGTH_OF_FILE = END_OF_PATH + LENGTH_OF_GRADE + 1;
	
	//following is a tester method; REMOVE FOR FINAL PRODUCT.
	public static void main(String[] args)
	{
		//addQuestion("Question 1", "images/question_1", 10);
		//addQuestion("Question 2", "images/question_2", 12);
		
		String name = getName(0);
		String id = getID(0);
		int index = getNameIndex("Question 2");
		
		System.out.println(L.shear(name) + "\n" + id + "\n" + index);
	}
	
	//Method adds question data to file "questions"; returns void.
	public static void addQuestion(String name, String filePath, int totalPoints)
	{
		String fixedName = L.fitToLength(LENGTH_OF_NAME, name);
		String fixedPath = L.fitToLength(LENGTH_OF_PATH, filePath);
		String fixedPoints = L.fitToLength(LENGTH_OF_GRADE, "" + totalPoints);
		String id = L.getID();
		
		try
		{
			fw = new FileWriter(DATABASE_FILE_PATH, true);
			fw.write(id + fixedName + fixedPath + fixedPoints + "\n");
			fw.close();
		}
		catch(Exception e)
		{
			System.out.println("exception");
		}		
	}
	
	//Method finds ID of question at index; returns found ID of question.
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
			System.out.println("Q74 error " + e);
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
			System.out.println("Q107 error " + e);
		}
			
		return -1;
	}
	
	//method finds file path of question at index; returns found file path.
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
			filePath = line.substring(END_OF_NAME, END_OF_PATH);
		}
		catch(Exception e)
		{
			System.out.println("Q134 error " + e);
		}
		
		return filePath;
	}
	
	//method finds name of question at index; returns found name.
	public static String getName(int index)
	{
		String name = "";
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
			name = line.substring(LENGTH_OF_ID, END_OF_NAME);
		}
		catch(Exception e)
		{
			System.out.println("Q161 error " + e);
		}
		
		return L.shear(name);
	}
	
	//method uses looks for index of searchterm "name"; returns index of where "name"
	//was found or -1 if not found.
	public static int getNameIndex(String searchName)
	{
		String fixedName = L.fitToLength(LENGTH_OF_NAME, searchName);
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
			System.out.println("Q195 error " + e);
		}
		
		return -1;
	}
	
	//To find the int value of the total number of points for question at index;
	//returns the int value found.
	public static int getPoints(int index)
	{
		String points = "";
		try
		{
			raf = new RandomAccessFile(DATABASE_FILE_PATH, "rw");
			if(LENGTH_OF_FILE * index < raf.length())
			{
				raf.seek(LENGTH_OF_FILE * index);
			}
			else
			{
				return -1;
			}
			
			String line = raf.readLine();
			points = line.substring(END_OF_PATH, LENGTH_OF_FILE - 1);
		}
		catch(Exception e)
		{
			System.out.println("Q223 error " + e);
		}
		
		points = L.shear(points);
		int p = Integer.parseInt(points);
		return p;
	}
}
