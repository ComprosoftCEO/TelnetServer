package com.comprosoft.telnet.command;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.comprosoft.telnet.Constants;
import com.comprosoft.telnet.io.IOHandler;

/**
 * Represents a command that can be called from the terminal
 * 
 * <i>To add a command to the list:</i>
 * <ul>
 * 	<li>There must be one constructor with the signature <b>public Class(IOHandler io)</b></li>
 *  <li>Add the command to the "AllCommands" enum</li>
 * </ul>
 */
public abstract class Command {

	private static final Logger logger = Logger.getLogger(Constants.LOGGER_NAME);
	private final IOHandler io;
	private final String command;	//What is my actual command name???
	
	
	/**
	 * Create a new command object
	 * 
	 * @param io Input/Output Handler
	 * @param command The name of the command (as a String)
	 */
	public Command(IOHandler io, String command) {
		this.io = io;
		this.command = command;
	}
	
	
	/**
	 * Get the IO Handler
	 * 
	 * @return Input/Output Handler
	 */
	public IOHandler getIOHandler() {
		return this.io;
	}
	
	
	/**
	 * Get the logger
	 * @return Logger
	 */
	public static Logger getLogger() {
		return logger;
	}
	
	
	
	/**
	 * Tokenize a string for a command
	 * 
	 * @param input The string to tokenize
	 * @return Array of tokens
	 */
	public static String[] tokenize(String input) {
		return input.split("\\s+");
	}
	
	
	/**
	 * Execute the command stored in this object
	 * 
	 * <b>All subclasses need to implement the method handleCommand</b>
	 * 
	 * @param args List of arguments in the command
	 * @return Command Result
	 */
	public final CommandResult handle(String[] args) {
		logger.log(Level.INFO, "Running the following command: " + command);
		return handleCommand(args);
	}
	protected abstract CommandResult handleCommand(String[] args);
	
}
