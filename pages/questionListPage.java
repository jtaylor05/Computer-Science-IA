package pages;

import java.util.*;

import database.Answers;
import database.Questions;

public class questionListPage 
{
	/*
	 * isTeacher - boolean holding true if user is teacher, false if not
	 * questionListFrame - JFrame holding the list of questions which students can answer or teachers can look at
	 * homePage - JButton leading back to the dropInPage
	 * questionList - LinkedList of all of the questions
	 * addQuestion - JButton allowing teacher user to add a question
	 */
	
	private boolean teacher;
	private ArrayList<Question> questionList;
	
	public questionListPage(boolean isTeacher, String ID)
	{
		teacher = isTeacher;
		
		if(teacher)
		{
			
		}
		else
		{
			questionList = makeList(ID);
		}
	}
	
	//public void questionPressed() - after a question is pressed, opens questionPage for that question
	
	//public void homePage() - closes questionListPage and opens dropInPage
	
	
	public ArrayList<Question> makeList(String userID)
	{
		ArrayList<Question> questions = new ArrayList<>();
		
		int count = 0;
		int index = 0;
		String QID = Questions.getID(index);
		while(QID != null)
		{
			int answerIndex = Answers.findAnswer(userID, QID);
			int points = -1;
			if(answerIndex > -1)
			{
				points = Answers.getPoints(answerIndex);
			}
			
			String name = Questions.getName(index);
			String filePath = Questions.getFilePath(index);
			int maxPoints = Questions.getPoints(index);	
			String message = "";
			
			Question q = null;
			if(points > -1)
			{
				String grade = points + "/" + maxPoints;
				q = new Question(name, filePath, grade, message);
			}
			else
			{
				q = new Question(name, filePath, maxPoints, message);
			}
						
			questions.add(q);
			
			index = index + 1;
			QID = Questions.getID(index);
		}
		
		
		return questions;
	}
	
	public static void main(String[] args)
	{
		
	}
}
