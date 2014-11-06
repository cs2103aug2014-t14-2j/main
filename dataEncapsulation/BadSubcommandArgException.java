package dataEncapsulation;

//@author A0126720N

@SuppressWarnings("serial")
public class BadSubcommandArgException extends Exception {
	public BadSubcommandArgException(String args) {
		super(args);
	}
}
