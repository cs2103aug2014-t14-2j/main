package dataManipulation;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import userInterface.ActionToggler;
import userInterface.ezCMessages;
import dataEncapsulation.ActionException;
import dataEncapsulation.BadSubcommandArgException;
import dataEncapsulation.Task;

public class ExceptionHandler {
	
	private JTextField userInput;
	private JTextArea display;
	private JLabel status;
	private ActionToggler enterToggle;
	
	private static final String quitRequest = "0";
	private static final String emptyString = " ";
	
	public ExceptionHandler(JTextField input, JLabel stat, 
			JTextArea disp, ActionToggler toggle) {
		userInput = input;
		display = disp;
		enterToggle = toggle;
		status = stat;
	}
	
	public void furtherAction(ActionException e) {
		List<Task> opts = e.getOptions();
		List<Subcommand> cc = e.getSubcommands();
		ezCMessages messages = ezCMessages.getInstance();
		
		String ofTasks = messages.getNumberedStringOfTasks(opts);
		String message = "That was a bit too vague - please choose which of "
				+ "these you would like to " 
							+ e.getLocation().toString() + ", or press 0 to exit: " + "\n\n" + 
				 "Format:\n\n# # #  or #, #, # or #-# or any combination\n\n" + ofTasks;
		setDisplayText(message);
		
		switch(e.getLocation()) {
		case DELETE:
			FurtherDeleter furtherDeleter = new FurtherDeleter(opts, cc);
			enterToggle.initializeLesser(furtherDeleter);
			break;
		case EDIT:
			FurtherEditer furtherEditer = new FurtherEditer(opts, cc);
			enterToggle.initializeLesser(furtherEditer);
			break;
		case REPEAT: 
			break;
		default:
			break;
		}
		
		enterToggle.setLesser();
	}
	
	public ArrayList<Task> getChoices(String input, List<Task> opts) throws BadSubcommandArgException {
		input = formatInput(input);
		
		String choices[] = input.trim().split("\\s+");
		ArrayList<Task> choiceList = new ArrayList<Task>();
		try {
			for(String s : choices) {
				int i = (Integer.parseInt(s)) - 1;	// user choices start at 1
				Task cur = opts.get(i);
				choiceList.add(cur);
			}
		} catch (IndexOutOfBoundsException ex) {
			throw new BadSubcommandArgException("The number you have entered is out of range, "
					+ "please try again.");
		} catch (Exception e) {
			throw new BadSubcommandArgException("You have entered a non-numeric key, "
					+ "please enter a number.");
		}
		
		return choiceList;
	}

	private String formatInput(String input) throws BadSubcommandArgException {
		if(input.contains(",")) {
			input = input.replaceAll(",", " ");
		}
		
		if (input.contains("-")) {
			input = correctRange(input);
		}
		
		if (hasRepeats(input)) {
			throw new BadSubcommandArgException("You selected the same item "
					+ "more than once, please select again or enter 0 to quit");
		}
		return input;
	}
	
	private boolean hasRepeats(String input) {
		String[] separatedNumbers = input.split("\\n+");
		
		for (int i = 0; i < separatedNumbers.length - 1; ++i) {
			for (int j = i + 1; j < separatedNumbers.length; ++j) {
				if (separatedNumbers[i].matches(separatedNumbers[j])) {
					return true;
				}
			}
		}
		
		return false;
	}

	private String correctRange(String input) throws BadSubcommandArgException {
		String dash = "-";
		assert (input.contains(dash));
		
		String[] brokenRange = input.split(dash);
		for (int i = 0; i < brokenRange.length - 1; ++i) {
			String[] valuesLeft = brokenRange[i].split(dash);
			String[] valuesRight = brokenRange[i + 1].split(dash);
			String leftInt = getRightmostInt(valuesLeft[valuesLeft.length - 1].trim());
			String rightInt = getLeftmostInt(valuesRight[valuesRight.length - 1].trim());
			String range = makeRange(leftInt, rightInt);
			input = mergeRange(input, range, leftInt, rightInt);
		}
		
		return input;
	}

	private String getRightmostInt(String trim) throws BadSubcommandArgException {
		if (trim == null) {
			return trim;
		} else if (trim.isEmpty()) {
			throw new BadSubcommandArgException("formatting error: no"
					+ "upper bound to your range");
		}
		String whitespace = "\\s+";
		
		if (!trim.contains("\t") && !trim.contains(" ")) {
			return trim;
		}
		String[] split = trim.split(whitespace);
		return split[split.length - 1];
	}

	private String getLeftmostInt(String trim) throws BadSubcommandArgException {
		if (trim == null) {
			return trim;
		} else if (trim.isEmpty()) {
			throw new BadSubcommandArgException("formatting error: no"
					+ "lower bound to your range");
		}
		String whitespace = "\\s+";
		
		if (!trim.contains("\t") && !trim.contains(" ")) {
			return trim;
		}
		String[] split = trim.split(whitespace);
		return split[0];
	}

	//@author A0126720N
	private String makeRange(String leftInt, String rightInt) throws BadSubcommandArgException {
		try {	
			int lower = Integer.parseInt(leftInt);
			int higher = Integer.parseInt(rightInt);
			String replacement = String.valueOf(lower);
			
			if (lower >= higher) {
				throw new BadSubcommandArgException("range lower bound is higher "
						+ "than range upper bound, please try again");
			}
			
			for (int i = lower + 1; i <= higher; ++i) {
				replacement = replacement + " " + i;
			}
			
			return replacement;
		} catch (Exception e) {
			throw new BadSubcommandArgException("non-numeric input found, "
					+ "please try again");
		}
	}

	private String mergeRange(String input, String range, String leftInt, 
			String rightInt) throws BadSubcommandArgException {
		String dash = "-";
		String space = " ";
		
		assert (input.contains(dash));
		
		String toReplace = "\\s+" + leftInt + "\\s*" + dash + "\\s*" + 
				rightInt + "\\s+";
		String result = input.replaceAll(toReplace, space + range + space);
		
		if (!result.matches(input)) {
			return result;
		}	// else range must be at beginning or end
		
		// check beginning
		toReplace = "^" + leftInt + "\\s*" + dash + "\\s*" + 
				rightInt + "\\s+";
		result = input.replaceAll(toReplace, range + space);
		
		if (!result.matches(input)) {
			return result;
		}	// else range must be at end
		
		toReplace = "\\s+" + leftInt + "\\s*" + dash + "\\s*" + 
				rightInt + "$";
		result = input.replaceAll(toReplace, space + range);
		
		if (!result.matches(input)) {
			return result;
		}	// else range must be at both
		
		toReplace = "^" + leftInt + "\\s*" + dash + "\\s*" + 
				rightInt + "$";
		result = input.replaceAll(toReplace, range);
		
		if (!result.matches(input)) {
			return result;
		}	else {
			throw new BadSubcommandArgException("error in reading your request,"
					+ " please try again or press 0 to exit");
		}
	}

	@SuppressWarnings("serial")
	class FurtherEditer extends AbstractAction {
		List<Task> options;
		List<Subcommand> subcommands;
		
		public FurtherEditer(List<Task> opts, List<Subcommand> subs) {
			options = opts;
			subcommands = subs;
		}
		
		public void actionPerformed(ActionEvent ev)  {
			status.setText(emptyString);
			String userChoice = userInput.getText();
			ArrayList<Task> choices = new ArrayList<Task>();
			
			if (userChoice.trim().matches(quitRequest)) {
				status.setText("exit selected");
				endExceptionHandling();
				return;
			}
			
			try {
				choices = getChoices(userChoice, options);
			} catch (Exception e) {
				status.setText(e.getMessage());
				return;
			}
			
			try {
				MultiEditor editor = new MultiEditor(choices, subcommands);
				String message = editor.execute();
				display.setText("Successfully edited: \n\n" + message);
				UndoRedoList.getInstance().pushUndoCommand(editor);
				endExceptionHandling();
			} catch (Exception e) {
				System.err.println(e.getMessage());
				status.setText("error in reading your request,"
						+ " please try again or press 0 to exit");
			}
		}
	}
	@SuppressWarnings("serial")
	class FurtherRepeater extends AbstractAction {
		List<Task> options;
		List<Subcommand> subcommands;
		List<Task> taskList = TotalTaskList.getInstance().getAllTasks();
		
		public FurtherRepeater(List<Task> opts, List<Subcommand> subs) {
			options = opts;
			subcommands = subs;
		}
		
		public void actionPerformed(ActionEvent ev)  {
			String userChoice = userInput.getText();
			ArrayList<Task> choices = new ArrayList<Task>();
			
			if (userChoice.trim().matches(quitRequest)) {
				status.setText("exit selected");
				endExceptionHandling();
				return;
			}
			
			try {
				choices = getChoices(userChoice, options);
			} catch (Exception e) {
				status.setText(e.getMessage());
				return;
			}
			

			String repeated = "Something went wrong. ):";
			for (Task t : choices) {
				try {
					repeated = new Repeat(subcommands).furtherRepeat(t, 
							subcommands);
				} catch ( Exception e ) {
					status.setText("Sorry, you've entered something wrong "
							+ "again. Please try repeating again!");
					endExceptionHandling();
					return;
				}
			}
			display.setText("Successfully added repeat tasks: \n" + repeated);
			endExceptionHandling();
		}
	}
	
	@SuppressWarnings("serial")
	class FurtherDeleter extends AbstractAction {
		List<Task> options;
		List<Subcommand> subcommands;
		
		public FurtherDeleter(List<Task> opts, List<Subcommand> subs) {
			options = opts;
			subcommands = subs;
		}
		
		public void actionPerformed(ActionEvent ev) {
			status.setText(emptyString);
			String userChoice = userInput.getText();
			
			if (userChoice.trim().matches(quitRequest)) {
				status.setText("exit selected");
				endExceptionHandling();
				return;
			}
			
			ArrayList<Task> choices = new ArrayList<Task>();
			
			try {
				choices = getChoices(userChoice, options);
			} catch (Exception e) {
				status.setText(e.getMessage());
				return;
			}
			
			try {
				MultiRemove remover = new MultiRemove(choices);
				String message = remover.execute();
			
				display.setText(message);
				userInput.setText("");
				UndoRedoList.getInstance().pushUndoCommand(remover);	//Push command into UndoRedoList
				endExceptionHandling();
			} catch (Exception e) {
				status.setText("error in processing your request,"
						+ " please try again or press 0 to exit");
	
				e.printStackTrace();
			}
		}
	}
	
	public void endExceptionHandling() {
		enterToggle.setMaster();
		userInput.setText("");
	}
	
	private void setDisplayText(String message) {
		display.setText(message);
		
		int top = 0;
		display.setCaretPosition(top);
	}
}
