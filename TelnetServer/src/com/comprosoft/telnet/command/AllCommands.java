package com.comprosoft.telnet.command;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import com.comprosoft.games.CraftingEngine.CraftingEngine;
import com.comprosoft.telnet.io.IOHandler;

public enum AllCommands {

	EXIT ("exit", "Exit the Telnet server", ExitCommandHandler.class),
	LIST("list","List all available commands", ListCommandHandler.class),
	CRAFT ("craft","Play the crafting engine game", CraftingEngine.class),
	UNKNOWN("unknown","", null);
	//EXIT ("exit", false, ExitHandler.class)
	
	
	private final String command_name;
	private final String description;
	private final Class<? extends Command> objectToInstantiate;
	
	AllCommands(String command, String description, Class<? extends Command> handler) {
		this.command_name = command;
		this.description = description;
		this.objectToInstantiate = handler;
	}
	
	
	/**
	 * Get the name for this command
	 * @return Command Name
	 */
	public String getName() {
		return this.command_name;
	}
	
	
	/**
	 * Get the command description
	 * @return Command description
	 */
	public String getDescription() {
		return this.description;
	}
	
	
	/**
	 * Get the list of all commands
	 * 
	 * @return List of all commands
	 */
	public static ArrayList<AllCommands> getAllCommands() {
		ArrayList<AllCommands> list = new ArrayList<AllCommands>();
		for (AllCommands ac : AllCommands.class.getEnumConstants()) {
			if (ac != UNKNOWN) {list.add(ac);}
		}
	
		return list;
	}
	
	
	
	/**
	 * Find the command in the list of commands given the name. <br>
	 * <br>
	 * <i>If it only matches part of the command, but it matches only one command, 
	 *    then it returns that command. Ex) "ex" becomes "exit" </i>
	 * 
	 * @param command Command to search for by name
	 * @return Command
	 */
	public static ArrayList<AllCommands> findCommand(String command) {
		
		ArrayList<AllCommands> list = new ArrayList<AllCommands>();
		for (AllCommands ac : AllCommands.class.getEnumConstants()) {
			if (ac != UNKNOWN &&
				(strncmp(ac.command_name,command,command.length()) == 0)) {
					list.add(ac);
			}
		}
		
		return list;
	}
	
	/**
	 * Compare two strings for n number of characters 
	 * 
	 * @param one First string to compare
	 * @param two Second string to compare
	 * @param len Number of characters to compare
	 * @return String.compare()
	 */
	private static int strncmp(String one, String two, int len) {
		String s1 = one.substring(0, (len > one.length() ? one.length(): len));
		String s2 = two.substring(0, (len > two.length() ? two.length(): len));
		//System.out.println(s1 + "," + s2);
		return s1.compareTo(s2);
	}
	
	
	/**
	 * Get the command associated with a string
	 * 
	 * @param command The command to search for
	 * @param io Input/Output handler
	 * @return New command
	 */
	public static Command getCommand(String command, IOHandler io) {
		ArrayList<AllCommands> cmd_list = findCommand(command);
		if (cmd_list.size() > 1) {
			return new MultipleCommandsHandler(io,cmd_list);
		} else if (cmd_list.size() < 1) {
			return new UnknownCommandHandler(io,command);
		}
		
		AllCommands cmd = cmd_list.get(0);
		Constructor<?> c = cmd.objectToInstantiate.getConstructors()[0];
		
		try {
			return (Command) c.newInstance(io);
		} catch (Exception e) {
			e.printStackTrace();
			return new CommandErrorHandler(io,command);
		}
	}
	
}
