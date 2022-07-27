package pages;

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
	
	public dropInPage(boolean isTeacher, String id)
	{
		
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
		//questionList(teacher, ID)
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
