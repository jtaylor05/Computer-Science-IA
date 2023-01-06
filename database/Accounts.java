package database;

import java.io.*; 
import library.L;

/**
 * Accesses database for user Accounts using .txt file: .\database\accounts.
 * Extends class Database.
 */
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
	
	/**
	 * @param username user-chosen String
	 * @param password user-chosen String
	 * @param email user-chosen String
	 * @param isTeacher boolean value representing whether user is teacher
	 */
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
	
	/**
	 * @param username search term
	 * @return index of parameter username in .txt file
	 */
	public static int getUsernameIndex(String username)
	{
		return getIndex(username, LENGTH_OF_NAME, DATABASE_FILE_PATH, LENGTH_OF_USERID, END_OF_NAME, LENGTH_OF_FILE);
	}
	
	/**
	 * @param username username search term
	 * @param minIndex starting index of search
	 * @return index of parameter username after minIndex in .txt file
	 */
	public static int getUsernameIndex(String username, int minIndex)
	{
		return getIndex(username, LENGTH_OF_NAME, DATABASE_FILE_PATH, LENGTH_OF_USERID, END_OF_NAME, LENGTH_OF_FILE, minIndex);
	}
	
	/**
	 * @param index entry number in .txt file
	 * @return String username at index
	 */
	public static String getUsername(int index)
	{
		return get(index, DATABASE_FILE_PATH, LENGTH_OF_USERID, END_OF_NAME, LENGTH_OF_FILE);
	}
	
	/**
	 * 
	 * @param email search term
	 * @return index of parameter email in .txt file
	 */
	public static int getEmailIndex(String email)
	{
		return getIndex(email, LENGTH_OF_EMAIL, DATABASE_FILE_PATH, END_OF_PASS, LENGTH_OF_FILE, LENGTH_OF_FILE);
	}
	
	/**
	 * @param email search term
	 * @param minIndex starting index of search
	 * @return index of parameter email after minIndex in .txt file
	 */
	public static int getEmailIndex(String email, int minIndex)
	{
		return getIndex(email, LENGTH_OF_EMAIL, DATABASE_FILE_PATH, END_OF_PASS, LENGTH_OF_FILE, LENGTH_OF_FILE, minIndex);
	}
	
	/**
	 * 
	 * @param password search term
	 * @return index of parameter password in .txt file
	 */
	public static int getPasswordIndex(String password)
	{
		return getIndex(password, LENGTH_OF_PASS, DATABASE_FILE_PATH, END_OF_NAME, END_OF_PASS, LENGTH_OF_FILE);
	}
	
	/**
	 * @param password search term
	 * @param minIndex starting index of search
	 * @return index of parameter password after minIndex in .txt file
	 */
	public static int getPasswordIndex(String password, int minIndex)
	{
		return getIndex(password, LENGTH_OF_PASS, DATABASE_FILE_PATH, END_OF_NAME, END_OF_PASS, LENGTH_OF_FILE, minIndex);
	}
	
	/**
	 * @param index entry number in .txt file
	 * @return String password at index
	 */
	public static String getPassword(int index)
	{
		return get(index, DATABASE_FILE_PATH, END_OF_NAME, END_OF_PASS, LENGTH_OF_FILE);
	}
	
	/**
	 * @param index entry number in .txt file
	 * @return String ID at index
	 */
	public static String getID(int index)
	{
		return get(index, DATABASE_FILE_PATH, 0, LENGTH_OF_USERID, LENGTH_OF_FILE);
	}
	
	/**
	 * @param ID search term
	 * @return index of parameter ID in .txt file
	 */
	public static int getIDIndex(String ID)
	{
		return getIndex(ID, LENGTH_OF_USERID, DATABASE_FILE_PATH, 0, LENGTH_OF_USERID, LENGTH_OF_FILE);
	}
	
	/**
	 * @param ID relevant user ID
	 * @param begin beginning of data in .txt entry
	 * @param end end of data in .txt entry
	 * @return int value found in .txt file entry
	 * polymorphs method get() in class Database
	 */
	public static int get(String ID, int begin, int end)
	{
		int index = getIDIndex(ID);
		
		String str = get(index, DATABASE_FILE_PATH, begin, end, LENGTH_OF_FILE);
		return Integer.parseInt(str);
	}
	
	/**
	 * @param ID relevant user ID
	 * @return int number of unanswered for parameter user ID
	 */
	public static int getUnanswered(String ID)
	{
		return get(ID, END_OF_EMAIL, UNANSWERED_INDEX);
	}
	
	/**
	 * @param ID user to change value of
	 * @param value new value
	 */
	public static void setUnanswered(String ID, int value)
	{
		int index = getIDIndex(ID);
		if(!isTeacher(index))
		{
			writeAt(value, index, 1, DATABASE_FILE_PATH, END_OF_EMAIL, LENGTH_OF_FILE);
		}
	}
	
	/**
	 * @param newQuestion boolean value of whether new question has been made
	 * @param replacedQuestion boolean value of whether old question has been replaced
	 * @param removedQuestion boolean value of whether question has been removed
	 * @param QID the question that was replaced or removed
	 */
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
	
	/**
	 * @param ID user value which has increased unanswered
	 */
	public static void updateUnanswered(String ID)
	{
		int index = getIDIndex(ID);
		if(!isTeacher(index))
		{
			setUnanswered(ID, getUnanswered(ID) - 1);
		}
		
	}
	
	/**
	 * @param ID relevant user ID
	 * @return int number of unresponded feedback for parameter user ID
	 */
	public static int getFeedback(String ID)
	{
		return get(ID, UNANSWERED_INDEX, FEEDBACK_INDEX);
	}
	
	/**
	 * @param ID user to change value of
	 * @param value new value
	 */
	public static void setFeedback(String ID, int value)
	{
		int index = getIDIndex(ID);
		if(!isTeacher(index))
		{
			writeAt(value, index, 1, DATABASE_FILE_PATH, UNANSWERED_INDEX, LENGTH_OF_FILE);
		}
	}
	
	/**
	 * @param login boolean value of whether a login has been made
	 * @param newFeedback boolean value of whether new feedback has been made
	 * @param responded boolean value of whether feedback has been responded to
	 * @param ID the user that will have the changed values
	 */
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
	
	/**
	 * @param ID relevant user ID
	 * @return int value of answered for parameter user ID
	 */
	public static int getAnswered(String ID)
	{
		return get(ID, END_OF_EMAIL, END_OF_ANSWERED);
	}
	
	/**
	 * @param ID user to change value of
	 * @param value new value
	 */
	public static void setAnswered(String ID, int value)
	{
		int index = getIDIndex(ID);
		if(!isTeacher(index))
		{
			writeAt(value, index, 1, DATABASE_FILE_PATH, END_OF_EMAIL, LENGTH_OF_FILE);
		}
	}
	
	/**
	 * @param login boolean value of whether a new login has occurred
	 * @param newResponse boolean value representing whether a new question has been answer
	 * @param responded boolean value representing whether an answer has been responded to
	 * @param ID the user that will have the changed value
	 */
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
	
	/**
	 * @param index index of relavent user to be checked
	 * @return boolean value at end of ID of whether user is teacher
	 */
	public static boolean isTeacher(int index)
	{
		String bool = get(index, DATABASE_FILE_PATH, LENGTH_OF_USERID - 1, LENGTH_OF_USERID, LENGTH_OF_FILE);
		return bool.equals("1");
	}
}
