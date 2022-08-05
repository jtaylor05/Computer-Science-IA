package pages;

public class Question 
{
	private boolean teacher = false;
	private boolean hasAnswer = false;
	
	private String ID;
	private String name;
	private String filePath;
	private String grade;
	private String message = "not graded";
	private int maxPoints;
	
	
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
		this.message = message;
		hasAnswer = true;
	}
		
	public Question(String ID, String name, int maxPoints)
	{
		this.ID = ID;
		this.name = name;
		this.maxPoints = maxPoints;
		teacher = true;
	}
		
	public String getID()
	{ return ID; }
	
	public String getName()
	{ return name; }
	
	public int getPoints()
	{ return maxPoints; }
	
	public String getPath()
	{ return filePath; }
	
	public boolean hasAnswered()
	{ return hasAnswer; }
	
	public String getGrade()
	{ return grade; }
	
	public String getMessage()
	{ return message; }
	
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
