package pages;

public class Question extends Record
{
	private boolean teacher = false;
	private String message = "not graded";
	
	
	public Question(String ID, String name, String filePath, int maxPoints, String message)
	{
		super(ID, name, filePath, -1, maxPoints, false);
		this.message = message;
	}
		
	public Question(String ID, String name, String filePath, int grade, int maxPoints, String message)
	{
		super(ID, name, filePath, grade, maxPoints, true);
		this.message = message;
	}
		
	public Question(String ID, String name, int maxPoints)
	{
		super(ID, name, null, -1, maxPoints, false);
		teacher = true;
	}
	
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
