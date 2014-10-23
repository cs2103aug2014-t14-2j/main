package dataManipulation;

import java.util.*;

import dataEncapsulation.EditedPair;
import dataEncapsulation.Task;


public class DataManipulation{
	
  public static Task add(List<Subcommand> cc) throws Exception {
    return TaskAdder.add(cc);
  }
  public static Task remove(List<Subcommand> cc) throws Exception {
    return TaskRemover.remove(cc);
  }
  public static EditedPair edit(List<Subcommand> cc) throws Exception {
    return TaskEditor.edit(cc);
  }
  public static Task markAsCompleted(List<Subcommand> cc) throws Exception {
	  return TaskEditor.markAsCompleted(cc);
  }
  
}
