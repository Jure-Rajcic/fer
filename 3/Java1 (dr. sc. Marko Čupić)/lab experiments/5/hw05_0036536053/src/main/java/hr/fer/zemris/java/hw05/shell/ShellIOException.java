package hr.fer.zemris.java.hw05.shell;

/**
 * class ShellIOException represents an exception when working with class MyShell
 * 
 * @author Jure Rajcic
 *
 */
public class ShellIOException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * creates a ShellIOException reference
	 */
	public ShellIOException() {}
	
	/**
	 * creates a ShellIOException reference with @param message
	 */
	public ShellIOException(String message) {
		super(message);
	}
	
	/**
	 * creates a ShellIOException reference with @param cause
	 */
	public ShellIOException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * creates a ShellIOException reference with @param message i @param cause
	 */
	public ShellIOException(String message, Throwable cause) {
		super(message, cause);
	}
}
