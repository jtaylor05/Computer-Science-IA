package pages;

import java.util.*;    
import java.awt.event.*;   
import java.awt.*;
import javax.swing.*;
import database.*;
import library.L;

/**
 * On-Run page opened. Allows all users to both register new accounts and log in with their account.
 */
public class logInPage extends JFrame
{
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
	private JPanel registerBox = new JPanel();
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
	
	/**
	 * Instantiates the log-in page window
	 */
	public logInPage()
	{
		okCloseBox.setBackground(L.LIGHT_BROWN);
		okCloseBox.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//ok button begins validation sequence
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
		//close button disposes of Frame
		close.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
			
		});
		close.setPreferredSize(new Dimension(200, 50));
		//toRegister alters page to registry
		toRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setupBox();
			}
		});
			c.fill = GridBagConstraints.HORIZONTAL;
			
			c.gridx = 0; c.gridy = 0;
			
			c.weightx = 0.5;
		okCloseBox.add(new JLabel(), c);
			c.fill = GridBagConstraints.NONE;
		
			c.gridx = 1; c.gridy = 0;
			
			c.weightx = 0;
			
			c.anchor = GridBagConstraints.LAST_LINE_END;
			c.insets = new Insets(5, 5, 5, 5);
			ok.setPreferredSize(new Dimension(60, 30));
			ok.setBackground(L.DARK_BROWN);
			ok.setForeground(Color.WHITE);
		okCloseBox.add(ok, c);
		
			c.gridx = 2;
			
			toRegister.setPreferredSize(new Dimension (100, 30));
			toRegister.setBackground(L.DARK_BROWN);
			toRegister.setForeground(Color.WHITE);
		okCloseBox.add(toRegister, c);
		
			c.gridx = 3;
			
			close.setPreferredSize(new Dimension(70, 30));
			close.setBackground(L.DARK_BROWN);
			close.setForeground(Color.WHITE);
		okCloseBox.add(close, c);
			c.gridx = 0; c.gridy = 0;
			c.anchor = GridBagConstraints.CENTER;
			c.insets = new Insets(0, 0, 0, 0);
		
		userPassBox.setBackground(Color.WHITE); registerBox.setBackground(Color.WHITE);
		userPassBox.setLayout(new GridBagLayout()); registerBox.setLayout(new GridBagLayout());
		//keyListener empties textField after first key is pressed
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
			
			c.gridwidth = 2;
			
			c.weightx = 0.5;
			
			c.insets = new Insets(0, 10, 5, 10);
		userPassBox.add(usernameBox, c);
		//makes the password hidden
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
		//same as passwordBox
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
				if(!emailBoxTyped && e.getKeyChar() >= 32 && e.getKeyChar() <= 126)
				{
					emailBox.setText("");
					emailBoxTyped = true;
				}
			}
					
			public void keyReleased(KeyEvent e)
			{
				if(emailBox.getText().equals(""))
				{
					emailBox.setText("Enter Email");
					emailBoxTyped = false;
				}
			}
		});
		setPassVisible.setBackground(L.LIGHT_BROWN);
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
			c.fill = GridBagConstraints.NONE;
			
			c.gridx = 1; c.gridy = 2;
			
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
			
			c.gridwidth = 3;
			
			c.weightx = 0.2; c.weighty = 0.2;
		getContentPane().add(prompt, c);
		prompt.setBackground(L.PRIME_BLUE);
		prompt.setForeground(L.DARK_BROWN);
		prompt.setOpaque(true);
		prompt.setHorizontalAlignment(SwingConstants.CENTER);
		prompt.addComponentListener(new ComponentListener() {

			@Override
			public void componentResized(ComponentEvent e) {
				int y = prompt.getHeight();
				Font f;
				if(register)
				{
					f = new Font("Arial", Font.PLAIN, y/6 + 10);
				}
				else
				{
					f = new Font("Arial", Font.PLAIN, y/6 + 5);
				}
				prompt.setFont(f);
				
			}

			public void componentMoved(ComponentEvent e) {}
			public void componentShown(ComponentEvent e) {}
			public void componentHidden(ComponentEvent e) {}
			
		});
		
			c.gridx = 1; c.gridy = 1;
			
			c.ipady = 40;
			
			c.weighty = 0;
		getContentPane().add(userPassBox, c);
			c.gridy = 2;
			
			c.ipady = 0;
		getContentPane().add(okCloseBox, c);
		setBackground(Color.WHITE);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
	}
	
	/**
	 * @return boolean value of whether username and password match an account.
	 */
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
	
	/**
	 * @return boolean value of whether account doesn't exist and was added.
	 */
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
			Accounts.add(username, password1, email, teacher);
			return true;
		}
		
		return false;
	}
	
	/**
	 * @param teacher boolean value of whether user brought to drop-in page is a teacher.
	 * @param ID ID of user brought to drop-in page.
	 */
	public void dropInPage(boolean teacher, String ID)
	{
		
		Accounts.updateFeedback(true, false, false, ID);
		Accounts.updateAnswered(true, false, false, ID);
		new dropInPage(teacher, ID).setVisible(true);
		setVisible(false);
		dispose();
	}
	

	/**
	 * @param i value added to end of queue
	 */
	public void enqueue(Integer i)
	{	pIndexs.add(i);	}
	/**
	 * @return value at front of queue
	 */
	public Integer dequeue()
	{	return pIndexs.remove(0);	}
	
	//opens log-in page
	public static void main(String[] args)
	{
		logInPage lip = new logInPage();
		lip.setSize(300, 300);
		lip.setVisible(true);
		lip.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	/**
	 * When the register or log-in box is changed, sets-up JFrame
	 */
	public void setupBox()
	{
		GridBagConstraints c = new GridBagConstraints();
		
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
			toRegister.setText("go register");
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

}
