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
import dataEncapsulation.Date;
import dataEncapsulation.Task;

public class ExceptionHandler {
	
	private JTextField userInput;
	private JTextArea display;
	private JLabel status;
	private ActionToggler enterToggle;
	
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
		
		String ofTasks = ezCMessages.getInstance().getStringOfTasks(opts);
		String message = "That was a bit too vague - please choose which of "
				+ "these you would like to " 
							+ e.getLocation().toString() + ": " + "\n" + 
				ofTasks;
		display.setText(message);
		
		switch(e.getLocation()) {
		case DELETE:
			FurtherDeleter furtherDeleter = new FurtherDeleter(opts, cc);
			enterToggle.initializeLesser(furtherDeleter);
			break;
		case EDIT:
			FurtherEditer furtherEditer = new FurtherEditer(opts, cc);
			enterToggle.initializeLesser(furtherEditer);
			break;
		default:
			break;
		}
		
		enterToggle.setLesser();
	}
	
	public ArrayList<Task> getChoices(String input, List<Task> opts) {
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
			
			ArrayList<Task> choices = getChoices(userChoice, options);
			
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
	class FurtherDeleter extends AbstractAction {
		List<Task> options;
		List<Subcommand> subcommands;
		
		public FurtherDeleter(List<Task> opts, List<Subcommand> subs) {
			options = opts;
			subcommands = subs;
		}
		
		public void actionPerformed(ActionEvent ev)  {
				String userChoice = userInput.getText();
			ArrayList<Task> choices = getChoices(userChoice, options);
			Date today = new Date();
			int i = 0;
			
			String ret = "";
			for (Task t : choices) {
				if(!t.getHasDeadline() || !(t.getEndDate().isBefore(today))) {
					i = 1;
				}
				else if(t.getEndDate().isBefore(today) && !(t.getIsComplete())) {
					i = 2;
				}
	
				try {
					Task deleted = Remove.doDeleteTask(t, i) ;
					ret = ret + "\n " + deleted.toString();
				} catch (Exception e) {
					status.setText("Sorry, you've entered something wrong "
							+ "again. Please try deleting again!");
					endExceptionHandling();
					return;
				}
				
			}
			display.setText("Successfully deleted: \n" + ret);
			endExceptionHandling();
		}
	}
	
	public void endExceptionHandling() {
		enterToggle.setMaster();
		userInput.setText("");
	}
}
