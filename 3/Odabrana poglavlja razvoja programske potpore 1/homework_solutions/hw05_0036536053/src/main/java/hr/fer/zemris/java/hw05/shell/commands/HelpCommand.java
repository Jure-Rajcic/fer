package hr.fer.zemris.java.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**
 * 
 * class HelpCommand represents linux 'help' command
 * 
 * @author Jure Rajcic
 *
 */
public class HelpCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Map<String, ShellCommand> commands = env.commands();
		
		if(arguments.isEmpty()) {
			for(String s : commands.keySet()) {
				ShellCommand command = commands.get(s);
				env.writeln(command.getCommandName());
			}
			return ShellStatus.CONTINUE;
		}
		
		ShellCommand command = commands.get(arguments);
		if(command == null) 
			env.writeln("Command [" + arguments + "] is not supported.");
		else {
			env.writeln(command.getCommandName());
			for(String des : command.getCommandDescription())
				env.writeln(des);
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> l = new ArrayList<>();
		l.add("If stated with zero arguments, it displays all names of supported commands.");
		l.add("If started with a single argument, prints name and description of selected command.");
		return Collections.unmodifiableList(l);
	}

}
