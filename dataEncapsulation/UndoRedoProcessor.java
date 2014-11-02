package dataEncapsulation;

import java.util.List;
import java.util.Stack;

import powerSearch.Searcher;
import userInterface.*;
import dataManipulation.*;



public class UndoRedoProcessor {
	
	public static Stack<Command> undoCommandStack = new Stack<Command>();
	public static Stack<Task> preEditTaskStack = new Stack<Task>();
	private static Stack<Command> redoCommandStack = new Stack<Command>();
	private static List<Task> taskList = TotalTaskList.getInstance().getList();
	
	public static void undo() throws Exception {
		
		Command commandToUndo = undoCommandStack.peek();
		redoCommandStack.add(undoCommandStack.pop());
		
		switch(commandToUndo.getType()) {
		
			case "add" :
				Command negatedAddCommand = new Remove(commandToUndo.getComponents());
				CommandHandler.executeCommand(negatedAddCommand);
				break;
				
			case "remove" :
				Command negatedRemoveCommand = new Add(commandToUndo.getComponents());
				CommandHandler.executeCommand(negatedRemoveCommand);
				break;
				
			case "edit" :
				Task preEditedTask = preEditTaskStack.pop();
				Command negatedEditCommand = new Edit(Add.dismantleTask(preEditedTask));
				CommandHandler.executeCommand(negatedEditCommand);
				break;
				
			case "finish" :
				List<Task> tasks = Searcher.search(commandToUndo.getComponents(), taskList);
				Task toMarkAsInComplete = tasks.get(0);
				toMarkAsInComplete.setIncomplete();
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
