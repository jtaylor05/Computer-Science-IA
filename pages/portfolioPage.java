package pages;

import java.util.*; 
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import database.*;

public class portfolioPage extends JFrame
{
	boolean teacher;
	private JPanel homeRow = new JPanel();
	private JPanel portfolioBox = new JPanel();
	
	private JButton home = new JButton("");
	
	private final Font studentFont = new Font("Arial", Font.BOLD, 20);
	private final Font questionFont = new Font(Font.MONOSPACED, Font.PLAIN, 15);
	
	private ArrayList<Answer> as;

	public portfolioPage(boolean teacher, String ID)
	{
		this.teacher = teacher;
		
		home.setText("Go to menu");
		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				dropInPage(teacher, ID);
			}	
		});
		homeRow.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.HORIZONTAL;
			
			c.weightx = 1;
		homeRow.add(new JLabel(""), c); 
			c.fill = GridBagConstraints.NONE;
			
			c.gridx = 1;
			
			c.weightx = 0;
			home.setPreferredSize(new Dimension(110, 30));
		homeRow.add(home, c);
		
		as = makeList(ID);
		int index = Accounts.getIDIndex(ID);
		
		portfolioBox.setLayout(new GridLayout(as.size() + 1, 1));
		JLabel student = new JLabel("STUDENT: " + Accounts.getUsername(index));
		student.setFont(studentFont);
		portfolioBox.add(student);
		for(int i = 0; i < as.size(); i++)
		{
			JLabel jl = new JLabel("" + as.get(i));
			jl.setFont(questionFont);
			portfolioBox.add(jl);
		}
		
		setLayout(new GridBagLayout());
			c.fill = GridBagConstraints.HORIZONTAL;
			
			c.gridx = 0; c.gridy = 0;
			
			c.weightx = 1;
		add(homeRow, c);
			c.fill = GridBagConstraints.VERTICAL;
			
			c.gridy = 1;
			
			c.weighty = 1;
		add(portfolioBox, c);
		setVisible(true);
		pack();
	}
	
	public portfolioPage(boolean teacher, String ID, String studentID)
	{
		this.teacher = teacher;
		
		home.setText("Go to portfolios");
		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				portfolioListPage(teacher, ID);
			}	
		});
		homeRow.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.HORIZONTAL;
			
			c.weightx = 1;
		homeRow.add(new JLabel(""), c); 
			c.fill = GridBagConstraints.NONE;
			
			c.gridx = 1;
			
			c.weightx = 0;
			home.setPreferredSize(new Dimension(130, 30));
		homeRow.add(home, c);
		
		as = makeList(studentID);
		int index = Accounts.getIDIndex(studentID);
		
		portfolioBox.setLayout(new GridBagLayout());
		JLabel student = new JLabel("STUDENT: " + Accounts.getUsername(index));
		student.setFont(studentFont);
			c.fill = GridBagConstraints.NONE;
			
			c.gridx = 0; c.gridy = 0;
			
			c.insets = new Insets(0, 0, 30, 0);
		portfolioBox.add(student, c);
			c.insets = new Insets(0, 0, 0, 0);
		for(int i = 0; i < as.size(); i++)
		{
			JLabel jl = new JLabel("" + as.get(i));
			jl.setFont(questionFont);
				c.gridy = i + 1;
			portfolioBox.add(jl, c);
		}
		
		setLayout(new GridBagLayout());
			c.fill = GridBagConstraints.HORIZONTAL;
		
			c.gridx = 0; c.gridy = 0;
		
			c.weightx = 1;
		add(homeRow, c);
			c.fill = GridBagConstraints.NONE;
		
			c.gridy = 1;
			
			c.weighty = 0;
			c.insets = new Insets(30, 0, 0, 0);
		add(portfolioBox, c);
			c.fill = GridBagConstraints.VERTICAL;
			
			c.gridy = 2;
			
			c.weighty = 1;
			c.insets = new Insets(0, 0, 0, 0);
		add(new JLabel(""), c);
		getContentPane().setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
	}
	
	//opens a dropInPage, closes current page
	public void dropInPage(boolean teacher, String ID)
	{
		new dropInPage(teacher, ID).setVisible(true);
		setVisible(false);
		dispose();
	}
	
	//opens a portfolioListPage, closes current page
	public void portfolioListPage(boolean teacher, String ID)
	{
		new portfolioListPage(teacher, ID).setVisible(true);
		setVisible(false);
		dispose();
	}
	
	//Make a list of all answers by ID.
	private ArrayList<Answer> makeList(String ID)
	{
		ArrayList<Answer> answers = new ArrayList<>();
		
		int index = -1;
		do
		{
			index = Answers.getUserIDIndex(ID, index + 1);
			
			String QID = Answers.getQID(index);
			int qIndex = Questions.getIDIndex(QID);
			String name = Questions.getName(qIndex);
			
			int grade = Answers.getPoints(index);
			int maxPoints = Questions.getPoints(qIndex);
			
			if(!name.equals(""))
			{
				Answer a = new Answer(name, grade, maxPoints);
				answers.add(a);
			}
		}
		while(index >= 0);
		
		return answers;
	}
}
