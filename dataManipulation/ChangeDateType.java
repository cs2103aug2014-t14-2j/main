package dataManipulation;

import java.util.List;

import userInterface.ezCMessages;
import dataEncapsulation.Date;

public class ChangeDateType extends Command {

	public ChangeDateType(List<Subcommand> commandComponents)
			throws IllegalArgumentException {
		super("change date type", commandComponents);
	}

	@Override
	public String execute() {
		String type = getDateType();
		changeDateFormat(type);
		
		ezCMessages messages = ezCMessages.getInstance();
		
		return messages.getChangeDateTypeMessage(type);
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
	protected void checkValidity() {
		super.checkValidity();
		checkForNoDuplicateSubcommands();
	}

}
