package cs2103;

// By Nelson: This should go into the Task Class as overloaded constructors

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
