package dataManipulation;

import java.util.List;

public class Note extends Command {

	public Note(List<Subcommand> commandComponents)
			throws IllegalArgumentException {
		super("note", commandComponents);
	}

	@Override
	public String execute() {
		String unimplemented = "This command has not been finished. :)";
		return unimplemented;
	}

	@Override
	protected void checkValidity() {
		super.checkValidity();
		checkForNoDuplicateSubcommands();
	}

}
