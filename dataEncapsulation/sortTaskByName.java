//@author A0108297X unused
package dataEncapsulation;

import java.util.Comparator;
public class sortTaskByName implements Comparator<Task> {
	    public int compare(Task o1, Task o2) {
	        Task s1 = o1;
	        Task s2 = o2;
	        return s1.getName().compareTo(s2.getName());
	    }
}
