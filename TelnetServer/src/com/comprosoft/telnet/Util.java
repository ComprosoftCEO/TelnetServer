package com.comprosoft.telnet;

/**
 * @author a.khettar
 * 
 */
public final class Util {

    /**
     * Builds welcome screen.
     * 
     * @return
     */
    public static String buildWelcomeScreen() {
        String cr = System.getProperty("os.name").matches("(W|w)indows.*") ? "\r\n" : "\n";
        StringBuilder builder = new StringBuilder();
        builder.append(cr);
        builder.append("======================================================"+cr);
        builder.append(cr);
        builder.append("   Welcome to Bryan's Telnet Server: Version 1.0   "+cr);
        builder.append(cr);
        builder.append("======================================================"+cr);
        builder.append(cr);
        builder.append(cr);
        builder.append("How about playing some fun games:"+cr);
        builder.append(cr);
        builder.append("    craft: Combine two elements to make a new element");
        builder.append(cr);
        return builder.toString();
    }

}
