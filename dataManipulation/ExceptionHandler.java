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
							+ e.getLocation().toString() + ": " + "\n" + 
				ofTasks;
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
		if(input.contains(",")) {
			input = input.replaceAll(",", " ");
		}
		
		if (input.contains("-")) {
			input = correctRange(input);
		}
		
		if (hasRepeats(input)) {
			throw new BadSubcommandArgException("you selected the same item "
					+ "more than once, please select again or enter 0 to quit");
		}
		
		String choices[] = input.split("\\s+");
		ArrayList<Task> ch = new ArrayList<Task>();
		try {
			for(String s : choices) {
				int i = (Integer.parseInt(s)) - 1;	// user choices start at 1
				Task cur = opts.get(i);
				ch.add(cur);
			}
		} catch (Exception e) {
			throw new BadSubcommandArgException("non-numeric input "
					+ "found, please try again");
		}
		return ch;
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
		String comma = ",";
		assert (input.contains(dash));
		
		String[] brokenRange = input.split(dash);
		for (int i = 0; i < brokenRange.length - 1; ++i) {
			String[] valuesLeft = brokenRange[i].split(comma);
			String[] valuesRight = brokenRange[i + 1].split(comma);
			String leftInt = valuesLeft[valuesLeft.length - 1].trim();
			String rightInt = valuesRight[valuesRight.length - 1].trim();
			String range = makeRange(leftInt, rightInt);
			input = mergeRange(input, range, leftInt, rightInt);
		}
		
		return input;
	}

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

	private String mergeRange(String input, String range, String leftInt, String rightInt) {
		String dash = "-";
		
		String left = String.valueOf(leftInt);
		String right = String.valueOf(rightInt);
		assert (input.contains(dash));
		
		String toReplace = "\\s{1,}" + left + "\\s+" + dash + "\\s+" + right + 
				"\\s{1,}";
		return input.replaceAll(toReplace, range);
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
			
			String ret = "";
			for (Task t : choices) {
				try {
					Task edited = new Edit(subcommands).furtherEdit(t, 
							subcommands);
					ret = ret + "\n " + edited.toString();
				} catch ( Exception e ) {
					status.setText("Sorry, you've entered something wrong "
							+ "again. Please try editing again!");
					endExceptionHandling();
					return;
				}
			}
			display.setText("Successfully edited: \n" + ret);
			endExceptionHandling();
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
			String userChoice = userInput.getText();
			ArrayList<Task> choices = new ArrayList<Task>();
			
			try {
				choices = getChoices(userChoice, options);
			} catch (Exception e) {
				status.setText(e.getMessage());
				return;
			}
			
			Remove remover = null;
			
			String ret = "";
			for (Task t : choices) {
				try {
					List<Subcommand> choiceSubcommands = 
						(new Add(subcommands)).dismantleTask(t);
					remover = new Remove(choiceSubcommands);
					String toReport = remover.executeRemoveLiteral();;
					ret = appendReport(ret, toReport);
				} catch (Exception e) {
					status.setText("Sorry, you've entered something wrong "
							+ "again. Please try deleting again!");
					endExceptionHandling();
					return;
				}
				
			}
			
			display.setText(ret);
			userInput.setText("");
			UndoRedoList.getInstance().pushUndoCommand(remover);	//Push command into UndoRedoList
			endExceptionHandling();
		}

		private String appendReport(String ret, String toReport) {
			if (!ret.isEmpty()) {
				ret = ret + "\n";
			}
			ret = ret + toReport;
			return ret;
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
