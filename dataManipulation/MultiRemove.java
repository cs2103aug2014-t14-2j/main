package dataManipulation;

import java.util.ArrayList;
import java.util.List;

import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandArgException;
import dataEncapsulation.BadSubcommandException;
import dataEncapsulation.Task;

public class MultiRemove extends MultiCommand<Remove> {

	private List<Remove> removers = new ArrayList<Remove>();
	
	public MultiRemove(List<Task> choices) throws BadCommandException,
			BadSubcommandException, BadSubcommandArgException {
		super(choices);
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

	@Override
	Remove makeCommand(List<Subcommand> choices) 
			throws BadCommandException, BadSubcommandException {
		return new Remove(choices);
	}

	@Override
	String specializedExecute(Remove command) throws Exception {
		return command.executeRemoveLiteral();
	}
}
