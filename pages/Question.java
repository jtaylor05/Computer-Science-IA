package pages;

public class Question 
{
	
	String name;
	String filePath;
	int maxPoints = -1;
	String grade = "no grade";
	String message;
	
	public Question(String name, String filePath, int maxPoints, String message)
	{
		this.name = name;
		this.filePath = filePath;
		this.maxPoints = maxPoints;
		this.message = message;
	}
	
	public Question(String name, String filePath, String grade, String message)
	{
		this.name = name;
		this.filePath = filePath;
		this.grade = grade;
		this.message = message;
	}
}
