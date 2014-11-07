package fileIo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import dataEncapsulation.Date;
import dataEncapsulation.Task;
import dataEncapsulation.TaskFileErrorException;
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
	public List<Task> getAllTasks(List<String> linesOfText) throws Exception {
		List<Task> taskList = reader.getAllTasks(linesOfText);
		return taskList;
	}

	// Takes the lines of text from the task file and creates tasks from them.
	public void rewriteFile() {
		try {
			fileStream.rewriteFile(list.getAllTasks());
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
	public List<String> readFromFile() throws FileNotFoundException, IOException {
		List<String> lines;
		lines = fileStream.readFromFile();
		return lines;
	}

	public void initializeTaskList(TotalTaskList taskList) throws Exception {
		List<String> fileContents;
		TaskFileErrorException badFile = null;

		fileContents = fileStream.readFromFile();
		Date today = new Date();
		List<Task> tasksFromFile;
		try {
			tasksFromFile = reader.getAllTasks(fileContents);
		} catch (TaskFileErrorException e) {
			tasksFromFile = e.getTaskList();
			badFile = e;
		}
		for(Task t : tasksFromFile) {
			if(t.getIsComplete() == false && !(t.getEndDate().isBefore(today))) {
				taskList.addNotCompleted(t);
			}
			else if(t.getIsComplete() == true) {
				taskList.addCompleted(t);
			}
			else if(t.getIsComplete() == false && t.getEndDate().isBefore(today)) {
				taskList.addOverdue(t);
			}
		}

		if (badFile != null) {
			throw badFile;
		}

		return;
	}

	public void recover(TaskFileErrorException e) throws FileNotFoundException, IOException {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date day = new Date();
		Calendar cal = Calendar.getInstance();
		fileStream.rewriteFile(e.getTaskList());
		TextIoStream errorFileStream = 
				new TextIoStream("RecoveredTaskFile.txt");
		errorFileStream.appendFile("\nDate of error detection: " + day + " @ "
				+ dateFormat.format(cal.getTime()) + "\n");
		errorFileStream.appendFile(e.getErrorList());
	}

}
