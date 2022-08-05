package pages;

import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import database.*;

public class questionPage extends JFrame
{
	/*
	 * isTeacher - boolean holding true if user is teacher, false if not
	 * questionFrame - JFrame holding all the components that make up a question.
	 * questionFile - File containing an image or text block that would be the question
	 * dropBox - JButton allowing the user to drop in their answer file
	 * answerList - jFrame holding all student answers. Teacher only.
	 * download - JButton which allows user to download file.
	 * edit - JButton which allows user to edit file in the application.
	 * redo - JButton which pings specific student user
	 * submit - JButton submitting answer in dropBox as user answer.
	 * bacl - JButton used to lead back to the questionListPage
	 */
	private Question question;
	private ArrayList<Answer> answers = new ArrayList<>();
	private boolean teacher;
	
	private JPanel questionBox = new JPanel();
	
	public questionPage(Question q, boolean isTeacher)
	{
		question = q;
		teacher = isTeacher;
		
		if(teacher)
		{
			answers = makeList();
		}
		else
		{
			
		}
	}

	//public Question edit() - allows user to draw on a questionFile and returns a new png in the dropBox
	public Question edit(boolean teacher)
	{
		Question q = question;
		if(teacher)
		{
			
		}
		else
		{
			
		}
		return q;
	}
	
	
	//public boolean submit() - allows user to submit files in dropBox as their student answer.
	//returns true if file is submitted, false if not
	
	public boolean submit(String filePath)
	{
		return false;
	}
	
	//public void download() - downloads questionFile as a file to computer
	
	//public void back() - closes the questionPage and reopens the questionListPage
	
	public ArrayList<Answer> makeList()
	{
		ArrayList<Answer> as = new ArrayList<>();
		int index = 0;
		String userID = Accounts.getID(index);
		while(userID != null) 
		{
			int answerIndex = Answers.findAnswer(userID, question.getID());
			Answer a;
			if(answerIndex > -1)
			{
				a = new Answer(question.getName(), Answers.getPoints(answerIndex), question.getPoints());
			}
			else
			{
				a = new Answer(question.getName(), question.getPoints());
			}
			as.add(a);
			
			index = index + 1;
			userID = Accounts.getID(index);
		}
		
		return as;
	}
}
