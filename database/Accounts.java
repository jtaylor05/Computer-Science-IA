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
	private final static int END_OF_EMAIL = END_OF_PASS + LENGTH_OF_EMAIL;
	private final static int UNANSWERED_INDEX = END_OF_EMAIL + 1;
	private final static int FEEDBACK_INDEX = UNANSWERED_INDEX + 1;
	private final static int LENGTH_OF_INFO = 2;
	private final static int END_OF_ANSWERED = END_OF_EMAIL + LENGTH_OF_INFO;
	private final static int LENGTH_OF_FILE = END_OF_ANSWERED + 1;
	
	//Method adds account data to file "accounts".
	public static void addAccount(String username, String password, String email, boolean isTeacher)
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
		
		
		
		try
		{
			fw = new FileWriter(DATABASE_FILE_PATH, true);
			if(isTeacher)
			{
				fw.write(id + teacher + fixedName + fixedPass + fixedEmail + answered + " \n");
			}
			else
			{
				fw.write(id + teacher + fixedName + fixedPass + fixedEmail + unanswered + "0\n");
			}
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
			raf.close();
		}
		catch(Exception e)
		{
			System.out.println("Line 87 error " + e);
		}
		
		return -1;
	}
	
	//does same as function above, however goes from minimum index
	public static int getUsernameIndex(String username, int minIndex)
	{
		String user = "";
		int index = minIndex;
		try
		{
			raf = new RandomAccessFile(DATABASE_FILE_PATH, "rw");
			raf.seek(LENGTH_OF_FILE * index);
			int length = (int)raf.length();
			while(raf.getFilePointer() < length)
			{
					
				String line = raf.readLine();
				user = line.substring(LENGTH_OF_ID, END_OF_NAME);
				user = L.shear(user);
				
				if(user.equals(username))
				{
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
	
	//To return username from certain index in database.
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
			raf.close();
		}
		catch(Exception e)
		{
			System.out.println("Line 114 error " + e);
		}
		
		return L.shear(username);
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
			raf.close();
		}
		catch(Exception e)
		{
			System.out.println("Line 148 error " + e);
		}
		
		return -1;
	}
	
	public static int getEmailIndex(String email, int minIndex)
	{
		String mail = "";
		int index = minIndex;
		try
		{
			raf = new RandomAccessFile(DATABASE_FILE_PATH, "rw");
			raf.seek(LENGTH_OF_FILE * index);
			int length = (int)raf.length();
			while(raf.getFilePointer() < length)
			{
					
				String line = raf.readLine();
				mail = line.substring(END_OF_PASS, LENGTH_OF_FILE);
				mail = L.shear(mail);
				
				if(mail.equals(email))
				{
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
			raf.close();
		}
		catch(Exception e)
		{
			System.out.println("Line 182 error " + e);
		}
			
		return -1;
	}
	
	//does same as method above, but goes from a minimum index
	public static int getPasswordIndex(String password, int minIndex)
	{
		String pass = "";
		int index = minIndex;
		try
		{
			raf = new RandomAccessFile(DATABASE_FILE_PATH, "rw");
			raf.seek(LENGTH_OF_FILE * index);
			int length = (int)raf.length();
			while(raf.getFilePointer() < length)
			{
					
				String line = raf.readLine();
				pass = line.substring(END_OF_NAME, END_OF_PASS);
				pass = L.shear(pass);
					
				if(pass.equals(password))
				{
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
			raf.close();
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
			raf.close();
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
			raf.close();
		}
		catch(Exception e)
		{
			System.out.println("error " + e);
		}
				
		return -1;
	}
	
	//gets the number of unanswered
	public static int getUnanswered(String ID)
	{
		int index = getIDIndex(ID);
		int unanswered = -1;
		if(!isTeacher(index))
		{
			try
			{
				raf = new RandomAccessFile(DATABASE_FILE_PATH, "rw");
				if(LENGTH_OF_FILE * index < raf.length())
				{
					raf.seek(LENGTH_OF_FILE * index);
				}
				else
				{
					return unanswered;
				}
				
				String line = raf.readLine();
				String temp = line.substring(END_OF_EMAIL, UNANSWERED_INDEX);
				unanswered = Integer.parseInt(temp);
				raf.close();
			}
			catch(Exception e)
			{
				System.out.println("error " + e);
			}
		}
		
		return unanswered;
	}
	
	//to change the amount of unanswered
	public static void setUnanswered(String ID, int value)
	{
		int index = getIDIndex(ID);
		if(!isTeacher(index))
		{
			try
			{
				raf = new RandomAccessFile(DATABASE_FILE_PATH, "rw");
				if(LENGTH_OF_FILE * index < raf.length())
				{
					raf.seek(LENGTH_OF_FILE * index + END_OF_EMAIL);
					raf.writeBytes("" + value);
				}
				raf.close();
			}
			catch(Exception e)
			{
				System.out.println("error " + e);
			}
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
		int index = getIDIndex(ID);
		int feedback = -1;
		if(!isTeacher(index))
		{
			try
			{
				raf = new RandomAccessFile(DATABASE_FILE_PATH, "rw");
				if(LENGTH_OF_FILE * index < raf.length())
				{
					raf.seek(LENGTH_OF_FILE * index);
				}
				else
				{
					return feedback;
				}
				
				String line = raf.readLine();
				String temp = line.substring(UNANSWERED_INDEX, FEEDBACK_INDEX);
				feedback = Integer.parseInt(temp);
				raf.close();
			}
			catch(Exception e)
			{
				System.out.println("error " + e);
			}
		}
		
		return feedback;
	}
	
	//changes value of feedback
	public static void setFeedback(String ID, int value)
	{
		int index = getIDIndex(ID);
		if(!isTeacher(index))
		{
			try
			{
				raf = new RandomAccessFile(DATABASE_FILE_PATH, "rw");
				if(LENGTH_OF_FILE * index < raf.length())
				{
					raf.seek(LENGTH_OF_FILE * index + UNANSWERED_INDEX);
					raf.writeBytes("" + value);
				}
				raf.close();
			}
			catch(Exception e)
			{
				System.out.println("error " + e);
			}
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
		int index = getIDIndex(ID);
		int answered = -1;
		if(isTeacher(index))
		{
			try
			{
				raf = new RandomAccessFile(DATABASE_FILE_PATH, "rw");
				if(LENGTH_OF_FILE * index < raf.length())
				{
					raf.seek(LENGTH_OF_FILE * index);
				}
				else
				{
					return answered;
				}
				
				String line = raf.readLine();
				String temp = line.substring(END_OF_EMAIL, END_OF_ANSWERED);
				answered = Integer.parseInt(L.shear(temp));
				raf.close();
			}
			catch(Exception e)
			{
				System.out.println("error " + e);
			}
		}
		
		return answered;
	}
	
	//changes answered value
	public static void setAnswered(String ID, int value)
	{
		int index = getIDIndex(ID);
		if(isTeacher(index))
		{
			try
			{
				raf = new RandomAccessFile(DATABASE_FILE_PATH, "rw");
				if(LENGTH_OF_FILE * index < raf.length())
				{
					raf.seek(LENGTH_OF_FILE * index + END_OF_EMAIL);
					System.out.println(value);
					raf.writeBytes("" + value);
				}
				raf.close();
			}
			catch(Exception e)
			{
				System.out.println("error " + e);
			}
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
			raf.close();
		}
		catch(Exception e)
		{
			System.out.println("Line 266 error " + e);
		}
		
		return isTeacher;
	}
}
