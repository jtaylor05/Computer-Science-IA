package pages;

import java.util.*;
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
	private JButton ok = new JButton("Ok");
	private JButton close = new JButton("Close");
	private JButton toRegister = new JButton("Go register");
	
	private LinkedList<Integer> pIndexs = new LinkedList<>();
	
	private final String logInPrompt = "Please enter log-in details";
	private final String logInFail = "Wrong account details";
	private final String registerPrompt = "Please enter new account details";
	private final String registerFail = "Account already exists";
	private JLabel prompt = new JLabel(logInPrompt);
	
	private JPanel userPassBox = new JPanel();
	private boolean userBoxTyped = false;
	private JTextField usernameBox = new JTextField("Enter Username");
	
	private boolean passwordVisible = false;
	private JButton setPassVisible = new JButton("Change Password Visibility");
	private String enteredPass = "";
	private String confirmedPass = "";
	private boolean passBoxTyped = false;
	private boolean confirmPassTyped = false;
	private final String passwordPrompt = "Enter Password";
	private JTextField passwordBox = new JTextField(passwordPrompt);
	private final String confirmPrompt = "Confirm Password";
	private JTextField confirmPasswordBox = new JTextField(confirmPrompt);
	private boolean emailBoxTyped = false;
	private JTextField emailBox = new JTextField("Enter Email");
	
	public logInPage()
	{
		okCloseBox.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
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
						if(register)
						{
							prompt.setText(registerFail);
						}
						else
						{
							prompt.setText(logInFail);
						}
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
		toRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!register)
				{
					register = true;
					toRegister.setText("Log-in");
					prompt.setText(registerPrompt);
					userPassBox.remove(setPassVisible);
						c.fill = GridBagConstraints.HORIZONTAL;
						
						c.gridx = 0; c.gridy = 2;
						
						c.gridwidth = 4;
						
						c.insets = new Insets(0, 10, 5, 10);
					userPassBox.add(confirmPasswordBox, c);
						c.gridy = 3;
					userPassBox.add(emailBox, c);
						c.fill = GridBagConstraints.NONE;
					
						c.gridx = 1; c.gridy = 4;
					
						c.weightx = 0;
					
						c.insets = new Insets(5, 10, 0, 10);
						c.anchor = GridBagConstraints.LAST_LINE_END;
					userPassBox.add(setPassVisible, c);
					
						c.gridx = 0; c.gridy = 0;
						c.insets = new Insets(0, 0, 0, 0);
						c.anchor = GridBagConstraints.CENTER;
				}
				else
				{
					register = false;
					toRegister.setText("Go register");
					prompt.setText(logInPrompt);
					confirmPasswordBox.setText(confirmPrompt);
					confirmPassTyped = false;
					userPassBox.remove(confirmPasswordBox);
					userPassBox.remove(emailBox);
					userPassBox.remove(setPassVisible);
						c.fill = GridBagConstraints.NONE;
					
						c.gridx = 1; c.gridy = 2;
					
						c.weightx = 0;
					
						c.insets = new Insets(5, 10, 0, 10);
						c.anchor = GridBagConstraints.LAST_LINE_END;
					userPassBox.add(setPassVisible, c);
					confirmedPass = "";
					
						c.gridx = 0; c.gridy = 0;
						c.insets = new Insets(0, 0, 0, 0);
						c.anchor = GridBagConstraints.CENTER;
				}
			}
		});
			c.fill = GridBagConstraints.HORIZONTAL;
			
			c.gridx = 0; c.gridy = 2;
			
			c.weightx = 1;
		okCloseBox.add(new JLabel(), c);
			c.fill = GridBagConstraints.NONE;
		
			c.gridx = 1; c.gridy = 2;
			
			c.weightx = 0;
			
			c.anchor = GridBagConstraints.LAST_LINE_END;
			ok.setPreferredSize(new Dimension(60, 30));
		okCloseBox.add(ok, c);
		
			c.gridx = 2;
			
			toRegister.setPreferredSize(new Dimension (100, 30));
		okCloseBox.add(toRegister, c);
		
			c.gridx = 3;
			
			c.insets = new Insets(0, 0, 0, 5);
			close.setPreferredSize(new Dimension(70, 30));
		okCloseBox.add(close, c);
			c.gridx = 0; c.gridy = 0;
			c.anchor = GridBagConstraints.CENTER;
			c.insets = new Insets(0, 0, 0, 0);
		
		userPassBox.setLayout(new GridBagLayout());
		usernameBox.addKeyListener(new KeyAdapter()
				{
					public void keyPressed(KeyEvent e)
					{
						if(!userBoxTyped && e.getKeyChar() >= 32 && e.getKeyChar() <= 126)
						{
							usernameBox.setText("");
							userBoxTyped = true;
						}
					}
					
					public void keyReleased(KeyEvent e)
					{
						if(usernameBox.getText().equals(""))
						{
							usernameBox.setText("Enter Username");
							userBoxTyped = false;
						}
					}
				});
			c.fill = GridBagConstraints.HORIZONTAL;
		
			c.gridwidth = 4;
			
			c.weightx = 0.5;
			
			c.insets = new Insets(0, 10, 5, 10);
		userPassBox.add(usernameBox, c);
		passwordBox.addKeyListener(new KeyAdapter()
				{
					public void keyReleased(KeyEvent e)
					{
						if(!passBoxTyped && e.getKeyChar() == 8)
						{
							passwordBox.setText("");
							passBoxTyped = true;
						}
						else if(!passBoxTyped && e.getKeyChar() >= 32 && e.getKeyChar() <= 126)
						{
								
							int length = passwordBox.getText().length() - passwordPrompt.length();
							String input = passwordBox.getText().substring(passwordPrompt.length());
							String str = "";
							for(int i = 0; i < length; i++)
							{
								str = str + "*";
							}
							passwordBox.setText(str);
							passBoxTyped = true;
								
							enteredPass = enteredPass + input; 
						}
					}
					
			
					public void keyPressed(KeyEvent e)
					{
						if(passwordVisible && e.getKeyChar() >= 32 && e.getKeyChar() <= 126)
						{
						  	enteredPass = enteredPass + e.getKeyChar();
						}
						else if(passBoxTyped && e.getKeyChar() >= 32 && e.getKeyChar() <= 126)
						{
							int length = passwordBox.getText().length();
							String str = "";
							for(int i = 0; i < length; i++)
							{
								str = str + "*";
							}
							
							passwordBox.setText(str);
								
							enteredPass = enteredPass += e.getKeyChar(); 
						}
						else if(passBoxTyped && e.getKeyChar() == 8)
						{	
							if(enteredPass.length() > 0)
							{
								enteredPass = enteredPass.substring(0, enteredPass.length()-1);
								System.out.println(enteredPass);
							}
								
							if(passwordVisible)
							{
								passwordBox.setText(enteredPass + " ");
							}
							else
							{
								int length = passwordBox.getText().length();
								String str = "";
								for(int i = 0; i < length; i++)
								{
									str = str + "*";
								}
								
								passwordBox.setText(str);
							}
						}
					}
				});
			c.gridx = 0; c.gridy = 1;
		userPassBox.add(passwordBox, c);
		confirmPasswordBox.addKeyListener(new KeyAdapter()
		{
			public void keyReleased(KeyEvent e)
			{
				if(!confirmPassTyped && e.getKeyChar() == 8)
				{
					confirmPasswordBox.setText("");
					confirmPassTyped = true;
				}
				else if(!confirmPassTyped && e.getKeyChar() >= 32 && e.getKeyChar() <= 126)
				{
						
					int length = confirmPasswordBox.getText().length() - confirmPrompt.length();
					String input = confirmPasswordBox.getText().substring(confirmPrompt.length());
					String str = "";
					for(int i = 0; i < length; i++)
					{
						str = str + "*";
					}
					confirmPasswordBox.setText(str);
					confirmPassTyped = true;
						
					confirmedPass = confirmedPass + input; 
				}
			}
			
	
			public void keyPressed(KeyEvent e)
			{
				if(passwordVisible && e.getKeyChar() >= 32 && e.getKeyChar() <= 126)
				{
					confirmedPass = confirmedPass + e.getKeyChar();
				}
				else if(confirmPassTyped && e.getKeyChar() >= 32 && e.getKeyChar() <= 126)
				{
					int length = confirmPasswordBox.getText().length();
					String str = "";
					for(int i = 0; i < length; i++)
					{
						str = str + "*";
					}
					
					confirmPasswordBox.setText(str);
						
					confirmedPass = confirmedPass += e.getKeyChar(); 
				}
				else if(confirmPassTyped && e.getKeyChar() == 8)
				{		
					if(confirmedPass.length() > 0)
					{
						confirmedPass = confirmedPass.substring(0, confirmedPass.length()-1);
					}
						
					if(passwordVisible)
					{
						confirmPasswordBox.setText(confirmedPass + " ");
					}
					else
					{
						int length = confirmPasswordBox.getText().length();
						String str = "";
						for(int i = 0; i < length; i++)
						{
							str = str + "*";
						}
						
						confirmPasswordBox.setText(str);
					}
				}
			}
		});
		emailBox.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e)
			{
				if(!userBoxTyped && e.getKeyChar() >= 32 && e.getKeyChar() <= 126)
				{
					emailBox.setText("");
					emailBoxTyped = true;
				}
			}
					
			public void keyReleased(KeyEvent e)
			{
				if(usernameBox.getText().equals(""))
				{
					emailBox.setText("Enter Email");
					emailBoxTyped = false;
				}
			}
		});
		setPassVisible.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				passwordVisible = !passwordVisible;
				if(passBoxTyped)
				{
					if(passwordVisible)
					{
						passwordBox.setText(enteredPass);
						confirmPasswordBox.setText(confirmedPass);
					}
					else
					{
						String str = "";
						for(int i = 0; i < enteredPass.length(); i++)
						{
							str = str + "*";
						}
						passwordBox.setText(str);
						
						str = "";
						for(int i = 0; i < confirmedPass.length(); i++)
						{
							str = str + "*";
						}
						confirmPasswordBox.setText(str);
					}
				}
				if(confirmPassTyped)
				{
					if(passwordVisible)
					{
						confirmPasswordBox.setText(confirmedPass);
					}
					else
					{
						String str = "";
						for(int i = 0; i < confirmedPass.length(); i++)
						{
							str = str + "*";
						}
						confirmPasswordBox.setText(str);
					}
				}
			}
		});
			c.gridx = 0; c.gridy = 2;
			
			c.weightx = 1;
		userPassBox.add(new JLabel("     "), c);
			c.fill = GridBagConstraints.NONE;
			
			c.gridx = 1;
			
			c.weightx = 0;
			
			c.insets = new Insets(5, 10, 0, 10);
			c.anchor = GridBagConstraints.LAST_LINE_END;
			setPassVisible.setPreferredSize(new Dimension(200, 30));
		userPassBox.add(setPassVisible, c);
			c.insets = new Insets(0, 0, 0, 0);
			c.anchor = GridBagConstraints.CENTER;
		
		setLayout(new GridBagLayout());
			c.fill = GridBagConstraints.BOTH;
		
			c.gridx = 0; c.gridy = 0;
			
			c.weightx = 0.2; c.weighty = 0.2;
		getContentPane().add(prompt, c);
		prompt.setHorizontalAlignment(SwingConstants.CENTER);
		prompt.addComponentListener(new ComponentListener() {

			@Override
			public void componentResized(ComponentEvent e) {
				int y = prompt.getHeight();
				Font f;
				if(register)
				{
					f = new Font("Arial", Font.PLAIN, y/4 + 5);
				}
				else
				{
					f = new Font("Arial", Font.PLAIN, y/8 + 5);
				}
				prompt.setFont(f);
				
			}

			public void componentMoved(ComponentEvent e) {}
			public void componentShown(ComponentEvent e) {}
			public void componentHidden(ComponentEvent e) {}
			
		});
		
			c.gridy = 1;
			
			c.ipady = 40;
			
			c.weighty = 0;
		getContentPane().add(userPassBox, c);
			c.gridy = 2;
			
			c.ipady = 0;
			
			c.insets = new Insets(20, 0, 0, 0);
		getContentPane().add(okCloseBox, c);
			
			c.gridx = 0; c.gridy = 0;
			
			c.insets = new Insets(0, 0, 0, 0);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
	}
	
	//using database "accounts", checks whether information matches a single account;
	//returns true if account is found, false if not.
	public boolean validateInfo()
	{
		String username = usernameBox.getText();
		String password = enteredPass;
		
		int uIndex = Accounts.getUsernameIndex(username);
		
		int index = -1;
		do
		{
			index = Accounts.getPasswordIndex(password, index + 1);
			enqueue(index);
		}
		while(index >= 0);
		
		int nextIndex = dequeue();
		while(nextIndex >= 0)
		{
			if(uIndex > -1 && nextIndex > -1 && uIndex == nextIndex)
			{
				return true;
			}
			nextIndex = dequeue();
		}
		return false;
	}
	
	//using database "accounts", checks whether username matches an account;
	//Adds account and returns true if not, returns false if yes.
	public boolean registerInfo()
	{
		String password1 = enteredPass;
		String password2 = confirmedPass;
		
		if(!password1.equals(password2) || password1.equals(""))
		{
			return false;
		}
		
		String username = usernameBox.getText();
		String email = emailBox.getText();
		
		if(email.equals("Enter Email") || email.equals(""))
		{
			return false;
		}
		
		boolean teacher = false;
		
		if(Accounts.getUsernameIndex(username) == -1)
		{
			Accounts.addAccount(username, password1, email, teacher);
			return true;
		}
		
		return false;
	}
	
	public void dropInPage(boolean teacher, String ID)
	{
		new dropInPage(teacher, ID).setVisible(true);
		setVisible(false);
		dispose();
	}
	
	public void enqueue(Integer i)
	{	pIndexs.add(i);	}
	public Integer dequeue()
	{	return pIndexs.remove(0);	}
	
	public static void main(String[] args)
	{
		logInPage lip = new logInPage();
		lip.setSize(300, 300);
		lip.setVisible(true);
		lip.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

}
