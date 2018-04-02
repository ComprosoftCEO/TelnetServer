package com.comprosoft.telnet.command;

import com.comprosoft.telnet.io.IOHandler;

/**
 * Command used to exit the Telnet server
 */
public class ExitCommandHandler extends Command {

	
	public ExitCommandHandler(IOHandler io) {
		super(io,"exit");
	}

    @Override
    public CommandResult handleCommand(String args[]) {
    	super.getIOHandler().WriteLine("goodbye...");
    	return CommandResult.COMMAND_EXIT;
    }

}
