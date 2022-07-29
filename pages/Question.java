package pages;

public class Question 
{
	String ID;
	
	String name;
	String filePath;
	int maxPoints = -1;
	String grade = "ungraded";
	boolean hasAnswer = false;
	String message;
	boolean teacher = false;
	
	public Question(String ID, String name, String filePath, int maxPoints, String message)
	{
		this.ID = ID;
		this.name = name;
		this.filePath = filePath;
		this.maxPoints = maxPoints;
		this.message = message;
	}
	
	public Question(String ID, String name, String filePath, String grade, String message)
	{
		this.ID = ID;
		this.name = name;
		this.filePath = filePath;
		this.grade = grade;
		hasAnswer = true;
		this.message = message;
	}
	
	public Question(String ID, String name, int maxPoints)
	{
		this.ID = ID;
		this.name = name;
		this.maxPoints = maxPoints;
		teacher = true;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getFilePath()
	{
		return filePath;
	}
	
	public String getGrade()
	{
		return grade;
	}
	
	public int getPoints()
	{
		return maxPoints;
	}
	
	public String getID()
	{
		return ID;
	}
	
	public String toString()
	{
		String str;
		if(hasAnswer)
		{
			str = name + " : (" + grade + ") " + message;
		}
		else if(teacher)
		{
			str = name;
		}
		else
		{
			str = name + " : (" + grade + ")"; 
		}
		
		return str;
	}
}
