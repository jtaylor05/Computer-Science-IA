package pages;

import java.util.*;

public class User 
{
	/*
	 * isTeacher - boolean which holds whether or not this user is a Teacher
	 * username - String holding username
	 * password - String holding password
	 * answerList - LinkedList holding all of the png answers to questions
	 * portfolio - Portfolio object holding this User's portfolio
	 */
	
	boolean isTeacher;
	
	String username;
	String password;
	
	int ID;
	
	//LinkedList<Images> answerList;
	
	//Portfolio portfolio;
	
	public User(boolean teacher, String username, String password, int ID)
	{
		isTeacher = teacher;
		this.username = username;
		this.password = password;
		this.ID = ID;
	}
	
	public String getPassword()
	{ return password; }
	
	public void changePassword(String password)
	{ this.password = password; }
	
	public String getUsername()
	{ return username; }
	
	public void changeUsername(String username)
	{ this.username = username; }
	
	public int getID()
	{ return ID; }
}
