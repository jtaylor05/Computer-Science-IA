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
		homeRow.add(new JLabel("")); homeRow.add(home);
		
		portfolioScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		portfolioScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		portfolios.setLayout(new GridLayout(StudentIDs.size(), 1));
		
		for(int i = 0; i < StudentIDs.size(); i++)
		{
			JButton jb = new JButton("");
			
			int index = Accounts.getIDIndex(StudentIDs.get(i));
			String name = Accounts.getUsername(index);
			jb.setText(name);
			
			jb.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					portfolioPage(teacher, ID, StudentIDs.get(index));
				}
			});
			
			portfolios.add(jb);
		}
		scroller.add(portfolios);
		
		setLayout(new GridLayout(2, 1));
		getContentPane().add(homeRow);
		getContentPane().add(scroller);
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
	
	public void portfolioPage(boolean teacher, String ID, String studentID)
	{
		new portfolioPage(teacher, ID, studentID).setVisible(true);
		setVisible(false);
		dispose();
	}
	
	public ArrayList<String> makeStudentList()
	{
		ArrayList<String> ids = new ArrayList<>();
		int index = 0;
		String id = Accounts.getID(index);
		
		while(id != null)
		{
			if(!Accounts.isTeacher(index))
			{
				ids.add(id);
			}
			
			index = index + 1;
			id = Accounts.getID(index);
		}
		
		return ids;
	}
}
