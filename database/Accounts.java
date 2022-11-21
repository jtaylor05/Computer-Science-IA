package database;

import java.io.*;
import library.L;

public class Accounts extends Database
{
	private final static String DATABASE_FILE_PATH = "database/accounts";

	private final static int END_OF_NAME = LENGTH_OF_USERID + LENGTH_OF_NAME;
	private final static int LENGTH_OF_PASS = 28;
	private final static int END_OF_PASS = END_OF_NAME + LENGTH_OF_PASS;
	private final static int LENGTH_OF_EMAIL = 48;
	private final static int END_OF_EMAIL = END_OF_PASS + LENGTH_OF_EMAIL;
	private final static int UNANSWERED_INDEX = END_OF_EMAIL + 1;
	private final static int FEEDBACK_INDEX = UNANSWERED_INDEX + 1;
	private final static int LENGTH_OF_INFO = 2;
	private final static int END_OF_ANSWERED = END_OF_EMAIL + LENGTH_OF_INFO;
	private final static int LENGTH_OF_FILE = END_OF_ANSWERED + 1;
	
	//Method adds account data to file "accounts".
	public static void add(String username, String password, String email, boolean isTeacher)
	{
		String fixedName = L.fitToLength(LENGTH_OF_NAME, username);
		String fixedPass = L.fitToLength(LENGTH_OF_PASS, password);
		String fixedEmail = L.fitToLength(LENGTH_OF_EMAIL, email);
		String id = L.getID();
		String teacher = "0";
		int unanswered = 0;
		int answered = 0;
		if(isTeacher)
		{
			teacher = "1";
			answered = Answers.numberAnswers();
		}
		else
		{
			unanswered = Questions.numberQuestions();
		}
		
		
		if(isTeacher)
		{
			write(id + teacher + fixedName + fixedPass + fixedEmail + answered + " \n", DATABASE_FILE_PATH);
		}
		else
		{
			write(id + teacher + fixedName + fixedPass + fixedEmail + unanswered + "0\n", DATABASE_FILE_PATH);
		}	
	}
	
	//Method searches for search term "username" of all usernames in file 
	//"accounts"; returns int index of account with "username".
	public static int getUsernameIndex(String username)
	{
		return getIndex(username, LENGTH_OF_NAME, DATABASE_FILE_PATH, LENGTH_OF_USERID, END_OF_NAME, LENGTH_OF_FILE);
	}
	
	//does same as function above, however goes from minimum index
	public static int getUsernameIndex(String username, int minIndex)
	{
		return getIndex(username, LENGTH_OF_NAME, DATABASE_FILE_PATH, LENGTH_OF_USERID, END_OF_NAME, LENGTH_OF_FILE, minIndex);
	}
	
	//To return username from certain index in database.
	public static String getUsername(int index)
	{
		return get(index, DATABASE_FILE_PATH, LENGTH_OF_USERID, END_OF_NAME, LENGTH_OF_FILE);
	}
	
	//Method searches for search term "email" of all email in file 
	//"accounts"; returns int index of account with "email".
	public static int getEmailIndex(String email)
	{
		return getIndex(email, LENGTH_OF_EMAIL, DATABASE_FILE_PATH, END_OF_PASS, LENGTH_OF_FILE, LENGTH_OF_FILE);
	}
	
	public static int getEmailIndex(String email, int minIndex)
	{
		return getIndex(email, LENGTH_OF_EMAIL, DATABASE_FILE_PATH, END_OF_PASS, LENGTH_OF_FILE, LENGTH_OF_FILE, minIndex);
	}
	
	//Method searches for search term "password" of all passwords in file 
	//"passwords"; returns int index of account with "password".
	public static int getPasswordIndex(String password)
	{
		return getIndex(password, LENGTH_OF_PASS, DATABASE_FILE_PATH, END_OF_NAME, END_OF_PASS, LENGTH_OF_FILE);
	}
	
	//does same as method above, but goes from a minimum index
	public static int getPasswordIndex(String password, int minIndex)
	{
		return getIndex(password, LENGTH_OF_PASS, DATABASE_FILE_PATH, END_OF_NAME, END_OF_PASS, LENGTH_OF_FILE, minIndex);
	}
	
	//Method finds password of account at index; returns found password of account.
	public static String getPassword(int index)
	{
		return get(index, DATABASE_FILE_PATH, END_OF_NAME, END_OF_PASS, LENGTH_OF_FILE);
	}
	
	//Method finds ID of account at index; returns found ID of account.
	public static String getID(int index)
	{
		return get(index, DATABASE_FILE_PATH, 0, LENGTH_OF_USERID, LENGTH_OF_FILE);
	}
	
	//method uses looks for index of searchterm "searchID"; returns index of where "searchID"
	//was found or -1 if not found.
	public static int getIDIndex(String ID)
	{
		return getIndex(ID, LENGTH_OF_USERID, DATABASE_FILE_PATH, 0, LENGTH_OF_USERID, LENGTH_OF_FILE);
	}
	
	//overloading method
	public static int get(String ID, int begin, int end)
	{
		int index = getIDIndex(ID);
		
		String str = get(index, DATABASE_FILE_PATH, begin, end, LENGTH_OF_FILE);
		return Integer.parseInt(str);
	}
	
	//gets the number of unanswered
	public static int getUnanswered(String ID)
	{
		return get(ID, END_OF_EMAIL, UNANSWERED_INDEX);
	}
	
	//to change the amount of unanswered
	public static void setUnanswered(String ID, int value)
	{
		int index = getIDIndex(ID);
		if(!isTeacher(index))
		{
			writeAt(value, index, 1, DATABASE_FILE_PATH, END_OF_EMAIL, LENGTH_OF_FILE);
		}
	}
	
	//automatically changes the unanswered
	public static void updateUnanswered(boolean newQuestion, boolean replacedQuestion, boolean removedQuestion, String QID)
	{
		int index = 0;
		String id = getID(index);
		while(id != null)
		{
			if(!isTeacher(index))
			{
				if(newQuestion)
				{
					setUnanswered(id, getUnanswered(id) + 1);
				}
				if((replacedQuestion || removedQuestion) && QID != null)
				{
					if(Answers.findAnswer(id, QID) >= 0)
					{
						setUnanswered(id, getUnanswered(id) - 1);
					}
				}
			}
			
			index = index + 1;
			id = getID(index);
		}
	}
	
	//automatically changes unanswered for a user
	public static void updateUnanswered(String ID)
	{
		int index = getIDIndex(ID);
		if(!isTeacher(index))
		{
			setUnanswered(ID, getUnanswered(ID) - 1);
		}
		
	}
	
	//returns number of questions which recieved feedback
	public static int getFeedback(String ID)
	{
		return get(ID, UNANSWERED_INDEX, FEEDBACK_INDEX);
	}
	
	//changes value of feedback
	public static void setFeedback(String ID, int value)
	{
		int index = getIDIndex(ID);
		if(!isTeacher(index))
		{
			writeAt(value, index, 1, DATABASE_FILE_PATH, UNANSWERED_INDEX, LENGTH_OF_FILE);
		}
	}
	
	//automatically changes feedback values
	public static void updateFeedback(boolean login, boolean newFeedback, boolean responded, String ID)
	{
		int index = 0;
		if(!isTeacher(index) && ID != null)
		{
			if(login)
			{
				String QID = Questions.getID(index);
				int count = 0;
				
				while(QID != null)
				{
					int temp = Answers.findAnswer(ID, QID);
					
					if(Answers.hasFeedback(temp))
					{
						count = count + 1;
					}
					index = index + 1;
					QID = Questions.getID(index);
				}
				setFeedback(ID, count);
			}
			if(newFeedback)
			{
				setFeedback(ID, getFeedback(ID) + 1);
			}
			if(responded)
			{
				setFeedback(ID, getFeedback(ID) - 1);
			}
		}
		
	}
	
	//returns the amount of answered questions (for teachers)
	public static int getAnswered(String ID)
	{
		return get(ID, END_OF_EMAIL, END_OF_ANSWERED);
	}
	
	//changes answered value
	public static void setAnswered(String ID, int value)
	{
		int index = getIDIndex(ID);
		if(!isTeacher(index))
		{
			writeAt(value, index, 1, DATABASE_FILE_PATH, END_OF_EMAIL, LENGTH_OF_FILE);
		}
	}
	
	//automatically changes answered value
	public static void updateAnswered(boolean login, boolean newResponse, boolean responded, String ID)
	{
		int index = getIDIndex(ID);
		if(isTeacher(index) && ID != null)
		{
			if(login)
			{
				int i = 0;
				String QID = Answers.getQID(i);
				int count = 0;
				
				while(QID != null)
				{
					if(Answers.isNewAnswer(i))
					{
						count = count + 1;
					}
					i = i + 1;
					QID = Answers.getQID(i);
				}
				setAnswered(ID, count);
			}
			if(newResponse)
			{
				setAnswered(ID, getAnswered(ID) + 1);
			}
			if(responded)
			{
				setAnswered(ID, getAnswered(ID) - 1);
			}
		}
		
	}
	
	//Method users last value of ID to check whether teacher or student;
	//returns boolean value true if a teacher, false if not;
	public static boolean isTeacher(int index)
	{
		String bool = get(index, DATABASE_FILE_PATH, LENGTH_OF_USERID - 1, LENGTH_OF_USERID, LENGTH_OF_FILE);
		return bool.equals("1");
	}
}
