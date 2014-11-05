package userInterface;

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
	private JTextArea textArea;
	private JTextField entry;
	
	private ActionToggler enterToggle;
	private ExceptionHandler exceptionHandler;
	
	public CommandHandlingAction(JLabel stat, JTextArea txtArea, JTextField ent, ActionToggler toggle) {
		status = stat;
		textArea = txtArea;
		entry = ent;
		enterToggle = toggle;
		exceptionHandler = new ExceptionHandler(ent, stat, txtArea, enterToggle);
	}
	
	public void actionPerformed(ActionEvent ev)  {
		try {
			String input = entry.getText();
			Command command = interpreter.formCommand(input);
			String feedback = command.execute();
			entry.setText("");
			UndoRedoList.getInstance().pushUndoCommand(command);	// Adds the command to the undo command stack

			textArea.setText(feedback);
		} catch (ActionException e) {
			entry.setText("");
			exceptionHandler.furtherAction(e);
		} catch (Exception e) {
			String message = ezCMessages.getInstance().getErrorMessage(e);
			status.setText(message);
		}
	}
}
