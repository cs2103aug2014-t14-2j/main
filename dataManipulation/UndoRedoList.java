/**
 * @author Nelson / A0111014J
 * 
 * This class processes the methods within the Undo and Redo classes and holds the stack of
 * commands to undo and redo.
 *
 */

package dataManipulation;

import java.util.Stack;

public class UndoRedoList {
	
	private Stack<Command> undoCommandStack;
	private Stack<Command> redoCommandStack;
	private static UndoRedoList url;

	private UndoRedoList() {
		
		undoCommandStack = new Stack<Command>();
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
		
		case ADD :
			undoCommandStack.add(commandToUndo);
			break;
		
		case REMOVE :
			undoCommandStack.add(commandToUndo);
			break;
			
		case EDIT :
			undoCommandStack.add(commandToUndo);
			break;
			
		case FINISH :
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
	
	public boolean isUndoStackEmpty() {
		
		if(undoCommandStack.size() == 0) {
			return true;
		}
		
		return false;
		
	}

}
