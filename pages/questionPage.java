package pages;

import java.util.*; 
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
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
	private Answer answer;
	private ArrayList<Answer> answers = new ArrayList<>();
	private boolean teacher;
	private String ID;
	
	private JPanel homeRow = new JPanel();
	private JPanel questionBox = new JPanel();
	
	private JButton home = new JButton("Go to questions");
	
	private JPanel answerBox = new JPanel();
	private JScrollPane answerScroller;
	
	public questionPage(Question q, String ID, boolean isTeacher)
	{
		question = q;
		teacher = isTeacher;
		this.ID = ID;
		
		if(teacher)
		{
			answers = makeList();
		}
		else
		{
			String name = question.getName();
			int index = Answers.findAnswer(ID, question.getID());
			int maxPoints = -1;
			if(question.hasAnswered())
			{
				maxPoints = Answers.getPoints(index);
			}
			String filePath;
			int grade = -1;
			if(index > -1)
			{
				filePath = Answers.getFilePath(index);
				grade = Answers.getPoints(index);
				answer = new Answer(name, filePath, grade, maxPoints);
			}
			else
			{
				answer = new Answer(name, maxPoints);
			}
			
		}
		
		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				questionList();
			}
		});
		homeRow.add(new JLabel("")); homeRow.add(home);
		
		if(teacher)
		{
			answerScroller = new JScrollPane(answerBox);
			answerScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			answerScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			answerBox.setLayout(new GridLayout(answers.size(), 1));
			
			for(int i = 0; i < answers.size(); i++)
			{
				JPanel jp = new JPanel();
				jp.setLayout(new GridLayout(1, 5));
						
				final Answer a = answers.get(i);
				
				Canvas c = new Canvas() {
					public void paint(Graphics g)
					{
						Toolkit t = Toolkit.getDefaultToolkit();
						Image i = t.getImage(a.getPath() + ".png");
						g.drawImage(i, 50, 50, this);
					}
				};
				jp.add(c);
				
				JLabel user = new JLabel(a.getUser());
				jp.add(user);
				
				JLabel grade = new JLabel("");
				if(a.hasAnswer())
				{
					grade.setText("Grade: " + a.getOutOf());
				}
				else
				{
					grade.setText("Grade: N/A");
				}
				jp.add(grade);
				
				JButton edit = new JButton("Edit");
				edit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e)
					{
						edit(teacher);
					}
				});
				jp.add(edit);
				
				JButton submit = new JButton("Submit");
				submit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e)
					{
						submit(answer.getPath());
					}
				});
				jp.add(submit);
				
				answerBox.add(jp);
			}
		}
		else
		{
			answerBox.setLayout(new GridLayout(1, 4));
			
			final Answer a;
			
			Canvas c = new Canvas() {
				public void paint(Graphics g)
				{
					Toolkit t = Toolkit.getDefaultToolkit();
					Image i = t.getImage(answer.getPath() + ".png");
					g.drawImage(i, 50, 50, this);
				}
			};
			
			answerBox.add(c);
			
			JLabel grade = new JLabel("");
			if(answer.hasAnswer())
			{
				grade.setText("Grade: " + answer.getOutOf());
			}
			else
			{
				grade.setText("Grade: N/A");
			}
			answerBox.add(grade);
			
			JButton edit = new JButton("Edit");
			edit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					edit(teacher);
				}
			});
			answerBox.add(edit);
			
			JButton submit = new JButton("Submit");
			submit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					submit(answer.getPath());
				}
			});
			answerBox.add(submit);
		}
		
		setLayout(new GridLayout(2, 1));
		getContentPane().add(homeRow);
		getContentPane().add(answerBox);
		getContentPane().setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
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
	
	public void questionList()
	{
		new questionListPage(teacher, ID).setVisible(true);
		setVisible(false);
		dispose();
	}
	
	public ArrayList<Answer> makeList()
	{
		ArrayList<Answer> as = new ArrayList<>();
		int index = 0;
		String userID = Accounts.getID(index);
		String name = Accounts.getUsername(index);
		
		System.out.println(question.getID());
		while(userID != null) 
		{
			System.out.println(userID);
			System.out.println(name);
			int answerIndex = Answers.findAnswer(userID, question.getID());
			
			Answer a;
			if(answerIndex > -1)
			{
				a = new Answer(name, question.getName(), Answers.getFilePath(answerIndex), Answers.getPoints(answerIndex), question.getPoints());
			}
			else
			{
				a = new Answer(name, question.getName(), question.getPoints());
			}
			as.add(a);
			
			index = index + 1;
			userID = Accounts.getID(index);
			name = Accounts.getUsername(index);
		}
		
		return as;
	}
}
