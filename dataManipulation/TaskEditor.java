package dataManipulation;

import globalClasses.CommandComponent;
import globalClasses.EditedPair;
import globalClasses.Task;
import globalClasses.ezC;

import java.util.Collections;
import java.util.List;

import powerSearch.ExactMatchSearcher;

public class TaskEditor {
		// NELSON
		// Locate task to be edited within list of tasks
		// Need to edit Task Object AND actual file
		// Returns a copy of edited task (Task Object)
		// Should REJECT DUPLICATE entries
	
		/**	For edit() method:
		 * 
		 * @param taskAttributes
		 * @return EditedPair<preEditedTask, postEditedTask>
		 * @throws Exception if 1) Task to be edited cannot be found or 2) Task is a duplicate after editing
		 */
	
		public static EditedPair edit(List<CommandComponent> taskAttributes) throws Exception {
			
			Task toEdit = searchTask(taskAttributes);
			Task preEdit = toEdit;
			Task postEdit = editTask(toEdit, taskAttributes);
			
			addEditedTask(preEdit, postEdit);
			
			return new EditedPair(preEdit, postEdit);
		}
		
		private static Task editTask(Task toEdit, List<CommandComponent> taskAttributes) throws Exception {
			
			Task afterEdit = setTaskAttributes(toEdit, taskAttributes);
			
			if(ExactMatchSearcher.isExactMatch(afterEdit)) {
				throw new Exception("Duplicate Task Found");
			}
			else {
				return afterEdit;
			}
		}

		private static Task setTaskAttributes(Task toEdit, List<CommandComponent> taskAttributes) {
			
			for(CommandComponent cc : taskAttributes) {

				switch (cc.getType()) {

				case TITLE:	toEdit.setName(cc.getContents());
							break;

				case CATEGORY:	toEdit.setCategory(cc.getContents());
								break;

				case LOCATION:	toEdit.setLocation(cc.getContents());
								break;

				case END:	toEdit.setEndDate(ezC.determineDate(cc.getContents()));
							break;

				case NOTE:	toEdit.setNote(cc.getContents());
							break;

				// case COMPLETE:	toEdit.setComplete(cc.getContents());
				//					break;

				default:
					break;
				}
			}
				
				return toEdit;
		}

		private static Task searchTask(List<CommandComponent> taskAttributes) throws Exception {
			
			for(CommandComponent cc : taskAttributes) {
				
				if(cc.getType() == CommandComponent.COMPONENT_TYPE.NAME) {
					Task toEdit = ExactMatchSearcher.find(cc.getContents());
					return toEdit;
				}
			}
			
			throw new Exception("Task Not Found");
		}

		// Adds the edited task to the total task list
		public static void addEditedTask(Task oldTask, Task newTask) {
			ezC.totalTaskList.remove(oldTask);
			ezC.totalTaskList.add(newTask);
			Collections.sort(ezC.totalTaskList, new globalClasses.sortTaskByEndDate());
		}
		
}