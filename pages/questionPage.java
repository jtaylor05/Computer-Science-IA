package pages;

import java.util.*; 
import javax.swing.*;
import javax.xml.transform.Templates;

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
		GridBagConstraints c = new GridBagConstraints();
		
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
		homeRow.setBackground(L.LIGHT_BROWN);
		homeRow.setLayout(new GridBagLayout());
			c.fill = GridBagConstraints.HORIZONTAL;
			
			c.gridx = 0;
			
			c.weightx = 1;
		homeRow.add(new JLabel(""), c); 
			c.fill = GridBagConstraints.NONE;
		
			c.gridx = 1;
			
			c.weightx = 0;
			
			c.insets = new Insets(3, 3, 3, 3);
			home.setPreferredSize(new Dimension(140, 30));
			home.setBackground(L.DARK_BROWN);
			home.setForeground(Color.WHITE);
		homeRow.add(home, c);
			c.insets = new Insets(0, 0, 0, 0);
		
		answerBox.setBackground(L.PRIME_BLUE);
		if(teacher)
		{
			answerScroller = new JScrollPane(answerBox);
			answerScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			answerScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			answerBox.setLayout(new GridBagLayout());
			for(int i = 0; i < answers.size(); i++)
			{
				final Answer a = answers.get(i);
				
				Canvas ca = new Canvas() {
					public void paint(Graphics g)
					{
						Toolkit t = Toolkit.getDefaultToolkit();
						Image i = t.getImage(a.getPath() + ".png");
						g.drawImage(i, 50, 50, this);
					}
				};
					c.fill = GridBagConstraints.HORIZONTAL;
				
					c.gridx = 0; c.gridy = i;
				
					c.weightx = 0.5;
					c.insets = new Insets(20, 0, 0, 0);
				answerBox.add(ca, c);
				
				JLabel user = new JLabel(a.getUser());
					c.fill = GridBagConstraints.NONE;
				
					c.gridx = 1;
				
					c.weightx = 0;
					user.setPreferredSize(new Dimension(150, 30));
				answerBox.add(user, c);
				
				JLabel grade = new JLabel("");
				if(a.hasAnswer() && a.getGrade() > -1)
				{
					grade.setText("Grade: " + a.getOutOf());
				}
				else if(a.hasAnswer() && a.getGrade() == -1)
				{
					grade.setText("Grade: Missing");
				}
				else if(a.hasAnswer() && a.getGrade() == -2)
				{
					grade.setText("Grade: *");
				}
				else
				{
					grade.setText("Grade: N/A");
				}
					c.gridx = 2;
				answerBox.add(grade, c);
				
				JButton edit = new JButton("Give Grade");
				edit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e)
					{
						grade(a, teacher);
					}
				});
					c.gridx = 3;
					
					c.insets = new Insets(3, 3, 3, 3);
					edit.setPreferredSize(new Dimension(130, 30));
					edit.setBackground(L.LIGHT_BROWN);
					edit.setForeground(Color.BLACK);
				answerBox.add(edit, c);
				
				JButton submit = new JButton("Give Feedback");
				submit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e)
					{
						submitFeedback(a, teacher);
					}
				});
					c.gridx = 4;
					submit.setPreferredSize(new Dimension(130, 30));
					submit.setBackground(L.LIGHT_BROWN);
					submit.setForeground(Color.BLACK);
				answerBox.add(submit, c);
					c.insets = new Insets(0, 0, 0, 0);
			}
		}
		else
		{
			answerBox.setLayout(new GridBagLayout());
			
			Canvas ca = new Canvas() {
				public void paint(Graphics g)
				{
					Toolkit t = Toolkit.getDefaultToolkit();
					Image i = t.getImage(answer.getPath() + ".png");
					g.drawImage(i, 50, 50, this);
				}
			};
				c.fill = GridBagConstraints.HORIZONTAL;
			
				c.gridx = 0;
		
				c.weightx = 0.5;
			answerBox.add(ca, c);
			
			JLabel grade = new JLabel("");
			if(answer.hasAnswer() && answer.getGrade() > -1)
			{
				grade.setText("Grade: " + answer.getOutOf());
			}
			else if(answer.hasAnswer() && answer.getGrade() == -1)
			{
				grade.setText("Missing");
			}
			else if(answer.hasAnswer() && answer.getGrade() == -2)
			{
				grade.setText("Grade: *");
			}
			else
			{
				grade.setText("Grade: N/A");
			}
				c.fill = GridBagConstraints.NONE;
			
				c.gridx = 1;
		
				c.weightx = 0;
				c.insets = new Insets(20, 0, 0, 0);
				grade.setPreferredSize(new Dimension(150, 30));
			answerBox.add(grade, c);
			
			JButton submit = new JButton("Submit");
			submit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					submitWork(answer, teacher);
				}
			});
				c.gridx = 2;
				
				c.insets = new Insets(3, 3, 3, 3);
				submit.setPreferredSize(new Dimension(150, 30));
				submit.setBackground(L.LIGHT_BROWN);
				submit.setForeground(Color.BLACK);
			answerBox.add(submit, c);
			c.insets = new Insets(0, 0, 0, 0);
		}
		
		setLayout(new GridBagLayout());
			c.fill = GridBagConstraints.HORIZONTAL;
			
			c.gridy = 0;
			
			c.weightx = 1;
			c.insets = new Insets(0, 0, 0, 0);
		getContentPane().add(homeRow, c);
			c.fill = GridBagConstraints.NONE;
			
			c.gridy = 1;
			
			c.ipady = 20;
			
			c.weighty = 1;
		getContentPane().add(answerBox, c);
		getContentPane().setVisible(true);
		setBackground(L.DARK_BLUE);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
	}

	//public Question edit() - allows user to draw on a questionFile and returns a new png in the dropBox
	public void grade(Answer a, boolean teacher)
	{
		JFrame grade = new JFrame("Grade");
		grade.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		grade.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		grade.setVisible(true);
		int temp = Accounts.getUsernameIndex(a.getUser());
		String uID = Accounts.getID(temp);
		int index = Answers.findAnswer(uID, question.getID());
		
		JLabel label1 = new JLabel("" + a.getName());
			c.fill = GridBagConstraints.HORIZONTAL;
			
			c.weightx = 0.5; c.weighty = 0.5;
			label1.setBackground(L.LIGHT_BROWN); label1.setOpaque(true);
		grade.add(label1, c);
		JLabel label2 = new JLabel("Max Points: " + a.getMaxPoints());
			c.gridy = 1;
			label2.setBackground(L.LIGHT_BROWN); label2.setOpaque(true);
		grade.add(label2, c);
		
		Canvas ca = new Canvas() {
			public void paint(Graphics g)
			{
				Toolkit t = Toolkit.getDefaultToolkit();
				Image i = t.getImage(a.getPath() + ".png");
				g.drawImage(i, 50, 50, this);
			}
		};
			c.fill = GridBagConstraints.BOTH;
			
			c.gridy = 2;
		grade.add(ca, c);
		
		JPanel newGrade = new JPanel(); newGrade.setLayout(new GridLayout(2,1));
		JLabel newGradeLabel = new JLabel("Enter New Grade:");
		newGradeLabel.setBackground(L.LIGHT_BLUE); newGradeLabel.setOpaque(true);
		JTextField newGradeText;
		if(index >= 0)
		{
			int oldPoints = Answers.getPoints(index);
			if(oldPoints >= 0)
			{
				newGradeText = new JTextField("" + Answers.getPoints(index));
			}
			else if(oldPoints == -1)
			{
				newGradeText = new JTextField("Missing");
			}
			else
			{
				newGradeText = new JTextField("*");
			}
		}
		else
		{
			newGradeText = new JTextField("NG");
		}
		newGrade.add(newGradeLabel); newGrade.add(newGradeText);
			c.fill = GridBagConstraints.HORIZONTAL;
			
			c.gridy = 3;
		grade.add(newGrade, c);
		
		JPanel buttons = new JPanel(); buttons.setLayout(new GridBagLayout());
		buttons.setBackground(L.LIGHT_BROWN);
		JButton close = new JButton("Close");
		close.setPreferredSize(new Dimension(70, 20));
		close.setBackground(L.DARK_BROWN); close.setForeground(Color.WHITE);
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				grade.dispose();
			}
		});
		JButton finish = new JButton("Finish");
		finish.setPreferredSize(new Dimension(70, 20));
		finish.setBackground(L.DARK_BROWN); finish.setForeground(Color.WHITE);
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
						if(!Answers.isNewAnswer(index))
						{
							Answers.changeNewAnswer(index);
						}
					}
					else
					{
						Answers.addAnswer(uID, question.getID(), "", points);
					}
					
					ArrayList<String> ids = L.makeTeacherList();
					for(int i = 0; i < ids.size(); i++)
					{
						Accounts.updateAnswered(false, false, true, ids.get(i));
					}
					
					grade.dispose();
					new questionPage(question, ID, teacher).setVisible(true);
					dispose();
				}
				else
				{
					label1.setText("Wrong Values");
				}
			}
		});
			c.insets = new Insets(10, 10, 10, 10);
		buttons.add(close, c); buttons.add(finish, c);
			c.gridy = 4;
			
			c.insets = new Insets(0, 0, 0, 0);
		grade.add(buttons, c);
		
		grade.pack();
	}
	
	//submits a file to filePath. returns true if done, false if not
	public void submitFeedback(Answer a, boolean teacher)
	{
		JFrame submit = new JFrame("Submit Image");
		submit.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		submit.setBackground(L.LIGHT_BROWN);
		submit.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		submit.setVisible(true);
		int temp = Accounts.getUsernameIndex(a.getUser());
		String uID = Accounts.getID(temp);
		int index = Answers.findAnswer(uID, question.getID());
			
		JLabel label = new JLabel("" + a.getName());
			c.fill = GridBagConstraints.HORIZONTAL;
		
			c.weightx = 1;
			label.setBackground(L.LIGHT_BROWN); label.setOpaque(true);
		submit.add(label, c);
			
		JPanel filePath = new JPanel(); filePath.setLayout(new GridLayout(2,1));
		JLabel filePathLabel = new JLabel("Enter file path:");
		filePathLabel.setBackground(L.LIGHT_BLUE); filePathLabel.setOpaque(true);
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
			c.fill = GridBagConstraints.HORIZONTAL;
		
			c.gridy = 1;
		submit.add(filePath, c);
			
		JPanel buttons = new JPanel(); buttons.setLayout(new GridBagLayout());
		buttons.setBackground(L.LIGHT_BROWN);
		JButton close = new JButton("Close");
		close.setPreferredSize(new Dimension(70, 20));
		close.setBackground(L.DARK_BROWN); close.setForeground(Color.WHITE);
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				submit.dispose();
			}
		});
		JButton finish = new JButton("Finish");
		finish.setPreferredSize(new Dimension(70, 20));
		finish.setBackground(L.DARK_BROWN); finish.setForeground(Color.WHITE);
		finish.addActionListener(new ActionListener() {
				
			public void actionPerformed(ActionEvent e)
			{
				boolean isFilePath = !"".equals(filePathText.getText()) && filePathText.getText().length() <= 48;
					
				if(isFilePath)
				{
						
					if(index >= 0)
					{
						Answers.changeFilePath(index, filePathText.getText());
						if(!Answers.hasFeedback(index))
						{
							Answers.changeFeedback(index);
						}
						if(!Answers.isNewAnswer(index))
						{
							Answers.changeNewAnswer(index);
						}
					}
					else
					{
						Answers.addAnswer(uID, question.getID(), filePathText.getText());
					}
					Accounts.updateFeedback(false, true, false, uID);
					
					ArrayList<String> ids = L.makeTeacherList();
					for(int i = 0; i < ids.size(); i++)
					{
						Accounts.updateAnswered(false, false, true, ids.get(i));
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
			c.insets = new Insets(10, 10, 10, 10);
		buttons.add(close, c); buttons.add(finish, c);
			c.gridy = 2;
		
			c.insets = new Insets(0, 0, 0, 0);
		submit.add(buttons, c);
		
		submit.pack();
	}
	
	//submits a file to filePath. returns true if done, false if not
	public void submitWork(Answer a, boolean teacher)
	{
		JFrame submit = new JFrame("Submit Image");
		submit.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		submit.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		submit.setVisible(true);
		int index = Answers.findAnswer(ID, question.getID());
		
		JLabel label = new JLabel("" + a.getName());
			c.fill = GridBagConstraints.HORIZONTAL;
	
			c.weightx = 1;
			label.setBackground(L.LIGHT_BROWN); label.setOpaque(true);
		submit.add(label, c);
		
		JPanel filePath = new JPanel(); filePath.setLayout(new GridLayout(2,1));
		JLabel filePathLabel = new JLabel("Enter file path:");
		filePathLabel.setBackground(L.LIGHT_BLUE); filePathLabel.setOpaque(true);
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
			c.fill = GridBagConstraints.HORIZONTAL;
		
			c.gridy = 1;
		submit.add(filePath, c);
		
		JPanel buttons = new JPanel(); buttons.setLayout(new GridBagLayout());
		buttons.setBackground(L.LIGHT_BROWN);
		JButton close = new JButton("Close");
		close.setPreferredSize(new Dimension(70, 20));
		close.setBackground(L.DARK_BROWN); close.setForeground(Color.WHITE);
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				submit.dispose();
			}
		});
		JButton finish = new JButton("Finish");
		finish.setPreferredSize(new Dimension(70, 20));
		finish.setBackground(L.DARK_BROWN); finish.setForeground(Color.WHITE);
		finish.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e)
			{
				boolean isFilePath = !"".equals(filePathText.getText()) && filePathText.getText().length() <= 48;
				
				if(isFilePath)
				{
					if(index >= 0)
					{
						Answers.changeFilePath(index, filePathText.getText());
						if(Answers.hasFeedback(index))
						{
							Answers.changeFeedback(index);
							Accounts.updateFeedback(false, false, true, ID);
						}
						if(!Answers.isNewAnswer(index))
						{
							Answers.changeNewAnswer(index);
						}
					}
					else
					{
						Answers.addAnswer(ID, question.getID(), filePathText.getText());
						Accounts.updateUnanswered(ID);
					}
					
					ArrayList<String> ids = L.makeTeacherList();
					for(int i = 0; i < ids.size(); i++)
					{
						Accounts.updateAnswered(false, true, false, ids.get(i));
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
			c.insets = new Insets(10, 10, 10, 10);
		buttons.add(close, c); buttons.add(finish, c);
			c.gridy = 2;
			
			c.insets = new Insets(0, 0, 0, 0);
		submit.add(buttons, c);
		
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
				System.out.println(name);
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
