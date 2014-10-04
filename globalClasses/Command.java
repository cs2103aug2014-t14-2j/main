package globalClasses;

import java.util.List;

public class Command {
	private COMMAND_TYPE type;
	private List<CommandComponent> components;
	
	public enum COMMAND_TYPE {
		ADD, DELETE, EDIT, SEARCH, INVALID
	}
	
	public Command(COMMAND_TYPE commandType, List<CommandComponent> commandComponents) {
		type = commandType;
		components = commandComponents;
	}
	
	public COMMAND_TYPE getType() {
		return type;
	}
	
	public List<CommandComponent> getComponents() {
		return components;
	}
}
