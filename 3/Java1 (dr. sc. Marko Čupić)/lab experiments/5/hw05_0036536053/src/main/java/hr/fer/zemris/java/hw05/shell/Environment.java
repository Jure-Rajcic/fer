package hr.fer.zemris.java.hw05.shell;

import java.util.SortedMap;

/**
 * interface shell serves as way of user speaking to shell
 * 
 * @author Jure Rajcic
 *
 */
public interface Environment {

	/**
	 * method reads user input as [command arg1 arg2 ...] in lowercase format
	 * 
	 * @return comand that user inputed
	 * @throws ShellIOException if exception ocurred while reading users input
	 */
	String readLine();
	
	/**
	 * method whic displays comands results to user
	 * 
	 * @param text is text which needs to be displazyed to user
	 * @throws ShellIOException if exception ocurred while writing responce to user
	 */
	default void write(String text) {
		System.out.print(text);
	}
	
	/**
	 * method whic displays comands results to user as line text

	 * @param text is text which needs to be displazyed to user as line text
	 * @throws ShellIOException if exception ocurred while writing responce to user
	 */
	default void writeln(String text) {
		write(text);
		System.out.println();
	}
	
	/**
	 * 
	 * @returns returns sorted map of all suported comands in shell
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**
	 * method whic displays current symbol whic is displayed when user writes comand in more lines
	 * @return current multiLine simbol
	 */
	Character getMultilineSymbol();
	
	/**
	 * method which changes current multiLine simbol to @param symbol
	 * 
	 */
	void setMultilineSymbol(Character symbol);
	
	/**
	 * method whic displays current symbol when shell expects user input (command)
	 * @return Prompt symbol
	 */
	Character getPromptSymbol();
	
	/**
	 * method which updates current promt symbol to @param  symbol
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * method displays current symbol to user, which is used when user wants to notify shell that command has more lines
	 * 
	 * @return MoreLines symbol
	 */
	Character getMorelinesSymbol();
	
	/**
	 * method changes more line symbol to @param symbol
	 */
	void setMorelinesSymbol(Character symbol);
	
}
