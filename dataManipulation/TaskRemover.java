package dataManipulation;

import java.util.ArrayList;

import userInterface.ezCMessages;
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
		if(flag == false) {
			//print error message
			lookingFor = null;
		}
		return lookingFor;
	}
	
	// YUI WEI
	public static void confirmDeleteTask(Task deletedTaskToPrintToUser) {
			String deletedTask = deletedTaskToPrintToUser.toString();
			ezCMessages.printConfirmRemoved(deletedTask);
	}

	//other things to maybe add:
	//	+ delete by category
	//	+ delete by keyword
}
