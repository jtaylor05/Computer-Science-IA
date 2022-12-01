package pages;

import library.L;

/**
 * Answer class holds all relevant answer data. Extends Record class.
 */
public class Answer extends Record
{
	private String username;
	
	/**
	 * @param user String username of user who answered question
	 * @param questionName String question name of question answered
	 * @param filePath String of answer image file path
	 * @param grade int of grade
	 * @param maxPoints int of highest grade possible
	 */
	public Answer(String user, String questionName, String filePath, int grade, int maxPoints)
	{
		super(null, questionName, filePath, grade, maxPoints, true);
		this.username = user;
	} 
	
	/**
	 * @param questionName String question name of question answered
	 * @param filePath String of answer image file path
	 * @param grade int of grade
	 * @param maxPoints int of highest grade possible
	 */
	public Answer(String questionName, String filePath, int grade, int maxPoints)
	{
		super(null, questionName, filePath, grade, maxPoints, true);
	} 
	
	/**
	 * @param questionName String question name of question answered
	 * @param filePath String of answer image file path
	 * @param grade int of grade
	 * @param maxPoints int of highest grade possible
	 */
	public Answer(String questionName, int grade, int maxPoints)
	{
		super(null, questionName, null, grade, maxPoints, true);
	}
	
	/**
	 * @param user String username of user who answered question
	 * @param questionName String question name of question answered
	 * @param maxPoints int of highest grade possible
	 */
	public Answer(String user, String questionName, int maxPoints)
	{
		super(null, questionName, null, -1, maxPoints, false);
		this.username = user;
	}
	
	/**
	 * @param questionName String question name of question answered
	 * @param maxPoints int of highest grade possible
	 */
	public Answer(String questionName, int maxPoints)
	{
		super(null, questionName, null, -1, maxPoints, false);
	}
	
	/**
	 * @return String username of user
	 */
	public String getUser()
	{
		return username;
	}
	
	/**
	 * Prints Record in proper format for Answer.
	 */
	@Override
	public String toString()
	{
		String name = L.fitToLength(23, this.name.toUpperCase());
		String points = L.fitToLength(10, "");
		if(grade > -1)
		{
			points = L.fitToLength(10, grade + "/" + maxPoints);
		}
		else if(grade == -1)
		{
			points = L.fitToLength(10, "Missing");
		}
		else if(grade == -2)
		{
			points = L.fitToLength(10, "Points: *");
		}
		return name + "    ~ " + points; 
	}
}
