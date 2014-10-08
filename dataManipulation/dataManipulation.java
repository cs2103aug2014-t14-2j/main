package dataManipulation;

import java.util.*;

import globalClasses.*;


public class DataManipulation {
	
  public static Task add(List<CommandComponent> cc) throws Exception {
    return TaskAdder.add(cc);
  }
  public static Task remove(List<CommandComponent> cc) throws Exception {
    return TaskRemover.remove(cc);
  }
  public static EditedPair edit(List<CommandComponent> cc) throws Exception {
    return TaskEditor.edit(cc);
  }
  public static Task markAsCompleted(List<CommandComponent> cc) throws Exception {
	  return TaskEditor.markAsCompleted(cc);
  }
  
}