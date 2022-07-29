package pages;

public class Answer 
{
	String questionName;
	int grade = -1;
	int maxPoints;
	
	public Answer(String questionName, int grade, int maxPoints)
	{
		this.questionName = questionName;
		this.grade = grade;
		this.maxPoints = maxPoints;
	}
	
	public Answer(String questionName, int maxPoints)
	{
		this.questionName = questionName;
		this.maxPoints = maxPoints;
	}
	
	public String getName()
	{
		return questionName;
	}
	
	public String getGrade() 
	{
		String finalGrade = grade + "/" + maxPoints;
		return finalGrade;
	}
	
	public int getPoints()
	{
		return maxPoints;
	}
	
	public String toString()
	{
		String str = "";
		
		str = str + questionName.toUpperCase() + "\n";
		str = str + grade + "/" + maxPoints;
		
		return str;
	}
}
