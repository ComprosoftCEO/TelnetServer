package com.comprosoft.games.CraftingEngine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

import com.comprosoft.telnet.io.IOHandler;
import com.comprosoft.telnet.io.TextColor;

/**
 * Holds all of the data necessary for a CraftingEngine game session.<br>
 * <br>
 * Contains most all methods (hint, save, load, craft, etc...)
 */
public class ElementCollection {

	static ArrayList<Element> AllElements;
	private ArrayList<Element> UnlockedElements = new ArrayList<Element>();
	private final IOHandler io;
	
	/**
	 * Create a new collection of elements. Essentially acts as a game session.
	 *
	 * @param io The IOReader to write to
	 * @throws IOException
	 */
	public ElementCollection(IOHandler io) throws IOException {
		this.io = io;
		
		if (AllElements == null) {
			AllElements = ReadFile();
		}
		FillUnlocked();
		
	}
	
	
	/**
	 * Parse the file stored in resources that lists all of the crafting recipes
	 * 
	 * @return An array containing all allowed elements
	 * @throws IOException
	 */
	private static ArrayList<Element> ReadFile() throws IOException {
		
		ArrayList<Element> list = new ArrayList<Element>();
		
		//Get the path inside the JAR and open the file for reading
		InputStream is = CraftingEngine.class.getResourceAsStream("/CraftingRecepies.txt");
		BufferedReader textReader = new BufferedReader(new InputStreamReader(is));
		
		String line;
		
		while ((line = textReader.readLine()) != null) {
			
			//Split recipe into    Name,Color:A+B;A+B;
			String RecipeParts[] = line.split(Pattern.quote(":"));
			String RecipeParts2[] = RecipeParts[0].split(Pattern.quote(","));
			
			try {
				list.add(new Element(RecipeParts2[0], RecipeParts[1], TextColor.NameToColor(RecipeParts2[1])));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		
		textReader.close();
		
		return list;
	}
	
	
	/**
	 * Set up a new game with only the default elements unlocked
	 */
	private void FillUnlocked() {
		for (int i = 0; i < 4; i++) {
			UnlockedElements.add(AllElements.get(i));
		}
	}
	

	/**
	 * Reset the game back to its default state
	 */
	public void Reset() {
		UnlockedElements.clear();
		FillUnlocked();
		io.ClearScreen();
		CraftingEngine.DrawTitle(io);
	}
	
	
	
	 /**
     * Craft two elements given together given a string formatted with<br>
     * <b>E1+E2</b><br>
     * <br>
     * If only one element is given, display the recipe(s) of that element<br>
     * If no crafting recipe exists or elements are unknown, throw an error to the console
     * 
     * @param input The formatted string with the recipe
     */
    public void Craft(String input) {
		
    	
        //Convert the list into an element
        String Inputs[] = input.split(Pattern.quote("+"));

        //Test for invalid recipe
        if (Inputs.length > 2) {
            io.SetTextColor(TextColor.RED);
            io.WriteLine("\nBad crafting recipe!");
            io.SetTextColor(TextColor.WHITE);
            io.WriteLine("");
            return;
        }

        //Test for blank input
        if (input.length() == 0) {return;}

        //if only one element is given, display that element's recipes
        if (Inputs.length == 1) {
            ShowRecipes(input.trim());
            return;
        }

        Craft(Inputs[0].trim(), Inputs[1].trim());
		
	}
	
    
	
	
	
	/**
	 * Show alll recipes of a given element
	 * 
	 * @param input The element
	 */
    private void ShowRecipes(String input) {
    	io.WriteLine("");
    	boolean isFound = false;
    	
    	for (Element e : UnlockedElements) {
    		if (e.GetName().toLowerCase().equals(input.toLowerCase())) {
    			e.DrawRecipes(io);
    			isFound = true;
    		}
    		
    	}

    	if (isFound == false) {
    		io.SetTextColor(TextColor.RED);
            io.WriteLine("Unknown element " + input);
            io.SetTextColor(TextColor.WHITE);
            io.WriteLine("");
    	}

    }
	
	
    
    
    /**
     * Test if an element is unlocked or not
     * 
     * @param input The element name to test
     * @return Unlocked or not??
     */
    private boolean TestUnlocked(String input) {
    	for (Element e : UnlockedElements) {
    		if (e.GetName().toLowerCase().equals(input.toLowerCase())) {
    			return true;
    		}
    	}
    	return false;
    }
	
    
	
    /**
     * Tests if alll elements have been unlocked
     * 
     * @return Are all unlocked or not?
     */
    
    @SuppressWarnings("unused")
	private boolean TestAllUnlocked() {
    	if (UnlockedElements.size() == AllElements.size()) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    
    
    /**
     * Craft two elements together given their string names
     * 
     * @param element1 The first element to craft
     * @param element2 The second element to craft
     */
    private void Craft(String element1, String element2) {

    	//First, verify that both elements are unlocked or valid
        boolean toBreak = false;
        if (TestUnlocked(element1) == false) {
            io.SetTextColor(TextColor.RED);
            io.WriteLine("\nUnknown element " + element1);
            toBreak = true;
        }

        if (TestUnlocked(element2) == false) {
            io.SetTextColor(TextColor.RED);
            io.WriteLine("\nUnknown element " + element2);
            toBreak = true;
        }

        if (toBreak == true) {
            io.SetTextColor(TextColor.WHITE);
        	io.WriteLine("");
        } else {

        	//Both items are unlocked
        	boolean didCraft = false;
        	
        	//Loop through all elements and do crafting
        	for (Element e : AllElements) {
        		if (e.TestElementCraftable(element1.toLowerCase(), element2.toLowerCase())) {
        			if (didCraft == false) {
                        io.SetTextColor(TextColor.WHITE);
                        io.WriteLine("\nYou crafted:");
                        didCraft = true;
        			}
        			e.DrawElement(io);
        		       		
        			if (TestUnlocked(e.GetName()) == false) {
        				UnlockedElements.add(e);
        			}
        		}
        		
        	}


        io.WriteLine("");

        //Nothing crafted. Display message
        if (didCraft == false) {
            io.SetTextColor(TextColor.RED);
            io.WriteLine("You didn't craft anything...");
            io.SetTextColor(TextColor.WHITE);
            io.WriteLine("");
        }
        }
    }
    
    
    
  
    	/**
    	 * List all unlocked elements that match a formatted list string<br>
    	 * <br>
    	 * <i>List is formatted with: list [a b c d ...], with a,b,c being the letter the element starts with</i>
    	 * 
    	 * @param command The formatted list command
    	 */
    	public void List(String command) {

		        //Get all valid chars
	            ArrayList<Character> ToSearchFor = new ArrayList<Character>();
	            boolean toBreak = false;
	            
	            for (String e : command.toLowerCase().split(Pattern.quote(" "))) {
	            	e.trim();
	            	
	            	//Make sure searches are single letter characters
	            	if (e.length() == 1) {
	            		ToSearchFor.add(e.toCharArray()[0]);
	            	} else if (!(e.equals("list") || e.length() == 0)) {
	            		io.SetTextColor(TextColor.RED);
	            		io.WriteLine("\nInvalid character " + e + "\n");
	            		toBreak = true;
	            	}
	            	
	            }

	            //All characters are valid
	            if (toBreak == false) {
	            	//Get unique values
	            	Set<Character> setWithUniqueValues = new HashSet<Character>(ToSearchFor);
	            	ListElements(new ArrayList<Character>(setWithUniqueValues));
	            	
	            }
			}    	

        
    	
    	  /**
         * List all unlocked elements that have a name starting with the letters in toFind
         * <i>A null list displays all unlocked elements</i>
         * 
         * @param toFind The list of starting characters to find
         */
        private void ListElements(ArrayList<Character> toFind) {

            Collections.sort(UnlockedElements);
            Collections.sort(toFind);
            
            if (toFind.size() == 0) {
                io.SetTextColor(TextColor.WHITE);
                io.WriteLine("\nAvailable Elements:");
                
        		for (Element e : UnlockedElements) {
                    e.DrawElement(io);
                }
                io.WriteLine("");
            } else {
                io.WriteLine("");

                for (Character c : toFind) {
                	
                    io.SetTextColor(TextColor.WHITE);
                    
        			io.WriteLine("------" + c.toString().toUpperCase() + "------");

                    boolean found = false;
                    
                    for (Element e : UnlockedElements) {
                    	if (e.GetName().substring(0, 1).toLowerCase().equals(c.toString().toLowerCase())) {
                    		e.DrawElement(io);
                    		found = true;
                    	}
                    	
                    }
                    
                    if (found == false) {
                      	io.SetTextColor(TextColor.MAGENTA);
                        io.WriteLine(" *No Elements Found");
                    }
                    
                    io.SetTextColor(TextColor.WHITE);
                    io.WriteLine("");
                }
            }
        	}

        	
        	
        
    	
        

        /**
         * Display a random crafting recipe to the screen<br>
         * <br>
         * <i>It may show recipes that have already been tried</i>
         */
        public void Hint() {

           //'Pick two random elements
           Random rand = new Random();
           
           while (true) {
           
        	   Element E1 = UnlockedElements.get(rand.nextInt(UnlockedElements.size()));
        	   Element E2 = UnlockedElements.get(rand.nextInt(UnlockedElements.size()));

        	   //**Origionally going to test if element already crafted
        	   //    Left out this code
        	   
        	   //boolean didCraft = false;

        	for (Element e : AllElements) {
        		if (e.TestElementCraftable(E1.GetName().toLowerCase(), E2.GetName().toLowerCase())) {
        			//if (didCraft == false) {
                        io.SetTextColor(TextColor.WHITE);
                        io.Write("\nTry combining ");
                        E1.DrawElement(io, false);
                        io.Write(" and ");
                        E2.DrawElement(io, false);
                        io.SetTextColor(TextColor.WHITE);
                        io.WriteLine("\n");
                      //  didCraft = true;
                        return;
        			//}        			
        		}
        	
        	}
        }

        }

    
       
        
        /**
    	 * Get the color associated with the name of an element
    	 * 
    	 * @param element The name of the element to search for
    	 * @return The color associated with this element
    	 */
    	public static TextColor GetElementColor(String element) {
    		
    		for (Element e : AllElements) {
    			if (e.GetName().toLowerCase().equals(element.toLowerCase())) {
    				return e.GetColor();
    			}
    		}
    		
    		return TextColor.WHITE;
    	}
    
}
