package dataManipulation;

import java.util.List;

import userInterface.ezCMessages;
import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandException;
import dataManipulation.CommandType.COMMAND_TYPE;

public class Help extends Command {

	public Help(List<Subcommand> commandComponents) 
			throws BadCommandException, BadSubcommandException {
		super(COMMAND_TYPE.HELP, commandComponents);
		}

	@Override
	public String execute() {
		
		switch(subcommands.get(0).getContents()) {
			
			case "add" :
				return ezCMessages.getInstance().getAddHelp();
			case "all" :
				return ezCMessages.getInstance().getAllHelp();
			case "edit" :
				return ezCMessages.getInstance().getEditHelp();
			case "date" :
				return ezCMessages.getInstance().getDateHelp();
			case "finish" :
				return ezCMessages.getInstance().getFinishHelp();
			case "list" :
				return ezCMessages.getInstance().getUserCommandDisplay();
			case "redo" :
				return ezCMessages.getInstance().getRedoHelp();
			case "remove" :
				return ezCMessages.getInstance().getRemoveHelp();
			case "repeat" :
				return ezCMessages.getInstance().getRepeatHelp();
			case "search" :
				return ezCMessages.getInstance().getSearchHelp();
			case "time" :
				return ezCMessages.getInstance().getTimeHelp();
			case "today" :
				return ezCMessages.getInstance().getTodayHelp();
			case "undo" :
				return ezCMessages.getInstance().getUndoHelp();
			case "unfinish" :
				return ezCMessages.getInstance().getUnfinishHelp();
			default :
				break;
		
		}
		return null;
	}

	@Override
	protected void checkValidity() throws BadSubcommandException {
		super.checkValidity();
		checkForNoDuplicateSubcommands();
	}

	@Override
	public String undo() throws Exception {
		return null;
	}

}
