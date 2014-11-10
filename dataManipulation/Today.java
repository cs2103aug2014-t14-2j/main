package dataManipulation;

//@author A0115696W

import java.util.ArrayList;
import java.util.List;

import userInterface.ezCMessages;
import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandException;
import dataEncapsulation.Date;
import dataManipulation.CommandType.COMMAND_TYPE;

public class Today extends Command {

	public Today(List<Subcommand> commandComponents)
			throws BadCommandException, BadSubcommandException {
		super(COMMAND_TYPE.TODAY, commandComponents);
	}

	@Override
	public String execute() throws Exception {
		Date today = new Date();
		Subcommand todaySubcommand = new Subcommand(Subcommand.TYPE.DATE, today.toString());
		List<Subcommand> toSearch = new ArrayList<Subcommand>();
		toSearch.add(todaySubcommand);
		
		Search searchCommand = new Search(toSearch);
		String response = searchCommand.execute();
		
		ezCMessages todayTasksMessage = ezCMessages.getInstance();
		String displayTodayTasks = todayTasksMessage.getTodayTasksListMessage();
		displayTodayTasks += response;
		
		return displayTodayTasks;
	}

	@Override
	public String undo() throws Exception {
		return null;
	}

}
