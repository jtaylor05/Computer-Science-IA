package pages;

public class Question 
{
	/*
	 * questionFilePath - String file path showing which question it is
	 * ID - int question number
	 * 
	 */
	
	String questionFilePath;
	
	static int ID;
	
	public Question(String filePath)
	{
		questionFilePath = filePath;
		
		ID++;
	}
	
	public String getFilePath()
	{ return questionFilePath; }
	
	public int getID()
	{ return ID; }

}
