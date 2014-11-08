package dataManipulation;

import java.util.List;

import userInterface.ezCMessages;
import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandException;
import dataEncapsulation.Date;
import dataManipulation.CommandType.COMMAND_TYPE;

public class ChangeDateType extends Command {

	public String previousType;
	
	public ChangeDateType(List<Subcommand> commandComponents)
			throws BadCommandException, BadSubcommandException {
		super(COMMAND_TYPE.CHANGE_DATE_TYPE, commandComponents);
	}

	@Override
	public String execute() {
		previousType = Date.getFormat();
		String type = getDateType();
		changeDateFormat(type);
		
		ezCMessages messages = ezCMessages.getInstance();
		return messages.getChangeDateTypeMessage(type);
	}
	
	@Override
	public String undo() throws Exception {
		if (previousType.equals("d/m")) {
			Date.changeFormatDm();
		} else {
			Date.changeFormatMd();
		}
		
		ezCMessages messages = ezCMessages.getInstance();
		return messages.getChangeDateTypeMessage(previousType);
	}

	private String getDateType() {
		return subcommands.get(0).getContents();
	}

	private void changeDateFormat(String type) {
		if (type.equals("d/m")) {
			Date.changeFormatDm();
		} else {
			Date.changeFormatMd();
		}
	}

	@Override
	protected void checkValidity() throws BadSubcommandException {
		super.checkValidity();
		checkForNoDuplicateSubcommands();
	}

}
