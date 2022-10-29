package pages;

import library.L;

public class Answer extends Record
{
	private String username;
	
	//constructors of Answer object
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
	
	//overrides toString()
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
