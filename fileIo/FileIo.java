package fileIo;

import globalClasses.Task;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/*
 * This class acts as the interface between the File IO component
 * and the Main and Data Manipulation components. The other
 * classes within the file IO package can be accessed through here.
 * 
 * Its usefulness is that we don't have to deal with the components
 * of the entire class and can neatly see the methods we care about.
 */

public class FileIo {
	private String fileName = "ezCTasks.txt";
	private static TextIoStream fileStream;
	
	public FileIo() {
		try {
			fileStream = new TextIoStream(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Takes the lines of text from the task file and creates tasks from them.
	public static List<Task> getAllTasks(List<String> linesOfText) {
		List<Task> taskList = TaskFileReader.getAllTasks(linesOfText);
		return taskList;
	}
	
	// Takes the lines of text from the task file and creates tasks from them.
		public void rewriteFile(List<Task> taskList) {
			try {
				fileStream.rewriteFile(taskList);
				return;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	/*
	 * Returns the contents from the task file as a list of strings with each
	 * index in the list corresponding to the matching line in the file.
	 * If there is an error, it returns null.
	 */
	@SuppressWarnings("finally")
	public List<String> readFromFile() {
		List<String> lines;
		try {
			lines = fileStream.readFromFile();
			return lines;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return null;	// change later - never return from finally
		}
	}
	
	public static void initializeTaskList(List<Task> taskList) {
		List<String> fileContents;
		try {
			fileContents = fileStream.readFromFile();
			taskList = TaskFileReader.getAllTasks(fileContents);
			return;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}