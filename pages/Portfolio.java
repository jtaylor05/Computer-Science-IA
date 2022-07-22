package pages;

import java.util.*;

public class Portfolio 
{
	/*
	 * numQuestions - int holding number of questions
	 * numRight - int holding number of questions answered right.
	 * questionList - a list of questions that this student has answered
	 */
	
	int ID;
	
	int numQuestions;
	int numRight;
	
	LinkedList<Question> questionList;
	
	public Portfolio(int studentID)
	{
		ID = studentID;
	}
	
	public float percentRight()
	{ return (float)numRight/numQuestions; }
}
