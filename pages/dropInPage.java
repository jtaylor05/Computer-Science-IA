package pages;

import java.awt.event.*; 
import java.awt.*;
import javax.swing.*;

import database.Accounts;
import library.L;

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
	private JLabel feedback = new JLabel();
	private JLabel newAnswers = new JLabel();
	
	
	public dropInPage(boolean isTeacher, String id)
	{
		teacher = isTeacher;
		ID = id;
		
		logOutRow.setBackground(L.LIGHT_BROWN);
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
			
			c.insets = new Insets(3, 0, 3, 10);
			c.anchor = GridBagConstraints.LAST_LINE_END;
			logOut.setPreferredSize(new Dimension(80, 30));
			logOut.setBackground(L.DARK_BROWN);
			logOut.setForeground(Color.WHITE);
		logOutRow.add(logOut, c);
			c.insets = new Insets(0, 0, 0, 0);
			c.anchor = GridBagConstraints.CENTER;
		
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
					feedback.setFont(f);
				}
			}

			public void componentMoved(ComponentEvent e) {}
			public void componentShown(ComponentEvent e) {}
			public void componentHidden(ComponentEvent e) {}
		};	
		
		infoBox.setBackground(L.LIGHT_BLUE);
		if(teacher)
		{
			newAnswers.setText("There have been " + Accounts.getAnswered(ID) + " new responses");
			newAnswers.addComponentListener(cl);
			infoBox.add(newAnswers);
		}
		else
		{
			unansweredQuestions.setText("You have " + Accounts.getUnanswered(ID) + " unanswered Questions");
			unansweredQuestions.addComponentListener(cl);
			feedback.setText("You have recieved feedback on " + Accounts.getFeedback(ID) + " question");
			feedback.addComponentListener(cl);
			infoBox.setLayout(new GridLayout(3, 1));
			infoBox.add(unansweredQuestions); infoBox.add(feedback);
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
		
		routingBox.setBackground(L.LIGHT_BROWN);
		routingBox.setLayout(new GridBagLayout());
			c.fill = GridBagConstraints.HORIZONTAL;
			
			c.gridx = 1;
			
			c.weightx = 0.5;
		routingBox.add(new JLabel(), c);
			c.fill = GridBagConstraints.NONE;
			
			c.gridx = 0; c.gridy = 0;
			
			c.weightx = 0;
			
			c.insets = new Insets(3, 10, 3, 5);
			questionPage.setPreferredSize(new Dimension(150, 30));
			questionPage.setBackground(L.DARK_BROWN);
			questionPage.setForeground(Color.WHITE);
		routingBox.add(questionPage, c);
			c.gridx = 2;
			
			c.insets = new Insets(3, 5, 3, 10);
			portfolioPage.setPreferredSize(new Dimension(150, 30));
			portfolioPage.setBackground(L.DARK_BROWN);
			portfolioPage.setForeground(Color.WHITE);
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
