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
		homeRow.add(new JLabel("")); homeRow.add(home);
		
		as = makeList(ID);
		int index = Accounts.getIDIndex(ID);
		
		portfolioBox.setLayout(new GridLayout(as.size() + 1, 1));
		JLabel student = new JLabel("STUDENT: " + Accounts.getUsername(index));
		portfolioBox.add(student);
		for(int i = 0; i < as.size(); i++)
		{
			JLabel jl = new JLabel("" + as.get(i));
			portfolioBox.add(jl);
		}
		
		setLayout(new GridLayout(2, 1));
		add(homeRow);
		add(portfolioBox);
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
		homeRow.add(new JLabel("")); homeRow.add(home);
		
		as = makeList(studentID);
		int index = Accounts.getIDIndex(studentID);
		
		portfolioBox.setLayout(new GridLayout(as.size() + 1, 1));
		JLabel student = new JLabel("STUDENT: " + Accounts.getUsername(index));
		portfolioBox.add(student);
		for(int i = 0; i < as.size(); i++)
		{
			JLabel jl = new JLabel("" + as.get(i));
			portfolioBox.add(jl);
		}
		
		setLayout(new GridLayout(2, 1));
		getContentPane().add(homeRow);
		getContentPane().add(portfolioBox);
		getContentPane().setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
	}
	
	public void dropInPage(boolean teacher, String ID)
	{
		new dropInPage(teacher, ID).setVisible(true);
		setVisible(false);
		dispose();
	}

	public void portfolioListPage(boolean teacher, String ID)
	{
		new portfolioListPage(teacher, ID).setVisible(true);
		setVisible(false);
		dispose();
	}
	
	private ArrayList<Answer> makeList(String ID)
	{
		ArrayList<Answer> answers = new ArrayList<>();
		
		int index = 0;
		String id = Answers.getUserID(index);
		while(id != null)
		{
			if(ID.equals(id))
			{
				String qID = Answers.getQID(index);
				int qIndex = Questions.getIDIndex(qID);
				String name = "";
				if(qIndex > -1)
				{
					name = Questions.getName(qIndex);
				}
				
				int grade = Answers.getPoints(index);
				int maxPoints = Questions.getPoints(qIndex);	
				Answer a = new Answer(name, grade, maxPoints);
				
				answers.add(a);
			}
			index = index + 1;
			id = Answers.getUserID(index);
		}
		return answers;
	}
}
