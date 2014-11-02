package dataEncapsulation;

@SuppressWarnings("serial")
public class BadSubcommandException extends Exception {
	public BadSubcommandException(String args) {
		super(args);
	}
}
