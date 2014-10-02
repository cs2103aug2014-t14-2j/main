package globalClasses;

public class CommandComponent {
	public enum COMPONENT_TYPE {
		NAME, CATEGORY, START, END, LOCATION, NOTE
	}
	
	private COMPONENT_TYPE type;
	private String contents;
	
	public CommandComponent(COMPONENT_TYPE componentType, String componentContents) {
		type = componentType;
		contents = componentContents;
	}
	
	public COMPONENT_TYPE getType() {
		return type;
	}
	
	public String getContents() {
		return contents;
	}
}
