package dataManipulation;

import java.util.List;

import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandArgException;
import dataEncapsulation.BadSubcommandException;
import dataEncapsulation.Task;

public class MultiFinish extends MultiCommand<Finish> {

	public MultiFinish(List<Task> choices) throws BadCommandException,
			BadSubcommandException, BadSubcommandArgException {
		super(choices);
	}

	@Override
	Finish makeCommand(List<Subcommand> choices) throws BadCommandException,
			BadSubcommandException, BadSubcommandArgException {
		return new Finish(choices);
	}

	@Override
	String specializedExecute(Finish command) throws Exception {
		return command.literalFinish();
	}

}
