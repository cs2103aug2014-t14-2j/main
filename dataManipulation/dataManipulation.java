package dataManipulation;

import java.util.*;
import globalClasses.*;


public class dataManipulation {
	public static Task add(List<CommandComponent> cc) {
		return TaskAdder.add(cc);
	}
	public static Task remove(List<CommandComponent> cc) {
		return TaskRemover.remove(cc);
	}
	public static Task edit(List<CommandComponent> cc) {
		return TaskEditor.edit(cc);
	}
}
