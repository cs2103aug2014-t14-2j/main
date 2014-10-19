package dataManipulation;

import java.util.List;

import dataManipulation.Subcommand.FREQUENCY;

public class Repeat extends Command {

	public Repeat(List<Subcommand> commandComponents)
			throws IllegalArgumentException {
		super("repeat", commandComponents);
	}

	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void checkValidity() {
		try {
			checkRepeatTwoComponents();
			checkFrequencyIsNotOnly();
		} catch (IllegalArgumentException e) {
			checkRepeatFourComponents();
			checkFrequencyIsOnly();
		}
		
	}
	protected void checkRepeatFourComponents() {
		checkForComponentAmount(4);
		
		for (int i = 0; i < components.size(); ++i) {
			Subcommand component = components.get(i);
			
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
					throw new IllegalArgumentException("invalid subcommand");
			}
		}
	}

	protected void checkRepeatTwoComponents() {
		checkForComponentAmount(2);
		
		for (int i = 0; i < components.size(); ++i) {
			Subcommand component = components.get(i);
			
			switch (component.getType()) {
				case FREQUENCY :	 // valid
					break;
				case NAME :
					break;
				default :
					throw new IllegalArgumentException("invalid subcommand");
			}
		}
	}

	protected void checkFrequencyIsNotOnly() {
		for (int i = 0; i < components.size(); ++i) {
			Subcommand component = components.get(i);
			
			if (component.getType() == Subcommand.TYPE.FREQUENCY) {
				if (component.getContents().equalsIgnoreCase(FREQUENCY.ONCE.toString())) {
					throw new IllegalArgumentException("too few subcommands");
				}
			}
		}
	}

	protected void checkFrequencyIsOnly() {
		for (int i = 0; i < components.size(); ++i) {
			Subcommand component = components.get(i);
			
			if (component.getType() == Subcommand.TYPE.FREQUENCY) {
				if (!component.getContents().equalsIgnoreCase(FREQUENCY.ONCE.toString())) {
					throw new IllegalArgumentException("too many subcommands");
				}
			}
		}
	}
}
