package pages;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import database.Accounts;

public class portfolioListPage extends JFrame
{
	ArrayList<String> StudentIDs = new ArrayList<>();
	
	private JPanel homeRow = new JPanel();
	private JPanel scroller = new JPanel();
	
	private JButton home = new JButton("Go to menu");
	
	private JPanel portfolios = new JPanel();
	private JScrollPane portfolioScroller = new JScrollPane(portfolios);
	
	public portfolioListPage(boolean teacher, String ID)
	{
		StudentIDs = makeStudentList();
		
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
		
		portfolioScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		portfolioScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		portfolios.setLayout(new GridBagLayout());
		
		for(int i = 0; i < StudentIDs.size(); i++)
		{
			JButton jb = new JButton("");
			
			int index = Accounts.getIDIndex(StudentIDs.get(i));
			String name = Accounts.getUsername(index);
			jb.setText(name);
			
			final int j = i;
			jb.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					portfolioPage(teacher, ID, StudentIDs.get(j));
				}
			});
			
				c.fill = GridBagConstraints.HORIZONTAL;
				
				c.gridx = 0; c.gridy = i;
				
				c.weightx = 1;
				c.insets = new Insets(10, 10, 10, 10);
			portfolios.add(jb, c);
		}
			portfolios.setPreferredSize(new Dimension(400, 200));
		scroller.add(portfolios);
		
		setLayout(new GridBagLayout());
			c.fill = GridBagConstraints.HORIZONTAL;
			
			c.gridx = 0; c.gridy = 0;
			
			c.weightx = 1;
			c.insets = new Insets(0, 0, 0, 0);
		getContentPane().add(homeRow, c);
			
			c.fill = GridBagConstraints.BOTH;
			
			c.gridx = 0;
			c.gridy = 1;
			
			c.weighty = 1;
		getContentPane().add(scroller, c);
		getContentPane().setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
	}
	
	//open a drop-in page, closes this page
	public void dropInPage(boolean teacher, String ID)
	{
		new dropInPage(teacher, ID).setVisible(true);
		setVisible(false);
		dispose();
	}
	
	//open a portfolio page, closes this page
	public void portfolioPage(boolean teacher, String ID, String studentID)
	{
		new portfolioPage(teacher, ID, studentID).setVisible(true);
		setVisible(false);
		dispose();
	}
	
	//Makes a list of all student ID's
	public ArrayList<String> makeStudentList()
	{
		ArrayList<String> ids = new ArrayList<>();
		int index = 0;
		String id = Accounts.getID(index);
		
		while(id != null)
		{
			if(!Accounts.isTeacher(index) && !id.equals(""))
			{
				ids.add(id);
			}
			
			index = index + 1;
			id = Accounts.getID(index);
		}
		return ids;
	}
}
