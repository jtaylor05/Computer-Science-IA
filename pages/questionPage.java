package pages;

import java.util.*; 
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import database.*;
import library.L;

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
			if(question.hasAnswer())
			{
				maxPoints = question.getMaxPoints();
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
			for(int i = 0; i < answers.size() - 1; i++)
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
				if(a.hasAnswer() && a.getGrade() > -1)
				{
					grade.setText("Grade: " + a.getOutOf());
				}
				else
				{
					grade.setText("Grade: N/A");
				}
				jp.add(grade);
				
				JButton edit = new JButton("Give Grade");
				edit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e)
					{
						grade(a, teacher);
					}
				});
				jp.add(edit);
				
				JButton submit = new JButton("Give Feedback");
				submit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e)
					{
						submitFeedback(a, teacher);
					}
				});
				jp.add(submit);
				
				answerBox.add(jp);
			}
		}
		else
		{
			answerBox.setLayout(new GridLayout(1, 4));
			
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
			if(answer.hasAnswer() && q.getGrade() > -1)
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
					//edit(teacher);
				}
			});
			answerBox.add(edit);
			
			JButton submit = new JButton("Submit");
			submit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					submitWork(answer, teacher);
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
	public void grade(Answer a, boolean teacher)
	{
		JFrame grade = new JFrame("Grade");
		grade.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		grade.setLayout(new GridLayout(5,1));
		grade.setVisible(true);
		int temp = Accounts.getUsernameIndex(a.getUser());
		String uID = Accounts.getID(temp);
		int index = Answers.findAnswer(uID, question.getID());
		
		JLabel label = new JLabel(" " + a.getName() + " Max Points: " + a.getMaxPoints());
		grade.add(label);
		
		Canvas c = new Canvas() {
			public void paint(Graphics g)
			{
				Toolkit t = Toolkit.getDefaultToolkit();
				Image i = t.getImage(a.getPath() + ".png");
				g.drawImage(i, 50, 50, this);
			}
		};
		grade.add(c);
		
		JPanel newGrade = new JPanel(); newGrade.setLayout(new GridLayout(2,1));
		JLabel newGradeLabel = new JLabel("Enter New Grade:");
		JTextField newGradeText;
		if(index >= 0)
		{
			newGradeText = new JTextField("" + Answers.getPoints(index));
		}
		else
		{
			newGradeText = new JTextField("NG");
		}
		newGrade.add(newGradeLabel); newGrade.add(newGradeText);
		grade.add(newGrade);
		
		JPanel buttons = new JPanel(); buttons.setLayout(new GridLayout(1, 3));
		JButton close = new JButton("Close");
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				grade.dispose();
			}
		});
		JButton finish = new JButton("Finish");
		finish.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e)
			{
				boolean isGrade = !"".equals(newGradeText.getText()) && newGradeText.getText().length() <= 48;
				int points;
				try
				{
					points = Integer.parseInt(newGradeText.getText());
				}
				catch(Exception error)
				{
					points = -1;
				}
				
				if(isGrade && points >= 0 && points <= a.getMaxPoints())
				{
					
					
					if(index >= 0)
					{
						Answers.changePoints(index, points);
					}
					else
					{
						Answers.addAnswer(uID, question.getID(), "", points);
					}
					grade.dispose();
					new questionPage(question, ID, teacher).setVisible(true);
					dispose();
				}
				else
				{
					label.setText("Wrong Values " + " Max: " + a.getMaxPoints());
				}
			}
		});
		buttons.add(close); buttons.add(finish);
		grade.add(buttons);
		
		grade.pack();
	}
	
	//submits a file to filePath. returns true if done, false if not
	public void submitFeedback(Answer a, boolean teacher)
	{
		JFrame submit = new JFrame("Submit Image");
		submit.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		submit.setLayout(new GridLayout(5,1));
		submit.setVisible(true);
		int temp = Accounts.getUsernameIndex(a.getUser());
		String uID = Accounts.getID(temp);
		int index = Answers.findAnswer(uID, question.getID());
			
		JLabel label = new JLabel(" " + a.getName());
		submit.add(label);
			
		JPanel filePath = new JPanel(); filePath.setLayout(new GridLayout(2,1));
		JLabel filePathLabel = new JLabel("Enter file path:");
		JTextField filePathText;
		if(index >= 0)
		{
			filePathText = new JTextField("" + L.shear(Answers.getFilePath(index)));
		}
		else
		{
			filePathText = new JTextField("");
		}
		filePath.add(filePathLabel); filePath.add(filePathText);
		submit.add(filePath);
			
		JPanel buttons = new JPanel(); buttons.setLayout(new GridLayout(1, 3));
		JButton close = new JButton("Close");
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				submit.dispose();
			}
		});
		JButton finish = new JButton("Finish");
		finish.addActionListener(new ActionListener() {
				
			public void actionPerformed(ActionEvent e)
			{
				boolean isFilePath = !"".equals(filePathText.getText()) && filePathText.getText().length() <= 48;
					
				if(isFilePath)
				{
						
					if(index >= 0)
					{
						Answers.changeFilePath(index, filePathText.getText());
					}
					else
					{
						Answers.addAnswer(uID, question.getID(), filePathText.getText());
					}
					submit.dispose();
					new questionPage(question, ID, teacher).setVisible(true);
					dispose();
				}
				else
				{
					label.setText("Wrong Values");
				}
			}
		});
		buttons.add(close); buttons.add(finish);
		submit.add(buttons);
		
		submit.pack();
	}
	
	//submits a file to filePath. returns true if done, false if not
	public void submitWork(Answer a, boolean teacher)
	{
		JFrame submit = new JFrame("Submit Image");
		submit.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		submit.setLayout(new GridLayout(5,1));
		submit.setVisible(true);
		int temp = Accounts.getUsernameIndex(a.getUser());
		String uID = Accounts.getID(temp);
		int index = Answers.findAnswer(uID, question.getID());
		
		JLabel label = new JLabel(" " + a.getName());
		submit.add(label);
		
		JPanel filePath = new JPanel(); filePath.setLayout(new GridLayout(2,1));
		JLabel filePathLabel = new JLabel("Enter file path:");
		JTextField filePathText;
		if(index >= 0)
		{
			filePathText = new JTextField("" + Answers.getFilePath(index));
		}
		else
		{
			filePathText = new JTextField("");
		}
		filePath.add(filePathLabel); filePath.add(filePathText);
		submit.add(filePath);
		
		JPanel buttons = new JPanel(); buttons.setLayout(new GridLayout(1, 3));
		JButton close = new JButton("Close");
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				submit.dispose();
			}
		});
		JButton finish = new JButton("Finish");
		finish.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e)
			{
				boolean isFilePath = !"".equals(filePathText.getText()) && filePathText.getText().length() <= 48;
				
				if(isFilePath)
				{
					
					if(index >= 0)
					{
						Answers.changeFilePath(index, filePathText.getText());
					}
					else
					{
						Answers.addAnswer(uID, question.getID(), filePathText.getText());
					}
					submit.dispose();
					new questionPage(question, ID, teacher).setVisible(true);
					dispose();
				}
				else
				{
					label.setText("Wrong Values");
				}
			}
		});
		buttons.add(close); buttons.add(finish);
		submit.add(buttons);
		
		submit.pack();
	}
	
	//public void download() - downloads questionFile as a file to computer
	
	//opens a questionListPage, closing the current page
	public void questionList()
	{
		new questionListPage(teacher, ID).setVisible(true);
		setVisible(false);
		dispose();
	}
	
	//make List of all answers a user has
	public ArrayList<Answer> makeList()
	{
		ArrayList<Answer> as = new ArrayList<>();
		int index = 0;
		String userID = Accounts.getID(index);
		String name = Accounts.getUsername(index);
		
		while(userID != null) 
		{
			int answerIndex = Answers.findAnswer(userID, question.getID());
			
			Answer a;
			if(!Accounts.isTeacher(index))
			{
				if(answerIndex > -1)
				{
					a = new Answer(name, question.getName(), Answers.getFilePath(answerIndex), Answers.getPoints(answerIndex), question.getMaxPoints());
					as.add(a);
				}
				else if(userID != null)
				{
					a = new Answer(name, question.getName(), question.getMaxPoints());
					as.add(a);
				}
			}
			
			index = index + 1;
			userID = Accounts.getID(index);
			name = Accounts.getUsername(index);
		}
		
		return as;
	}
}
