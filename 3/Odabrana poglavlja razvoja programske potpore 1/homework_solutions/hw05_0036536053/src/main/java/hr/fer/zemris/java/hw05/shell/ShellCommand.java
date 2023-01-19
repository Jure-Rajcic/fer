package hr.fer.zemris.java.hw05.shell;

import java.util.ArrayList;
import java.util.List;

/**
 * interface ShellCommand represents suported command for MyShell class
 * 
 * @author Jure Rajcic
 *
 */
public interface ShellCommand {

	/**
	 * method which executes given command
	 * 
	 * @param env environment in which command is executed
	 * @param arguments command arguments
	 * @returns ShellStatus that tells us if shwll will continue to work
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * 
	 * @returns name of command
	 */
	String getCommandName();
	
	/**
	 * @returns list with command description
	 */
	List<String> getCommandDescription();


	/**
	 * @returns array of Strings thaht suports double qutes used in arguments for specific comands
	 */
	default String[] getArgumentsSuportingQuotes(String arguments) {
		ArrayList<String> list = new ArrayList<String>();
		char[] characters = arguments.toCharArray();

		boolean ignore_space = false;
		int start = 0;
		for(int end = 0; end < characters.length; end++) {
			if (characters[end] == '"') {
				ignore_space = !ignore_space;
			}
			if (characters[end] == ' ') {
				if (!ignore_space) {
					list.add(replacedString(characters, start, end));
					start = end + 1;
				} 
			}
		}
		list.add(replacedString(characters, start, characters.length ));
		return list.toArray(new String[list.size()]);
	}

	default String replacedString(char[] characters, int start, int end) {
		String ret = new String(characters, start, end - start);
		if (ret.contains("\"") && !(ret.charAt(0) == '\"' && ret.charAt(ret.length() - 1) == '\"'))
			throw new ShellIOException("Invalid argument syntax");
		return ret.replace("\"", "");
	}
}
