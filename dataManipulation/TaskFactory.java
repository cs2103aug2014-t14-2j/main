package dataManipulation;

import globalClasses.Date;
import globalClasses.Task;



// By Nelson: This should go into the Task Class as overloaded constructors
// By Natalie: This is an override. Factory classes are used in industry when
// programmers want an easy way to deal with multiple constructors at one time.
// By Kaushik: All the necessary constructors have already been added on the Task Class.
// In fact, you can use some if-else statements and send it to Task Class directly. 
// However, I like this idea of TaskFactory as well.
// By Natalie: This is intended to be a portable version of the if statements so we only
// have to write them once. (I will delete these comments at the next commit to clean it up)


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
	// expects null arguments if location, note, or endTime are not specified
	public static Task makeTask(String name, String category, 
			String location, String note, Date startTime, Date endTime) {
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

	// Has location, may have note and/or endTime
	private static Task makeLocationTask(String name, String category,
			String location, String note, Date startTime, Date endTime) {
		Task newTask;
		
		if (note == null) {
			newTask = makeNoNoteTask(name, category, location, startTime, 
					endTime);
		} else {
			newTask = makeNotetask(name, category, location, note, startTime, 
					endTime);
		}

		return newTask;
	}
	
	// Does not have location, may have note and/or endTime
	private static Task makeNoLocationTask(String name, String category,
			String note, Date startTime, Date endTime) {
		Task newTask;
		
		if (note == null) {
			newTask = makeNoNoteTask(name, category, startTime, endTime);
		} else {
			newTask = makeNotetask(name, category, note, startTime, endTime);
		}

		return newTask;
	}
	
	// Has location and note, may have endTime
	private static Task makeNotetask(String name, String category,
			String location, String note, Date startTime, Date endTime) {
		Task newTask;
		
		if (endTime == null) {
			newTask = new Task(name, category, location, note, startTime);
		} else {
			newTask = new Task(name, category, location, note, startTime, endTime);
		}
		
		return newTask;
	}

	// Does not have location, has note, may have endTime
	private static Task makeNotetask(String name, String category, String note,
			Date startTime, Date endTime) {
		Task newTask;
		
		if (endTime == null) {
			newTask = new Task(name, category, note, startTime);
		} else {
			newTask = new Task(name, category, note, startTime, endTime);
		}
		
		return newTask;
	}

	// Has location, does not have note, may have endTime
	private static Task makeNoNoteTask(String name, String category,
			String location, Date startTime, Date endTime) {
		Task newTask;
		
		if (endTime == null) {
			newTask = new Task(name, category, location, startTime);
		} else {
			newTask = new Task(name, category, location, startTime, endTime);
		}
		
		return newTask;
	}
	
	// Does not have location or note, may have endTime
	private static Task makeNoNoteTask(String name, String category,
			Date startTime, Date endTime) {
		Task newTask;
		
		if (endTime == null) {
			newTask = new Task(name, category, startTime);
		} else {
			newTask = new Task(name, category, startTime, endTime);
		}
		
		return newTask;
	}
}
