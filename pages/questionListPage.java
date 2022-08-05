package pages;

import java.util.*; 
import database.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import library.L;

public class questionListPage extends JFrame
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
	
	private JPanel homeRow = new JPanel();
	private JPanel scroller = new JPanel();
	
	private JButton home = new JButton("Go to menu");
	
	private JPanel questions = new JPanel();
	private JScrollPane questionScroller = new JScrollPane(questions);
	
	public questionListPage(boolean isTeacher, String ID)
	{
		teacher = isTeacher;
		
		if(teacher)
		{
			questionList = makeList();
		}
		else
		{
			questionList = makeList(ID);
		}
		
		for(int i = 0; i < questionList.size(); i++)
		{
			System.out.println(questionList.get(i));
		}
		
		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				dropInPage(teacher, ID);
			}
		});
		homeRow.add(new JLabel("")); homeRow.add(home);
		
		questionScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		questionScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		questions.setLayout(new GridLayout(questionList.size(), 1));
		if(teacher)
		{
			for(int i = 0; i < questionList.size(); i++)
			{
				JPanel jp = new JPanel();
				jp.setLayout(new GridLayout(1, 3));
				
				final Question q = questionList.get(i);
				
				Canvas c = new Canvas() {
					public void paint(Graphics g)
					{
						Toolkit t = Toolkit.getDefaultToolkit();
						Image i = t.getImage(q.getPath() + ".png");
						g.drawImage(i, 50, 50, this);
					}
				};
				jp.add(c);
				
				JLabel jl = new JLabel();
				jl.setText("Out of " + q.getPoints() + " points");
				jp.add(jl);
				
				questions.add(jp);
			}
		}
		else
		{
			for(int i = 0; i < questionList.size(); i++)
			{
				JPanel jp = new JPanel();
				jp.setLayout(new GridLayout(1, 4));
				
				final Question q = questionList.get(i);
				
				Canvas c = new Canvas() {
					public void paint(Graphics g)
					{
						Toolkit t = Toolkit.getDefaultToolkit();
						Image i = t.getImage(q.getPath() + ".png");
						g.drawImage(i, 50, 50, this);
					}
				};
				jp.add(c);
				
				JButton jb = new JButton(questionList.get(i).getName());
				jb.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new questionPage(q, teacher).setVisible(true);
						setVisible(false);
						dispose();
					}
				});
				jp.add(jb);
				
				JLabel jl = new JLabel();
				if(q.hasAnswered())
				{
					jl.setText("Points: " + q.getGrade());
				}
				else
				{
					jl.setText("Points: N/A");
				}
				jp.add(jl);
				
				JLabel message = new JLabel(q.getMessage());
				jp.add(message);
				
				questions.add(jp);
			}
		}
		scroller.add(questionScroller);
		
		setLayout(new GridLayout(2, 1));
		getContentPane().add(homeRow);
		getContentPane().add(scroller);
		setVisible(true);
		pack();
	}
	
	public void dropInPage(boolean teacher, String ID)
	{
		new dropInPage(teacher, ID).setVisible(true);
		setVisible(false);
		dispose();
	}
	
	public ArrayList<Question> makeList()
	{
		ArrayList<Question> questions = new ArrayList<>();
		
		int index = 0;
		String QID = Questions.getID(index);
		
		while(QID != null)
		{
			String name = Questions.getName(index);
			name = L.shear(name);
			int maxPoints = Questions.getPoints(index);
			
			Question q = new Question(QID, name, maxPoints);
			
			questions.add(q);
		}
		
		return questions;
	}
	
	public ArrayList<Question> makeList(String userID)
	{
		ArrayList<Question> questions = new ArrayList<>();
		
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
			name = L.shear(name);
			String filePath = Questions.getFilePath(index);
			int maxPoints = Questions.getPoints(index);	
			String message = "";
			Question q = null;
			if(points > -1)
			{
				String grade = points + "/" + maxPoints;
				q = new Question(QID, name, filePath, grade, message);
			}
			else
			{
				q = new Question(QID, name, filePath, maxPoints, message);
			}
						
			questions.add(q);
			
			index = index + 1;
			QID = Questions.getID(index);
		}
		
		return questions;
	}
	
	public static void main(String[] args)
	{
		System.out.println(Accounts.getID(0));
		new questionListPage(Accounts.isTeacher(0), Accounts.getID(0));
	}
}
