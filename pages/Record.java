package pages;

/**
 * Abstract Record class.
 */
public abstract class Record 
{
	protected String ID;
	protected String name;
	protected String filePath;
	protected int grade;
	protected int maxPoints;
	protected boolean hasAnswer;
	
	/**
	 * @param ID String relevant ID type
	 * @param name String relevant name type
	 * @param filePath String relevant image file path type
	 * @param grade int grade of item
	 * @param maxPoints int max points of item
	 * @param hasAnswer boolean value of whether data has answer
	 */
	public Record(String ID, String name, String filePath, int grade, int maxPoints, boolean hasAnswer)
	{
		this.ID = ID;
		this.name = name;
		this.filePath = filePath;
		this.grade = grade;
		this.maxPoints = maxPoints;
		this.hasAnswer = hasAnswer;
	}

	/**
	 * @return String relevant ID
	 */
	public String getID()
	{
		return ID;
	}
	
	/**
	 * @return String relevant name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * @return String relevant file path
	 */
	public String getPath()
	{
		return filePath;
	}
	
	/**
	 * @return int max points of item
	 */
	public int getMaxPoints()
	{
		return maxPoints;
	}
	
	/**
	 * @return int grade of item
	 */
	public int getGrade()
	{
		return grade;
	}
	
	/**
	 * @return String of how many points out of max points grade is
	 */
	public String getOutOf()
	{
		return grade + "/" + maxPoints;
	}
	
	/**
	 * @return boolean value of whether item has answer
	 */
	public boolean hasAnswer()
	{
		return hasAnswer;
	}
	
	/**
	 * abstract toString class for each class
	 */
	public abstract String toString();
}
