package com.comprosoft.telnet.io;



/**
 * Stores colors that can be used with the IOReader
 */
public enum TextColor {
	
	BLACK ("\u001B[22;30m"),
	DARK_RED ("\u001B[22;31m"),
	DARK_GREEN ("\u001B[22;32m"),
	DARK_YELLOW ("\u001B[22;33m"),
	DARK_BLUE ("\u001B[22;34m"),
	DARK_MAGENTA ("\u001B[22;35m"),
	DARK_CYAN ("\u001B[22;36m"),
	DARK_GRAY ("\u001B[1;30m"),		//LightBlack = Dark Gray
	GRAY ("\u001B[22;37m"),			//Dark White = Light Gray
	
	RED ("\u001B[1;31m"),
	GREEN ("\u001B[1;32m"),
	YELLOW ("\u001B[1;33m"),
	BLUE ("\u001B[1;34m"),
	MAGENTA ("\u001B[1;35m"),
	CYAN ("\u001B[1;36m"),
	WHITE ("\u001B[1;37m");
	
	private String EscapeCode;
	
	/**
	 * Create a new TextColor
	 * 
	 * @param EscapeCode The escape code sequence used to print the color to the terminal
	 */
	TextColor(String EscapeCode) {
		this.EscapeCode = EscapeCode;
	}
	
	/**
	 * Get the escape code sequence used to print color to the terminal
	 * 
	 * @return An Ascii escape code
	 */
	public String GetEscapeCode() {
		return this.EscapeCode;
	}
	
	/**
	 * Convert the name of the color into a color enum<br>
	 * <br>
	 * <b>The following colors are allowed (in upper or lower case):</b>
	 * <ul>
	 * 	<li>Black</li>
	 * 	<li>Blue</li>
	 * 	<li>Cyan</li>  
	 * 	<li>Dark Blue</li> 
	 * 	<li>Dark Cyan</li> 
	 * 	<li>Dark Gray</li> 
	 * 	<li>Dark Green</li> 
	 * 	<li>Dark Magenta</li> 
	 * 	<li>Dark Red</li> 
	 * 	<li>Dark Yellow</li>
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
	 * @return The corresponding text color
	 */
	public static TextColor NameToColor(String name) {
        name = name.toLowerCase();
        switch (name) {
            case "black":
                return TextColor.BLACK;
            case "blue":
                return TextColor.BLUE;
            case "cyan":
                return TextColor.CYAN;
            case "dblue":
            case "dkblue":
            case "d blue":
            case "dk blue":
            case "darkblue":
            case "dark blue":
                return TextColor.DARK_BLUE;
            case "dcyan":
            case "dkcyan":
            case "d cyan":
            case "dk cyan":
            case "darkcyan":
            case "dark cyan":
                return TextColor.DARK_CYAN;
            case "dgray":
            case "dkgray":
            case "d gray":
            case "dk gray":
            case "darkgray":
            case "dark gray":
                return TextColor.DARK_GRAY;
            case "dgreen":
            case "dkgreen":
            case "d green":
            case "dk green":
            case "darkgreen":
            case "dark green":
                return TextColor.DARK_GREEN;
            case "dmagenta": 
            case "dkmagenta": 
            case "d magenta": 
            case "dk magenta":
            case "darkmagenta": 
            case "dark magenta":
                return TextColor.DARK_MAGENTA;
            case "dred": 
            case "dkred":
            case "d red": 
            case "dk red":
            case "darkred":
            case "dark red":
                return TextColor.DARK_RED;
            case "dyellow": 
            case "dkyellow": 
            case "d yellow": 
            case "dk yellow": 
            case "darkyellow": 
            case "dark yellow":
                return TextColor.DARK_YELLOW;
            case "gray":
            	return TextColor.WHITE;
            case "green":
                return TextColor.GREEN;
            case "magenta":
                return TextColor.MAGENTA;
            case "red":
                return TextColor.RED;
            case "white":
            	return TextColor.WHITE;
            case "yellow":
                return TextColor.YELLOW;
            default:
                return TextColor.WHITE;
        }
        
	}
	
/* This is a really useful table that I have to inlcude:

	╔══════════╦════════════════════════════════╦═════════════════════════════════════════════════════════════════════════╗
	║  Code    ║             Effect             ║                                   Note                                  ║
	╠══════════╬════════════════════════════════╬═════════════════════════════════════════════════════════════════════════╣
	║ 0        ║  Reset / Normal                ║  all attributes off                                                     ║
	║ 1        ║  Bold or increased intensity   ║                                                                         ║
	║ 2        ║  Faint (decreased intensity)   ║  Not widely supported.                                                  ║
	║ 3        ║  Italic                        ║  Not widely supported. Sometimes treated as inverse.                    ║
	║ 4        ║  Underline                     ║                                                                         ║
	║ 5        ║  Slow Blink                    ║  less than 150 per minute                                               ║
	║ 6        ║  Rapid Blink                   ║  MS-DOS ANSI.SYS; 150+ per minute; not widely supported                 ║
	║ 7        ║  [[reverse video]]             ║  swap foreground and background colors                                  ║
	║ 8        ║  Conceal                       ║  Not widely supported.                                                  ║
	║ 9        ║  Crossed-out                   ║  Characters legible, but marked for deletion.  Not widely supported.    ║
	║ 10       ║  Primary(default) font         ║                                                                         ║
	║ 11–19    ║  Alternate font                ║  Select alternate font `n-10`                                           ║
	║ 20       ║  Fraktur                       ║  hardly ever supported                                                  ║
	║ 21       ║  Bold off or Double Underline  ║  Bold off not widely supported; double underline hardly ever supported. ║
	║ 22       ║  Normal color or intensity     ║  Neither bold nor faint                                                 ║
	║ 23       ║  Not italic, not Fraktur       ║                                                                         ║
	║ 24       ║  Underline off                 ║  Not singly or doubly underlined                                        ║
	║ 25       ║  Blink off                     ║                                                                         ║
	║ 27       ║  Inverse off                   ║                                                                         ║
	║ 28       ║  Reveal                        ║  conceal off                                                            ║
	║ 29       ║  Not crossed out               ║                                                                         ║
	║ 30–37    ║  Set foreground color          ║  See color table below                                                  ║
	║ 38       ║  Set foreground color          ║  Next arguments are `5;n` or `2;r;g;b`, see below                       ║
	║ 39       ║  Default foreground color      ║  implementation defined (according to standard)                         ║
	║ 40–47    ║  Set background color          ║  See color table below                                                  ║
	║ 48       ║  Set background color          ║  Next arguments are `5;n` or `2;r;g;b`, see below                       ║
	║ 49       ║  Default background color      ║  implementation defined (according to standard)                         ║
	║ 51       ║  Framed                        ║                                                                         ║
	║ 52       ║  Encircled                     ║                                                                         ║
	║ 53       ║  Overlined                     ║                                                                         ║
	║ 54       ║  Not framed or encircled       ║                                                                         ║
	║ 55       ║  Not overlined                 ║                                                                         ║
	║ 60       ║  ideogram underline            ║  hardly ever supported                                                  ║
	║ 61       ║  ideogram double underline     ║  hardly ever supported                                                  ║
	║ 62       ║  ideogram overline             ║  hardly ever supported                                                  ║
	║ 63       ║  ideogram double overline      ║  hardly ever supported                                                  ║
	║ 64       ║  ideogram stress marking       ║  hardly ever supported                                                  ║
	║ 65       ║  ideogram attributes off       ║  reset the effects of all of 60-64                                      ║
	║ 90–97    ║  Set bright foreground color   ║  aixterm (not in standard)                                              ║
	║ 100–107  ║  Set bright background color   ║  aixterm (not in standard)                                              ║
	╚══════════╩════════════════════════════════╩═════════════════════════════════════════════════════════════════════════╝
*/
}
