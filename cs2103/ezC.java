package cs2103;

public class ezC {
	
	// NATALIE - FileIoStream relies on your pre-written FileIoStream Class?
	Lisk<Task> totalTaskList;
	String fileName;
	FileIoStream fileStream (given fileName)
	List<String> Read();
	
	// YUI WEI
	public static Date determineDate(String dateString) {
		
	}
	
	// NATALIE
	public static List<Task> decipherFileContents(List<String> fileContent) {
		
	}
	
	// VERNON
	public static Task extractTask(String userInput) {
		
	}
	
	// NELSON
	// Add task to overall list
	// Keep list AND file sorted by deadline
	// Deny duplicates to be added 
	public static void doAddTask(Task toBeAdded) {
		
	}
	
	// NELSON
	// Print a message to user indicating task X was added
	public static void confirmAddTask(Task addedTaskToPrintToUser) {
		
	}
	
	// NELSON
	// Locate task to be edited within list of tasks
	// Replace specified parts (How to determine what to edit?)
	// Need to edit Task Object AND actual file
	// Returns a copy of edited task (Task Object)
	// Should REJECT DUPLICATE entries
	public static Task doEditTask(Task toEdit, String editedContent) {
		
	}
	
	// NELSON
	// Prints the task that got edited
	public static void confirmEditTask(Task oldTask, Task newTask) {
		
	}
	
	// YUI WEI
	public static List<Task> findTask(String taskName) {
		
	}
	
	// VERNON
	public static Task narrowSearch(List<Task> narrowedTaskList) {
		
	}
	
	// YUI WEI
	public static Task doDeleteTask(Task taskToDelete) {
		
	}
	
	// YUI WEI
	public static void confirmDeleteTask(Task deletedTaskToPrintToUser) {
		
	}
	
	// VERNON
	public static void doShowAll() {
		
	}
	
	// VERNON
	public static void doShowToday() {
		
	}
}
