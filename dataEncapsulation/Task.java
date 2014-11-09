	/**
	 * @author Kadayam Suresh Kaushik A0108297X
	 * 
	 * Task Class (Instant) containing Constructor and all methods. - Uses the Date Class
	 * @return format: Depends on which method is being used.
	 * 
	 * 
	 * name, category, location, note, startdate, enddate, starttime, endtime
	 * name, emptylist, null, null, today, null, null, null
	 */
package dataEncapsulation;

import java.util.Calendar;
public class Task {
	
	public static final String MESSAGE_NO_END = "No Specified End Date";
	public static final String MESSAGE_NO_LOCATION = "No Specified Location";
	public static final String MESSAGE_NO_NOTE = "No Specified Note";
	public static final String MESSAGE_COMPLETED = "Yes";
	public static final String MESSAGE_NO_CATEGORY = "No Specified Category";
	private static final String NEW_LINE = System.getProperty("line.separator");
	
	private Date startdate = new Date();
	private Date enddate = new Date();
	private String name = new String();
	private String location = new String();
	private String note = new String();
	private String category = new String();
	private Time starttime = new Time();
	private Time endtime = new Time();
	
	private boolean hasDeadline = false;
	private boolean isComplete = false;
	private boolean hasNote = false;
	private boolean hasLocation = false; 
	private boolean hasStartTime = false;
	private boolean hasEndTime = false;
	private boolean isOverdue = false;
	private boolean hasCategory = false;
	
	//has every field
	public Task(String nm, String cat, String loc, String note, Date start, Date end, Time s, Time e) throws Exception {
		if (nm != null) {
			this.setName(nm);
		}
		if (cat != null) {
			this.setCategory(cat);
		}
		if (loc != null) {
			this.setLocation(loc);
		}
		if (note != null) {
			this.setNote(note);
		}
		if (start != null) {
			this.startdate = start;
		}
		if (end != null) {
			this.setEndDate(end);
		}
		if (s != null) {
			this.setStartTime(s);
		}
		if (e != null) {
			this.setEndTime(e);
		}

		setIfOverdue();
	}
	
	public Task(String name, String first_category, String location, Date start, Date end, Time s, Time e) throws Exception {
		this.setName(name);
		this.setCategory(first_category);
		this.setLocation(location);
		this.setEndDate(end);
		this.startdate = start;
		this.setStartTime(s);
		this.setEndTime(e);
		setIfOverdue();
	}
	
	//has every field except END TIME
	public Task(String name, String first_category, String location, String note, Date start, Date end, Time s) throws Exception {
		this.setName(name);
		this.setCategory(first_category);
		this.setLocation(location);
		this.setNote(note);
		this.setEndDate(end);
		this.startdate = start;
		this.setStartTime(s);
		Time autoEndTime = new Time(s.getHours() + 1, s.getMins());
		this.setEndTime(autoEndTime);
		setIfOverdue();
	}
	
	public Task(String name, String first_category, String location, Date start, Date end, Time s) throws Exception {
		this.setName(name);
		this.setCategory(first_category);
		this.setLocation(location);
		this.setEndDate(end);
		this.startdate = start;
		this.setStartTime(s);
		Time autoEndTime = new Time(s.getHours() + 1, s.getMins());
		this.setEndTime(autoEndTime);
		setIfOverdue();
	}
	
	
	
	//has every field except TIME
	public Task(String name, String first_category, String location, String note, Date start, Date end) throws Exception {
		this.setName(name);
		this.setCategory(first_category);
		this.setLocation(location);
		this.setNote(note);
		this.setEndDate(end);
		this.startdate = start;
		this.setStartTime(new Time());
		this.setEndTime(new Time(23, 59));
		setIfOverdue();
	}
	
	//no loc or note or no Time
	public Task(String name, String category, Date start, Date end) throws Exception{
		this.setName(name);
		this.setCategory(category);
		this.setEndDate(end);
		this.setStartDate(start);
		this.setStartTime(new Time());
		this.setEndTime(new Time(23, 59));
		setIfOverdue();
	}
	//no location or note but has time
	public Task(String name, String category, Date start, Date end, Time s, Time e) throws Exception{
		this.setName(name);
		this.setCategory(category);
		this.setEndDate(end);
		this.setStartDate(start);
		this.setStartTime(s);
		this.setEndTime(e);
		setIfOverdue();
	}
	//no loc or note but has start time
	public Task(String name, String category, Date start, Date end, Time s) throws Exception{
		this.setName(name);
		this.setCategory(category);
		this.setEndDate(end);
		this.setStartDate(start);
		this.setStartTime(s);
		Time autoEndTime = new Time(s.getHours() + 1, s.getMins());
		this.setEndTime(autoEndTime);
		setIfOverdue();
	}
	
	public Task(String name, Date start, Date end, Time s, Time e) throws Exception {
		this.setName(name);
		this.setEndDate(end);
		this.startdate = start;
		this.setStartTime(s);
		this.setEndTime(e);
		setIfOverdue();
		
	}
	//no start time, loc or note | | GIVEN ONLY ONE DATE = end date.
	public Task(String name, String category, Date end) throws Exception{
		this.setName(name);
		this.setCategory(category);
		this.startdate = new Date();
		enddate = end;
		this.setStartTime(new Time());
		this.setEndTime(new Time(23, 59));
		setIfOverdue();
	}
	
	public Task(String name, Date date, Time time) throws Exception {
		this.setName(name);
		this.setStartDate(date);
		this.setEndDate(date);
		this.setStartTime(time);
		Time autoEndTime = new Time(time.getHours() + 1, time.getMins());
		this.setEndTime(autoEndTime);
		setIfOverdue();
	}
	//no extra details at all
	public Task(String name, String category) throws Exception{
		this.setName(name);
		this.setCategory(category);
		this.startdate = new Date();
		this.setStartTime(new Time());
		this.setEndTime(new Time(23, 59));
		setIfOverdue();
	}
	
	public Task(String name, String first_category, String location) throws Exception{
		this.setName(name);
		this.setCategory(first_category);
		this.setLocation(location);
		this.setStartDate(new Date());
		this.setStartTime(new Time());
		this.setEndTime(new Time(23, 59));
		setIfOverdue();
	}
	
	//no start or end time but has loc and note
	public Task(String name, String first_category, String location, String note) throws Exception{
		this.setName(name);
		this.setCategory(first_category);
		this.setLocation(location);
		this.setNote(note);
		this.setStartDate(new Date());
		this.setStartTime(new Time());
		this.setEndTime(new Time(23, 59));
		setIfOverdue();
	}
	//No end date
	public Task(String name, String first_category, String location, String note, Date startdate) throws Exception{
		this.setName(name);
		this.setCategory(first_category);
		this.setLocation(location);
		this.setNote(note);
		this.setStartDate(startdate);
		this.setStartTime(new Time());
		this.setEndTime(new Time(23, 59));
		setIfOverdue();
	}
	
	public Task(String name, String category, String location, Date start, Date end) throws Exception{
		this.setName(name);
		this.setCategory(category);
		this.setStartDate(start);
		this.setEndDate(end);
		this.setLocation(location);
		this.setStartTime(new Time());
		this.setEndTime(new Time(23, 59));
		setIfOverdue();
	}
	
	public Task(String name, String category, String location, Date startTime) throws Exception{
		this.setName(name);
		this.setCategory(category);
		this.setLocation(location);
		this.startdate = startTime;
		this.setStartTime(new Time());
		this.setEndTime(new Time(23, 59));
		setIfOverdue();
	}
	
	private void setIfOverdue() throws Exception {
		if (!hasDeadline) {
			return;
		}
		
		Date today = new Date();
		if (enddate.isBefore(today)) {
			isOverdue = true;
			return;
		} 
		
		boolean isOnSameDay = today.isEqual(enddate);
		
		if (!isOnSameDay) {
			return;	// not before, so must be after
		}
		
		if (!hasEndTime) {
			return;
		}
		
		Time now = getNow();
		int equal = 0;
		if (endtime.compareTo(now) < equal) {
			isOverdue = true;
		}
	}

	private Time getNow() throws Exception {
		Calendar now = Calendar.getInstance();
		int hour = now.get(Calendar.HOUR_OF_DAY);
		int min = now.get(Calendar.MINUTE);
		Time nowAsTime = new Time(hour, min);
		return nowAsTime;
	}

	///////////////////////////////////////////////////////////GETTER SETTER
	
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
		hasNote = true;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String loc) {
		this.location = loc;
		hasLocation = true;
	}
	public Date getEndDate() {
		return enddate;
	}
	
	public Date getStartDate() {
		return startdate;
	}
	
	public void setStartDate(Date start) {
		startdate = start;
	}
	
	public void setEndDate(Date end) {
		this.enddate = end;
		if(end == null) {
			hasDeadline = false;
		} else {
			hasDeadline = true;
		}
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		if (category == null) {
			hasCategory = false;
			return;
		}
		hasCategory = true;
		this.category = category;
		return;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean getHasDeadline() {
		return hasDeadline;
	}
	
	public boolean getHasNote() {
		return hasNote;
	}
	
	public boolean getHasLocation() {
		return hasLocation;
	}
	
	public boolean getIsComplete() {
		return isComplete;
	}
	
	public Time getStartTime() {
		setHasStartTime(true);
		return starttime;
	}
	
	//@author A0126720N
	public boolean hasCategory() {
		return hasCategory;
	}
	
	public void setStartTime(Time s) throws Exception {
		if(s == null) {
			setHasStartTime(false);
			return;
		}  
		
		setHasStartTime(true);
		starttime = s;
		
		if (!hasEndTime) {
			Time autoEndTime = new Time(s.getHours() + 1, s.getMins());
			this.setEndTime(autoEndTime);
		}
	}
	
	public Time getEndTime() {
		return endtime;
	}
	
	public void setEndTime(Time e) {
		if(e == null) {
			setHasEndTime(false);
			return;
		} 
		setHasEndTime(true);
		endtime = e;
	}
	
	
	public void setComplete(){
		this.isComplete = true;
		isOverdue = false;
	}
	
	public void setIncomplete() throws Exception {	// Using this for the undo command
		this.isComplete = false;
		setIfOverdue();
	}
	
	///////////////////////////////////////////////////////////TOSTRING, TOPRINT, EQUALS
	
	public String toString(){
		String answer = new String();
		answer = answer + "Task: " + this.name + NEW_LINE;
		if(hasCategory){
			answer = answer + "Category: " + this.category + NEW_LINE;
		} else {
			answer = answer + "Category: " + MESSAGE_NO_CATEGORY + NEW_LINE;
		}
		if(!hasDeadline){
		answer = answer + "Start: " + this.getStartDate().toString() + " @ " +
				this.getStartTime().toString() + NEW_LINE + "End: " + 
				MESSAGE_NO_END + NEW_LINE;
		} else {
			answer = answer + "Start: " + this.getStartDate().toString() + 
					" @ " + this.getStartTime().toString() + NEW_LINE + 
					"End: " + this.getEndDate().toString() + " @ " + 
					this.getEndTime().toString() + NEW_LINE;
		}
		if(hasLocation){
			answer = answer + "Location: " + this.getLocation() + NEW_LINE;
		} else {
			answer = answer + "Location: " + MESSAGE_NO_LOCATION + NEW_LINE;
		}
		if(hasNote){
			answer = answer + "Note: " + this.getNote() + NEW_LINE;
		} else {
			answer = answer + "Note: " + MESSAGE_NO_NOTE + NEW_LINE;
		}
		if(isComplete) {
			answer = answer + "Completed: Yes" + NEW_LINE;
		} else {
			answer = answer + "Completed: No" + NEW_LINE;
		}
		return answer;
	}
	
	public String toPrint(){
		String answer = new String();
		answer = answer + "Task: " + this.name + NEW_LINE;
		if(hasCategory){
			answer = answer + "Category: " + this.category + NEW_LINE;
		} 
		if(!hasDeadline){
			answer = answer + "Start: " + this.getStartDate().toString() + 
					" @ " + this.getStartTime().toString() + NEW_LINE + 
					"End: " + MESSAGE_NO_END + NEW_LINE;
		} else {
			answer = answer + "Start: " + this.getStartDate().toString() + 
					" @ " + this.getStartTime().toString() + NEW_LINE + "End: "
					+ this.getEndDate().toString() + " @ " + 
					this.getEndTime().toString() + NEW_LINE;
		} if(hasLocation){
			answer = answer + "Location: " + this.location + NEW_LINE;
		}
		if(hasNote){
			answer = answer + "Note: " + this.note + NEW_LINE;
		}
		if(isComplete) {
			answer = answer + "Completed: Yes" + NEW_LINE;
		} else {
			answer = answer + "Completed: No" + NEW_LINE;
		}
		return answer;
	}
	
	public boolean isCompleted(){
		return isComplete;
	}

	public boolean hasStartTime() {
		return hasStartTime;
	}

	public void setHasStartTime(boolean hasStartTime) {
		this.hasStartTime = hasStartTime;
	}

	public boolean hasEndTime() {
		return hasEndTime;
	}
	
	public boolean isOverdue() throws Exception {
		if (isComplete) {
			return false;
		}
		
		setIfOverdue();
		return isOverdue;
	}

	public void setHasEndTime(boolean hasEndTime) {
		this.hasEndTime = hasEndTime;
	}
	
	/*
	 * @author Nelson / A0111014J
	 */
	public boolean isEqualTask(Task other) {
		
		if(this.name.equalsIgnoreCase(other.name)
				&&
			((!this.hasCategory && !other.hasCategory) || (this.category.equalsIgnoreCase(other.category)))
				&&
			((!this.hasLocation && !other.hasLocation) || (this.location.equalsIgnoreCase(other.location)))
				&&
			((!this.hasNote && !other.hasNote) || (this.note.equalsIgnoreCase(other.note)))
				&&
			this.startdate.toString().equalsIgnoreCase(other.startdate.toString())
				&&
			((!this.hasDeadline && !other.hasDeadline) || (this.enddate.isEqual(other.enddate)))
				&&
			this.starttime.compareTo(other.starttime) == 0
				&&
			this.endtime.compareTo(other.endtime) == 0) {
				return true;
		}
		else {
			return false;
		}
		
	}
	
	/*
	 * @author Yui Wei / A0115696W
	 */
	public boolean endsBefore(Task another) {
		boolean result;
		
		Date myED = this.getEndDate();
		Time myET = this.getEndTime();
		Date anED = another.getEndDate();
		Time anET = another.getEndTime();
		
		if (myED.isBefore(anED)) { //this.date before another.date
			return true;
		} else if (myED.isEquals(anED)) { //this.date same as another.date
			if (myET.compareTo(anET) < 0) { 
				result = true;	//this.time is before another.time
			} else { result = false; }
		} else { result = false; } //this.date after another.date
		
		return result;
	}
	
	/*
	 * Checks if the period of the task is valid (doesn't end before it starts)
	 * @author Yui Wei / A0115696W
	 */
	public boolean isValid() {
		boolean result;
		Date mySD = this.getStartDate();
		Date myED = this.getEndDate();
		Time myST = this.getStartTime();
		Time myET = this.getEndTime();
		
		if (myED.isBefore(mySD)) { //end is before start
			result = false;
		} else if (myED.isEquals(mySD)) { //start and end same day
			if (myET.compareTo(myST) < 0) { //end time before start time
				result = false;
			} else { result = true;	}
		} else { result = true;	}
		
		return result;
	}
}
