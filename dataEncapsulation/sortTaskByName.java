//@ Kaushik A0108297X
package dataEncapsulation;

import java.util.Comparator;
public class sortTaskByName implements Comparator<Object> {
	    public int compare(Object o1, Object o2) {
	        Task s1 = (Task) o1;
	        Task s2 = (Task) o2;
	        return s1.getName().compareTo(s2.getName());
	    }
}
