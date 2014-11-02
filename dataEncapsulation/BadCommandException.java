package dataEncapsulation;

@SuppressWarnings("serial")
public class BadCommandException extends Exception {
	public BadCommandException(String args) {
		super(args);
	}
}
