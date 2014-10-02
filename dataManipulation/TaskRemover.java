package dataManipulation;

import java.util.ArrayList;
import globalClasses.*;
import fileIo.FileIo;

public class TaskRemover {
	// YUI WEI
	
	public static Task doDeleteTask(Task lookingFor, FileIo IoStream) {
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
