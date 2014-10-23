package dataManipulation;

import fileIo.FileIo;
import dataManipulation.Subcommand;


import java.util.Collections;
import java.util.List;

import dataEncapsulation.EditedPair;
import dataEncapsulation.Task;
import dataEncapsulation.Date;
import dataEncapsulation.UndoRedoProcessor;
import dataEncapsulation.ezC;
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
	
		public static EditedPair edit(List<Subcommand> taskAttributes) throws Exception {
			
			Task toEdit = searchTaskByName(taskAttributes);
			Task preEdit = toEdit;
			Task postEdit = editTask(toEdit, taskAttributes);
			
			addEditedTask(preEdit, postEdit);
			UndoRedoProcessor.preEditTaskStack.add(toEdit);	// Add the pre-edited task into the pre edited task stack
			//UndoRedoProcessor.postEditTaskStack.add(postEdit);	// Add the edited task into the post edited task stack
			
			return new EditedPair(preEdit, postEdit);
		}
		
		private static Task editTask(Task toEdit, List<Subcommand> taskAttributes) throws Exception {
			
			Task editTaskExceptName = TaskAdder.buildTask(taskAttributes);
			Task editTaskIncludingName = setTaskAttributes(editTaskExceptName, taskAttributes);
			
			if(ExactMatchSearcher.isTaskDuplicate(editTaskIncludingName)) {
				throw new Exception("Duplicate Task Found");
			}
			else {
				return editTaskIncludingName;
			}
		}
		
		public static Task markAsCompleted(List<Subcommand> taskAttributes) throws Exception {
			
			Task taskToBeMarked = searchTaskByName(taskAttributes);
			Task taskMarked = taskToBeMarked;
			taskMarked.setComplete();
			addEditedTask(taskToBeMarked, taskMarked);
			//UndoRedoProcessor.undoFinishComponentStack.add(taskAttributes);
			
			return taskMarked;
		}
		
		public static Task markAsIncomplete(List<Subcommand> taskAttributes) throws Exception {
			
			Task taskToBeMarked = searchTaskByName(taskAttributes);
			Task taskMarked = taskToBeMarked;
			taskMarked.setIncomplete();
			addEditedTask(taskToBeMarked, taskMarked);
			
			return taskMarked;
		}
		
		private static Task setTaskAttributes(Task toEdit, List<Subcommand> taskAttributes) {
			
			for(Subcommand cc : taskAttributes) {

				switch (cc.getType()) {

				case TITLE:	toEdit.setName(cc.getContents());
							break;

				case CATEGORY:	toEdit.setCategory(cc.getContents());
								break;

				case LOCATION:	toEdit.setLocation(cc.getContents());
								break;

				case END:	toEdit.setEndDate(Date.determineDate(cc.getContents())); //changed from ezC.determineDate to Date.determineDate
							break;

				case NOTE:	toEdit.setNote(cc.getContents());
							break;

				default:
					break;
				}
			}
				
				return toEdit;
		}

		private static Task searchTaskByName(List<Subcommand> taskAttributes) throws Exception {
			
			for(Subcommand cc : taskAttributes) {
				
				if(cc.getType() == Subcommand.TYPE.NAME) {
					List<Task> toEditArray = ExactMatchSearcher.exactSearch(cc, ezC.totalTaskList.getList());
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
		// Edited by Kaushik
		public static void addEditedTask(Task oldTask, Task newTask) {
			ezC.totalTaskList.remove(oldTask);
			ezC.totalTaskList.add(newTask);
			Collections.sort(ezC.totalTaskList.getList(), new dataEncapsulation.sortTaskByEndDate());
			FileIo IoStream = new FileIo();
			IoStream.rewriteFile(); //rewriteFile does not take in any arguments for now.
		}
		
}
