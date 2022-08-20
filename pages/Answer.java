package pages;

public class Answer 
{
	String username;
	String questionName;
	int grade = -1;
	int maxPoints;
	String filePath;
	boolean hasAnswer = true;
	
	public Answer(String user, String questionName, String filePath, int grade, int maxPoints)
	{
		this.username = user;
		this.questionName = questionName;
		this.grade = grade;
		this.maxPoints = maxPoints;
		this.filePath = filePath;
	} 
	
	public Answer(String questionName, String filePath, int grade, int maxPoints)
	{
		this.questionName = questionName;
		this.grade = grade;
		this.maxPoints = maxPoints;
		this.filePath = filePath;
	} 
	
	public Answer(String questionName, int grade, int maxPoints)
	{
		this.questionName = questionName;
		this.grade = grade;
		this.maxPoints = maxPoints;
	}
	
	public Answer(String user, String questionName, int maxPoints)
	{
		this.username = user;
		this.questionName = questionName;
		this.maxPoints = maxPoints;
		hasAnswer = false;
	}
	
	public Answer(String questionName, int maxPoints)
	{
		this.questionName = questionName;
		this.maxPoints = maxPoints;
		hasAnswer = false;
	}
	
	public String getName()
	{
		return questionName;
	}
	
	public String getOutOf() 
	{
		String finalGrade = grade + "/" + maxPoints;
		return finalGrade;
	}
	
	public int getPoints()
	{
		return maxPoints;
	}
	
	public int getGrade()
	{
		return grade;
	}
	
	public String getPath()
	{
		return filePath;
	}
	
	public String getUser()
	{
		return username;
	}
	
	public boolean hasAnswer()
	{
		return hasAnswer;
	}
	
	public String toString()
	{
		String str = "";
		
		str = str + questionName.toUpperCase() + "\n";
		str = str + grade + "/" + maxPoints;
		
		return str;
	}
}
