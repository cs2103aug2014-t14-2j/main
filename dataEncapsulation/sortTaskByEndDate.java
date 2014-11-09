//@ Kaushik A0108297X
package dataEncapsulation;

import java.util.Comparator;

public class sortTaskByEndDate implements Comparator<Task> {
	    public int compare(Task o1, Task o2) {
	        Task s1 =  o1;
	        Task s2 =  o2;
	        if(s1.getEndDate().isBefore(s2.getEndDate())){
	        	return -1;
	        }
	        else if(s1.getEndDate().isEquals(s2.getEndDate())){
	        	return 0;
	        }
	        else
	        	return 1;
	    }
}
