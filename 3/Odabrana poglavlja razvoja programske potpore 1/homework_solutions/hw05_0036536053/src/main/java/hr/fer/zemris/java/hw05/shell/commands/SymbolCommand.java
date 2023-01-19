package hr.fer.zemris.java.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**
 * class SymbolCommand represents symbol commands
 * 
 * @author Jure Rajcic
 *
 */
public class SymbolCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) { // TODO ode 
		String[] args = arguments.split(" ");
		if (!(args.length == 1 || args.length == 2)) {
			env.writeln("Invalid number of arguments.");
			return ShellStatus.CONTINUE;
		}

		String keyword = args[0].toUpperCase();
		Character c;
		switch (keyword) {
			case "PROMPT" -> c = env.getPromptSymbol();
			case "MORELINES" -> c = env.getMorelinesSymbol();
			case "MULTILINE" -> c = env.getMultilineSymbol();
			default -> {
				env.writeln("invalid argumetn [" + keyword + "]");
				return ShellStatus.CONTINUE;
			}
		}
		// Symbol for PROMPT is '>'
		if (args.length == 1) {
			env.writeln("Symbol for " + keyword + " is '" + c + "'");
		}
		// Symbol for PROMPT changed from '>' to '#'
		else {
			String symbol = args[1];
			if (symbol.length() != 1) {
				env.writeln("symbol can onl be charracter");
			}
			switch (keyword) {
				case "PROMPT" -> env.setPromptSymbol(symbol.charAt(0));
				case "MORELINES" -> env.setMorelinesSymbol(symbol.charAt(0));
				case "MULTILINE" -> env.setMultilineSymbol(symbol.charAt(0));
				default -> {}
			}
			env.writeln("Symbol for "+ keyword+ " changed from '" + c + "' to '"+ symbol +"'");
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("The [" + getCommandName()+ "] command changes one of PROMPT, MORELINES or MULTILINE character.");
		return Collections.unmodifiableList(list);
	}

}
