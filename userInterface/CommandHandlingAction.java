package userInterface;

//@author A0126720N

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dataEncapsulation.ActionException;
import dataManipulation.Command;
import dataManipulation.ExceptionHandler;
import dataManipulation.UndoRedoList;

@SuppressWarnings("serial")
public class CommandHandlingAction extends AbstractAction {
	private CommandInterpreter interpreter = CommandInterpreter.getInstance();
	
	private JLabel status;
	private JTextArea display;
	private JTextField userInput;
	
	private ActionToggler enterToggle;
	private ExceptionHandler exceptionHandler;
	
	public CommandHandlingAction(JLabel stat, JTextArea disp, JTextField usrIn, ActionToggler toggle) {
		status = stat;
		display = disp;
		userInput = usrIn;
		enterToggle = toggle;
		exceptionHandler = new ExceptionHandler(usrIn, stat, disp, enterToggle);
	}
	
	public void actionPerformed(ActionEvent ev)  {
		try {
			status.setText(" ");
			String input = userInput.getText();
			Command command = interpreter.formCommand(input);
			String feedback = command.execute();
			userInput.setText("");
			
			UndoRedoList.getInstance().pushUndoCommand(command);	//Push command into UndoRedoList
			
			setDisplayText(feedback);
		} catch (ActionException e) {
			userInput.setText("");
			exceptionHandler.furtherAction(e);
		} catch (Exception e) {
			String message = ezCMessages.getInstance().getErrorMessage(e);
			status.setText(message);
		}
	}
	
	private void setDisplayText(String message) {
		display.setText(message);
		
		int top = 0;
		display.setCaretPosition(top);
	}
}
