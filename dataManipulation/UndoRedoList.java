/**
 * @author nellystix
 */

package dataManipulation;

import java.util.Stack;

import dataEncapsulation.Task;

public class UndoRedoList {
	
	private Stack<Command> undoCommandStack;
	private Stack<Task> preEditTaskStack;
	private Stack<Command> redoCommandStack;
	private static UndoRedoList url;

	private UndoRedoList() {
		
		undoCommandStack = new Stack<Command>();
		preEditTaskStack = new Stack<Task>();
		redoCommandStack = new Stack<Command>();
		
	}
	
	public static UndoRedoList getInstance() {
		
		if(url == null) {
			url = new UndoRedoList();
		}

			return url;
			
	}
	
	public void pushUndoCommand(Command commandToUndo) {
		
		switch(commandToUndo.getType()) {
		
		case "add" :
			undoCommandStack.add(commandToUndo);
			break;
		
		case "remove" :
			undoCommandStack.add(commandToUndo);
			break;
			
		case "edit" :
			undoCommandStack.add(commandToUndo);
			break;
			
		case "finish" :
			undoCommandStack.add(commandToUndo);
			break;
			
		default :
			break;
			
		}
		
	}
	
	public Command popUndoCommand() {
		
		return undoCommandStack.pop();
		
	}
	
	public Command peekUndoCommand() {
		
		return undoCommandStack.peek();
		
	}
	
	public void pushRedoCommand(Command commandToRedo) {
		
		redoCommandStack.add(commandToRedo);
		
	}
	
	public Command popRedoCommand() {
		
		return redoCommandStack.pop();
		
	}
	
	public Command peekRedoCommand() {
		
		return redoCommandStack.peek();
		
	}
	
	public void pushPreEditedTask(Task taskToAdd) {
		
		preEditTaskStack.add(taskToAdd);
		
	}
	
	public Task popPreEditedTask() {
		
		return preEditTaskStack.pop();
		
	}
	
	public Task peekPreEditedTask() {
		
		return preEditTaskStack.peek();
		
	}

}
