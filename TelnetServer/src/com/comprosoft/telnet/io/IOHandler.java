package com.comprosoft.telnet.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * Handles all input and output for the telnet server
 */
public class IOHandler {

	private Socket socket;
	final private InputStreamReader input_reader;
	final private BufferedReader reader;
	final private PrintWriter out;
	private TextColor txtColor = TextColor.WHITE;
	private BackColor bgColor;
	
	
	/**
	 * Create a new server Input/Output instance
	 * 
	 * @param socket The socket sending and receiving information from the server
	 * @throws IOException 
	 */
	public IOHandler(Socket socket) throws IOException {
		this.socket = socket;

		this.input_reader = new InputStreamReader(socket.getInputStream());
		this.reader = new BufferedReader(this.input_reader);
        this.out = new PrintWriter(socket.getOutputStream(), true);
        
        this.SetTextColor(txtColor);
	}
	
	
	/**
	 * Read a line without any prompt given
	 * 
	 * @return User input
	 * @throws IOException
	 */
	public String readLine() throws IOException {
		return readLine("");
	}
	
	
    /**
     * Read a line from the terminal, and update with backspaces
     * 
     * @param prompt Prompt to display before asking for user input
     * @return User input
     * @throws IOException 
     */
    public String readLine(String prompt) throws IOException {
    	
    	String retStr = "";
    	int tempChar = 0;
    	
    	//Display the prompt
    	this.Write(prompt);
    	this.Flush();
    	
    	while ((char) tempChar != '\n') {
    		while (reader.ready()) {
    			tempChar = reader.read();
    			
    			if (tempChar > 31 && tempChar < 127) {
    				retStr += (char) tempChar;
    			}
    			//System.out.println(tempChar);
    			
    			//Code for special Chars
    			if (tempChar == 27) {
    				
    				//Read 2 random bytes
    				flushSpecialChar();
    			}
    			
    			if (tempChar == 8) {
    				socket.sendUrgentData(32);
    				socket.sendUrgentData(8);
    				retStr = backspace(retStr);
    			}
    				
    		}
    	}
    	
    	return retStr;
    }
    
    
    /**
     * Remove the last character on a string
     * 
     * @param str The string to modify
     * @return The modified string
     */
    private String backspace(String str) {
    	 if (str != null && str.length() > 0) {
    	      str = str.substring(0, str.length()-1);
    	  }
    	  return str;
    }
    
    
    /**
     * Flush out the buffer when there is a special char
     * 
     * @return The last char in the buffer. (Indicates the special char that was sent)
     * @throws IOException 
     */
    private int flushSpecialChar() throws IOException {
    	reader.read();
    	return reader.read();
    }
    
    
    
	/**
	 * Set the text color for the terminal screen
	 * 
	 * @param c New color for the text
	 */
	public void SetTextColor(TextColor c) {
		this.txtColor = c;
		out.print(c.GetEscapeCode());
		out.flush();
	}

	
	
	/**
	 * Set the background color of the terminal screen
	 * 
	 * @param c The new background color
	 */
	public void SetBackgroundColor(BackColor c) {
		this.bgColor = c;
		out.println(c.GetEscapeCode());
		out.flush();
	}
	
	
	/**
	 * Get the current text color
	 * 
	 * @return The current text color
	 */
	public TextColor GetTextColor() {
		return this.txtColor;
	}
	
	
	/**
	 * Get the current background color
	 * 
	 * @return The current background color
	 */
	public BackColor GetBackgroundColor() {
		return this.bgColor;
	}
	
	
	/**
	 * Write to the output stream, but don't enter at the end
	 * 
	 * @param input The string to write to the output
	 */
	public void Write(String input) {
		out.print(input);
	}
	
	
	/**
	 * 
	 * Write to the output stream, and end with a newline
	 * 
	 * @param input The string to write to the output
	 */
	public void WriteLine(String input) {
		out.println(input);
	}
	
	
	
	/**
	 * Clear all text in the console
	 */
	public void ClearScreen() {
		out.print("\u001B[2J");
		out.flush();
	}
	
	
	/**
	 * Flush the output stream
	 */
	public void Flush() {
		out.flush();
	}
	
	
}
