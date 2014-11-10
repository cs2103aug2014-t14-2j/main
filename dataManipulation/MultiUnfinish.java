package dataManipulation;

//@author A0126720N

import java.util.List;

import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandArgException;
import dataEncapsulation.BadSubcommandException;
import dataEncapsulation.Task;

public class MultiUnfinish extends MultiCommand<Unfinish> {

	public MultiUnfinish(List<Task> choices) throws BadCommandException,
			BadSubcommandException, BadSubcommandArgException {
		super(choices);
	}

	@Override
	Unfinish makeCommand(List<Subcommand> choices) throws BadCommandException,
			BadSubcommandException, BadSubcommandArgException {
		return new Unfinish(choices);
	}

	@Override
	String specializedExecute(Unfinish command) throws Exception {
		return command.literalUnfinish();
	}


}
