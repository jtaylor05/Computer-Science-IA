package pages;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

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
	
	
	public dropInPage(boolean isTeacher, String id)
	{
		teacher = isTeacher;
		ID = id;
		
		logOutRow.setLayout(new GridLayout(1, 2));
		logOut.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
				dispose();
			}
		});
		logOutRow.add(new JLabel()); logOutRow.add(logOut);
		
		unansweredQuestions.setText("You have " + " unanswered Questions");
		returnedQuestions.setText("You have " + " returned questions");
		feedback.setText("You have recieved feedback on " + " question");
		infoBox.setLayout(new GridLayout(3, 1));
		infoBox.add(unansweredQuestions); infoBox.add(returnedQuestions); infoBox.add(feedback);
		
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
		
		routingBox.setLayout(new GridLayout(1, 3));
		routingBox.add(questionPage); routingBox.add(new JLabel("   ")); routingBox.add(portfolioPage);
		
		setLayout(new GridLayout(3, 1));
		getContentPane().add(logOutRow);
		getContentPane().add(infoBox);
		getContentPane().add(routingBox);
		getContentPane().setVisible(true);
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
		//portfolioList(teacher, ID)
		setVisible(false);
		dispose();
	}
}
