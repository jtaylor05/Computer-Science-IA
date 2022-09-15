package pages;

public class Record 
{
	protected String ID;
	protected String name;
	protected String filePath;
	protected int grade;
	protected int maxPoints;
	protected boolean hasAnswer;
	
	public Record(String ID, String name, String filePath, int grade, int maxPoints, boolean hasAnswer)
	{
		this.ID = ID;
		this.name = name;
		this.filePath = filePath;
		this.grade = grade;
		this.maxPoints = maxPoints;
		this.hasAnswer = hasAnswer;
	}

	public String getID()
	{
		return ID;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getPath()
	{
		return filePath;
	}
	
	public int getMaxPoints()
	{
		return maxPoints;
	}
	
	public int getGrade()
	{
		return grade;
	}
	
	public String getOutOf()
	{
		return grade + "/" + maxPoints;
	}
	
	public boolean hasAnswer()
	{
		return hasAnswer;
	}
}
