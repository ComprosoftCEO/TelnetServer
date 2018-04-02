package com.comprosoft.telnet.command;

/**
 * All possible returns from a comman
 */
public enum CommandResult {
	COMMAND_OK,			// The command ran successfully
	COMMAND_EXIT,		// Exit the telnet server
	COMMAND_RESPLASH	// Redisplay the splash screen
}
