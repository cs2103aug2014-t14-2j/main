package dataManipulation;

import java.util.List;

import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandException;
import dataEncapsulation.Date;
import dataEncapsulation.Task;
import dataManipulation.CommandType.COMMAND_TYPE;
import powerSearch.Searcher;
import userInterface.ezCMessages;

public class Search extends Command {

	public Search(List<Subcommand> commandComponents)
			throws BadCommandException, BadSubcommandException {
		super(COMMAND_TYPE.SEARCH, commandComponents);
	}

	@Override
	public String execute() throws Exception {
		TotalTaskList list = TotalTaskList.getInstance();
		List<Subcommand> components = this.getComponents();

		if(this.getComponents().contains(Subcommand.TYPE.COMPLETED)) {
			
		}
		if(components.contains(Subcommand.TYPE.FREE)) {
			Date d = new Date();
			for (Subcommand cc : components) {
				if (cc.getType() == Subcommand.TYPE.DATE) {
					d = new Date().determineDate(cc.getContents());
				} else { throw new Exception("Please enter date."); }
			}
			String results = Searcher.searchTimeSlot(list.getList(), d);
			return results;
		} else {
			List<Task> results = Searcher.search(subcommands, list.getList());
			ezCMessages messages = ezCMessages.getInstance();
			return messages.getStringOfTasks(results);
		}
		
	}

}
