package dataEncapsulation;

//@author A0126720N

@SuppressWarnings("serial")
public class BadSubcommandException extends Exception {
	public BadSubcommandException(String args) {
		super(args);
	}
}
