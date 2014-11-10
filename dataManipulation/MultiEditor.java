package dataManipulation;

//@author A0126720N

import java.util.ArrayList;
import java.util.List;

import userInterface.ezCMessages;
import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandArgException;
import dataEncapsulation.BadSubcommandException;
import dataEncapsulation.Task;
import dataManipulation.CommandType.COMMAND_TYPE;

public class MultiEditor extends Command {

	private List<Edit> editors = new ArrayList<Edit>();
	private List<Edit> successfulEdits = new ArrayList<Edit>();
	private List<Task> tasksToEdit = new ArrayList<Task>();
	
	public MultiEditor(List<Task> choices, List<Subcommand> changes) throws BadCommandException,
			BadSubcommandException, BadSubcommandArgException {
		super(COMMAND_TYPE.EDIT, changes);	// check only edit-type subcommands
		tasksToEdit = choices;
		initializeEditors(choices);
		super.type = COMMAND_TYPE.INVALID;	// should not be recognized as a user-command
	}

	private void initializeEditors(List<Task> choices) throws 
	BadSubcommandException, BadSubcommandArgException, BadCommandException {
		for (int i = 0; i < choices.size(); ++i) {
			Edit editor = new Edit(subcommands);
			editors.add(editor);
		}
	}

	@Override
	public String execute() throws Exception {
		successfulEdits.clear();
		
		String message = new String();
		List<Task> failedToEdit = new ArrayList<Task>();
		ezCMessages messages = ezCMessages.getInstance();
		String failureMessage = new String();
		
		for (int i = 0; i < editors.size(); ++i) {
			Edit editor = editors.get(i);
			Task toEdit = tasksToEdit.get(i);
			try {
				editor.furtherEdit(toEdit);
				Task justEdited = editor.getTask();
				message = appendReport(message, 
						messages.makePrintableTask(justEdited) + "\n");
				successfulEdits.add(editor);
			} catch (Exception e) {
				failedToEdit.add(toEdit);
				failureMessage = e.getMessage();
			}
		}
		
		if (!failedToEdit.isEmpty()) {
			String failureHeader = "Could not edit each of the following because " + failureMessage.toLowerCase();
			String failedTasks = messages.getStringOfTasks(failedToEdit);
			String toAppend = failureHeader + "\n\n" + failedTasks;
			message = appendReport(message, toAppend);
		}
		
		return message;
	}

	@Override
	public String undo() throws Exception {
		String message = "Successfully unedited:";
		ezCMessages messages = ezCMessages.getInstance();
		
		for (Edit editor : successfulEdits) {
			editor.undo();
			Task justEdited = editor.getTask();
			String toReport = messages.makePrintableTask(justEdited);
			message = appendReport(message, "\n" + toReport);
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
