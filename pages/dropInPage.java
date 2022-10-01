package pages;

import java.awt.event.*; 
import java.awt.*;
import javax.swing.*;

import database.Accounts;

public class dropInPage extends JFrame
{
	/*
	 * isTeacher - boolean holding true if user is teacher, false if not
	 * dropInFrame - JFrame holding all components needed for the Drop-In page
	 * logOut - JButton allowing for a user to log out of their account
	 * questionList - JButton leading to the questionListPage
	 * portfolioList - JButton leading to the portfolioPage. Teachers only.
	 * 
	 */
	private boolean teacher;
	private String ID;
	
	private JButton logOut = new JButton("log out");
	private JButton questionPage = new JButton("Go to Questions");
	private JButton portfolioPage = new JButton("Go to Portfolios");
	
	private JPanel logOutRow = new JPanel();
	private JPanel infoBox = new JPanel();
	private JPanel routingBox = new JPanel();
	
	private JLabel unansweredQuestions = new JLabel();
	private JLabel returnedQuestions = new JLabel();
	private JLabel feedback = new JLabel();
	private JLabel newAnswers = new JLabel();
	
	
	public dropInPage(boolean isTeacher, String id)
	{
		teacher = isTeacher;
		ID = id;
		
		logOutRow.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		logOut.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
				dispose();
			}
		});
			c.fill = GridBagConstraints.HORIZONTAL;
			
			c.weightx = 1;
		logOutRow.add(new JLabel(""), c); 
			c.fill = GridBagConstraints.NONE;
			
			c.gridx = 1;
			
			c.insets = new Insets(0, 0, 0, 10);
			logOut.setPreferredSize(new Dimension(80, 30));
		logOutRow.add(logOut);
			c.insets = new Insets(0, 0, 0, 0);
		
		ComponentListener cl = new ComponentListener() {

			@Override
			public void componentResized(ComponentEvent e) {
				int y = infoBox.getHeight();
				Font f;
				if(teacher)
				{
					f = new Font("Arial", Font.PLAIN, y/5 + 5);
					newAnswers.setFont(f);
				}
				else
				{
					f = new Font("Arial", Font.PLAIN, y/12 + 5);
					unansweredQuestions.setFont(f);
					returnedQuestions.setFont(f);
					feedback.setFont(f);
				}
			}

			public void componentMoved(ComponentEvent e) {}
			public void componentShown(ComponentEvent e) {}
			public void componentHidden(ComponentEvent e) {}
		};	
			
		if(teacher)
		{
			newAnswers.setText("There have been " + " new responses");
			newAnswers.addComponentListener(cl);
			infoBox.add(newAnswers);
		}
		else
		{
			unansweredQuestions.setText("You have " + " unanswered Questions");
			unansweredQuestions.addComponentListener(cl);
			returnedQuestions.setText("You have " + " returned questions");
			feedback.setText("You have recieved feedback on " + " question");
			infoBox.setLayout(new GridLayout(3, 1));
			infoBox.add(unansweredQuestions); infoBox.add(returnedQuestions); infoBox.add(feedback);
		}
		questionPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				questionList();
			}
		});
		portfolioPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				portfolioList();
			}
		});
		
		routingBox.setLayout(new GridBagLayout());
			c.fill = GridBagConstraints.HORIZONTAL;
			
			c.gridx = 1;
			
			c.weightx = 0.5;
		routingBox.add(new JLabel(), c);
			c.fill = GridBagConstraints.NONE;
			
			c.gridx = 0; c.gridy = 0;
			
			c.weightx = 0;
			
			c.insets = new Insets(0, 10, 0, 5);
			questionPage.setPreferredSize(new Dimension(150, 30));
		routingBox.add(questionPage, c);
			c.gridx = 2;
			
			c.insets = new Insets(0, 5, 0, 10);
			portfolioPage.setPreferredSize(new Dimension(150, 30));
		routingBox.add(portfolioPage, c);
			c.insets = new Insets(0, 0, 0, 0);
		
		setLayout(new GridBagLayout());
			c.fill = GridBagConstraints.HORIZONTAL;
			
			c.gridx = 0; c.gridy = 0;
			
			c.weightx = 0.5;
		getContentPane().add(logOutRow, c);
			c.fill = GridBagConstraints.BOTH;
			
			c.gridy = 1;
			
			c.ipady = 50;
			
			c.weighty = 0.5;
		getContentPane().add(infoBox, c);
			c.fill = GridBagConstraints.HORIZONTAL;
			
			c.gridy = 2;
			
			c.ipady = 0;
			
			c.weighty = 0;
		getContentPane().add(routingBox, c);
		getContentPane().setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
	}
	
	//Logs out of application for current account; returns nothing.
	public void logOut()
	{
		setVisible(false);
		dispose();
	}
	
	//opens question list page and closes drop in page; returns nothing.
	public void questionList()
	{
		new questionListPage(teacher, ID).setVisible(true);
		setVisible(false);
		dispose();
	}
	
	//opens portfolio list page and closes drop in page; returns nothing.
	public void portfolioList()
	{
		if(teacher)
		{
			new portfolioListPage(teacher, ID).setVisible(true);
		}
		else
		{
			new portfolioPage(teacher, ID).setVisible(true);
		}
		setVisible(false);
		dispose();
	}
	
	public static void main(String[] args)
	{
		//new dropInPage(Accounts.isTeacher(0), Accounts.getID(0)).setVisible(true);
		new dropInPage(Accounts.isTeacher(1), Accounts.getID(1)).setVisible(true);
	}
}
