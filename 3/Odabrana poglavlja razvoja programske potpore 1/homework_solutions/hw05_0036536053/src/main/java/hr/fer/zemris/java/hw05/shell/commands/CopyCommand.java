package hr.fer.zemris.java.hw05.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
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
 * class CopyCommand represents linux os copy command
 * 
 * @author Jure Rajcic
 *
 */
public class CopyCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) { 
		// IZAZIVA GRESKU copy path/"path 4"/c.txt     path/"path 4"/b.txt
		// RADI copy "path/path 4/c.txt"     "path/path 4/b.txt"
		String[] args = getArgumentsSuportingQuotes(arguments);
		

		if (args.length != 2) {
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

		Path destinationPath = Paths.get(args[1]);
		if (Files.isDirectory(destinationPath)) {
			env.writeln("path: [" + destinationPath + "] points to directory. Creating file with name [" + args[1] + "] inside given directory.");
			destinationPath = Path.of(args[1] + "/" + sourcePath.getFileName().toString());
		} else if (Files.exists(destinationPath)){
			env.write("path: [" + destinationPath + "] points to existing file. Do you want to override file? [yes]\n" + env.getPromptSymbol()+" ");
			String userAnswer = env.readLine();
			if (!userAnswer.equalsIgnoreCase("yes")) 
				return ShellStatus.CONTINUE;
		}
		if (sourcePath.equals(destinationPath)) {
			env.writeln("Fail: source and destination paths are same");
			return ShellStatus.CONTINUE;
		}
		try (
			BufferedInputStream reader = new BufferedInputStream(Files.newInputStream(sourcePath));
			BufferedOutputStream writer = new BufferedOutputStream(Files.newOutputStream(destinationPath));
			) {
				byte[] buff = new byte[4 * 1024];
				int r = reader.read(buff);
				while (r == buff.length) {
					writer.write(buff);
				}
				writer.write(buff, 0, r);
		} catch (IOException e) {}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override 
	public List<String> getCommandDescription() {
		List<String> l = new ArrayList<>();
		l.add("The ["+ getCommandName()+"] command needs two arguments: source file and destination file.");
		l.add("If the second argument is directory, original file will be copied into that directory.");
		return Collections.unmodifiableList(l);
	}

}
