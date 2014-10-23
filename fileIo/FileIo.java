package fileIo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import dataEncapsulation.Task;
import dataManipulation.TotalTaskList;

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
	private TextIoStream fileStream;
	private TotalTaskList list = TotalTaskList.getInstance();
	private TaskFileReader reader = TaskFileReader.getInstance();
	
	private static FileIo fileIo;
	
	public FileIo() {
		try {
			fileStream = new TextIoStream(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static FileIo getInstance() {
		if (fileIo == null) {
			fileIo = new FileIo();
		}
		
		return fileIo;
	}
	
	// Takes the lines of text from the task file and creates tasks from them.
	public List<Task> getAllTasks(List<String> linesOfText) {
		List<Task> taskList = reader.getAllTasks(linesOfText);
		return taskList;
	}
	
	// Takes the lines of text from the task file and creates tasks from them.
		public void rewriteFile() {
			try {
				fileStream.rewriteFile(list.getList());
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
	
	public void initializeTaskList(List<Task> taskList) {
		List<String> fileContents;
		try {
			fileContents = fileStream.readFromFile();
			taskList.addAll(reader.getAllTasks(fileContents));
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
