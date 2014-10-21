

import java.util.Comparator;

public class sortTaskByEndDate implements Comparator<Object> {
	    public int compare(Object o1, Object o2) {
	        Task s1 = (Task) o1;
	        Task s2 = (Task) o2;
	        if(s1.getEndDate().isBefore(s2.getEndDate())){
	        	return -1;
	        }
	        else if(s1.getEndDate().isEqual(s2.getEndDate())){
	        	return 0;
	        }
	        else
	        	return 1;
	    }
}
