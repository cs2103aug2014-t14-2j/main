/**
 * This interface allows a class to communicate with the user without direct
 * contact with the user. 
 */

package dataEncapsulation;

public interface Mediator {
	public String call(String message);
	
	public void send(String message);
}
