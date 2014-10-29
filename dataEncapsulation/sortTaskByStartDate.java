package dataEncapsulation;

import java.util.Comparator;

public class sortTaskByStartDate implements Comparator<Task> {
	    public int compare(Task o1, Task o2) {
	        Task s1 =  o1;
	        Task s2 =  o2;
	        if(s1.getStartDate().isBefore(s2.getStartDate())){
	        	return -1;
	        }
	        else if(s1.getStartDate().isEqual(s2.getStartDate())){
	        	return 0;
	        }
	        else
	        	return 1;
	    }
}