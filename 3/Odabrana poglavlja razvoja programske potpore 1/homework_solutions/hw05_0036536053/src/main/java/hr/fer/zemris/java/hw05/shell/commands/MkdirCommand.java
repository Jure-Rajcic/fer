package hr.fer.zemris.java.hw05.shell.commands;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**
 * class MkdirCommand represents linux os mkdir command
 * 
 * @author Jure Rajcic
 *
 */
public class MkdirCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		if (arguments.isEmpty() || arguments.contains(" ")) {
			env.writeln("Invalid number of arguments.");
			return ShellStatus.CONTINUE;
		}
		if (Files.isDirectory(Path.of(arguments))) {
			env.writeln("directory: [" + arguments + "] already exists.");
			return ShellStatus.CONTINUE;
		}
		if (!new File(arguments).mkdirs());
			env.writeln("Cant create directory");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> l = new ArrayList<>();
		l.add("The ["+getCommandName() +"] command takes a single argument: directory name, and creates the appropriate directory structure.");
		return Collections.unmodifiableList(l);
	}
}
