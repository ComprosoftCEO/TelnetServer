package com.comprosoft.telnet.server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.comprosoft.telnet.Constants;
import com.comprosoft.telnet.configuration.ServerProperties;

/**
 * Telnet Server<br>
 * <br>
 * <b>Created by:</b> Bryan McClain, Comprosoft 2017<br>
 * <br>
 * <b>Origional Code: </b>a.khettar<br>
 * <b>Date: </b>20/02/2013
 */
public class TelnetServer {

    private final Logger logger = Logger.getLogger(Constants.LOGGER_NAME);
    private ServerSocket server = null;
    private final ExecutorService executor = Executors
            .newFixedThreadPool(Integer.valueOf(ServerProperties.MAX_THREADS.getValue()));
    private final String workingDir = System.getProperty("user.dir");

    private final int GIVEN_PORT;

    /**
     * Create a new instance of the Telnet Server
     * 
     * @param port The port number to run the server
     */
    public TelnetServer(String port) {
        GIVEN_PORT = port != null ? Integer.valueOf(port).intValue() : 0;
    }

    /**
     * The main method to start the telnet server
     */
    public void run() {

        try {
            // establish a connection
            server = new ServerSocket(GIVEN_PORT == 0 ? Integer.parseInt(ServerProperties.PORT_NUMBER.getValue()) : GIVEN_PORT);
            logger.info("Server running and listening on port : "
                    + (GIVEN_PORT == 0 ? Integer.parseInt(ServerProperties.PORT_NUMBER.getValue()): GIVEN_PORT));

            while (true) {
                Socket s = server.accept();
                logger.info("Connection to IP: " + s.getInetAddress().toString());
                executor.execute(new ClientWorker(s, workingDir));
            }

        } catch (BindException e) {
        	logger.log(Level.SEVERE,e.getMessage());
        	
        } catch (IOException e) {
        	e.printStackTrace();
            logger.log(Level.WARNING, "Another server is already running. Shutting down the server..");
        } finally {
            executor.shutdown();
        }

    }

    /**
     * Checks if the server is running.
     * 
     * @return Is the server running or not?
     */
    public boolean isRunning() {
        return !server.isClosed();
    }

    
    /**
     * Shutdowns all the connection and the server
     * 
     * @throws IOException
     */
    public void shutDown() throws IOException {
        if (server != null) {

            server.close();

        }

    }
}
