package dataManipulation;

import java.util.List;

import dataEncapsulation.Task;

public class All extends Command {

	public All(List<Subcommand> commandComponents)
			throws IllegalArgumentException {
		super("all", commandComponents);
	}

	@Override
	public String execute() {
		List<Task> allTasks = getAllUncompletedTasks();
		String stringTasks = getStringOfAllTasks(allTasks);
		return stringTasks;
	}

	private List<Task> getAllUncompletedTasks() {
		// TODO Auto-generated method stub
		return null;
	}

	private String getStringOfAllTasks(List<Task> list) {
		ezC
	}

	@Override
	protected void checkValidity() {
		checkForNoComponents();
	}

}
