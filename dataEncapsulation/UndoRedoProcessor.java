package dataEncapsulation;

import java.util.List;
import java.util.Stack;

import userInterface.*;
import dataManipulation.*;

/**
 * 
 * @author nellystix
 * 
 * This class processes the undo and redo functions of the program that allows the user to undo
 * or redo as many commands as has been executed AS LONG AS the program has not been quit. All changes
 * made after quitting the program are final.
 *
 */

public class UndoRedoProcessor {
	
	public static Stack<Command> undoCommandStack = new Stack<Command>();
	private static Stack<Task> preEditTaskStack = new Stack<Task>();
	private static Stack<Task> postEditTaskStack = new Stack<Task>();
	private static Stack<List<Subcommand>> undoFinishComponentStack = new Stack<List<Subcommand>>();
	private static Stack<Command> redoCommandStack = new Stack<Command>();
	
	public static void undo() throws Exception {
		
		Command commandToUndo = undoCommandStack.peek();
		redoCommandStack.add(undoCommandStack.pop());
		
		switch(commandToUndo.getType()) {
		
			case "add" :
				Command negatedAddCommand = new Remove(commandToUndo.getComponents());
				CommandHandler.executeCommand(negatedAddCommand);
				redoCommandStack.add(commandToUndo);
				break;
				
			case "remove" :
				Command negatedRemoveCommand = new Add(commandToUndo.getComponents());
				CommandHandler.executeCommand(negatedRemoveCommand);
				redoCommandStack.add(commandToUndo);
				break;
				
			case "edit" :
				Task preEditedTask = preEditTaskStack.pop();
				Command negatedEditCommand = new Edit(Add.dismantleTask(preEditedTask));
				CommandHandler.executeCommand(negatedEditCommand);
				redoCommandStack.add(new Edit(Add.dismantleTask(postEditTaskStack.pop())));
				break;
				
			case "finish" :
				Finish.markAsIncomplete(undoFinishComponentStack.pop());
				redoCommandStack.add(commandToUndo);
				break;
				
			default:
				break;
				
		}
	}
	
	public static void redo() throws Exception {
		
		Command commandToRedo = redoCommandStack.peek();
		undoCommandStack.add(redoCommandStack.pop());
		
		switch(commandToRedo.getType()) {
			
			case "add" :
				CommandHandler.executeCommand(commandToRedo);
				break;
				
			case "remove" :
				CommandHandler.executeCommand(commandToRedo);
				break;
				
			case "edit" :
				CommandHandler.executeCommand(commandToRedo);
				break;
				
			case "finish" :
				CommandHandler.executeCommand(commandToRedo);
				break;
		
			default:
				break;
		
		}
	}

}
