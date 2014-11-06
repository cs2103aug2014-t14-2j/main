package fileIo;

import java.util.ArrayList;
import java.util.List;

import userInterface.Autocomplete;
import dataEncapsulation.Date;
import dataEncapsulation.Task;
import dataManipulation.TaskFactory;

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
 */

//@author A0126720N

public class TaskFileReader {
	private static TaskFileReader tfr;
	
	private TaskFileReader() {}
	
	public static TaskFileReader getInstance() {
		if (tfr == null) {
			tfr = new TaskFileReader();
		}
		
		return tfr;
	}
	
	
	// integers are indexes for their corresponding location in the task 
	// component list
	public enum TASK_COMPONENT {
		NAME(0), CATEGORY(1), START(2), END(3), LOCATION(4), NOTE(5), 
		COMPLETED(6), START_TIME(7), END_TIME(8);
		private int value;
		
		private TASK_COMPONENT(int val) {
			value = val;
		}
		
		public int getIndex() {
			return value;
		}
	}
	
	private final String LINE_TITLE_DELIMITER = ":";
	private final String EMPTY_STRING = "";
	
	private final String MESSAGE_NO_END = "No Specified End Date";
	private final String MESSAGE_NO_LOCATION = "No Specified Location";
	private final String MESSAGE_NO_NOTE = "No Specified Note";
	private final String MESSAGE_COMPLETED = "Yes";
	
	private final int FIRST_POSITION = 0;

	private List<String> taskComponents = new ArrayList<String>();
	private TaskFactory factory = TaskFactory.getInstance();

	public List<Task> getAllTasks(List<String> componentsFromFile) throws Exception {
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

	private Task createTask() throws Exception {
		checkForMissingComponents();
		
		String name = getName();
		String category = getCategory();
		String location = getLocation();
		String note = getNote();
		String startDateString = getStartDateString();
		String endDateString = getEndDateString();
		String completed = getCompleted();
		
		Date start = (new Date()).determineDate(startDateString);
		Date end = (new Date()).determineDate(endDateString);
		
		Task newTask = factory.makeTask(name, category, location, note, start, end, null, null);
		addToAutocomplete(name, category, location);
		
		if (completed.equalsIgnoreCase(MESSAGE_COMPLETED)) {
			newTask.setComplete();
		}
		
		return newTask;
	}

	private void addToAutocomplete(String name,
			String category, String location) {
		Autocomplete autocomplete = Autocomplete.getInstance();
		
		if (category != null) {
			autocomplete.addCategory(category);
		}
		if (location != null) {
			autocomplete.addLocation(location);
		}
		if (name != null) {
			autocomplete.addTitle(name);
		}
	}

	// sets missing components to null, as expected by TaskFactory
	private void checkForMissingComponents() {
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

	// adds the component from the line to the list of task components, in its
	// designated location
	private void addComponent(String currentLine) {
		TASK_COMPONENT component = determineComponentType(currentLine);
		int index = component.getIndex();
		String componentData = getComponentData(currentLine);
		
		if (component == TASK_COMPONENT.START) {
			component = TASK_COMPONENT.START_TIME;
			index = component.getIndex();
			componentData = getTimeData(currentLine);
			
			taskComponents.set(index, componentData);
			
			componentData = getDateData(currentLine);
		}
			
		taskComponents.set(index, componentData);
		
		return;
	}

	private String getDateData(String currentLine) {
		String[] split = currentLine.split("@");
		String time = split[0];
		time = time.trim();
		return time;
	}

	private String getTimeData(String currentLine) {
		String[] split = currentLine.split("@");
		String time = split[1];
		time = time.trim();
		return time;
	}

	// made public for testing purposes. May have use outside of this class
	public TASK_COMPONENT determineComponentType(String string) {
		String lineTitle = getFirstWord(string);
		TASK_COMPONENT lineType = interpretTitle(lineTitle);
		return lineType;
	}
	
	// made public for testing purposes. May have use outside of this class
	public String getComponentData(String currentLine) {
		String lineTitle = getFirstWord(currentLine) + LINE_TITLE_DELIMITER;
		
		String lineData = 
				currentLine.replaceFirst(lineTitle, EMPTY_STRING);
		
		String noWhitespaceData = lineData.trim();
		
		return noWhitespaceData;
	}

	// made public for testing purposes. May have use outside of this class
	public String getFirstWord(String string) {
		String[] stringDividedAtFirstWord = string.split(LINE_TITLE_DELIMITER);
		
		String lineTitle = stringDividedAtFirstWord[FIRST_POSITION];
		
		return lineTitle;
	}
	
	// made public for testing purposes. May have use outside of this class
	public TASK_COMPONENT interpretTitle(String lineTitle) {
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
		} else if (lineTitle.equalsIgnoreCase("completed")) {
			return TASK_COMPONENT.COMPLETED;
		} else {
			System.out.println(lineTitle);
			throw new RuntimeException("Invalid task in file");
		}
	}

	@SuppressWarnings("unused")
	// resets the task component list to have a space for each component
	private void clearTaskComponents() {
		taskComponents.clear();
		
		for (TASK_COMPONENT i : TASK_COMPONENT.values()) {
			taskComponents.add(null);
		}
		
		return;
	}
	
	private String getName() {
		return taskComponents.get(TASK_COMPONENT.NAME.getIndex());
	}
	
	private String getCategory() {
		return taskComponents.get(TASK_COMPONENT.CATEGORY.getIndex());
	}
	
	private String getLocation() {
		return taskComponents.get(TASK_COMPONENT.LOCATION.getIndex());
	}
	
	private String getNote() {
		return taskComponents.get(TASK_COMPONENT.NOTE.getIndex());
	}
	
	private String getStartDateString() {
		return taskComponents.get(TASK_COMPONENT.START.getIndex());
	}
	
	private String getEndDateString() {
		return taskComponents.get(TASK_COMPONENT.END.getIndex());
	}
	
	private String getCompleted() {
		return taskComponents.get(TASK_COMPONENT.COMPLETED.getIndex());
	}

}