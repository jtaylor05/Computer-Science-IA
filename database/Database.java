package database;

import java.io.File;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.nio.file.Files;

import library.L;

public class Database 
{
	protected static FileWriter fw;
	protected static RandomAccessFile raf;
	
	protected final static int LENGTH_OF_USERID = 37;
	protected final static int LENGTH_OF_QUESTIONID = 36;
	protected final static int LENGTH_OF_NAME = 23;
	protected final static int LENGTH_OF_PATH = 80;
	protected final static int LENGTH_OF_GRADE = 10;
	
	protected static <T> void write(T s, String DATABASE_FILE_PATH)
	{
		try
		{
			fw = new FileWriter(DATABASE_FILE_PATH, true);
			
			fw.write("" + s);
			
			fw.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}		
	}
	
	protected static <T> void writeAt(T s, int index, int LENGTH_OF_S, String DATABASE_FILE_PATH, int begin, int LENGTH_OF_FILE)
	{
		String str = L.fitToLength(LENGTH_OF_S, "" + s);
		try
		{
			raf = new RandomAccessFile(DATABASE_FILE_PATH, "rw");
			if(LENGTH_OF_FILE * index < raf.length())
			{
				raf.seek(LENGTH_OF_FILE * index + begin);
				raf.writeBytes(str);
			}
			raf.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	protected static void remove(int index, String DATABASE_FILE_PATH, int LENGTH_OF_FILE)
	{
		File temp = new File("database/temp");
		File database = new File(DATABASE_FILE_PATH);
		try
		{
			raf = new RandomAccessFile(database, "r");
			fw = new FileWriter(temp, true);
			int count;
			int length = (int)raf.length()/LENGTH_OF_FILE;
			String line = raf.readLine();
			for(count = 0; count < length; count++)
			{
				if(count != index)
				{
					fw.write(line + "\n");
				}
				line = raf.readLine();
			}
			raf.close();
			fw.close();
			
			Files.delete(database.toPath());
			temp.renameTo(database);
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}	
	}
	
	protected static int getIndex(String st, int LENGTH_OF_ST, String DATABASE_FILE_PATH, int begin, int end, int LENGTH_OF_FILE)
	{
		String fixedST = L.fitToLength(LENGTH_OF_ST, st);
		
		String s = "";
		int index = -1;
		try
		{
			raf = new RandomAccessFile(DATABASE_FILE_PATH, "rw");
			int length = (int)raf.length();
			while(raf.getFilePointer() < length)
			{
				index = index + 1;
				
				String line = raf.readLine();
				s = line.substring(begin, end);
				if(s.equals(fixedST))
				{
					return index;
				}
				
				raf.seek(LENGTH_OF_FILE * (index + 1));
			}
			raf.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return -1;
	}
	
	protected static int getIndex(String st, int LENGTH_OF_ST, String DATABASE_FILE_PATH, int begin, int end, int LENGTH_OF_FILE, int minIndex)
	{
		String fixedST = L.fitToLength(LENGTH_OF_ST, st);
		String s = "";
		int index = minIndex;
		try
		{
			raf = new RandomAccessFile(DATABASE_FILE_PATH, "rw");
			raf.seek(LENGTH_OF_FILE * index);
			int length = (int)raf.length();
			while(raf.getFilePointer() < length)
			{
					
				String line = raf.readLine();
				s = line.substring(begin, end);
				if(s.equals(fixedST))
				{
					return index;
				}
				index = index + 1;
				raf.seek(LENGTH_OF_FILE * index);
			}
			raf.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
				
		return -1;
	}
	
	protected static String get(int index, String DATABASE_FILE_PATH, int begin, int end, int LENGTH_OF_FILE)
	{
		String data = "";
		try
		{
			raf = new RandomAccessFile(DATABASE_FILE_PATH, "rw");
			if(LENGTH_OF_FILE * index < raf.length())
			{
				raf.seek(LENGTH_OF_FILE * index);
			}
			else
			{
				return null;
			}
			
			String line = raf.readLine();
			data = line.substring(begin, end);
			raf.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return L.shear(data);
	}
	
	protected static int countEntries(String DATABASE_FILE_PATH, int LENGTH_OF_FILE)
	{
		int count = 0;
		try
		{
			raf = new RandomAccessFile(DATABASE_FILE_PATH, "rw");
			int length = (int)raf.length();
			while(raf.getFilePointer() < length)
			{
				count = count + 1;
				raf.seek(LENGTH_OF_FILE * count);
			}
			raf.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return count;
	}
}