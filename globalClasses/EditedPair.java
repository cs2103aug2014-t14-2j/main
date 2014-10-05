package globalClasses;


public class EditedPair {
	
	private Task preEdit;
	private Task postEdit;

	public EditedPair(Task preEdit, Task postEdit) {
		this.preEdit = preEdit;
		this.postEdit = postEdit;
	}
	
	public Task getOld() {
		return preEdit;
	}
	
	public Task getNew() {
		return postEdit;
	}
	
}
