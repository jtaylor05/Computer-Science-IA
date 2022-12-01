package database;

import java.io.*;
import java.nio.file.Files;

import library.L;

/**
 * Accesses database for answers using .txt file: .\database\answers.
 * Extends class Database.
 */
public class Answers extends Database
{
	private final static String DATABASE_FILE_PATH = "database/answers";

	private final static int END_OF_ID = LENGTH_OF_USERID + LENGTH_OF_QUESTIONID;
	private final static int END_OF_PATH = END_OF_ID + LENGTH_OF_PATH;
	private final static int END_OF_GRADE = END_OF_PATH + LENGTH_OF_GRADE;
	private final static int GOTTEN_FEEDBACK = END_OF_GRADE + 1;
	private final static int NEW_ANSWER = GOTTEN_FEEDBACK + 1;
	private final static int LENGTH_OF_FILE = NEW_ANSWER + 1;
	
	//to force an update
	public static void main(String[] args)
	{
		update();
	}
	
	/**
	 * @param userID user ID of who made answer
	 * @param qID question ID of question answered
	 * @param filePath image file path
	 * @param points grade given
	 */
	public static void add(String userID, String qID, String filePath, int points)
	{
		String id = userID + qID;
		String fixedPath = L.fitToLength(LENGTH_OF_PATH, filePath);
		String fixedPoints = L.fitToLength(LENGTH_OF_GRADE, "" + points);
		
		write(id + fixedPath + fixedPoints + "10\n", DATABASE_FILE_PATH);
	}
	
	/**
	 * @param userID user ID of who made answer
	 * @param qID question ID of question answered
	 * @param filePath image file path
	 */
	public static void add(String userID, String qID, String filePath)
	{
		String id = userID + qID;
		String fixedPath = L.fitToLength(LENGTH_OF_PATH, filePath);
		String fixedPoints = L.fitToLength(LENGTH_OF_GRADE, "-2");
			
		write(id + fixedPath + fixedPoints + "01\n", DATABASE_FILE_PATH);	
	}
	
	/**
	 * @param index index of entry to be removed
	 */
	public static void remove(int index)
	{
		remove(index, DATABASE_FILE_PATH, LENGTH_OF_FILE);	
	}
	
	/**
	 * @param userID search term of user ID who made answer
	 * @param QID search term of question ID answered
	 * @return index of answer with the two search terms as fields
	 */
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
	
	/**
	 * @param index entry number in .txt file
	 * @return String user ID at index
	 */
	public static String getUserID(int index)
	{
		return get(index, DATABASE_FILE_PATH, 0, LENGTH_OF_USERID, LENGTH_OF_FILE);
	}
	
	/**
	 * @param index entry number in .txt file
	 * @return String question ID at index
	 */
	public static String getQID(int index)
	{
		return get(index, DATABASE_FILE_PATH, LENGTH_OF_USERID, END_OF_ID, LENGTH_OF_FILE);
	}
	
	/**
	 * @param index entry number in .txt file
	 * @return String image file path at index
	 */
	public static String getFilePath(int index)
	{
		return get(index, DATABASE_FILE_PATH, END_OF_ID, END_OF_PATH, LENGTH_OF_FILE);
	}
	
	/**
	 * @param index entry number in .txt file
	 * @param newPath new image file path
	 */
	public static void changeFilePath(int index, String newPath)
	{
		writeAt(newPath, index, LENGTH_OF_PATH, DATABASE_FILE_PATH, END_OF_ID, LENGTH_OF_FILE);
	}
	
	/**
	 * @param ID search term
	 * @return index of parameter user ID in .txt file
	 */
	public static int getUserIDIndex(String ID)
	{
		return getIndex(ID, LENGTH_OF_USERID, DATABASE_FILE_PATH, 0, LENGTH_OF_USERID, LENGTH_OF_FILE);
	}
	
	/**
	 * @param ID search term
	 * @param minIndex starting index of search
	 * @return index of parameter user ID after minIndex in .txt file
	 */
	public static int getUserIDIndex(String ID, int minIndex)
	{
		return getIndex(ID, LENGTH_OF_USERID, DATABASE_FILE_PATH, 0, LENGTH_OF_USERID, LENGTH_OF_FILE, minIndex);
	}
	
	/**
	 * @param ID search term
	 * @return index of parameter question ID in .txt file
	 */
	public static int getQIDIndex(String ID)
	{
		return getIndex(ID, LENGTH_OF_QUESTIONID, DATABASE_FILE_PATH, LENGTH_OF_USERID, END_OF_ID, LENGTH_OF_FILE);
	}
	
	/**
	 * @param ID search term
	 * @param minIndex starting index of search
	 * @return index of parameter question ID after minIndex in .txt file
	 */
	public static int getQIDIndex(String ID, int minIndex)
	{
		return getIndex(ID, LENGTH_OF_QUESTIONID, DATABASE_FILE_PATH, LENGTH_OF_USERID, END_OF_ID, LENGTH_OF_FILE, minIndex);
	}
	
	/**
	 * @param index entry number in .txt file
	 * @return int grade of answer
	 */
	public static int getPoints(int index)
	{
		String val = get(index, DATABASE_FILE_PATH, END_OF_PATH, END_OF_GRADE, LENGTH_OF_FILE);
		return Integer.parseInt(val);
	}
	
	/**
	 * @param index entry number in .txt file
	 * @param newPoints new grade for answer
	 */
	public static void changePoints(int index, int newPoints)
	{
		writeAt(newPoints, index, LENGTH_OF_GRADE, DATABASE_FILE_PATH, END_OF_PATH, LENGTH_OF_FILE);
	}
	
	/**
	 * updates all answers in databases. Clears answers which don't have users or questions.
	 */
	public static void update()
	{
		int index = 0;
		String userID = getUserID(index);
		while(userID != null)
		{
			if(Accounts.getIDIndex(userID) == -1)
			{
				remove(index);
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
				remove(index);
				QID = getQID(index);
			}
			else
			{
				index++;
				QID = getQID(index);
			}
		}
	}
	
	/**
	 * @return number of entries in the database.
	 */
	public static int numberAnswers()
	{
		return countEntries(DATABASE_FILE_PATH, LENGTH_OF_FILE);
	}
	
	/**
	 * @param index entry number of .txt file
	 * @return boolean value of whether the answer has feedback
	 */
	public static boolean hasFeedback(int index)
	{
		String bool = get(index, DATABASE_FILE_PATH, END_OF_GRADE, GOTTEN_FEEDBACK, LENGTH_OF_FILE);
		return bool.equals("1");
	}
	
	/**
	 * @param index entry number of .txt file to have feedback toggled
	 */
	public static void changeFeedback(int index)
	{
		boolean hasFeedback = hasFeedback(index);
		if(hasFeedback)
		{
			writeAt(0, index, 1, DATABASE_FILE_PATH, END_OF_GRADE, LENGTH_OF_FILE);
		}
		else
		{
			writeAt(1, index, 1, DATABASE_FILE_PATH, END_OF_GRADE, LENGTH_OF_FILE);
		}
	}
	
	/**
	 * @param index entry number of .txt file
	 * @return boolean value of whether answer has had a grade or feedback
	 */
	public static boolean isNewAnswer(int index)
	{
		String bool = get(index, DATABASE_FILE_PATH, GOTTEN_FEEDBACK, NEW_ANSWER, LENGTH_OF_FILE);
		return bool.equals("1");
	}
	
	/**
	 * @param index entry number of .txt file to have new answer toggled
	 */
	public static void changeNewAnswer(int index)
	{
		boolean isNewAnswer = isNewAnswer(index);
		if(isNewAnswer)
		{
			writeAt(0, index, 1, DATABASE_FILE_PATH, END_OF_GRADE, LENGTH_OF_FILE);
		}
		else
		{
			writeAt(1, index, 1, DATABASE_FILE_PATH, END_OF_GRADE, LENGTH_OF_FILE);
		}
	}
}