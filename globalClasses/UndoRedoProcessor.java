package globalClasses;

import java.util.List;
import java.util.Stack;

import userInterface.CommandHandler;
import dataManipulation.TaskAdder;
import dataManipulation.TaskEditor;

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
	public static Stack<Task> preEditTaskStack = new Stack<Task>();
	public static Stack<Task> postEditTaskStack = new Stack<Task>();
	public static Stack<List<CommandComponent>> undoFinishComponentStack = new Stack<List<CommandComponent>>();
	public static Stack<Command> redoCommandStack = new Stack<Command>();
	
	public static void undo() throws Exception {
		
		Command commandToUndo = undoCommandStack.peek();
		redoCommandStack.add(undoCommandStack.pop());
		
		switch(commandToUndo.getType()) {
		
			case ADD :
				Command negatedAddCommand = new Command(Command.COMMAND_TYPE.REMOVE, commandToUndo.getComponents());
				CommandHandler.executeCommand(negatedAddCommand);
				redoCommandStack.add(commandToUndo);
				break;
				
			case REMOVE :
				Command negatedRemoveCommand = new Command(Command.COMMAND_TYPE.ADD, commandToUndo.getComponents());
				CommandHandler.executeCommand(negatedRemoveCommand);
				redoCommandStack.add(commandToUndo);
				break;
				
			case EDIT :
				Task preEditedTask = preEditTaskStack.pop();
				Command negatedEditCommand = new Command(Command.COMMAND_TYPE.EDIT, TaskAdder.dismantleTask(preEditedTask));
				CommandHandler.executeCommand(negatedEditCommand);
				redoCommandStack.add(new Command(Command.COMMAND_TYPE.EDIT, TaskAdder.dismantleTask(postEditTaskStack.pop())));
				break;
				
			case FINISH :
				TaskEditor.markAsIncomplete(undoFinishComponentStack.pop());
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
			
			case ADD :
				CommandHandler.executeCommand(commandToRedo);
				break;
				
			case REMOVE :
				CommandHandler.executeCommand(commandToRedo);
				break;
				
			case EDIT :
				CommandHandler.executeCommand(commandToRedo);
				break;
				
			case FINISH :
				CommandHandler.executeCommand(commandToRedo);
				break;
		
			default:
				break;
		
		}
	}

}
