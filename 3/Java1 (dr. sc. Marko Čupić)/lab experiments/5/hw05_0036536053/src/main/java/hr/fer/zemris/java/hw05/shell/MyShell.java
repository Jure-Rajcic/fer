package hr.fer.zemris.java.hw05.shell;

import java.util.Set;

/**
 * class MyShell represents a shell
 * 
 * @author Jure Rajcic
 *
 */
public class MyShell {

	private Environment env;
	private ShellStatus status;

	public MyShell() {
		this.env = new EnvironmentImpl();
		this.status = ShellStatus.CONTINUE;
	}

	// TODO procitat uputo (2. dio jos jedanput i vidit jel triba jos ista osim
	// sljedeceg)
	// implementirat custom iznimku u progra zajedno s terminiranjem

	public static void main(String[] args) {
		MyShell shell = new MyShell();
		shell.env.writeln("Welcome to MyShell v 1.0");
		try {
			while (shell.status == ShellStatus.CONTINUE) {
				shell.env.write(shell.env.getPromptSymbol() + " ");
				String command = shell.env.readLine();
				while (command.charAt(command.length() - 1) == shell.env.getMorelinesSymbol()) {
					shell.env.write(shell.env.getMultilineSymbol() + " ");
					command = command.substring(0, command.length() - 1) + shell.env.readLine();
				}
				Set<String> keys = shell.env.commands().keySet();
				// comand now loks like [comand] or [comand args]
				int separatorIndex = 0;
				while (separatorIndex < command.length() && !Character.isWhitespace(command.charAt(separatorIndex))) {
					separatorIndex++;
				}
				String name = command.substring(0, separatorIndex);
				String arguments = command.substring(separatorIndex).trim();

				if (!keys.contains(name)) {
					shell.env.writeln("Given command is not supported.");
				} else {
					shell.status = shell.env.commands().get(name).executeCommand(shell.env, arguments);
				}
			}
		} catch (ShellIOException e) {
			shell.env.writeln("ShellIOException: " + e.getMessage());
		}
		shell.env.writeln("goodbye!");
	}
}
