package hr.fer.zemris.java.hw05.shell.commands;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**
 * class HexdumpCommand implements linux os hexdump command
 * 
 * @author Jure Rajcic
 *
 */
public class HexdumpCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.isEmpty() || arguments.contains(" ")) {
			env.writeln("Invalid number of arguments.");
			return ShellStatus.CONTINUE;
		}

		Path sourcePath = Paths.get(arguments);
		if (!Files.exists(sourcePath)) {
			env.writeln("path: [" + sourcePath + "] does not exist.");
			return ShellStatus.CONTINUE;
		}
		if (Files.isDirectory(sourcePath)) {
			env.writeln("path: [" + sourcePath + "] points to directory.");
			return ShellStatus.CONTINUE;
		}
		try {
			FileInputStream input = new FileInputStream(sourcePath.toFile());
			int offset = 0;
			byte[] buffer = new byte[16];
	
			while (input.read(buffer) != -1) {
				System.out.format("%08X: ", offset);
				offset += 16;
				for (int i = 0; i < 16; i++) {
					if (i < buffer.length) {
						System.out.format("%02X ", buffer[i]);
					} else {
						System.out.print("   ");
					}
	
					if (i == 7) {
						System.out.print(" ");
					}
				}
	
				System.out.print(" ");
				for (int i = 0; i < buffer.length; i++) {
					if (i == 8) {
						System.out.print(" ");
					}
					int value = buffer[i];
					if (value < 32 || value > 126) {
						System.out.print(".");
					} else {
						System.out.write(value);
					}
				}
				System.out.println();
			}
			input.close();
		} catch (IOException e) {
			env.writeln("Error while reading file.");
			return ShellStatus.CONTINUE;
		}

		
		
		// try (
		// 	BufferedInputStream is = new BufferedInputStream(Files.newInputStream(sourcePath));
		// 	) {
		// 		byte[] buff = new byte[16];
		// 		long counter = 0;
		// 		int r = is.read(buff);
		// 		while (r != -1) {
		// 			String output = "";
		// 			output += String.format("%08X:", counter);
		// 			int index = 0;
		// 			while (index < buff.length / 2) {
		// 				if (r <= index)
		// 					output += "   ";
		// 				else 
		// 					output += String.format(" %02X", buff[index]);
		// 				index++;
		// 			}
		// 			output += "|";
		// 			while (index < buff.length) {
		// 				if (r <= index)
		// 					output += "   ";
		// 				else 
		// 					output += String.format("%02X ", buff[index]);
		// 				index++;
		// 			}
		// 			if (r <= index) 
		// 				output += String.format("| %s", new String(buff).substring(0, r));
		// 			else 
		// 				output += String.format("| %s", new String(buff));
		// 			env.writeln(output);
		// 			counter += r;
		// 			r = is.read(buff);
		// 		}
		// } catch (IOException e) {}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> l = new ArrayList<>();
		l.add("The [" + getCommandName()+ "] method expects a single argument - file name.");
		l.add("Method creates hex output of file's content.");
		return Collections.unmodifiableList(l);
	}

}
