package com.comprosoft.telnet.command;

import java.util.ArrayList;

import com.comprosoft.telnet.io.IOHandler;
import com.comprosoft.telnet.io.TextColor;

/**
 * Used when multiple commands are found when searching for a partial command
 */
public final class MultipleCommandsHandler extends Command {

	private final ArrayList<AllCommands> commands;
	
	/**
	 * Create a new multiple commands handler
	 * 
	 * @param io Input and Output handler
	 * @param cmds List of commands that were discovered
	 */
	public MultipleCommandsHandler(IOHandler io, ArrayList<AllCommands> cmds) {
		super(io,"Multiple Commands Handler");
		this.commands = cmds;
	}

	@Override
	protected CommandResult handleCommand(String[] args) {
		
		IOHandler io = super.getIOHandler();
		
		io.SetTextColor(TextColor.RED);
		io.Write("Error: ");
		io.SetTextColor(TextColor.CYAN);;
		io.WriteLine("Resolves to multiple commands!");
		for (AllCommands c : this.commands) {
			super.getIOHandler().WriteLine("\t"+c.getName());
		}
		
		return CommandResult.COMMAND_OK;
	}
}
