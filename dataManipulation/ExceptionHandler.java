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
		System.out.println("That was a bit too vague - please choose which of these you would like to " 
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
	public static String editFurther(ArrayList<Task> ch, List<Subcommand> cc) {
		String ret = "";
		for (Task t : ch) {
			try {
				Task edited = new Edit(cc).editTask(t, cc);
				ret = ret + "\n " + edited.toString();
			} catch ( Exception e) {
				System.out.println("Sorry, you've entered something wrong again. Please try editing again!");
			}
		}
		return "Successfully edited: \n" + ret;
	}
	
	public static String deleteFurther(ArrayList<Task> ch, List<Subcommand> cc) {
		String ret = "";
		for (Task t : ch) {
			
			try {
				Task deleted = Remove.doDeleteTask(t) ;
				ret = ret + "\n " + deleted.toString();
			} catch (Exception e) {
				System.out.println("Sorry, you've entered something wrong again. Please try deleting again!");
			}
		}
		return "Successfully deleted: \n" + ret;
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
