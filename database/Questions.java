package database;

import java.io.*; 
import java.nio.file.Files;

import library.L;

public class Questions extends Database
{
	private final static String DATABASE_FILE_PATH = "database/questions";

	
	private final static int END_OF_NAME = LENGTH_OF_QUESTIONID + LENGTH_OF_NAME;
	private final static int END_OF_PATH = END_OF_NAME + LENGTH_OF_PATH;
	private final static int LENGTH_OF_FILE = END_OF_PATH + LENGTH_OF_GRADE + 1;
	
	//Method adds question data to file "questions"; returns void.
	public static void add(String name, String filePath, int totalPoints)
	{
		String fixedName = L.fitToLength(LENGTH_OF_NAME, name);
		String fixedPath = L.fitToLength(LENGTH_OF_PATH, filePath);
		String fixedPoints = L.fitToLength(LENGTH_OF_GRADE, "" + totalPoints);
		String id = L.getID();
		
		write(id + fixedName + fixedPath + fixedPoints + "\n", DATABASE_FILE_PATH);
		
		Accounts.updateUnanswered(true, false, false, null);
	}
	
	//replaces a previous question with a new one
	public static void replace(String name, String filePath, int totalPoints, String QID)
	{
		String fixedName = L.fitToLength(LENGTH_OF_NAME, name);
		String fixedPath = L.fitToLength(LENGTH_OF_PATH, filePath);
		String fixedPoints = L.fitToLength(LENGTH_OF_GRADE, "" + totalPoints);
		String id = QID;
		
		int index = getIDIndex(QID);
		System.out.println(index);
		int prevPoints = getPoints(index);
		if(totalPoints != prevPoints)
		{
			float ratio = totalPoints/ (float) prevPoints;
			int i = -1;
			do
			{
				i = Answers.getQIDIndex(QID, i + 1);
				if(i >= 0)
				{
					int grade = Answers.getPoints(i);
					if(grade > -1)
					{
						int newGrade = (int)(ratio * grade);
						Answers.changePoints(i, newGrade);
					}
				}
			}
			while(i > -1);
		}
		
		writeAt(id + fixedName + fixedPath + fixedPoints + "\n", index, LENGTH_OF_FILE, DATABASE_FILE_PATH, 0, LENGTH_OF_FILE);
		
		Accounts.updateUnanswered(false, true, false, QID);
		
		Answers.update();
	}
	
	//removes question from database
	public static void remove(String QID)
	{
		int index = getIDIndex(QID);
		remove(index, DATABASE_FILE_PATH, LENGTH_OF_FILE);
		
		Accounts.updateUnanswered(false, false, true, QID);
		
		Answers.update();
	}
	
	//Method finds ID of question at index; returns found ID of question.
	public static String getID(int index)
	{
		return get(index, DATABASE_FILE_PATH, 0, LENGTH_OF_QUESTIONID, LENGTH_OF_FILE);
	}
	
	//method uses looks for index of searchterm "ID"; returns index of where "ID"
	//was found or -1 if not found.
	public static int getIDIndex(String ID)
	{
		return getIndex(ID, LENGTH_OF_QUESTIONID, DATABASE_FILE_PATH, 0, LENGTH_OF_QUESTIONID, LENGTH_OF_FILE);
	}
	
	//method finds file path of question at index; returns found file path.
	public static String getFilePath(int index)
	{
		return get(index, DATABASE_FILE_PATH, END_OF_NAME, END_OF_PATH, LENGTH_OF_FILE);
	}
	
	//method finds name of question at index; returns found name.
	public static String getName(int index)
	{
		return get(index, DATABASE_FILE_PATH, LENGTH_OF_QUESTIONID, END_OF_NAME, LENGTH_OF_FILE);
	}
	
	//method uses looks for index of searchterm "name"; returns index of where "name"
	//was found or -1 if not found.
	public static int getNameIndex(String name)
	{
		return getIndex(name, LENGTH_OF_NAME, DATABASE_FILE_PATH, LENGTH_OF_QUESTIONID, END_OF_NAME, LENGTH_OF_FILE);
	}
	
	//To find the int value of the total number of points for question at index;
	//returns the int value found.
	public static int getPoints(int index)
	{
		String val = get(index, DATABASE_FILE_PATH, END_OF_PATH, LENGTH_OF_FILE - 1, LENGTH_OF_FILE);
		return Integer.parseInt(val);
	}
	
	//counts number of questions in database
	public static int numberQuestions()
	{
		return countEntries(DATABASE_FILE_PATH, LENGTH_OF_FILE);
	}
	
}
