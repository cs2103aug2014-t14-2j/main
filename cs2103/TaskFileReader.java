package cs2103;

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
		
		for (int i = 0; i < componentsFromFile.size(); ++i) {
			String currentLine = componentsFromFile.get(i);
			
			if (currentLine.equals(EMPTY_STRING)) {
				Task newTask = createTask();
				if (newTask != null) {
					taskList.add(newTask);
				}
			} else {
				clearTaskComponents();
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
		
		Task newTask = TaskFactory.makeTask(name, category, location, note, 
				start, end);
		return newTask;
	}

	// sets missing components to null, as expected by TaskFactory
	private static void checkForMissingComponents() {
		String location = getLocation();
		String note = getNote();
		String end = getEndDateString();
		
		if ( end.equals(MESSAGE_NO_END)) {
			taskComponents.set(TASK_COMPONENT.END.getIndex(), null);
		}
		
		if ( location.equals(MESSAGE_NO_LOCATION)) {
			taskComponents.set(TASK_COMPONENT.LOCATION.getIndex(), null);
		}
		
		if ( note.equals(MESSAGE_NO_NOTE)) {
			taskComponents.set(TASK_COMPONENT.NOTE.getIndex(), null);
		}
		
		return;
		
	}

	// not to be used - waiting for Yui's version
	private static Date determineDate(String startDateString) {
		Date date = new Date(1,1,1);
		return date;
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

	public static TASK_COMPONENT determineComponentType(String string) {
		String lineTitle = getFirstWord(string);
		TASK_COMPONENT lineType = interpretTitle(lineTitle);
		return lineType;
	}
	
	public static String getComponentData(String currentLine) {
		String lineTitle = getFirstWord(currentLine) + LINE_TITLE_DELIMITER;
		
		String lineData = 
				currentLine.replaceFirst(lineTitle, EMPTY_STRING);
		
		String noWhitespaceData = lineData.trim();
		
		return noWhitespaceData;
	}

	public static String getFirstWord(String string) {
		String[] stringDividedAtFirstWord = string.split(LINE_TITLE_DELIMITER);
		
		String lineTitle = stringDividedAtFirstWord[FIRST_POSITION];
		
		return lineTitle;
	}
	
	public static TASK_COMPONENT interpretTitle(String lineTitle) {
		if (lineTitle.equalsIgnoreCase("name")) {
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