package pages;

import java.awt.event.*; 
import java.awt.*;
import javax.swing.*;
import database.*;

public class logInPage extends JFrame
{

	/*
	 * pageType - int value, 0 for normal, 1 for register page, 2 for teacher log-in
	 * logInFrame - JFrame where all components will be held to log-in
	 * forgotPassword - JButton where one can press to go through process of reseting password
	 * teacherLogIn - JButton which will lead you to an identical log-in page for teachers
	 * registerButton - JButton which will lead you to a slightly different page for registering and account
	 * usernameBox - JTextField which user will enter in their username
	 * passwordBox - JTextField used both to enter in password or register a new one
	 * retryPasswordBox - JTextField used in registration to confirm password
	 * prompt - JLabel which would prompt user if something was wrong
	 * continue - JButton which sends inputed information to central system which accesses Database. 
	 * 			  Will open to dropInPage if log-in information is correct or will resent log-in
	 *    	      page if account has been registered.
	 */
	
	private boolean teacher = false;
	private boolean register = false;
	
	private JPanel okCloseBox = new JPanel();
	private JButton ok = new JButton("ok");
	private JButton close = new JButton("close");
	
	private JLabel prompt = new JLabel("please enter log-in details");
	
	private JPanel userPassBox = new JPanel();
	private JTextField usernameBox = new JTextField("Enter Username");
	private JTextField passwordBox = new JTextField("Enter Password");
	private JTextField confirmPasswordBox = new JTextField("Confirm Password");
	private JTextField emailBox = new JTextField("Enter Email");
	
	public logInPage(boolean isTeacher, boolean isRegister)
	{
		teacher = isTeacher;
		register = isRegister;
		
		okCloseBox.setLayout(new GridLayout(1, 2));
		ok.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				if(register)
				{
					if(registerInfo())
					{
						prompt.setText("account successfully registered");
					}
					else
					{
						prompt.setText("Invalid or already existing account");
					}
				}
				else
				{
					if(validateInfo())
					{
						int index = Accounts.getUsernameIndex(usernameBox.getText());
						teacher = Accounts.isTeacher(index);
						String ID = Accounts.getID(index);
						dropInPage(teacher, ID);
					}
					else
					{
						prompt.setText("wrong account details");
					}
				}
			}
			
		});
		close.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
			
		});
		close.setPreferredSize(new Dimension(200, 50));
		okCloseBox.add(close); okCloseBox.add(ok);
		
		userPassBox.setLayout(new GridLayout(5, 1));
		userPassBox.add(new JLabel(""));
		userPassBox.add(usernameBox); userPassBox.add(new JLabel(""));
		userPassBox.add(passwordBox); userPassBox.add(new JLabel(""));
		
		this.setLayout(new GridLayout(3, 1));
		this.getContentPane().add(prompt);
		this.getContentPane().add(userPassBox);
		this.getContentPane().add(okCloseBox);
		pack();
	}
	
	//using database "accounts", checks whether information matches a single account;
	//returns true if account is found, false if not.
	public boolean validateInfo()
	{
		String username = usernameBox.getText();
		String password = passwordBox.getText();
		
		int uIndex = Accounts.getUsernameIndex(username);
		int pIndex = Accounts.getUsernameIndex(password);
		
		if(uIndex > -1 && pIndex > -1 && uIndex == pIndex)
		{
			return true;
		}
		return false;
	}
	
	//using database "accounts", checks whether username matches an account;
	//Adds account and returns true if not, returns false if yes.
	public boolean registerInfo()
	{
		String password1 = passwordBox.getText();
		String password2 = confirmPasswordBox.getText();
		
		if(!password1.equals(password2))
		{
			return false;
		}
		
		String username = usernameBox.getText();
		String email = emailBox.getText();
		boolean teacher = false;
		
		if(Accounts.getUsernameIndex(username) == -1)
		{
			Accounts.addAccount(username, password1, email, teacher);
			return true;
		}
		
		return false;
	}
	
	//public void registerPage() - refreshes log-in page as register info page when registerButton is pressed
	//	changes pageType to 1
	//public void teacherPage() - refreshes log-in page as teacher log-in page when teacherLogIn is pressed
	//	changes pageType to 2
	
	public void dropInPage(boolean teacher, String ID)
	{
		//dropInPage(teacher, ID);
		setVisible(false);
		dispose();
	}
	
	public static void main(String[] args)
	{
		logInPage lip = new logInPage(false, false);
		lip.setSize(300, 300);
		lip.setVisible(true);
		lip.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
}
