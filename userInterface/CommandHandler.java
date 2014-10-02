package userInterface;

import globalClasses.Command;
import globalClasses.Command.COMMAND_TYPE;
import globalClasses.CommandComponent;
import globalClasses.Date;
import globalClasses.Task;
import globalClasses.ezC;
import fileIo.FileIo;

import java.util.List;

import dataManipulation.TaskAdder;
import dataManipulation.TaskRemover;

public class CommandHandler {
	
	/**
	 * Will call the appropriate method from data manipulation or search
	 * based on the command type
	 * @param command
	 * @return a string containing the feedback returned by the called method
	 */
	public String executeCommand(Command cmd) {
		COMMAND_TYPE type = cmd.getType();
		List<CommandComponent> cc = cmd.getComponents();
		Task workingTask = makeTask(cc);
		switch (type) {
		case ADD:
			break;
		case DELETE:
			TaskRemover.doDeleteTask(workingTask, IoStream);
			break;
		case EDIT:
			break;
		case SEARCH:
			break;
		default:
			break;
		}
	}
	public Task makeTask(List<CommandComponent> cc) {
		String name = cc.get(0).getContents();
		String cat = cc.get(1).getContents();
		String startDate = cc.get(2).getContents();
		String endDate = cc.get(3).getContents();
		String location = cc.get(4).getContents();
		String note = cc.get(5).getContents();
		Date start = ezC.determineDate(startDate);
		Date end = ezC.determineDate(endDate);
		Task workingTask = new Task(name, cat, location, note, start, end);
		return workingTask;
	}
}
