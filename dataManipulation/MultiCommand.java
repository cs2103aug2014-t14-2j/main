package dataManipulation;

//@author A0126720N

import java.util.ArrayList;
import java.util.List;

import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandArgException;
import dataEncapsulation.BadSubcommandException;
import dataEncapsulation.Task;
import dataManipulation.CommandType.COMMAND_TYPE;

public abstract class MultiCommand<T extends Command> extends Command {

	protected List<T> commands = new ArrayList<T>();
	
	public MultiCommand(List<Task> choices) throws BadCommandException,
			BadSubcommandException, BadSubcommandArgException {
		super(COMMAND_TYPE.INVALID, new ArrayList<Subcommand>());
		initializeCommands(choices);
	}

	private void initializeCommands(List<Task> choices) throws 
	BadSubcommandException, BadSubcommandArgException, BadCommandException {
		for (Task t : choices) {
			List<Subcommand> choiceSubcommands = 
				Add.dismantleTask(t);
			T newCommand = makeCommand(choiceSubcommands);
			commands.add(newCommand);
		}
	}
	
	abstract T makeCommand(List<Subcommand> choices) 
			throws BadCommandException, BadSubcommandException, 
			BadSubcommandArgException;
	
	abstract String specializedExecute(T command) throws Exception;

	@Override
	public String execute() throws Exception {
		String message = "";
		for (T command : commands) {
			String toReport = specializedExecute(command);
			message = appendReport(message, toReport);
		}
		return message;
	}

	@Override
	public String undo() throws Exception {
		String message = "";
		for (T command : commands) {
			String toReport = command.undo();
			message = appendReport(message, toReport);
		}
		return message;
	}

	private String appendReport(String ret, String toReport) {
		if (!ret.isEmpty()) {
			ret = ret + "\n";
		}
		ret = ret + toReport;
		return ret;
	}
}
