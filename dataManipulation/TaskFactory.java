package dataManipulation;

import dataEncapsulation.Date;
import dataEncapsulation.Task;

/*
 * Decides which constructor of Task to call based on which arguments are
 * available. Expects unavailable data in its arguments to be indicated by
 * null.
 * 
 * Mandatory arguments: name, category, startTime
 * Optional arguments: location, note, endTime
 * 
 * author: Natalie Rawle
 */

public class TaskFactory {
	private static TaskFactory factory;
	
	private TaskFactory() {}
	
	public static TaskFactory getInstance() {
		if (factory == null) {
			factory = new TaskFactory();
		}
		
		return factory;
	}
	
	
	// expects null arguments if location, note, startTime, or endTime are not 
	// specified
	public Task makeTask(String name, String category, 
			String location, String note, Date startTime, Date endTime) 
					throws IllegalArgumentException {
		category = checkVitalComponents(name, category);
		
		Task newTask;
		
		if (location == null) {
			newTask = makeNoLocationTask(name, category, note, startTime, 
					endTime);
		} else {
			newTask = makeLocationTask(name, category, location, note, 
					startTime, endTime);
		}
		
		return newTask;
	}

	/**
	 * Ensures that no task will ever be without a name or category, which are
	 * not optional for the class. If no category is specified (as it IS
	 * optional for the user), it gives it the category of "no category".
	 * @param name
	 * @param category
	 * @return the value that category should be
	 */
	private String checkVitalComponents(String name, String category) 
			throws IllegalArgumentException {
		if (name == null) {
			throw new IllegalArgumentException("task does not have name");
		}
			
		if (category == null) {
			return "no category";
		}
		
		return category;
	}

	// Has: location
	// Does not have:
	// May have: note, startTime, endTime
	private Task makeLocationTask(String name, String category,
			String location, String note, Date startTime, Date endTime) {
		Task newTask;
		
		if (note == null) {
			newTask = makeNoNoteTask(name, category, location, startTime, 
					endTime);
		} else {
			newTask = makeNoteTask(name, category, location, note, startTime, 
					endTime);
		}

		return newTask;
	}
	
	// Has:
	// Does not have: location
	// May have: note, startTime, endTime
	private Task makeNoLocationTask(String name, String category,
			String note, Date startTime, Date endTime) {
		Task newTask;
		
		if (note == null) {
			newTask = makeNoNoteTask(name, category, startTime, endTime);
		} else {
			newTask = makeNoteTask(name, category, note, startTime, endTime);
		}

		return newTask;
	}
	
	// Has: location, note
	// Does not have:
	// May have: startTime, endTime
	private Task makeNoteTask(String name, String category,
			String location, String note, Date startTime, Date endTime) {
		Task newTask;
		
		if (startTime == null) {
			newTask = makeLocNoteNoStartTask(name, category, location, note, endTime);
		} else {
			newTask = makeLocNoteStartTask(name, category, location, note, startTime, endTime);
		}
		
		return newTask;
	}

	// Has: note
	// Does not have: location
	// May have: startTime, endTime
	private Task makeNoteTask(String name, String category, String note,
			Date startTime, Date endTime) {
		Task newTask;
		
		if (startTime == null) {
			newTask = makeNoteNoStartTask(name, category, note, endTime);
		} else {
			newTask = makeNoteStartTask(name, category, note, startTime, endTime);
		}
		
		return newTask;
	}

	// Has: location
	// Does not have: note
	// May have: startTime, endTime
	private Task makeNoNoteTask(String name, String category,
			String location, Date startTime, Date endTime) {
		Task newTask;
		
		if (startTime == null) {
			newTask = makeLocNoStartTask(name, category, location, endTime);
		} else {
			newTask = makeLocStartTask(name, category, location, startTime, endTime);
		}
		
		return newTask;
	}
	
	// Has:
	// Does not have: location, note
	// May have: startTime, endTime
	private Task makeNoNoteTask(String name, String category,
			Date startTime, Date endTime) {
		Task newTask;
		
		if (startTime == null) {
			newTask = makeNoStartTask(name, category, endTime);
		} else {
			newTask = makeStartTask(name, category, startTime, endTime);
		}
		
		return newTask;
	}
	
	// Has: location, note, startTime
	// Does not have:
	// May have: endTime
	private Task makeLocNoteStartTask(String name, String category,
			String location, String note, Date startTime, Date endTime) {
		Task newTask;
		
		if (endTime == null) {
			newTask = new Task(name, category, location, note, startTime);
		} else {
			newTask = new Task(name, category, location, note, startTime, endTime);
		}
		
		return newTask;
	}
	
	
	// Has: location, note
	// Does not have: startTime
	// May have: endTime
	private Task makeLocNoteNoStartTask(String name, String category,
			String location, String note, Date endTime) {
		Task newTask;
		Date startDate = new Date();	// default is today
		
		if (endTime == null) {
			newTask = new Task(name, category, location, note, startDate);
		} else {
			newTask = new Task(name, category, location, note, startDate, endTime);
		}
		
		return newTask;
	}
	
	// Has: note, startTime
	// Does not have: location
	// May have: endTime
	private Task makeNoteStartTask(String name, String category,
			String location, Date startTime, Date endTime) {
		Task newTask;
		
		if (endTime == null) {
			newTask = new Task(name, category, location, startTime);
		} else {
			newTask = new Task(name, category, location, startTime, endTime);
		}
		
		return newTask;
	}
	
	// Has: note
	// Does not have: location, startTime
	// May have: endTime
	private Task makeNoteNoStartTask(String name, String category,
			String note, Date endTime) {
		Task newTask;
		Date startDate = new Date();	// default is today
		
		if (endTime == null) {
			newTask = new Task(name, category, note, startDate);
		} else {
			newTask = new Task(name, category, note, startDate, endTime);
		}
		
		return newTask;
	}
	
	// Has: location, startTime
	// Does not have: note
	// May have: endTime
	private Task makeLocStartTask(String name, String category,
			String location, Date startTime, Date endTime) {
		Task newTask;
		
		if (endTime == null) {
			newTask = new Task(name, category, location, startTime);
		} else {
			newTask = new Task(name, category, location, startTime, endTime);
		}
		
		return newTask;
	}
	
	// Has: location
	// Does not have: note, startTime
	// May have: endTime
	private Task makeLocNoStartTask(String name, String category,
			String location, Date endTime) {
		Task newTask;
		Date startDate = new Date();	// default is today
		
		if (endTime == null) {
			newTask = new Task(name, category, location, startDate);
		} else {
			newTask = new Task(name, category, location, startDate, endTime);
		}
		
		return newTask;
	}
	
	// Has: startTime
	// Does not have: location, note
	// May have: endTime
	private Task makeStartTask(String name, String category,
			Date startTime, Date endTime) {
		Task newTask;
		
		if (endTime == null) {
			newTask = new Task(name, category, startTime);
		} else {
			newTask = new Task(name, category, startTime, endTime);
		}
		
		return newTask;
	}
	
	// Has:
	// Does not have: location, note, startTime
	// May have: endTime
	private Task makeNoStartTask(String name, String category,
			Date endTime) {
		Task newTask;
		Date startDate = new Date();	// default is today
		
		if (endTime == null) {
			newTask = new Task(name, category, startDate);
		} else {
			newTask = new Task(name, category, startDate, endTime);
		}
		
		return newTask;
	}
}
