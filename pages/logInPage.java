package pages;

public class logInPage 
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
	
	//public logInPage() - initialises all fields above
	
	//public boolean validateInfo(pageType) - after password and username is submitted, sent to database to check if
	//info is there. Returns true if info is there, false if not.
	
	//public boolean validatePassword() - after submitting to register, check if two password boxes have
	//equal text fields. Returns true if so, false if not.
	
	//public boolean registerInfo(pageType) - after password is validated, sends info to database. If info
	//already exists in database, returns false and info is not added. If info doesn't exist in database
	//returns true and info is added.
	
	//public void registerPage() - refreshes log-in page as register info page when registerButton is pressed
	//	changes pageType to 1
	//public void teacherPage() - refreshes log-in page as teacher log-in page when teacherLogIn is pressed
	//	changes pageType to 2
	
	//public void continue() - closes log-in page and opens dropInPage
	
	
}
