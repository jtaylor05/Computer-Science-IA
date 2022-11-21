package database;

import java.io.*;
import java.nio.file.Files;

import library.L;

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
	
	//Method adds answer data to file "answer".
	public static void add(String userID, String qID, String filePath, int points)
	{
		String id = userID + qID;
		String fixedPath = L.fitToLength(LENGTH_OF_PATH, filePath);
		String fixedPoints = L.fitToLength(LENGTH_OF_GRADE, "" + points);
		
		write(id + fixedPath + fixedPoints + "10\n", DATABASE_FILE_PATH);
	}
	
	//Method adds answer data to file "answer".
	public static void add(String userID, String qID, String filePath)
	{
		String id = userID + qID;
		String fixedPath = L.fitToLength(LENGTH_OF_PATH, filePath);
		String fixedPoints = L.fitToLength(LENGTH_OF_GRADE, "-2");
			
		write(id + fixedPath + fixedPoints + "01\n", DATABASE_FILE_PATH);	
	}
	
	//removes an answer from database.
	public static void remove(int index)
	{
		remove(index, DATABASE_FILE_PATH, LENGTH_OF_FILE);	
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
		return get(index, DATABASE_FILE_PATH, 0, LENGTH_OF_USERID, LENGTH_OF_FILE);
	}
	
	//Method finds question ID of answer at index; returns found question ID.
	public static String getQID(int index)
	{
		return get(index, DATABASE_FILE_PATH, LENGTH_OF_USERID, END_OF_ID, LENGTH_OF_FILE);
	}
	
	//method finds file path of answer image at index; returns found file path.
	public static String getFilePath(int index)
	{
		return get(index, DATABASE_FILE_PATH, END_OF_ID, END_OF_PATH, LENGTH_OF_FILE);
	}
	
	//changes file path of an answer
	public static void changeFilePath(int index, String newPath)
	{
		writeAt(newPath, index, LENGTH_OF_PATH, DATABASE_FILE_PATH, END_OF_ID, LENGTH_OF_FILE);
	}
	
	//method uses looks for index of searchterm "searchUserID"; returns index of where "searchUserID"
	//was found or -1 if not found.
	public static int getUserIDIndex(String ID)
	{
		return getIndex(ID, LENGTH_OF_USERID, DATABASE_FILE_PATH, 0, LENGTH_OF_USERID, LENGTH_OF_FILE);
	}
	
	//method uses looks for index of searchterm "searchUserID"; returns index of where "searchUserID"
	//was found or -1 if not found.
	public static int getUserIDIndex(String ID, int minIndex)
	{
		return getIndex(ID, LENGTH_OF_USERID, DATABASE_FILE_PATH, 0, LENGTH_OF_USERID, LENGTH_OF_FILE, minIndex);
	}
	
	//method uses looks for index of searchterm "searchQID"; returns index of where "searchQID"
	//was found or -1 if not found.
	public static int getQIDIndex(String ID)
	{
		return getIndex(ID, LENGTH_OF_QUESTIONID, DATABASE_FILE_PATH, LENGTH_OF_USERID, END_OF_ID, LENGTH_OF_FILE);
	}
	
	//method uses looks for index of searchterm "searchQID"; returns index of where "searchQID"
	//was found or -1 if not found.
	public static int getQIDIndex(String ID, int minIndex)
	{
		return getIndex(ID, LENGTH_OF_QUESTIONID, DATABASE_FILE_PATH, LENGTH_OF_USERID, END_OF_ID, LENGTH_OF_FILE, minIndex);
	}
	
	//To find the int value of the total number of points for question at index;
	//returns the int value found.
	public static int getPoints(int index)
	{
		String val = get(index, DATABASE_FILE_PATH, END_OF_PATH, END_OF_GRADE, LENGTH_OF_FILE);
		return Integer.parseInt(val);
	}
	
	//changes grade value
	public static void changePoints(int index, int newPoints)
	{
		writeAt(newPoints, index, LENGTH_OF_GRADE, DATABASE_FILE_PATH, END_OF_PATH, LENGTH_OF_FILE);
	}
	
	//updates other databases if there is a change in values
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
	
	//counts the number of answers in the database
	public static int numberAnswers()
	{
		return countEntries(DATABASE_FILE_PATH, LENGTH_OF_FILE);
	}
	
	//whether the question at index has feedback
	public static boolean hasFeedback(int index)
	{
		String bool = get(index, DATABASE_FILE_PATH, END_OF_GRADE, GOTTEN_FEEDBACK, LENGTH_OF_FILE);
		return bool.equals("1");
	}
	
	//changes the boolean value of feedback
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
	
	//checks whether answer is new
	public static boolean isNewAnswer(int index)
	{
		String bool = get(index, DATABASE_FILE_PATH, GOTTEN_FEEDBACK, NEW_ANSWER, LENGTH_OF_FILE);
		return bool.equals("1");
	}
	
	//changes boolean value of newAnswer
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