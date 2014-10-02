package dataManipulation;


import globalClasses.Date;
import globalClasses.Task;
import globalClasses.ezC;
import fileIo.FileIo;

public class TaskRemover {
	// YUI WEI
	
	public static Task doDeleteTask(List<CommandComponents> cc, FileIo IoStream) {
		Task lookingFor = new Task(cc.get(0), cc.get(1), cc.get(4), cc.get(5), cc.get(2), cc.get(3));
		boolean flag = false;
		for (Task t : ezC.totalTaskList) {
			if (t.equals(lookingFor)) {
				flag = true;
				ezC.totalTaskList.remove(t);
				IoStream.rewriteFile(ezC.totalTaskList);
			}
		}
		if(flag = false) {
			//print error message
			lookingFor = null;
		}

		return lookingFor;
	}
	
	// YUI WEI
	public static void confirmDeleteTask(Task deletedTaskToPrintToUser) {
				System.out.println(deleted.toString + "has been deleted.");
	}

	//other things to maybe add:
	//	+ delete by category
	//	+ delete by keyword
}
