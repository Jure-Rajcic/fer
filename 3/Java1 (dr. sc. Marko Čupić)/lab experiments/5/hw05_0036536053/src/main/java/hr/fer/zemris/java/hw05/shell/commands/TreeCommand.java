package hr.fer.zemris.java.hw05.shell.commands;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**
 * class TreeCommand represents tree command
 * 
 * @author Jure Rajcic
 *
 */
public class TreeCommand implements ShellCommand {

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
		
		File dir = new File(arguments);
		printTree(dir, env, 0);
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> l = new ArrayList<>();
		l.add("The [" + getCommandName()+"] command expects a single argument - directory - and prints a tree (each directory level shifts output two charatcers to the right).");
		l.add("Each directory level shitfs output two characters to the right.");
		return Collections.unmodifiableList(l);
	}
	
	/**
	 * private method to print tree of given directory @param f
	 * 
	 * @param env environment in wich we display tree structure
	 * @param level counter for depth responcible for padding
	 */
	private static void printTree(File f, Environment env, int level) {
		env.write(" ".repeat(level * 3) + f.getName() + "\n");
		if(f.isDirectory()) {
			File[] files = f.listFiles();
			if(files==null) return;
			for(File file : files)
				printTree(file, env, level+1);
		}
	}

}
