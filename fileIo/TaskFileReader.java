package fileIo;

import globalClasses.Date;
import globalClasses.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * This class reads the file contents from the ezC task storage file.
 * It does not anticipate incorrect formatting and will throw an exception when
 * it encounters a bad file line.
 * 
 * Note: It would be better to report to the user which task is incorrectly
 * formatted, and then to just skip that task and let the user deal with it
 * himself. Perhaps give the option of deleting the task or replacing the task
 * with a valid one. (dump the invalid lines on the screen for the user to see.
 * After dumping, once more give the option of just deleting the garbage if it
 * is unusable to the user)
 * 
 * @author Natalie Rawle
 *
 */

public class TaskFileReader {
	
	// integers are indexes for their corresponding location in the task 
	// component list
	public enum TASK_COMPONENT {
		NAME(0), CATEGORY(1), START(2), END(3), LOCATION(4), NOTE(5);
		private int value;
		
		private TASK_COMPONENT(int val) {
			value = val;
		}
		
		public int getIndex() {
			return value;
		}
	}
	
	private static final String LINE_TITLE_DELIMITER = ":";
	private static final String EMPTY_STRING = "";
	
	private static final String MESSAGE_NO_END = "No Specified End Date";
	private static final String MESSAGE_NO_LOCATION = "This Task has no specified Location.";
	private static final String MESSAGE_NO_NOTE = "This Task has no specified Note.";
	
	private static final int FIRST_POSITION = 0;

	private static List<String> taskComponents = new ArrayList<String>();

	public static List<Task> getAllTasks(List<String> componentsFromFile) {
		List<Task> taskList = new ArrayList<Task>();
		clearTaskComponents();
		
		for (int i = 0; i < componentsFromFile.size(); ++i) {
			String currentLine = componentsFromFile.get(i);
			
			if (currentLine.equals(EMPTY_STRING)) {
				Task newTask = createTask();
				if (newTask != null) {
					taskList.add(newTask);
				}
				clearTaskComponents();
			} else {
				addComponent(currentLine);
			}
		}
		
		return taskList;
	}

	private static Task createTask() {
		checkForMissingComponents();
		
		String name = getName();
		String category = getCategory();
		String location = getLocation();
		String note = getNote();
		String startDateString = getStartDateString();
		String endDateString = getEndDateString();
		
		Date start = determineDate(startDateString);
		Date end = determineDate(endDateString);
		
		Task newTask = dataManipulation.TaskFactory.makeTask(name, category, location, note, 
				start, end);
		return newTask;
	}

	// sets missing components to null, as expected by TaskFactory
	private static void checkForMissingComponents() {
		String location = getLocation();
		String note = getNote();
		String end = getEndDateString();
		
		if (end.equals(MESSAGE_NO_END)) {
			taskComponents.set(TASK_COMPONENT.END.getIndex(), null);
		}
		
		if (location.equals(MESSAGE_NO_LOCATION)) {
			taskComponents.set(TASK_COMPONENT.LOCATION.getIndex(), null);
		}
		
		if (note.equals(MESSAGE_NO_NOTE)) {
			taskComponents.set(TASK_COMPONENT.NOTE.getIndex(), null);
		}
		
		return;
		
	}

	// by Yui- taken directly from ezC
	public static Date determineDate(String dateString) {
		String month, day, year;
		int mm, dd, yyyy;
		if (dateString.contains(",")) {
			int space = dateString.indexOf(' ');
			int comma = dateString.indexOf(',');
			month = dateString.substring(0, space);
			day = dateString.substring(space + 1, comma);
			year = dateString.substring(comma);
			year = cleanUpYear(year);
			mm = monthInteger(month);
		} else if (dateString.contains("/")) {
			String dateStr[] = dateString.split("/", 3);
			day = dateStr[0];
			month = dateStr[1];
			year = dateStr[2];
			mm = Integer.parseInt(month);
		} else {
			String dateStr[] = dateString.split(" ", 3);
			day = dateStr[0];
			month = dateStr[1];
			year = dateStr[2];
			mm = Integer.parseInt(month);
		}
		dd = Integer.parseInt(day);
		yyyy = Integer.parseInt(year);
		
		Date userDate = new Date(dd, mm, yyyy);
		return userDate;
	}
	
	private static String cleanUpYear(String year) {
		String space = " ";
		String comma = ",";
		String emptyString = new String();
		
		if (year.contains(space)) {
			year = year.replace(space, emptyString);
		}
		
		if (year.contains(comma)) {
			year = year.replace(comma, emptyString);
		}
		
		return year;
	}

	// Also by Yui - should be in Date class as an enum
	public static int monthInteger(String month1) {
		int monthNum;
		switch (month1.toLowerCase()) {
			case "january" :		
				monthNum = 1; 			
				break;
			case "february":		
				monthNum = 2;				
				break;
			case "march":			
				monthNum = 3;				
				break;
			case "april":			
				monthNum = 4;				
				break;
			case "may":				
				monthNum = 5;				
				break;
			case "june":			
				monthNum = 6;				
				break;
			case "july":			
				monthNum = 7;				
				break;
			case "august":			
				monthNum = 8;				
				break;
			case "september":		
				monthNum = 9;				
				break;
			case "october":			
				monthNum = 10;				
				break;
			case "november":		
				monthNum = 11;				
				break;
			case "december":		
				monthNum = 12;				
				break;
			default: 				
				monthNum = 0;				
				break;
		}
		return monthNum;
	}

	// adds the component from the line to the list of task components, in its
	// designated location
	private static void addComponent(String currentLine) {
		TASK_COMPONENT component = determineComponentType(currentLine);
		int index = component.getIndex();
		String componentData = getComponentData(currentLine);
		
		taskComponents.set(index, componentData);
		return;
	}

	// made public for testing purposes. May have use outside of this class
	public static TASK_COMPONENT determineComponentType(String string) {
		String lineTitle = getFirstWord(string);
		TASK_COMPONENT lineType = interpretTitle(lineTitle);
		return lineType;
	}
	
	// made public for testing purposes. May have use outside of this class
	public static String getComponentData(String currentLine) {
		String lineTitle = getFirstWord(currentLine) + LINE_TITLE_DELIMITER;
		
		String lineData = 
				currentLine.replaceFirst(lineTitle, EMPTY_STRING);
		
		String noWhitespaceData = lineData.trim();
		
		return noWhitespaceData;
	}

	// made public for testing purposes. May have use outside of this class
	public static String getFirstWord(String string) {
		String[] stringDividedAtFirstWord = string.split(LINE_TITLE_DELIMITER);
		
		String lineTitle = stringDividedAtFirstWord[FIRST_POSITION];
		
		return lineTitle;
	}
	
	// made public for testing purposes. May have use outside of this class
	public static TASK_COMPONENT interpretTitle(String lineTitle) {
		if (lineTitle.equalsIgnoreCase("task")) {
			return TASK_COMPONENT.NAME;
		} else if (lineTitle.equalsIgnoreCase("category")) {
			return TASK_COMPONENT.CATEGORY;
		} else if (lineTitle.equalsIgnoreCase("start")) {
			return TASK_COMPONENT.START;
		} else if (lineTitle.equalsIgnoreCase("end")) {
			return TASK_COMPONENT.END;
		} else if (lineTitle.equalsIgnoreCase("location")) {
			return TASK_COMPONENT.LOCATION;
		} else if (lineTitle.equalsIgnoreCase("note")) {
			return TASK_COMPONENT.NOTE;
		} else {
			throw new RuntimeException("Invalid task in file");
		}
	}

	@SuppressWarnings("unused")
	// resets the task component list to have a space for each component
	private static void clearTaskComponents() {
		taskComponents.clear();
		
		for (TASK_COMPONENT i : TASK_COMPONENT.values()) {
			taskComponents.add(null);
		}
		
		return;
	}
	
	private static String getName() {
		return taskComponents.get(TASK_COMPONENT.NAME.getIndex());
	}
	
	private static String getCategory() {
		return taskComponents.get(TASK_COMPONENT.CATEGORY.getIndex());
	}
	
	private static String getLocation() {
		return taskComponents.get(TASK_COMPONENT.LOCATION.getIndex());
	}
	
	private static String getNote() {
		return taskComponents.get(TASK_COMPONENT.NOTE.getIndex());
	}
	
	private static String getStartDateString() {
		return taskComponents.get(TASK_COMPONENT.START.getIndex());
	}
	
	private static String getEndDateString() {
		return taskComponents.get(TASK_COMPONENT.END.getIndex());
	}

}