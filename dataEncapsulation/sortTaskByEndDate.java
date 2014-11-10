package dataEncapsulation;

import java.util.Comparator;

//@author A0126720N
public class sortTaskByEndDate implements Comparator<Task> {
	    public int compare(Task first, Task second) {
	        int endDateResults = compareEndDate(first, second);
	    	if (endDateResults != 0) {
	    		return endDateResults;
	    	}
	    	
	    	int endTimeResults = compareEndTime(first, second);
	    	if (endTimeResults != 0) {
	    		return endTimeResults;
	    	}
	    	
	    	int startDateResults = compareStartDate(first, second);
	    	if (startDateResults != 0) {
	    		return startDateResults;
	    	}
	    	
	    	int startTimeResults = compareStartTime(first, second);
	    	if (startTimeResults != 0) {
	    		return startTimeResults;
	    	}
	    	
	    	else return first.getName().compareTo(second.getName());
	    }

		private int compareStartTime(Task first, Task second) {
			if (!first.hasStartTime() && !second.hasStartTime()) {
				return 0;
			} else if (first.hasStartTime() && !second.hasStartTime()) {
				return -1;
			} else if (!first.hasStartTime() && second.hasStartTime()) {
				return 1;
			} 
			
			return first.getStartTime().compareTo((second.getStartTime()));
		}

		private int compareStartDate(Task first, Task second) {
			if (!first.hasStartTime() && !second.hasStartTime()) {
				return 0;
			} else if (first.hasStartTime() && !second.hasStartTime()) {
				return -1;
			} else if (!first.hasStartTime() && second.hasStartTime()) {
				return 1;
			} else if (first.getStartDate().isBefore(second.getStartDate())) {
				return -1;
			} else if (first.getStartDate().isEquals((second.getStartDate()))) {
				return 0;
			} else {
				return 1;
			}
		}

		private int compareEndTime(Task first, Task second) {
			if (!first.hasEndTime() && !second.hasEndTime()) {
				return 0;
			} else if (first.hasEndTime() && !second.hasEndTime()) {
				return -1;
			} else if (!first.hasEndTime() && second.hasEndTime()) {
				return 1;
			} 
			
			return first.getEndTime().compareTo((second.getEndTime()));
		}

		private int compareEndDate(Task first, Task second) {
			if (!first.getHasDeadline() && !second.getHasDeadline()) {
				return 0;
			} else if (first.getHasDeadline() && !second.getHasDeadline()) {
				return -1;
			} else if (!first.getHasDeadline() && second.getHasDeadline()) {
				return 1;
			} else if (first.getEndDate().isBefore(second.getEndDate())) {
				return -1;
			} else if (first.getEndDate().isEquals((second.getEndDate()))) {
				return 0;
			} else {
				return 1;
			}
		}
		
		
}

//@author A0108297X-unused
/*
 *         public int compare(Task o1, Task o2) {
            Task s1 =  o1;
            Task s2 =  o2;
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
*/
