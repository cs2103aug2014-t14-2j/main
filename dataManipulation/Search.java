package dataManipulation;

//@author A0108297X

import java.util.List;

import powerSearch.Searcher;
import powerSearch.SlotSearch;
import userInterface.ezCMessages;
import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandException;
import dataEncapsulation.Date;
import dataEncapsulation.Task;
import dataManipulation.CommandType.COMMAND_TYPE;

public class Search extends Command {

	public Search(List<Subcommand> commandComponents)
			throws BadCommandException, BadSubcommandException {
		super(COMMAND_TYPE.SEARCH, commandComponents);
	}

	@Override
	public String execute() throws Exception {
		TotalTaskList list = TotalTaskList.getInstance();

		if(hasSubcommandType(Subcommand.TYPE.COMPLETED)) {
			return handleCompletedSearch(list);
		} else if(hasSubcommandType(Subcommand.TYPE.FREE)) {
			return handleFreeSearch(list, subcommands);
		} else {
			return handleNormalSearch(list);
		}
		
	}

	private String handleFreeSearch(TotalTaskList list,
			List<Subcommand> components) throws BadSubcommandException,
			Exception {
		checkForComponentAmount(2);
		Date date = getDateForFree();
	//	String results = Searcher.searchTimeSlot(list.getList(), date);
		String results = SlotSearch.execute(date);
		if (results.length() == 0) {
			return "You have no free slots on " + date.toPrint();
		} else {
			return "Your free slots for " + date.toPrint() + " are: \n" + results;
		}
	}

	private String handleNormalSearch(TotalTaskList list) throws Exception {
		List<Task> results = Searcher.search(subcommands, list.getNotCompleted());
		ezCMessages messages = ezCMessages.getInstance();
		return messages.getStringOfTasks(results);
	}

	private String handleCompletedSearch(TotalTaskList list) throws Exception {
		List<Task> results = Searcher.search(subcommands, list.getCompleted());
		ezCMessages messages = ezCMessages.getInstance();
		return messages.getStringOfTasks(results);
	}

	//@author A0126720N
	private Date getDateForFree() throws Exception {
		assert(subcommands.size() == 2);
		int firstIndex = 0;
		int secondIndex = firstIndex + 1;
		
		String dateString;
		
		if (subcommands.get(firstIndex).getType() == Subcommand.TYPE.DATE) {
			dateString = subcommands.get(0).getContents();
		} else if (subcommands.get(secondIndex).getType() == Subcommand.TYPE.DATE) {
			dateString = subcommands.get(secondIndex).getContents();
		} else {
			throw new BadSubcommandException("missing date component");
		}
		
		Date realDate = (new Date()).determineDate(dateString);
		return realDate;
	}

	@Override
	public String undo() throws Exception {
		return null;
	}

}
