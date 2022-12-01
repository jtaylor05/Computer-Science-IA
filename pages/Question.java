package pages;

/**
 * Question class holds all relevant Question data. Extends Record class.
 */
public class Question extends Record
{
	private boolean teacher = false;
	
	/**
	 * @param ID String ID of question
	 * @param name String name of question
	 * @param filePath String file path of question image
	 * @param maxPoints int max points of questions
	 */
	public Question(String ID, String name, String filePath, int maxPoints)
	{
		super(ID, name, filePath, -1, maxPoints, false);
	}
		
	/**
	 * @param ID String ID of question
	 * @param name String name of question
	 * @param filePath String file path of question image
	 * @param grade int grade of current relavent answer of question
	 * @param maxPoints int max points of question
	 */
	public Question(String ID, String name, String filePath, int grade, int maxPoints)
	{
		super(ID, name, filePath, grade, maxPoints, true);
	}
		
	/**
	 * @param ID String ID of question
	 * @param name String name of question
	 * @param maxPoints int max points of question
	 */ 
	public Question(String ID, String name, int maxPoints)
	{
		super(ID, name, null, -1, maxPoints, false);
		teacher = true;
	}
	
	/**
	 * @return whether Question was made for teacher user
	 */
	public boolean isTeacher()
	{
		return teacher;
	}
	
	/**
	 * Prints Question in proper form
	 */
	@Override
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
