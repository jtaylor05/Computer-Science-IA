package pages;

import java.util.*;   
import database.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import library.L;

public class questionListPage extends JFrame
{
	private boolean teacher;
	private String ID;
	private ArrayList<Question> questionList;
	
	private JPanel homeRow = new JPanel();
	private JPanel scroller = new JPanel();
	
	private JButton home = new JButton("Go to menu");
	
	private boolean isEdit = false;
	
	private JPanel questions = new JPanel();
	private JScrollPane questionScroller = new JScrollPane(questions);
	
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
		
		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				dropInPage(teacher, ID);
			}
		});
		
		questionScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		questionScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		if(teacher)
		{
			questions.setLayout(new GridBagLayout());
			for(int i = 0; i < questionList.size(); i++)
			{
				
				final Question q = questionList.get(i);
				
				Canvas ca = new Canvas() {
					public void paint(Graphics g)
					{
						Toolkit t = Toolkit.getDefaultToolkit();
						Image i = t.getImage(q.getPath() + ".png");
						g.drawImage(i, 50, 50, this);
					}
				};
					c.fill = GridBagConstraints.HORIZONTAL;
					
					c.gridx = 0; c.gridy = i;
					
					c.weightx = 0.5;
				questions.add(ca, c);
				
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
					c.insets = new Insets(0, 0, 0, 20);
					jb.setPreferredSize(new Dimension(150, 30));
				questions.add(jb, c);
				
				JLabel jl = new JLabel();
				jl.setText("Out of " + q.getMaxPoints() + " points");
					c.gridx = 2;
					c.insets = new Insets(0, 0, 0, 0);
				questions.add(jl, c);
			}
			
			JButton add = new JButton("add");
			add.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					if(!isEdit) {addQuestion();}
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
					
				}
			});
			
			
			homeRow.setLayout(new GridBagLayout());
				c.fill = GridBagConstraints.NONE;
			
				c.gridx = 0;
			
				c.insets = new Insets(10, 0, 0, 0);
				edit.setPreferredSize(new Dimension(150, 30));
			homeRow.add(edit, c);
				c.gridx = 1;
				add.setPreferredSize(new Dimension(60, 30));
			homeRow.add(add, c);
				c.gridx = 3;
				home.setPreferredSize(new Dimension(120, 30));
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
				
				Canvas ca = new Canvas() {
					public void paint(Graphics g)
					{
						Toolkit t = Toolkit.getDefaultToolkit();
						Image i = t.getImage(q.getPath() + ".png");
						g.drawImage(i, 50, 50, this);
					}
				};
					c.fill = GridBagConstraints.HORIZONTAL;
				
					c.gridx = 0; c.gridy = i;
				
					c.weightx = 0.5;
				questions.add(ca, c);
				
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
					c.insets = new Insets(0, 0, 0, 20);
					jb.setPreferredSize(new Dimension(150, 30));
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
				else
				{
					jl.setText("Points: N/A");
				}
					c.gridx = 2;
					c.insets = new Insets(0, 0, 0, 0);
				questions.add(jl, c);
				
				JLabel message = new JLabel(q.getMessage());
					c.gridx = 3;
				questions.add(message, c);
			}
			
			homeRow.setLayout(new GridBagLayout());
				c.fill = GridBagConstraints.HORIZONTAL;
				
				c.gridx = 0;
				
				c.weightx = 1;
			homeRow.add(new JLabel(""), c); 
				c.fill = GridBagConstraints.NONE;
				
				c.gridx = 1;
				
				c.weightx = 0;
			homeRow.add(home, c);
		}
			questionScroller.setPreferredSize(new Dimension(400, 200));
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
	
	//closes current page and opens drop-in page
	public void dropInPage(boolean teacher, String ID)
	{
		new dropInPage(teacher, ID).setVisible(true);
		setVisible(false);
		dispose();
	}
	
	public void addQuestion()
	{
		JFrame add = new JFrame("Add Question");
		add.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		add.setLayout(new GridLayout(5,1));
		add.setVisible(true);
		
		JLabel label = new JLabel("Add a Question");
		add.add(label);
		
		JPanel name = new JPanel(); name.setLayout(new GridLayout(2,1));
		JLabel nameLabel = new JLabel("Enter name:");
		JTextField nameText = new JTextField();
		nameText.setPreferredSize(new Dimension(200, 30));
		name.add(nameLabel); name.add(nameText);
		add.add(name);
		
		JPanel filePath = new JPanel(); filePath.setLayout(new GridLayout(2,1));
		JLabel filePathLabel = new JLabel("Enter file path:");
		JTextField filePathText = new JTextField();
		filePathText.setPreferredSize(new Dimension(200, 30));
		filePath.add(filePathLabel); filePath.add(filePathText);
		add.add(filePath);
		
		JPanel maxPoints = new JPanel(); maxPoints.setLayout(new GridLayout(2,1));
		JLabel maxPointsLabel = new JLabel("Enter max points:");
		JTextField maxPointsText = new JTextField();
		maxPointsText.setPreferredSize(new Dimension(200, 30));
		maxPoints.add(maxPointsLabel); maxPoints.add(maxPointsText);
		add.add(maxPoints);
		
		JPanel buttons = new JPanel(); buttons.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JButton close = new JButton("Close");
		close.setPreferredSize(new Dimension(70, 20));
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				add.dispose();
			}
		});
		JButton finish = new JButton("Add");
		finish.setPreferredSize(new Dimension(70, 20));
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
			c.insets = new Insets(10, 10, 10, 10);
		buttons.add(close, c); buttons.add(finish, c);
		add.add(buttons);
		
		add.pack();
	}
	
	public void editQuestion(Question q)
	{
		JFrame edit = new JFrame("Edit Question");
		edit.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		edit.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		edit.setVisible(true);
		int index = Questions.getIDIndex(q.getID());
		
		JLabel label = new JLabel("Edit " + q.getName());
			c.fill = GridBagConstraints.HORIZONTAL;
			
			c.weighty = 1;
		edit.add(label, c);
		
		JPanel name = new JPanel(); name.setLayout(new GridLayout(2,1));
		JLabel nameLabel = new JLabel("Enter name:");
		JTextField nameText = new JTextField(q.getName());
		name.add(nameLabel); name.add(nameText);
			c.gridy = 1;
		edit.add(name, c);
		
		JPanel filePath = new JPanel(); filePath.setLayout(new GridLayout(2,1));
		JLabel filePathLabel = new JLabel("Enter file path:");
		JTextField filePathText = new JTextField(L.shear(Questions.getFilePath(index)));
		filePath.add(filePathLabel); filePath.add(filePathText);
			c.gridy = 2;
		edit.add(filePath, c);
		
		JPanel maxPoints = new JPanel(); maxPoints.setLayout(new GridLayout(2,1));
		JLabel maxPointsLabel = new JLabel("Enter max points:");
		JTextField maxPointsText = new JTextField("" + q.getMaxPoints());
		maxPoints.add(maxPointsLabel); maxPoints.add(maxPointsText);
			c.gridy = 3;
		edit.add(maxPoints, c);
		
		JPanel buttons = new JPanel(); buttons.setLayout(new GridBagLayout());
		JButton close = new JButton("Close");
		close.setPreferredSize(new Dimension(70, 20));
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				edit.dispose();
			}
		});
		JButton remove = new JButton("Remove");
		remove.setPreferredSize(new Dimension(90, 20));
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
		finish.setPreferredSize(new Dimension(70, 20));
		finish.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e)
			{
				boolean isName = !"".equals(nameText.getText()) && nameText.getText().length() <= 23;
				boolean isFilePath = !"".equals(filePathText.getText()) && filePathText.getText().length() <= 48;
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
	
	//makes a list of questions as above, but includes userID's answers if there is one
	public ArrayList<Question> makeList(String userID)
	{
		ArrayList<Question> questions = new ArrayList<>();
		
		int index = 0;
		String QID = Questions.getID(index);
		while(QID != null)
		{
			int answerIndex = Answers.findAnswer(userID, QID);
			int points = -2;
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
			if(points > -2)
			{
				q = new Question(QID, name, filePath, points, maxPoints, message);
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
	
	//makes a list of all questions, their IDs, names, and max points
	public ArrayList<Question> makeList()
	{
		ArrayList<Question> questions = new ArrayList<>();
			
		int index = 0;
		String QID = Questions.getID(index);
			
		while(QID != null)
		{
			String name = Questions.getName(index);
			int maxPoints = Questions.getPoints(index);
				
			Question q = new Question(QID, name, maxPoints);
				
			questions.add(q);
				
			index = index + 1;
			QID = Questions.getID(index);
		}
		
		questions = sortList(questions);
		
		return questions;
	}
	
	public ArrayList<Question> sortList(ArrayList<Question> list)
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
