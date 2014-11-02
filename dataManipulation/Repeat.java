package dataManipulation;

import java.util.List;

import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandException;
import dataManipulation.Subcommand.FREQUENCY;

public class Repeat extends Command {

	public Repeat(List<Subcommand> commandComponents)
			throws BadCommandException, BadSubcommandException {
		super("repeat", commandComponents);
	}

	@Override
	public String execute() {
		String unimplemented = "This command has not been finished. :)";
		return unimplemented;
	}

	@Override
	protected void checkValidity() throws BadSubcommandException {
		try {
			checkForNoDuplicateSubcommands();
			checkRepeatTwoComponents();
			checkFrequencyIsNotOnly();
		} catch (IllegalArgumentException e) {
			checkRepeatFourComponents();
			checkFrequencyIsOnly();
		}
		
	}
	protected void checkRepeatFourComponents() throws BadSubcommandException {
		checkForComponentAmount(4);
		
		for (int i = 0; i < subcommands.size(); ++i) {
			Subcommand component = subcommands.get(i);
			
			switch (component.getType()) {
				case FREQUENCY :	 // valid
					break;
				case NAME :
					break;
				case START :
					break;
				case END :
					break;
				default :
					throw new BadSubcommandException("invalid subcommand");
			}
		}
	}

	protected void checkRepeatTwoComponents() throws BadSubcommandException {
		checkForComponentAmount(2);
		
		for (int i = 0; i < subcommands.size(); ++i) {
			Subcommand component = subcommands.get(i);
			
			switch (component.getType()) {
				case FREQUENCY :	 // valid
					break;
				case NAME :
					break;
				default :
					throw new BadSubcommandException("invalid subcommand");
			}
		}
	}

	protected void checkFrequencyIsNotOnly() throws BadSubcommandException {
		for (int i = 0; i < subcommands.size(); ++i) {
			Subcommand component = subcommands.get(i);
			
			if (component.getType() == Subcommand.TYPE.FREQUENCY) {
				if (component.getContents().equalsIgnoreCase(FREQUENCY.ONCE.toString())) {
					throw new BadSubcommandException("too few subcommands");
				}
			}
		}
	}

	protected void checkFrequencyIsOnly() throws BadSubcommandException {
		for (int i = 0; i < subcommands.size(); ++i) {
			Subcommand component = subcommands.get(i);
			
			if (component.getType() == Subcommand.TYPE.FREQUENCY) {
				if (!component.getContents().equalsIgnoreCase(FREQUENCY.ONCE.toString())) {
					throw new BadSubcommandException("too many subcommands");
				}
			}
		}
	}
}
