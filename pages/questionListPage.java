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
		
		questionScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		questionScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		if(teacher)
		{
			questions.setLayout(new GridLayout(questionList.size() + 1, 1));
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
				jp.add(jb);
				
				JLabel jl = new JLabel();
				jl.setText("Out of " + q.getPoints() + " points");
				jp.add(jl);
				
				questions.add(jp);
			}
			
			JButton add = new JButton("add");
			add.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					if(!isEdit) {addQuestion();}
				}
			});
			questions.add(add);
			
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
						add.setText("Stop Editing to Add");
					}
					
				}
			});
			
			
			homeRow.setLayout(new GridLayout(1, 3));
			homeRow.add(edit); homeRow.add(new JLabel("")); homeRow.add(home);
		}
		else
		{
			questions.setLayout(new GridLayout(questionList.size(), 1));
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
						new questionPage(q, ID, teacher).setVisible(true);
						setVisible(false);
						dispose();
					}
				});
				jp.add(jb);
				
				JLabel jl = new JLabel();
				if(q.hasAnswered())
				{
					jl.setText("Points: " + q.getOutOf());
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
			
			homeRow.setLayout(new GridLayout(1, 2));
			homeRow.add(new JLabel("")); homeRow.add(home);
		}
		scroller.add(questionScroller);
		
		setLayout(new GridLayout(2, 1));
		getContentPane().add(homeRow);
		getContentPane().add(scroller);
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
		name.add(nameLabel); name.add(nameText);
		add.add(name);
		
		JPanel filePath = new JPanel(); filePath.setLayout(new GridLayout(2,1));
		JLabel filePathLabel = new JLabel("Enter file path:");
		JTextField filePathText = new JTextField();
		filePath.add(filePathLabel); filePath.add(filePathText);
		add.add(filePath);
		
		JPanel maxPoints = new JPanel(); maxPoints.setLayout(new GridLayout(2,1));
		JLabel maxPointsLabel = new JLabel("Enter max points:");
		JTextField maxPointsText = new JTextField();
		maxPoints.add(maxPointsLabel); maxPoints.add(maxPointsText);
		add.add(maxPoints);
		
		JPanel buttons = new JPanel(); buttons.setLayout(new GridLayout(1, 2));
		JButton close = new JButton("Close");
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		JButton jb = new JButton("Add");
		jb.addActionListener(new ActionListener() {
			
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
		buttons.add(close); buttons.add(jb);
		add.add(buttons);
		
		add.pack();
	}
	
	public void editQuestion(Question q)
	{
		JFrame edit = new JFrame("Edit Question");
		edit.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		edit.setLayout(new GridLayout(5,1));
		edit.setVisible(true);
		int index = Questions.getIDIndex(q.getID());
		
		JLabel label = new JLabel("Edit " + q.getName());
		edit.add(label);
		
		JPanel name = new JPanel(); name.setLayout(new GridLayout(2,1));
		JLabel nameLabel = new JLabel("Enter name:");
		JTextField nameText = new JTextField(q.getName());
		name.add(nameLabel); name.add(nameText);
		edit.add(name);
		
		JPanel filePath = new JPanel(); filePath.setLayout(new GridLayout(2,1));
		JLabel filePathLabel = new JLabel("Enter file path:");
		JTextField filePathText = new JTextField(L.shear(Questions.getFilePath(index)));
		filePath.add(filePathLabel); filePath.add(filePathText);
		edit.add(filePath);
		
		JPanel maxPoints = new JPanel(); maxPoints.setLayout(new GridLayout(2,1));
		JLabel maxPointsLabel = new JLabel("Enter max points:");
		JTextField maxPointsText = new JTextField("" + q.getPoints());
		maxPoints.add(maxPointsLabel); maxPoints.add(maxPointsText);
		edit.add(maxPoints);
		
		JPanel buttons = new JPanel(); buttons.setLayout(new GridLayout(1, 3));
		JButton close = new JButton("Close");
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				edit.dispose();
			}
		});
		JButton remove = new JButton("Remove");
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
		buttons.add(close); buttons.add(remove); buttons.add(finish);
		edit.add(buttons);
		
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
			
		return questions;
	}
}
