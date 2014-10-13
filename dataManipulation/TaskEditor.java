package dataManipulation;

import fileIo.FileIo;
import globalClasses.CommandComponent;
import globalClasses.EditedPair;
import globalClasses.Task;
import globalClasses.ezC;

import java.util.ArrayList;
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
			
			Task toEdit = searchTaskByName(taskAttributes);
			Task preEdit = toEdit;
			Task postEdit = editTask(toEdit, taskAttributes);
			
			addEditedTask(preEdit, postEdit);
			
			return new EditedPair(preEdit, postEdit);
		}
		
		private static Task editTask(Task toEdit, List<CommandComponent> taskAttributes) throws Exception {
			
			Task editTaskExceptName = TaskAdder.buildTask(taskAttributes);
			Task editTaskIncludingName = setTaskAttributes(editTaskExceptName, taskAttributes);
			
			if(ExactMatchSearcher.isTaskDuplicate(editTaskIncludingName)) {
				throw new Exception("Duplicate Task Found");
			}
			else {
				return editTaskIncludingName;
			}
		}
		
		public static Task markAsCompleted(List<CommandComponent> taskAttributes) throws Exception {
			assert ezC.totalTaskList != null;
			
			Task taskToBeMarked = searchTaskByName(taskAttributes);
			Task taskMarked = taskToBeMarked;
			taskMarked.setComplete();
			addEditedTask(taskToBeMarked, taskMarked);
			
			return taskMarked;
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

				default:
					break;
				}
			}
				
				return toEdit;
		}

		private static Task searchTaskByName(List<CommandComponent> taskAttributes) throws Exception {
			
			for(CommandComponent cc : taskAttributes) {
				
				if(cc.getType() == CommandComponent.COMPONENT_TYPE.NAME) {
					ArrayList<Task> toEditArray = ExactMatchSearcher.exactSearch(cc, ezC.totalTaskList);
					if(toEditArray.size() > 1) {
						throw new Exception("Too many searches returned, need a more specific task to edit.");
					}
					else {
						Task toEdit = toEditArray.get(0);
						return toEdit;
					}
				}
			}
			
			throw new Exception("Task Not Found");
		}

		// Adds the edited task to the total task list
		public static void addEditedTask(Task oldTask, Task newTask) {
			ezC.totalTaskList.remove(oldTask);
			ezC.totalTaskList.add(newTask);
			Collections.sort(ezC.totalTaskList, new globalClasses.sortTaskByEndDate());
			FileIo IoStream = new FileIo();
			IoStream.rewriteFile(ezC.totalTaskList);
		}
		
}