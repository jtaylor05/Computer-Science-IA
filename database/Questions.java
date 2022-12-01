package database;

import java.io.*; 
import java.nio.file.Files;

import library.L;

/**
 * Accesses database for questions using .txt file: .\database\questions.
 * Extends class Database.
 */
public class Questions extends Database
{
	private final static String DATABASE_FILE_PATH = "database/questions";

	
	private final static int END_OF_NAME = LENGTH_OF_QUESTIONID + LENGTH_OF_NAME;
	private final static int END_OF_PATH = END_OF_NAME + LENGTH_OF_PATH;
	private final static int LENGTH_OF_FILE = END_OF_PATH + LENGTH_OF_GRADE + 1;
	
	/**
	 * @param name user-chosen name of question
	 * @param filePath user-chosen file path of image
	 * @param totalPoints user-chosen max points
	 */
	public static void add(String name, String filePath, int totalPoints)
	{
		String fixedName = L.fitToLength(LENGTH_OF_NAME, name);
		String fixedPath = L.fitToLength(LENGTH_OF_PATH, filePath);
		String fixedPoints = L.fitToLength(LENGTH_OF_GRADE, "" + totalPoints);
		String id = L.getID();
		
		write(id + fixedName + fixedPath + fixedPoints + "\n", DATABASE_FILE_PATH);
		
		Accounts.updateUnanswered(true, false, false, null);
	}
	
	/**
	 * 
	 * @param name user-chosen name of question
	 * @param filePath user-chosen file path of image
	 * @param totalPoints user-chosen max points
	 * @param QID question ID of question being replaced
	 */
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
	
	/**
	 * @param QID question ID of question to be removed
	 */
	public static void remove(String QID)
	{
		int index = getIDIndex(QID);
		remove(index, DATABASE_FILE_PATH, LENGTH_OF_FILE);
		
		Accounts.updateUnanswered(false, false, true, QID);
		
		Answers.update();
	}
	
	/**
	 * @param index entry number in .txt file
	 * @return String question ID at index
	 */
	public static String getID(int index)
	{
		return get(index, DATABASE_FILE_PATH, 0, LENGTH_OF_QUESTIONID, LENGTH_OF_FILE);
	}
	
	/**
	 * @param ID search term
	 * @return index of parameter question ID in .txt file
	 */
	public static int getIDIndex(String ID)
	{
		return getIndex(ID, LENGTH_OF_QUESTIONID, DATABASE_FILE_PATH, 0, LENGTH_OF_QUESTIONID, LENGTH_OF_FILE);
	}
	
	/**
	 * @param index entry number in .txt file
	 * @return String file path at index
	 */
	public static String getFilePath(int index)
	{
		return get(index, DATABASE_FILE_PATH, END_OF_NAME, END_OF_PATH, LENGTH_OF_FILE);
	}
	
	/**
	 * @param index entry number in .txt file
	 * @return String question name at index
	 */
	public static String getName(int index)
	{
		return get(index, DATABASE_FILE_PATH, LENGTH_OF_QUESTIONID, END_OF_NAME, LENGTH_OF_FILE);
	}
	
	/**
	 * @param name search term
	 * @return index of parameter question name in .txt file
	 */
	public static int getNameIndex(String name)
	{
		return getIndex(name, LENGTH_OF_NAME, DATABASE_FILE_PATH, LENGTH_OF_QUESTIONID, END_OF_NAME, LENGTH_OF_FILE);
	}
	
	/**
	 * @param index entry number in .txt file
	 * @return int max points at index
	 */
	public static int getPoints(int index)
	{
		String val = get(index, DATABASE_FILE_PATH, END_OF_PATH, LENGTH_OF_FILE - 1, LENGTH_OF_FILE);
		return Integer.parseInt(val);
	}
	
	/**
	 * @return number of entries in the database
	 */
	public static int numberQuestions()
	{
		return countEntries(DATABASE_FILE_PATH, LENGTH_OF_FILE);
	}
	
}
