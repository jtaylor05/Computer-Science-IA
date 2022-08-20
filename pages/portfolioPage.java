package pages;

import java.util.*;
import javax.swing.*;
import database.*;

public class portfolioPage extends JFrame
{
	private JPanel homeRow = new JPanel();
	private JPanel portfolioBox = new JPanel();
	
	private JButton home = new JButton("Go to menu");
	
	private JLabel portfolio = new JLabel("");
	
	/*
	 * portfolioFrame - JFrame containing all components in portfolio page
	 * portfolioList - a list of all the student accounts, each with a link to their portfolio
	 * portfolio - Portfolio object holding the portfolio
	 * homePage - JButton opening homepage
	 */
	
	private ArrayList<Answer> as;

	public portfolioPage(String ID)
	{
		as = makeList(ID);
		int index = Accounts.getIDIndex(ID);
		String p = "STUDENT: " + Accounts.getUsername(index) + "\n";
		p = p + "--------------------------------------------\n";

		for(int i = 0; i < as.size(); i++)
		{
			p = p + as.get(i);
		}
		
		System.out.println(p);
		portfolio.setText(p);
	}
	
	//public void homePage() - closes portfolioPage and opens dropInPage

	//public void openPortfolio() - after a portfolio link is pressed, this will then download the portfolio and open it
	
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
	
	public static void main(String[] args)
	{
		portfolioPage pp = new portfolioPage(Accounts.getID(0));
	}
	
}
