package pages;

public class Question extends Record
{
	private boolean teacher = false;
	
	
	public Question(String ID, String name, String filePath, int maxPoints)
	{
		super(ID, name, filePath, -1, maxPoints, false);
	}
		
	public Question(String ID, String name, String filePath, int grade, int maxPoints)
	{
		super(ID, name, filePath, grade, maxPoints, true);
	}
		
	public Question(String ID, String name, int maxPoints)
	{
		super(ID, name, null, -1, maxPoints, false);
		teacher = true;
	}
	
	public boolean isTeacher()
	{
		return teacher;
	}
	
	public String toString()
	{
		String str;
		if(hasAnswer)
		{
			str = name + " : (" + grade + ") ";
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
