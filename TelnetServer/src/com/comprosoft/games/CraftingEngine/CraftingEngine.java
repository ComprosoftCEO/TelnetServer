package com.comprosoft.games.CraftingEngine;

import java.io.IOException;
import java.util.regex.Pattern;

import com.comprosoft.telnet.command.Command;
import com.comprosoft.telnet.command.CommandResult;
import com.comprosoft.telnet.io.IOHandler;
import com.comprosoft.telnet.io.TextColor;

/**
 * A fun game where you combine two element to make a new element
 */
public class CraftingEngine extends Command {

	private ElementCollection game;		//Holds all elements and associated actions
	private boolean running;
	
	
	/**
	 * Create a new CraftingEngine game session
	 * 
	 * @param io The IOReader to write to
	 * @throws IOException
	 */
	public CraftingEngine(IOHandler io) throws IOException {
		super(io,"craft");
		this.game = new ElementCollection(io);
	}


    
    /**
     * Run a CraftingGame session
     */
	public void RunGame() {
		
		IOHandler io = super.getIOHandler();
		//io.SetBackgroundColor(BackColor.BLACK);
		io.ClearScreen();
		CraftingEngine.DrawTitle(io);
		
		this.running = true;
		
		//Run the main loop
		while (running) {
			io.SetTextColor(TextColor.WHITE);
			io.Write("> ");
			io.Flush();
			
			try {
				
				String command = io.readLine();
				
				//Trim off excess spaces
				command = command.trim();
				command = command.replaceAll("\\P{Print}", "");
				

				//If the input was not a command, do the crafting
				if (!runCommand(command)) {
					game.Craft(command);
				}
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
				
		}
	}
	
	
	
	/**
	 * All non-gameplay related commands
	 * 
	 * @param command The command to run
	 * @return Was a command actually run or not???
	 */
	private boolean runCommand(String command) {

		IOHandler io = super.getIOHandler();
		command = command.toLowerCase();

		//Special case for list command
		if (command.split(Pattern.quote(" "))[0].equals("list")) {
			game.List(command);
			return true;
		}
		
		if (command.equals("exit")) {
			this.running = false;
			//io.ClearScreen();
			return true;
		}
		
		if (command.equalsIgnoreCase("help")) {
			CraftingEngine.DrawHelp(io);
			return true;
		}
		
		
		if (command.equals("clear")) {
			io.ClearScreen();
			return true;
		}
		

		if (command.equals("reset")) {
			game.Reset();
			return true;
		}
		
		
		if (command.equals("hint")) {
			game.Hint();
			return true;
		}
		
		
		return false;
	}
	
	
	
	/**
	 * Draw the main title screen for the CraftingEngine
	 * 
	 * @param io The IOReader to write to
	 */
	public static void DrawTitle(IOHandler io) {

		io.SetTextColor(TextColor.GREEN);
		io.WriteLine(" _____            __ _   _                _____            _            ");
		io.WriteLine("/  __ \\          / _| | (_)              |  ___|          (_)           ");
		io.WriteLine("| /  \\/_ __ __ _| |_| |_ _ _ __   __ _   | |__ _ __   __ _ _ _ __   ___ ");
		io.WriteLine("| |   | '__/ _` |  _| __| | '_ \\ / _` |  |  __| '_ \\ / _` | | '_ \\ / _ \\");
		io.WriteLine("| \\__/\\ | | (_| | | | |_| | | | | (_| |  | |__| | | | (_| | | | | |  __/");
		io.WriteLine(" \\____/_|  \\__,_|_|  \\__|_|_| |_|\\__, |  \\____/_| |_|\\__, |_|_| |_|\\___|");
		io.WriteLine("                                  __/ |               __/ |             ");
        io.WriteLine("(C) Comprosoft 2016              |___/               |___/              ");
        io.WriteLine("  *Based on Little Alchemy by Jakub Koziol");
        io.WriteLine("\n\n");

        io.SetTextColor(TextColor.CYAN);
        io.WriteLine("Type in a recepie with:");

        io.SetTextColor(TextColor.MAGENTA);
        io.Write("   (Element)");
        io.SetTextColor(TextColor.WHITE);
        io.Write(" + ");
        io.SetTextColor(TextColor.YELLOW);
        io.WriteLine("(Element)\n");

        io.SetTextColor(TextColor.GRAY);
        io.WriteLine("  Type 'list' to show available elements");
        io.SetTextColor(TextColor.RED);
        io.WriteLine("  Type 'help' to show additional help");
        io.SetTextColor(TextColor.WHITE);
        io.WriteLine("\n");
		        
	}
	
	
	/**
	 * Draw the help screen to the console
	 * 
	 * @param io The IOReader to write to
	 */
	public static void DrawHelp(IOHandler io) {
		
		  io.ClearScreen();

	        io.SetTextColor(TextColor.GREEN);;
	        io.WriteLine("Help Screen:");
	        io.WriteLine("------------");

	        io.SetTextColor(TextColor.CYAN);
	        io.WriteLine("Type in a recepie with:");

	        io.SetTextColor(TextColor.MAGENTA);
	        io.Write("   (Element)");
	        io.SetTextColor(TextColor.WHITE);
	        io.Write(" + ");
	        io.SetTextColor(TextColor.YELLOW);
	        io.WriteLine("(Element)\n");

	        io.SetTextColor(TextColor.GREEN);
	        io.WriteLine("\nAll Commands:");
	        io.WriteLine("--------------");

	        io.SetTextColor(TextColor.GRAY);
	        io.WriteLine("  (Element) = show the crafting recepie(s) for that element");
	        io.SetTextColor(TextColor.RED);
	        io.WriteLine("  list [L] = show available elements");
	        io.WriteLine("     [L] (optional) = Starting letter(s), number(s), or symbol(s)");

	        io.WriteLine("");

	        io.SetTextColor(TextColor.YELLOW);;
	        io.WriteLine("  hint = Show a random crafting recepie using unlocked elements");
	        io.WriteLine("");

	        /* These don't work, yet
	        io.SetTextColor(TextColor.BLUE);
	        io.WriteLine("  save = Get the current password");
	        io.SetTextColor(TextColor.MAGENTA);;
	        io.WriteLine("  load = Enter a password to load progress");
	        io.SetTextColor(TextColor.RED);;
	        io.WriteLine("  reset = reset game progress");

	        io.WriteLine("");
*/
	        io.SetTextColor(TextColor.CYAN);
	        io.WriteLine("  clear = clear the screen");
	        io.SetTextColor(TextColor.GREEN);
	        io.WriteLine("  help = display all commands");
	        io.SetTextColor(TextColor.RED);
	        io.WriteLine("  exit = quit the crafting engine game");
	        
	        io.SetTextColor(TextColor.WHITE);
	        io.WriteLine("");
	}


	/**
	 * Run the Crafting Engine game
	 * 
	 * @param args None used
	 * @return Command Result
	 */
	public CommandResult handleCommand(String[] args) {
		RunGame();
		return CommandResult.COMMAND_RESPLASH;
	}
	
}
