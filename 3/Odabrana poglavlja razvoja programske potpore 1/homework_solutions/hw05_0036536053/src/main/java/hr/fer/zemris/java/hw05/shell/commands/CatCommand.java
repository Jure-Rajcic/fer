package hr.fer.zemris.java.hw05.shell.commands;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**
 * class CatCommand represents linux os cat command
 * 
 * @author Jure Rajcic
 *
 */
public class CatCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		//  cat "path/path 4/c.txt" GBK ovo baca gresku: cat "path/path 4/c".txt  ili cat path/"path 4"/c.txt ili
		String[] args = getArgumentsSuportingQuotes(arguments);
		System.out.println(Arrays.toString(args));

		// String[] args = arguments.split(" ");
		if (!(args.length == 1 || args.length == 2)) {
			env.writeln(arguments.isEmpty() ? "No arguments given." : "Invalid number of arguments.");
			return ShellStatus.CONTINUE;
		}
		Path sourcePath = Paths.get(args[0]);
		if (!Files.exists(sourcePath)) {
			env.writeln("path: [" + sourcePath + "] does not exist.");
			return ShellStatus.CONTINUE;
		}
		if (Files.isDirectory(sourcePath)) {
			env.writeln("path: [" + sourcePath + "] points to directory.");
			return ShellStatus.CONTINUE;
		}
		if (!Files.isReadable(sourcePath)) {
			env.writeln("Acess denied, path:[" + sourcePath + "] points to file wich is not readable.");
			return ShellStatus.CONTINUE;
		}
		Charset charset = Charset.defaultCharset();
		if (args.length == 2) {
			try {
				charset = Charset.forName(args[1]);
				// start main program in class MyShell and run command charsets
			} catch (IllegalArgumentException e) {
				env.writeln("[" + args[1] + "] is not suported charset name.");
				return ShellStatus.CONTINUE;
			}
		}

		try (
			BufferedInputStream is = new BufferedInputStream(Files.newInputStream(sourcePath));
			) {
				byte[] buff = new byte[4 * 1024];
				int r = is.read(buff);
				while (r == buff.length) {
					env.write(new String(buff, charset));
				}
				env.writeln(new String(buff, charset));
		} catch (IOException e) {}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override 
	public List<String> getCommandDescription() {
		List<String> l = new ArrayList<>();
		l.add("Command [" + getCommandName() + "] takes one or two arguments.");
		l.add("The first argument (required) is a path to some file.");
		l.add("The second argument is charset name that to interpret bytes from file.");
		l.add("If not provided, a default platform charset will be used.");
		return Collections.unmodifiableList(l);
	}

}
