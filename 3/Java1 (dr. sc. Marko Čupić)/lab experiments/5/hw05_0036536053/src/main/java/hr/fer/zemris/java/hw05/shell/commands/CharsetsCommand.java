package hr.fer.zemris.java.hw05.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**
 * class CharsetsCommand represents command thath lists java suported charsets 
 * 
 * @author Jure Rajcic
 *
 */
public class CharsetsCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Map<String, Charset> charsetMap = Charset.availableCharsets();
		for(String key : charsetMap.keySet()) {
			env.writeln(charsetMap.get(key).displayName());
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override 
	public List<String> getCommandDescription() {
		List<String> l = new ArrayList<>();
		l.add("Command [" + getCommandName() + "] takes no arguments, and shows supported charsets names.");
		return Collections.unmodifiableList(l);
	}

}
