package dataManipulation;

import java.util.List;

import dataEncapsulation.EditedPair;
import dataEncapsulation.Task;
import dataEncapsulation.UndoRedoProcessor;

public class Edit extends Command {

	public Edit(List<Subcommand> commandComponents)
			throws IllegalArgumentException {
		super("edit", commandComponents);
	}

	@Override
	public String execute() {
		Task toEdit = searchTaskByName(taskAttributes);
		Task preEdit = toEdit;
		Task postEdit = editTask(toEdit, taskAttributes);
		
		addEditedTask(preEdit, postEdit);
		UndoRedoProcessor.preEditTaskStack.add(toEdit);	// Add the pre-edited task into the pre edited task stack
		UndoRedoProcessor.postEditTaskStack.add(postEdit);	// Add the edited task into the post edited task stack
		
		return new EditedPair(preEdit, postEdit);
	}
	
	

	@Override
	protected void checkValidity() {
		for (int i = 0; i < subcommands.size(); ++i) {
			Subcommand component = subcommands.get(i);
			
			switch (component.getType()) {
				case NAME :
					break; // valid
				case CATEGORY :
					break;
				case END :
					break;
				case LOCATION :
					break;
				case NOTE :
					break;
				case START :
					break;
				case TITLE :
					break;
				default :
					throw new IllegalArgumentException("invalid subcommand");
			}
		}
		
		checkForNoDuplicateSubcommands();
	}

}
