package com.comprosoft.telnet.command;

import java.util.logging.Level;

import com.comprosoft.telnet.io.IOHandler;
import com.comprosoft.telnet.io.TextColor;

/**
 * Represents when an error happens when instantiating a command
 */
public final class CommandErrorHandler extends Command {

	private String failed;
	
	/**
	 * Create a new Error Command
	 * 
	 * @param io IO Output stream
	 * @param failed_command Name of the command that failed to instantiate
	 */
	public CommandErrorHandler(IOHandler io, String failed_command) {
		super (io,"Error Command Handler");
		this.failed = failed_command;
	}
	
	
	@Override
	protected CommandResult handleCommand(String[] args) {
		
		String message = "Failed to instantiate the command '"+failed+"'";
		IOHandler io = super.getIOHandler();
		
		Command.getLogger().log(Level.WARNING,message);
		
		io.SetTextColor(TextColor.RED);
		io.WriteLine("Failed to instantiate the command '"+failed+"'");
		return CommandResult.COMMAND_OK;
	}

}
