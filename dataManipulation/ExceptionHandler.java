package dataManipulation;

import java.util.ArrayList;
import java.util.Scanner;

import userInterface.ezCMessages;
import dataEncapsulation.ActionException;

public class ExceptionHandler {
	
	private static Scanner inputSc = new Scanner(System.in);
	
	public static String furtherAction(ActionException e) {
		String ofTasks = ezCMessages.getStringOfTasks(e.getOptions());
		System.out.println("That was a bit too vague -please choose which of these you would like to " 
							+ e.getLocation().toString() + ": ");
		System.out.println(ofTasks);
		String userChoice = inputSc.nextLine();
		ArrayList<Integer> choices = getChoices(userChoice);
		String cmdHandlerOut;
		switch(e.getLocation()) {
		case DELETE:
			cmdHandlerOut = deleteFurther(choices);
			break;
		case EDIT:
			cmdHandlerOut = editFurther(choices);
			break;
		default:
			cmdHandlerOut = "wat";
			break;
		}
		return cmdHandlerOut;
	}
	public static editFurther(ArrayList<Integer> ch) {
		for (int i : ch) {
			
		}
	}
	
	public static deleteFurther(ArrayList<Integer> ch) {
		for (int i : ch) {
			
		}
	}
	
	public static ArrayList<Integer> getChoices(String input) {
		String str = input;
		if(input.contains(",")) {
			str.replaceAll(",", " ");
		}
		String choices[] = str.split(" ");
		ArrayList<Integer> ch = new ArrayList<Integer>();
		for(String s : choices) {
			ch.add(Integer.parseInt(s));
		}
		return ch;
	}
}
