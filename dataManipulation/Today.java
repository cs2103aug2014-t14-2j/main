package dataManipulation;

import java.util.ArrayList;
import java.util.List;

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
		
		return response;
	}

	@Override
	public String undo() throws Exception {
		return null;
	}

}
