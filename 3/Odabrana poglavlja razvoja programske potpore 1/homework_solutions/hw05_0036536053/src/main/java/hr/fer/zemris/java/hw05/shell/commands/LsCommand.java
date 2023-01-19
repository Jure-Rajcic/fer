package hr.fer.zemris.java.hw05.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**
 * class LsCommand represents linux os ls command
 * 
 * @author Jure Rajcic
 *
 */
public class LsCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		if (arguments.isEmpty() || arguments.contains(" ")) {
			env.writeln("Invalid number of arguments.");
			return ShellStatus.CONTINUE;
		}
		if (!Files.isDirectory(Paths.get(arguments))) {
			env.writeln("path: [" + arguments + "] is not directory.");
			return ShellStatus.CONTINUE;
		}

		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		File dir = new File(arguments);
		File[] files = dir.listFiles();
		
		for(File file : files) {
			Path path = file.toPath();
			BasicFileAttributeView faView = Files.getFileAttributeView(
				path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
			try {
				BasicFileAttributes attributes = faView.readAttributes();
				FileTime fileTime = attributes.creationTime();
				String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
				String output = "";
				output += Files.isDirectory(path) ? "d" : "-";
				output += Files.isReadable(path) ? "r" : "-";
				output += Files.isWritable(path) ? "w" : "-";
				output += Files.isExecutable(path) ? "x" : "-";
				output += String.format("%1$10s", Files.size(path)) + " ";
				output += formattedDateTime + " " + path.getFileName();
				env.writeln(output);
			} catch (IOException e) {
				env.writeln("Can't read file atributes.");
				return ShellStatus.CONTINUE;
			}
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> l = new ArrayList<>();
		l.add("Command ["+ getCommandName()+"] expects a single argument - directory - and writes and writes a directory listing (not recursive).");
		return Collections.unmodifiableList(l);
	}

}
