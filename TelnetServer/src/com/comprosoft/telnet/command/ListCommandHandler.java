package com.comprosoft.telnet.command;

import java.util.ArrayList;

import com.comprosoft.telnet.io.IOHandler;
import com.comprosoft.telnet.io.TextColor;

public final class ListCommandHandler extends Command {

	
	/**
	 * Create a new list command object
	 * @param io Input/Output handler
	 */
	public ListCommandHandler(IOHandler io) {
		super(io,"list");
	}
	
	
	@Override
	protected CommandResult handleCommand(String[] args) {
		
		ArrayList<AllCommands> list = AllCommands.getAllCommands();
		IOHandler io = super.getIOHandler();
		
		io.SetTextColor(TextColor.WHITE);
		for (AllCommands ac : list) {
			String str = String.format("    %-10s - %s",ac.getName(), ac.getDescription());
			io.WriteLine(str);
		}
	
		return CommandResult.COMMAND_OK;
	}

}
