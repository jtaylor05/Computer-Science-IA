package pages;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import database.Accounts;
import library.L;

/**
 * List page of all users. Includes navigation to dropInPage and 
 * portfolio page. Only accessible to teachers.
 */
public class portfolioListPage extends JFrame
{
	ArrayList<String> StudentIDs = new ArrayList<>();
	
	private JPanel homeRow = new JPanel();
	private JPanel scroller = new JPanel();
	
	private JButton home = new JButton("Go to menu");
	
	private JPanel portfolios = new JPanel();
	private JScrollPane portfolioScroller = new JScrollPane(portfolios);
	
	/**
	 * @param teacher boolean value of whether user is a teacher
	 * @param ID String ID of user
	 */
	public portfolioListPage(boolean teacher, String ID)
	{
		StudentIDs = makeStudentList();
		
		GridBagConstraints c = new GridBagConstraints();
		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				dropInPage(teacher, ID);
			}
		});
		homeRow.setBackground(L.LIGHT_BROWN);
		homeRow.setLayout(new GridBagLayout());
			c.fill = GridBagConstraints.HORIZONTAL;
			
			c.gridx = 0;
			
			c.weightx = 1;
		homeRow.add(new JLabel(""), c); 
			c.fill = GridBagConstraints.NONE;
		
			c.gridx = 1;
			
			c.weightx = 0;
			
			c.insets = new Insets(3, 3, 3, 3);
			home.setPreferredSize(new Dimension(140, 30));
			home.setBackground(L.DARK_BROWN);
			home.setForeground(Color.WHITE);
		homeRow.add(home, c);
			c.insets = new Insets(0, 0, 0, 0);
		
		portfolioScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		portfolioScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		scroller.setBackground(L.DARK_BLUE);
		portfolios.setBackground(L.PRIME_BLUE);
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
			
				c.fill = GridBagConstraints.NONE;
				
				c.gridx = 0; c.gridy = i;
				
				c.weightx = 1;
				c.insets = new Insets(10, 10, 10, 10);
				jb.setPreferredSize(new Dimension(120, 30));
				jb.setBackground(L.LIGHT_BROWN);
				jb.setForeground(Color.BLACK);
			portfolios.add(jb, c);
		}
			portfolios.setPreferredSize(new Dimension(300, 200));
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
	
	/**
	 * @param teacher boolean value of whether logged in user is teacher
	 * @param ID String ID of logged in user
	 */
	public void dropInPage(boolean teacher, String ID)
	{
		new dropInPage(teacher, ID).setVisible(true);
		setVisible(false);
		dispose();
	}
	
	/**
	 * @param teacher boolean value of whether logged in user is teacher
	 * @param ID String ID of logged in user
	 * @param studentID String ID of student portfolio
	 */
	public void portfolioPage(boolean teacher, String ID, String studentID)
	{
		new portfolioPage(teacher, ID, studentID).setVisible(true);
		setVisible(false);
		dispose();
	}
	
	/**
	 * @return ArrayList of all student IDs in accounts database
	 */
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
