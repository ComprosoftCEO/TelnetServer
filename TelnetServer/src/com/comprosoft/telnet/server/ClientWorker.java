package com.comprosoft.telnet.server;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.comprosoft.telnet.command.AllCommands;
import com.comprosoft.telnet.command.Command;
import com.comprosoft.telnet.io.IOHandler;
import com.comprosoft.telnet.io.TextColor;

/**
 * 
 * Client Worker handling request of a client.<br>
 * <br>
 * <b>Created by: </b>Bryan McClain, Comprosoft 2017<br>
 * <b>Original code: </b> A.Khettar
 * 
 */
public class ClientWorker implements Runnable {

    private final Socket socket;
    private final IOHandler io;
    private final Logger logger = Logger.getLogger(ClientWorker.class.getName());

    /**
     * Create a new Telnet Server client worker
     * 
     * @param socket The socket running the server
     * @param homeDir The home directory for the server
     * @throws IOException 
     */
    public ClientWorker(final Socket socket, String homeDir) throws IOException {
        this.socket = socket;
      	this.io = new IOHandler(socket);
    }
    
    


    /*
     * Execute the server code for this client
     */
    @Override
    public void run() {

        try {
        	showWelcomeScreen();
            
            boolean cancel = false;
            while (!cancel) {
             
                io.SetTextColor(TextColor.WHITE);
                String command = io.readLine("-> ");
                command = command.trim();
                command = command.replaceAll("\\P{Print}", "");
                
                if (command == null) {
                	io.WriteLine("Null!");
                	continue;
                }
                
                if (command.length() == 0) {continue;}
    
                
                //handle the command
                String tokens[] = Command.tokenize(command);
                Command cmd = AllCommands.getCommand(tokens[0], io);
                
                switch(cmd.handle(tokens)) {
                case COMMAND_EXIT:
                	cancel = true;
                	break;
                	
                case COMMAND_RESPLASH:
                	io.ClearScreen();
                	showWelcomeScreen();
                	break;
                	
                default:
                	break;
                
                }
                
            }
            
        } catch (IOException e) {
        	io.SetTextColor(TextColor.RED);
        	io.WriteLine(e.getMessage());
            logger.log(Level.SEVERE, e.getMessage(), e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Failed to close the socket", e);
            }
        }
    }
    
    
    
    /**
     * Builds welcome screen.
     * 
     * @return
     */
    private void showWelcomeScreen() {
    	
    	io.SetTextColor(TextColor.WHITE);
    	io.WriteLine("");
        io.WriteLine("======================================================");
        io.WriteLine("");
        io.WriteLine("   Welcome to Comprosoft Telnet Server: Version 1.0   ");
        io.WriteLine("");
        io.WriteLine("======================================================");

        io.WriteLine("");
        io.WriteLine("");
        
        io.SetTextColor(TextColor.CYAN);
        io.Write("Type ");
        io.SetTextColor(TextColor.MAGENTA);
        io.Write("\"List\"");
        io.SetTextColor(TextColor.CYAN);
        io.WriteLine(" to show all available commands.");
        
        io.WriteLine("");
        io.WriteLine("");
    }

}
