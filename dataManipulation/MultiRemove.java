package dataManipulation;

import java.util.ArrayList;
import java.util.List;

import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandArgException;
import dataEncapsulation.BadSubcommandException;
import dataEncapsulation.Task;
import dataManipulation.CommandType.COMMAND_TYPE;

public class MultiRemove extends Command {

	private List<Remove> removers = new ArrayList<Remove>();
	
	public MultiRemove(ArrayList<Task> choices) throws BadCommandException,
			BadSubcommandException, BadSubcommandArgException {
		super(COMMAND_TYPE.INVALID, new ArrayList<Subcommand>());
		initializeRemovers(choices);
	}

	private void initializeRemovers(ArrayList<Task> choices) throws 
	BadSubcommandException, BadSubcommandArgException, BadCommandException {
		for (Task t : choices) {
			List<Subcommand> choiceSubcommands = 
				(new Add()).dismantleTask(t);
			Remove newRemove = new Remove(choiceSubcommands);
			removers.add(newRemove);
		}
	}

	@Override
	public String execute() throws Exception {
		String message = "";
		for (Remove remove : removers) {
			String toReport = remove.executeRemoveLiteral();
			message = appendReport(message, toReport);
		}
		return message;
	}

	@Override
	public String undo() throws Exception {
		String message = "";
		for (Remove remove : removers) {
			String toReport = remove.undo();
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
