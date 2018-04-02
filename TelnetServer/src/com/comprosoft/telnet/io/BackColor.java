package com.comprosoft.telnet.io;

/**
 * Stores background colors that can be used with the IOReader to change
 * 	the color of the terminal background
 */
public enum BackColor {
	
	BLACK ("\u001B[40m"),
	RED ("\u001B[41m"),
	GREEN ("\u001B[42m"),
	YELLOW ("\u001B[43m"),
	BLUE ("\u001B[44m"),
	MAGENTA ("\u001B[45m"),
	CYAN ("\u001B[46m"),
	WHITE ("\u001B[47m");
	
	
	private String EscapeCode;
	
	/**
	 * Create a new BackColor object
	 * 
	 * @param EscapeCode The ANSI character escape sequence used to change the terminal color
	 */
	BackColor(String EscapeCode) {
		this.EscapeCode = EscapeCode;
	}
	
	
	/**
	 * Get the ANSI escape sequence used to print color to the terminal
	 * 
	 * @return ANSI escape sequence for the background color
	 */
	public String GetEscapeCode() {
		return this.EscapeCode;
	}
	
	
	
	/**
	 * Convert the name of the color into a color enum<br>
	 * <br>
	 * <b>The following colors are allowed (in upper or lower case):</b><br>
	 * <i>**Note: dark and light colors are exactly the same.<i>
	 * <ul>
	 * 	<li>Black</li>
	 * 	<li>Blue</li>
	 * 	<li>Cyan</li>  
	 * 	<li>Dark Blue (same as Blue)</li> 
	 * 	<li>Dark Cyan (same as Cyan)</li> 
	 * 	<li>Dark Gray (same as Gray)</li> 
	 * 	<li>Dark Green (same as Green)</li> 
	 * 	<li>Dark Magenta (same as Magenta)</li> 
	 * 	<li>Dark Red (same as Red)</li> 
	 * 	<li>Dark Yellow (same as Yellow)</li>
	 * 	<li>Gray</li> 
	 * 	<li>Green</li>  
	 * 	<li>Magenta</li>  
	 * 	<li>Red</li>  
	 * 	<li>White</li>  
	 * 	<li>Yellow</li>  
	 * </ul>
	 * <i>Dark colors can be specified as:</i>
	 * <ul>
	 * 	<li><i>dark (color)</i></li>
	 *  <li><i>dark(color)</i></li>
	 *  <li><i>dk (color)</i></li>
	 *  <li><i>dk(color)</i></li>
	 *  <li><i>d (color)</i></li>
	 *  <li><i>d(color)</i></li>
	 * </ul>
	 * 
	 * @param name The name of the color to parse
	 * @return The corresponding background color
	 */
	public static BackColor NameToColor(String name) {
        name = name.toLowerCase();
        switch (name) {
            case "black":
                return BackColor.BLACK;
            case "blue":
                return BackColor.BLUE;
            case "cyan":
                return BackColor.CYAN;
            case "dblue":
            case "dkblue":
            case "d blue":
            case "dk blue":
            case "darkblue":
            case "dark blue":
                return BackColor.BLUE;
            case "dcyan":
            case "dkcyan":
            case "d cyan":
            case "dk cyan":
            case "darkcyan":
            case "dark cyan":
                return BackColor.CYAN;
            case "dgray":
            case "dkgray":
            case "d gray":
            case "dk gray":
            case "darkgray":
            case "dark gray":
                return BackColor.BLACK;
            case "dgreen":
            case "dkgreen":
            case "d green":
            case "dk green":
            case "darkgreen":
            case "dark green":
                return BackColor.GREEN;
            case "dmagenta": 
            case "dkmagenta": 
            case "d magenta": 
            case "dk magenta":
            case "darkmagenta": 
            case "dark magenta":
                return BackColor.MAGENTA;
            case "dred": 
            case "dkred":
            case "d red": 
            case "dk red":
            case "darkred":
            case "dark red":
                return BackColor.RED;
            case "dyellow": 
            case "dkyellow": 
            case "d yellow": 
            case "dk yellow": 
            case "darkyellow": 
            case "dark yellow":
                return BackColor.YELLOW;
            case "gray":
            	return BackColor.WHITE;
            case "green":
                return BackColor.GREEN;
            case "magenta":
                return BackColor.MAGENTA;
            case "red":
                return BackColor.RED;
            case "white":
            	return BackColor.WHITE;
            case "yellow":
                return BackColor.YELLOW;
            default:
                return BackColor.WHITE;
        }
	}
}
