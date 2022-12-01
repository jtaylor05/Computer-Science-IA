package pages;

import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.transform.Templates;

import java.awt.event.*;
import java.io.File;
import java.awt.*;
import database.*;
import library.L;

/**
 * Page holding all relevant details for a singular question. Navigates to 
 * questionListPage.
 */
public class questionPage extends JFrame
{
	private Question question;
	private Answer answer;
	private LinkedList<Answer> answers = new LinkedList<>();
	private boolean teacher;
	private String ID;
	
	private JPanel homeRow = new JPanel();
	
	private JButton home = new JButton("Go to questions");
	
	private JPanel answerBox = new JPanel();
	private JScrollPane answerScroller;
	
	/**
	 * @param q relevant question
	 * @param ID String ID of user currently logged in
	 * @param isTeacher boolean value of whether user is a teacher
	 */
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
				
				JPanel image = new JPanel() {
					protected void paintComponent(Graphics g)
					{
						super.paintComponent(g);
						
						Image img = null;
						try
						{
							img = ImageIO.read(new File(a.getPath())).getScaledInstance(340, 240, Image.SCALE_DEFAULT);
						}
						catch(Exception e) {}
						
						g.drawImage(img, 2, 2, this);
					}
				};
				image.setPreferredSize(new Dimension(340, 240));
					c.fill = GridBagConstraints.HORIZONTAL;
					
					c.gridx = 0; c.gridy = i;
					
					c.weightx = 0.5;
					
					c.insets = new Insets(10, 10, 10, 10);
				answerBox.add(image, c);
				
				JLabel user = new JLabel(a.getUser());
					c.fill = GridBagConstraints.NONE;
				
					c.gridx = 1;
				
					c.weightx = 0;
					
					c.insets = new Insets(0, 10, 0, 10);
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
						grade(a);
					}
				});
					c.gridx = 3;
					
					c.insets = new Insets(8, 8, 8, 8);
					edit.setPreferredSize(new Dimension(130, 30));
					edit.setBackground(L.LIGHT_BROWN);
					edit.setForeground(Color.BLACK);
				answerBox.add(edit, c);
				
				JButton submit = new JButton("Give Feedback");
				submit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e)
					{
						submitFeedback(a);
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
			
			JPanel image = new JPanel() {
				protected void paintComponent(Graphics g)
				{
					super.paintComponent(g);
					
					Image img = null;
					try
					{
						img = ImageIO.read(new File(answer.getPath())).getScaledInstance(340, 240, Image.SCALE_DEFAULT);
					}
					catch(Exception e) {}
					
					g.drawImage(img, 2, 2, this);
				}
			};
			image.setPreferredSize(new Dimension(340, 240));
				c.fill = GridBagConstraints.HORIZONTAL;
				
				c.gridx = 0;
				
				c.weightx = 0.5;
				
				c.insets = new Insets(10, 10, 10, 10);
			answerBox.add(image, c);
			
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
				c.insets = new Insets(0, 10, 0, 10);
				grade.setPreferredSize(new Dimension(150, 30));
			answerBox.add(grade, c);
			
			JButton submit = new JButton("Submit");
			submit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					submitWork(answer);
				}
			});
				c.gridx = 2;
				
				c.insets = new Insets(8, 8, 8, 8);
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

	/**
	 * @param a Answer that is being edited and graded
	 */
	public void grade(Answer a)
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
			
			c.ipady = 20;
			label2.setBackground(L.LIGHT_BROWN); label2.setOpaque(true);
		grade.add(label2, c);
			c.ipady = 0;
		
		JPanel image = new JPanel() {
			protected void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				
				Image img = null;
				try
				{
					img = ImageIO.read(new File(a.getPath())).getScaledInstance(340, 240, Image.SCALE_DEFAULT);
				}
				catch(Exception e) {}
				
				g.drawImage(img, 2, 2, this);
			}
		};
		image.setPreferredSize(new Dimension(340, 240));
			c.fill = GridBagConstraints.BOTH;
			
			c.gridy = 2;
		grade.add(image, c);
		
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
			
			c.ipady = 20;
			c.ipadx = 20;
		grade.add(newGrade, c);
			c.ipady = 0;
			c.ipadx = 0;
		
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
				boolean isGrade = !"".equals(newGradeText.getText());
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
						if(Answers.isNewAnswer(index))
						{
							Answers.changeNewAnswer(index);
						}
					}
					else
					{
						Answers.add(uID, question.getID(), "", points);
						Answers.changeNewAnswer(index);
						Answers.changeFeedback(index);
					}
					
					LinkedList<String> ids = L.makeTeacherList();
					for(int i = 0; i < ids.size(); i++)
					{
						if(!Answers.isNewAnswer(index))
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
	
	/**
	 * @param a Answer to get edited and get new image file path
	 */
	public void submitFeedback(Answer a)
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
				boolean isFilePath = !"".equals(filePathText.getText()) && filePathText.getText().length() <= 80;
					
				if(isFilePath)
				{
						
					if(index >= 0)
					{
						Answers.changeFilePath(index, filePathText.getText());
						if(!Answers.hasFeedback(index))
						{
							Answers.changeFeedback(index);
						}
						if(Answers.isNewAnswer(index))
						{
							Answers.changeNewAnswer(index);
						}
					}
					else
					{
						Answers.add(uID, question.getID(), filePathText.getText());
						Answers.changeNewAnswer(index);
						Answers.changeFeedback(index);
					}
					Accounts.updateFeedback(false, true, false, uID);
					
					LinkedList<String> ids = L.makeTeacherList();
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
	
	/**
	 * @param a Answer to gain initial image submission for students
	 */
	public void submitWork(Answer a)
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
				boolean isFilePath = !"".equals(filePathText.getText()) && filePathText.getText().length() <= 80;
				
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
						Answers.add(ID, question.getID(), filePathText.getText());
						Accounts.updateUnanswered(ID);
					}
					
					LinkedList<String> ids = L.makeTeacherList();
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
	
	/**
	 * Opens a new questionListPage
	 */
	public void questionList()
	{
		new questionListPage(teacher, ID).setVisible(true);
		setVisible(false);
		dispose();
	}
	
	/**
	 * @return LinkedList of all student user answers 
	 */
	public LinkedList<Answer> makeList()
	{
		LinkedList<Answer> as = new LinkedList<>();
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
	
	/**
	 * @param list LinkedList to be sorted
	 * @return LinkedList sorted alphabetically by username
	 */
	public LinkedList<Answer> sortList(LinkedList<Answer> list)
	{
		for(int i = 0; i < list.size(); i++)
		{
			for(int j = i; j > 0; j--)
			{
				Answer first = list.get(j);
				Answer second = list.get(j - 1);
				if(first.getName().compareTo(second.getName()) < 0) 
				{
					Answer temp = list.get(j);
					list.set(j, list.get(j - 1));
					list.set(j - 1, temp);
				}		
			}
		}
		return list;
	}
}
