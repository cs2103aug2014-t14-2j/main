package dataManipulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import userInterface.ezCMessages;
import dataEncapsulation.ActionException;
import dataEncapsulation.Task;

public class ExceptionHandler {
	
	private static Scanner inputSc = new Scanner(System.in);
	
	public static String furtherAction(ActionException e) {
		List<Task> opts = e.getOptions();
		List<Subcommand> cc = e.getSubcommands();
		
		String ofTasks = ezCMessages.getInstance().getStringOfTasks(opts);
		System.out.println("That was a bit too vague -please choose which of these you would like to " 
							+ e.getLocation().toString() + ": ");
		System.out.println(ofTasks);
		String userChoice = inputSc.nextLine();
		ArrayList<Task> choices = getChoices(userChoice, opts);
		
		String cmdHandlerOut;
		switch(e.getLocation()) {
		case DELETE:
			cmdHandlerOut = deleteFurther(choices, cc);
			break;
		case EDIT:
			cmdHandlerOut = editFurther(choices, cc);
			break;
		default:
			cmdHandlerOut = "wat";
			break;
		}
		
		return cmdHandlerOut;
	}
	public static editFurther(ArrayList<Task> ch) {
		for (Task t : ch) {
			
		}
	}
	
	public static deleteFurther(ArrayList<Task> ch) {
		for (Task t : ch) {
			
		}
	}
	
	public static ArrayList<Task> getChoices(String input, List<Task> opts) {
		String str = input;
		if(input.contains(",")) {
			str.replaceAll(",", " ");
		}
		String choices[] = str.split(" ");
		ArrayList<Task> ch = new ArrayList<Task>();
		for(String s : choices) {
			int i = (Integer.parseInt(s));
			Task cur = opts.get(i);
			ch.add(cur);
		}
		return ch;
	}
}
