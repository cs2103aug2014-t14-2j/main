package dataEncapsulation;

import java.util.Comparator;

public class StartComparator implements Comparator<Task> {

	public int compare(Task o1, Task o2) {
        Task s1 = o1;
        Task s2 = o2;
        return compareTo(s1, s2);
    }
    public boolean equals(Task t1, Task t2) {
    	return compareTo(t1, t2) == 0;
    }
	
    public int compareTo(Task thisone, Task another) {
		Date mySD = thisone.getStartDate();
		Time myST = thisone.getStartTime();
		Date myED = thisone.getEndDate();
		Time myET = thisone.getEndTime();
		
		Date anSD = another.getStartDate();
		Time anST = another.getStartTime();
		Date anED = another.getEndDate();
		Time anET = another.getEndTime();
		
		if (mySD.isBefore(anSD)) { //startdate is before
			return -1;
		} else if (mySD.isEquals(anSD)) {
			if (myST.compareTo(anST) < 0) {
				return -1;
			} else if (myST.compareTo(anST) == 0) {
				return thisone.getName().compareTo(another.getName());
			} else {
				return 1;
			}
		} else {
			return 1;
		}
	}

}
