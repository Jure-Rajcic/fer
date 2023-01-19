package hr.fer.zemris.java.hw05.shell;

import java.util.Collections;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw05.shell.commands.CatCommand;
import hr.fer.zemris.java.hw05.shell.commands.CharsetsCommand;
import hr.fer.zemris.java.hw05.shell.commands.CopyCommand;
import hr.fer.zemris.java.hw05.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.HelpCommand;
import hr.fer.zemris.java.hw05.shell.commands.HexdumpCommand;
import hr.fer.zemris.java.hw05.shell.commands.LsCommand;
import hr.fer.zemris.java.hw05.shell.commands.MkdirCommand;
import hr.fer.zemris.java.hw05.shell.commands.SymbolCommand;
import hr.fer.zemris.java.hw05.shell.commands.TreeCommand;

/**
 * class EnvironmentImpl represens implementation of Enviroment interface 
 * @author Jure Rajcic
 *
 */
public class EnvironmentImpl implements Environment {

	private Character promptSymbol = '>';
	private Character morelinesSymbol = '\\';
	private Character multilineSymbol = '|';
	private SortedMap<String, ShellCommand> commands;
	private Scanner sc;
	
	/**
	 * constructors which fills commads map
	 */
	public EnvironmentImpl() {
		this.sc = new Scanner(System.in);
		Map<String, ShellCommand> commands = new TreeMap<>();
		commands.put("cat", new CatCommand());
		commands.put("charsets", new CharsetsCommand());
		commands.put("copy", new CopyCommand());
		commands.put("exit", new ExitShellCommand());
		commands.put("help", new HelpCommand());
		commands.put("hexdump", new HexdumpCommand());
		commands.put("ls", new LsCommand());
		commands.put("mkdir", new MkdirCommand());
		commands.put("symbol", new SymbolCommand());
		commands.put("tree", new TreeCommand());
		this.commands = Collections.unmodifiableSortedMap((SortedMap<String, ShellCommand>) commands);

	}

	@Override
	public String readLine() {
		return sc.nextLine().trim().replaceAll("\\s+", " ").toLowerCase();
	}
	
	@Override
	public SortedMap<String, ShellCommand> commands() {
		return this.commands;
	}

	@Override
	public Character getMultilineSymbol() {
		return this.multilineSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		this.multilineSymbol = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return this.promptSymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		this.promptSymbol = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return this.morelinesSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		this.morelinesSymbol = symbol;
	}

}
