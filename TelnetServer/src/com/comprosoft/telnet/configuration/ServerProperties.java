package com.comprosoft.telnet.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.CodeSource;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.comprosoft.telnet.Constants;
import com.comprosoft.telnet.server.TelnetServer;


/**
 * Holds all global server properties. Automatically loads from file
 *   when server is first run.
 */
public enum ServerProperties {
	PORT_NUMBER ("port.num", "23"),
	MAX_THREADS ("max.threads", "120");
	
	
	
	private final String propertyName;		//The name in the properties file
	private final String defaultValue;		//The default value if no property is found
	
	private static final Logger logger = Logger.getLogger(ServerProperties.class.getName());
	private static final Properties properties = loadProperties();		//The properties loaded from the file

	
	
	/**
	 * Create a new server property type
	 * 
	 * @param name The name of the property in the properties file
	 * @param defaultValue The default value if no property is found
	 */
	ServerProperties(String name, String defaultValue) {
		this.propertyName = name;
		this.defaultValue = defaultValue;
	}
	
	/**
	 * Get the value of a server propertyy
	 * 
	 * @return The value of the property, as a string.
	 */
	public String getValue() {
		if (properties != null &&
			properties.getProperty(this.propertyName) != null) {
				return properties.getProperty(this.propertyName);
		} else {	
			return this.defaultValue;
		}
	}
	
	
	/**
	 * Get the default value for this server property
	 * 
	 * @return The default value, as a string
	 */
	public String getDefaultValue() {
		return this.defaultValue;
	}
	
	
	/**
	 * Load the properties from the server config file
	 * 
	 * @return A propertites object with all server properties
	 */
	private static Properties loadProperties() {
		
        Properties prop = new Properties();
        try {
        	
        	//Get the parent directory of the jar
        	CodeSource codeSource = TelnetServer.class.getProtectionDomain().getCodeSource();
        	String jarDir = (new File(codeSource.getLocation().toURI().getPath())).getParentFile().getPath();
        	String path = jarDir+File.separator+Constants.CONFIG_FILE_NAME;
        	
        	logger.log(Level.INFO, "Reading configuration file from [" + path + "]");
        	
            InputStream in = new FileInputStream(path);
        	if (in != null) {
                prop.load(in);
            }
        	
        	in.close();

        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.WARNING, "Error reading configuration file. Default values will be used instead.");
        }
		
        
		return prop;
	}
	
}
