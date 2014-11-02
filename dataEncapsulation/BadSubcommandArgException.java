package dataEncapsulation;

public class BadSubcommandArgException extends Exception {
	public BadSubcommandArgException(String args) {
		super(args);
	}
}
