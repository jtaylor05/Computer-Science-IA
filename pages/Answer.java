package pages;

import library.L;

public class Answer extends Record
{
	String username;
	String questionName;
	int grade = -1;
	int maxPoints;
	String filePath;
	boolean hasAnswer = true;
	
	public Answer(String user, String questionName, String filePath, int grade, int maxPoints)
	{
		super(null, questionName, filePath, grade, maxPoints, true);
		this.username = user;
	} 
	
	public Answer(String questionName, String filePath, int grade, int maxPoints)
	{
		super(null, questionName, filePath, grade, maxPoints, true);
	} 
	
	public Answer(String questionName, int grade, int maxPoints)
	{
		super(null, questionName, null, grade, maxPoints, true);
		this.questionName = questionName;
		this.grade = grade;
		this.maxPoints = maxPoints;
	}
	
	public Answer(String user, String questionName, int maxPoints)
	{
		super(null, questionName, null, -1, maxPoints, false);
		this.username = user;
	}
	
	public Answer(String questionName, int maxPoints)
	{
		super(null, questionName, null, -1, maxPoints, false);
	}
	
	public String getUser()
	{
		return username;
	}
	
	public String toString()
	{
		String name = L.fitToLength(23, questionName.toUpperCase());
		String points = L.fitToLength(10, grade + "/" + maxPoints);
		return name + "    ~ " + points; 
	}
}
