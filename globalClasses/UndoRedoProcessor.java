package globalClasses;

import java.util.List;
import java.util.Stack;

import dataManipulation.Command;
import dataManipulation.TaskEditor;
import userInterface.CommandHandler;

public class UndoRedoProcessor {
	
	public static Stack<Command> undoCommandStack = new Stack<Command>();
	public static Stack<List<CommandComponent>> undoEditComponentStack = new Stack<List<CommandComponent>>();
	public static Stack<List<CommandComponent>> undoFinishComponentStack = new Stack<List<CommandComponent>>();
	public static Stack<Command> redoCommandStack = new Stack<Command>();
	
	public static void undo(Stack<Command> undoCommand) throws Exception {
		
		Command commandToUndo = undoCommandStack.peek();
		redoCommandStack.add(undoCommandStack.pop());
		
		switch(commandToUndo.getType()) {
		
			case ADD :
				Command negatedAddCommand = new Command(dataManipulation.COMMAND_TYPE.REMOVE, commandToUndo.getComponents());
				CommandHandler.executeCommand(negatedAddCommand);
				redoCommandStack.add(negatedAddCommand);
				break;
				
			case REMOVE :
				Command negatedRemoveCommand = new Command(dataManipulation.COMMAND_TYPE.ADD, commandToUndo.getComponents());
				CommandHandler.executeCommand(negatedRemoveCommand);
				redoCommandStack.add(negatedRemoveCommand);
				break;
				
			case EDIT :
				Command negatedEditCommand = new Command(dataManipulation.COMMAND_TYPE.EDIT, undoEditComponentStack.pop());
				CommandHandler.executeCommand(negatedEditCommand);
				redoCommandStack.add(negatedEditCommand);
				break;
				
			case FINISH :
				Command negatedFinishCommand = new Command(dataManipulation.COMMAND_TYPE.FINISH, undoFinishComponentStack.pop());
				TaskEditor.markAsIncomplete(undoFinishComponentStack.pop());
				redoCommandStack.add(negatedFinishCommand);
				break;
				
			default:
				break;
				
		}
	}

}
