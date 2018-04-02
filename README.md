# Telnet Server
Telnet Server written in Java

<br>

## Compiling and Running
The Telnet server is designed to run with the [Eclipse IDE](https://www.eclipse.org/). The main method is in the file _ServerControl.java_, which uses a JFrame interface to display the server log.

<br>

## Server Properties
Various properties of the server (such as the port number or maximum amount of threads) can be changed in the _server.properties_ file. Additional properties can be added by modifying _ServerProperties.java_ and adding more entries to the defined enum.

<br>

## Adding Commands
The list of commands is stored in _AllCommands.java_, and adding a command is as simple as defining another entry in the enum. When adding a command, be sure to include a short description. 

Commands are stored in the enum using the "Class" class (see the [Javadocs on Reflection](https://docs.oracle.com/javase/tutorial/reflect/)). For a class to be a valid:
1. It must extend the abstract class "Command"
2. It must have one constructor:
```java
public constructor(IOHandler io);
```
While some special commands may have different constructors, this is the format needed to add a command to the AllCommands enum.

<br>

## Credits
The Telnet Server code is based on Ayache Khettar's [telnet-server](https://github.com/akhettar/telnet-server). However, extension modifications were made to the code to adapt it for other uses.

The Crafting Engine code comes from another project, [CraftingEngine](https://github.com/ComprosoftCEO/CraftingEngine), which is based on the game [Little Alchemy](https://littlealchemy.com/) by Jakob Koziol. As of yet, the Java version doesn't implement saving and loading.
