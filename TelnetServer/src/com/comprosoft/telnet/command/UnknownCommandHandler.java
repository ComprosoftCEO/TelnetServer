package com.comprosoft.telnet.command;

import com.comprosoft.telnet.io.IOHandler;
import com.comprosoft.telnet.io.TextColor;


/**
 * Used when a command is not found
 */
public final class UnknownCommandHandler extends Command {

    private final String command;

    /**
     * Create a new Unknown Command handler
     * 
     * @param io The input/output handler
     * @param command The command that failed
     */
    public UnknownCommandHandler(IOHandler io, String command) {
    	super(io,"Unknown Command Handler");
        this.command = command;
    }

    
    /*
     * (non-Javadoc)
     * 
     * @see com.akhettar.telnet.command.CommandHandler#handle()
     */
    @Override
    protected CommandResult handleCommand(String args[]) {

    	IOHandler io = super.getIOHandler();
    	io.SetTextColor(TextColor.RED);
    	io.WriteLine("Unknown command [" + command + "]");
    	
        return CommandResult.COMMAND_OK;
    }

}
