package pages;

public class Answer 
{
	String questionName;
	int grade;
	int maxPoints;
	
	public Answer(String questionName, int grade, int maxPoints)
	{
		this.questionName = questionName;
		this.grade = grade;
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
}
