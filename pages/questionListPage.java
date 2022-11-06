package pages;

import java.util.*;    
import database.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.AttributeSet.ColorAttribute;

import java.awt.event.*;
import java.io.*;
import java.awt.*;
import java.awt.image.*;
import library.L;

public class questionListPage extends JFrame
{
	private boolean teacher;
	private String ID;
	private LinkedList<Question> questionList;
	
	private JPanel homeRow = new JPanel();
	private JPanel scroller = new JPanel();
	
	private JButton home = new JButton("Go to menu");
	
	private boolean isEdit = false;
	
	private JPanel questions = new JPanel();
	private JScrollPane questionScroller = new JScrollPane(questions);
	
	//instantiates questionListPage Frame
	public questionListPage(boolean isTeacher, String ID)
	{
		GridBagConstraints c = new GridBagConstraints();
		
		teacher = isTeacher;
		this.ID = ID;
		
		if(teacher)
		{
			questionList = makeList();
		}
		else
		{
			questionList = makeList(ID);
		}
		
		homeRow.setBackground(L.LIGHT_BROWN);
		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				dropInPage(teacher, ID);
			}
		});
		
		questions.setBackground(L.LIGHT_BLUE);
		scroller.setBackground(L.DARK_BLUE);
		questionScroller.setBackground(L.DARK_BLUE);
		questionScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		questionScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		if(teacher)
		{
			questions.setLayout(new GridBagLayout());
			for(int i = 0; i < questionList.size(); i++)
			{
				
				final Question q = questionList.get(i);
				
				JPanel image = new JPanel() {
					protected void paintComponent(Graphics g)
					{
						super.paintComponent(g);
						
						Image img = null;
						try
						{
							img = ImageIO.read(new File(q.getPath())).getScaledInstance(160, 200, Image.SCALE_DEFAULT);
						}
						catch(Exception e) {}
						
						g.drawImage(img, 2, 2, this);
					}
				};
				image.setPreferredSize(new Dimension(160, 200));
					c.fill = GridBagConstraints.HORIZONTAL;
					
					c.gridx = 0; c.gridy = i;
					
					c.weightx = 0.5;
					
					c.insets = new Insets(0, 0, 10, 0);
				questions.add(image, c);
				
				JButton jb = new JButton(q.getName());
				jb.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e)
					{
						if(isEdit)
						{
							editQuestion(q);
						}
						else
						{
							new questionPage(q, ID, teacher).setVisible(true);
							setVisible(false);
							dispose();
						}
					}
				});
					c.fill = GridBagConstraints.NONE;
					
					c.gridx = 1;
					
					c.weightx = 0;
					jb.setPreferredSize(new Dimension(150, 30));
					jb.setBackground(L.LIGHT_BROWN);
					jb.setForeground(Color.BLACK);
				questions.add(jb, c);
				
				JLabel jl = new JLabel();
				jl.setText("Out of " + q.getMaxPoints() + " points");
					c.gridx = 2;
					c.insets = new Insets(0, 0, 0, 0);
				questions.add(jl, c);
			}
			
			JButton add = new JButton("Add");
			add.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					addQuestion();
				}
			});
			
			JButton edit = new JButton("Edit a Question");
			edit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					if(isEdit)
					{
						isEdit = false;
						edit.setText("Edit a Question");
						add.setText("Add");
					}
					else
					{
						isEdit = true;
						edit.setText("Stop Editing");
						add.setText("X");
					}
					add.setEnabled(!isEdit);
				}
			});
			
			
			homeRow.setLayout(new GridBagLayout());
				c.fill = GridBagConstraints.NONE;
			
				c.gridx = 0;
			
				c.insets = new Insets(3, 3, 3, 3);
				edit.setPreferredSize(new Dimension(150, 30));
				edit.setBackground(L.DARK_BROWN);
				edit.setForeground(Color.WHITE);
			homeRow.add(edit, c);
				c.gridx = 1;
				add.setPreferredSize(new Dimension(60, 30));
				add.setBackground(L.DARK_BROWN);
				add.setForeground(Color.WHITE);
			homeRow.add(add, c);
				c.gridx = 3;
				home.setPreferredSize(new Dimension(120, 30));
				home.setBackground(L.DARK_BROWN);
				home.setForeground(Color.WHITE);
			homeRow.add(home, c);
				c.fill = GridBagConstraints.HORIZONTAL;
				
				c.gridx = 2;
				
				c.weightx = 0.5;
				
				c.insets = new Insets(0, 0, 0, 0);
			homeRow.add(new JLabel(""), c); 
		}
		else
		{
			questions.setLayout(new GridBagLayout());
			for(int i = 0; i < questionList.size(); i++)
			{
				
				final Question q = questionList.get(i);
				
				JPanel image = new JPanel() {
					protected void paintComponent(Graphics g)
					{
						super.paintComponent(g);
						
						Image img = null;
						try
						{
							img = ImageIO.read(new File(q.getPath())).getScaledInstance(160, 200, Image.SCALE_DEFAULT);
						}
						catch(Exception e) {}
						
						g.drawImage(img, 2, 2, this);
					}
				};
				image.setPreferredSize(new Dimension(160, 200));
					c.fill = GridBagConstraints.HORIZONTAL;
					
					c.gridx = 0; c.gridy = i;
					
					c.weightx = 0.5;
					
					c.insets = new Insets(0, 0, 10, 0);
				questions.add(image, c);
				
				JButton jb = new JButton(questionList.get(i).getName());
				jb.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new questionPage(q, ID, teacher).setVisible(true);
						setVisible(false);
						dispose();
					}
				});
					c.fill = GridBagConstraints.NONE;
				
					c.gridx = 1;
				
					c.weightx = 0;
					jb.setPreferredSize(new Dimension(150, 30));
					jb.setBackground(L.LIGHT_BROWN);
					jb.setForeground(Color.BLACK);
				questions.add(jb, c);
				
				JLabel jl = new JLabel();
				if(q.hasAnswer() && q.getGrade() > -1)
				{
					jl.setText("Points: " + q.getOutOf());
				}
				else if(q.hasAnswer() && q.getGrade() == -1)
				{
					jl.setText("Points: Missing");
				}
				else if(q.hasAnswer() && q.getGrade() == -2)
				{
					jl.setText("Points: *");
				}
				else
				{
					jl.setText("Points: N/A");
				}
					c.gridx = 2;
					c.insets = new Insets(0, 0, 0, 0);
				questions.add(jl, c);
			}
			
			homeRow.setLayout(new GridBagLayout());
				c.fill = GridBagConstraints.HORIZONTAL;
				
				c.gridx = 0;
				
				c.weightx = 1;
			homeRow.add(new JLabel(""), c); 
				c.fill = GridBagConstraints.NONE;
				
				c.gridx = 1;
				
				c.weightx = 0;
				c.insets = new Insets(3, 3, 3, 3);
				home.setPreferredSize(new Dimension(120, 30));
				home.setBackground(L.DARK_BROWN);
				home.setForeground(Color.WHITE);
			homeRow.add(home, c);
				c.insets = new Insets(0, 0, 0, 0);
		}
			questionScroller.setPreferredSize(new Dimension(450, 200));
		scroller.add(questionScroller, c);
			
		setLayout(new GridBagLayout());
			c.fill = GridBagConstraints.HORIZONTAL;
			
			c.gridx = 0; c.gridy = 0;
			
			c.weightx = 1;
		getContentPane().add(homeRow, c);
			c.fill = GridBagConstraints.BOTH;
			
			c.gridy = 1;
			
			c.weighty = 1;
		getContentPane().add(scroller, c);
		getContentPane().setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
	}
	
	//disposes questionListPage and opens drop-in page
	public void dropInPage(boolean teacher, String ID)
	{
		new dropInPage(teacher, ID).setVisible(true);
		setVisible(false);
		dispose();
	}
	
	//Opens a pop-up that takes information for a new question
	public void addQuestion()
	{
		JFrame add = new JFrame("Add Question");
		add.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		add.setLayout(new GridLayout(5,1));
		add.setVisible(true);
		
		JLabel label = new JLabel("Add a Question");
		label.setHorizontalAlignment(WIDTH/2);
		label.setBackground(L.LIGHT_BROWN); label.setOpaque(true);
		add.add(label);
		
		JPanel name = new JPanel(); name.setLayout(new GridLayout(2,1));
		JLabel nameLabel = new JLabel("Enter name:");
		nameLabel.setBackground(L.LIGHT_BLUE); nameLabel.setOpaque(true);
		JTextField nameText = new JTextField();
		nameText.setPreferredSize(new Dimension(200, 30));
		name.add(nameLabel); name.add(nameText);
		add.add(name);
		
		JPanel filePath = new JPanel(); filePath.setLayout(new GridLayout(2,1));
		JLabel filePathLabel = new JLabel("Enter file path:");
		filePathLabel.setBackground(L.LIGHT_BLUE); filePathLabel.setOpaque(true);
		JTextField filePathText = new JTextField();
		filePathText.setPreferredSize(new Dimension(200, 30));
		filePath.add(filePathLabel); filePath.add(filePathText);
		add.add(filePath);
		
		JPanel maxPoints = new JPanel(); maxPoints.setLayout(new GridLayout(2,1));
		JLabel maxPointsLabel = new JLabel("Enter max points:");
		maxPointsLabel.setBackground(L.LIGHT_BLUE); maxPointsLabel.setOpaque(true);
		JTextField maxPointsText = new JTextField();
		maxPointsText.setPreferredSize(new Dimension(200, 30));
		maxPoints.add(maxPointsLabel); maxPoints.add(maxPointsText);
		add.add(maxPoints);
		
		JPanel buttons = new JPanel(); buttons.setLayout(new GridBagLayout());
		buttons.setBackground(L.LIGHT_BROWN);
		GridBagConstraints c = new GridBagConstraints();
		JButton close = new JButton("Close");
		close.setPreferredSize(new Dimension(70, 20));
		close.setBackground(L.DARK_BROWN); close.setForeground(Color.WHITE);
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				add.dispose();
			}
		});
		JButton finish = new JButton("Add");
		finish.setPreferredSize(new Dimension(70, 20));
		finish.setBackground(L.DARK_BROWN); finish.setForeground(Color.WHITE);
		finish.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e)
			{
				boolean isName = !"".equals(nameText.getText());
				boolean isFilePath = !"".equals(filePathText.getText());
				int maxPoints; 
				try
				{
					maxPoints = Integer.parseInt(maxPointsText.getText());
				}
				catch(Exception ex)
				{
					maxPoints = -1;
				}
				
				if(isName && isFilePath && maxPoints > 0)
				{
					Questions.addQuestion(nameText.getText(), filePathText.getText(), maxPoints);
					add.dispose();
					new questionListPage(teacher, ID).setVisible(true);
					dispose();
				}
				else
				{
					label.setText("Wrong Values");
				}
			}
		});
			c.insets = new Insets(5, 5, 5, 5);
		buttons.add(close, c); buttons.add(finish, c);
		add.add(buttons);
		
		add.pack();
	}
	
	//opens a pop-up page including information of a current question. All alterations to data are saved 
	//to the database
	public void editQuestion(Question q)
	{
		JFrame edit = new JFrame("Edit Question");
		edit.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		edit.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		edit.setVisible(true);
		int index = Questions.getIDIndex(q.getID());
		
		JLabel label = new JLabel("Edit " + q.getName());
		label.setHorizontalTextPosition(WIDTH/2);
			c.fill = GridBagConstraints.HORIZONTAL;
			
			c.weighty = 1;
			label.setBackground(L.LIGHT_BROWN); label.setOpaque(true);
		edit.add(label, c);
		
		JPanel name = new JPanel(); name.setLayout(new GridLayout(2,1));
		JLabel nameLabel = new JLabel("Enter name:");
		nameLabel.setBackground(L.LIGHT_BLUE); nameLabel.setOpaque(true);
		JTextField nameText = new JTextField(q.getName());
		name.add(nameLabel); name.add(nameText);
			c.gridy = 1;
		edit.add(name, c);
		
		JPanel filePath = new JPanel(); filePath.setLayout(new GridLayout(2,1));
		JLabel filePathLabel = new JLabel("Enter file path:");
		filePathLabel.setBackground(L.LIGHT_BLUE); filePathLabel.setOpaque(true);
		JTextField filePathText = new JTextField(L.shear(Questions.getFilePath(index)));
		filePath.add(filePathLabel); filePath.add(filePathText);
			c.gridy = 2;
		edit.add(filePath, c);
		
		JPanel maxPoints = new JPanel(); maxPoints.setLayout(new GridLayout(2,1));
		JLabel maxPointsLabel = new JLabel("Enter max points:");
		maxPointsLabel.setBackground(L.LIGHT_BLUE); maxPointsLabel.setOpaque(true);
		JTextField maxPointsText = new JTextField("" + q.getMaxPoints());
		maxPoints.add(maxPointsLabel); maxPoints.add(maxPointsText);
			c.gridy = 3;
		edit.add(maxPoints, c);
		
		JPanel buttons = new JPanel(); buttons.setLayout(new GridBagLayout());
		buttons.setBackground(L.LIGHT_BROWN);
		JButton close = new JButton("Close");
		close.setPreferredSize(new Dimension(70, 20));
		close.setBackground(L.DARK_BROWN); close.setForeground(Color.WHITE);
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				edit.dispose();
			}
		});
		JButton remove = new JButton("Remove");
		remove.setPreferredSize(new Dimension(90, 20));
		remove.setBackground(L.DARK_BROWN); remove.setForeground(Color.WHITE);
		remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				Questions.removeQuestion(q.getID());
				edit.dispose();
				new questionListPage(teacher, ID).setVisible(true);
				dispose();
			}
		});
		JButton finish = new JButton("Finish");
		finish.setBackground(L.DARK_BROWN); finish.setForeground(Color.WHITE);
		finish.setPreferredSize(new Dimension(70, 20));
		finish.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e)
			{
				boolean isName = !"".equals(nameText.getText()) && nameText.getText().length() <= 23;
				boolean isFilePath = !"".equals(filePathText.getText()) && filePathText.getText().length() <= 80;
				int maxPoints; 
				try
				{
					maxPoints = Integer.parseInt(maxPointsText.getText());
				}
				catch(Exception ex)
				{
					maxPoints = -1;
				}
				
				if(isName && isFilePath && maxPoints > 0)
				{
					Questions.replaceQuestion(nameText.getText(), filePathText.getText(), maxPoints, q.getID());
					edit.dispose();
					new questionListPage(teacher, ID).setVisible(true);
					dispose();
				}
				else
				{
					label.setText("Wrong Values");
				}
			}
		});
			c.insets = new Insets(10, 10, 10, 10);
		buttons.add(close, c); buttons.add(remove, c); buttons.add(finish, c);
			c.gridy = 4;
			
			c.insets = new Insets(0, 0, 0, 0);
		edit.add(buttons, c);
		
		edit.pack();
	}
	
	//makes lists of all questions with grade of particular user
	public LinkedList<Question> makeList(String userID)
	{
		LinkedList<Question> questions = new LinkedList<>();
		
		int index = 0;
		String QID = Questions.getID(index);
		while(QID != null)
		{
			int answerIndex = Answers.findAnswer(userID, QID);
			int points = -3;
			if(answerIndex > -1)
			{
				points = Answers.getPoints(answerIndex);
			}
			
			String name = Questions.getName(index);
			name = L.shear(name);
			String filePath = Questions.getFilePath(index);
			int maxPoints = Questions.getPoints(index);
			Question q = null;
			q = new Question(QID, name, filePath, points, maxPoints);
						
			questions.add(q);
			
			index = index + 1;
			QID = Questions.getID(index);
		}
		
		questions = sortList(questions);
		
		return questions;
	}
	
	//makes a list of all questions, their IDs, names, and max points
	public LinkedList<Question> makeList()
	{
		LinkedList<Question> questions = new LinkedList<>();
			
		int index = 0;
		String QID = Questions.getID(index);
			
		while(QID != null)
		{
			String name = Questions.getName(index);
			int maxPoints = Questions.getPoints(index);
			String filePath = Questions.getFilePath(index);
			
			Question q = new Question(QID, name, filePath, maxPoints);
			questions.add(q);
				
			index = index + 1;
			QID = Questions.getID(index);
		}
		
		questions = sortList(questions);
		
		return questions;
	}
	
	//sorts a list of questions alphabetically by their name
	public LinkedList<Question> sortList(LinkedList<Question> list)
	{
		for(int i = 0; i < list.size(); i++)
		{
			for(int j = i; j > 0; j--)
			{
				Question first = list.get(j);
				Question second = list.get(j - 1);
				if(first.getName().compareTo(second.getName()) < 0) 
				{
					Question temp = list.get(j);
					list.set(j, list.get(j - 1));
					list.set(j - 1, temp);
				}		
			}
		}
		return list;
	}
}
