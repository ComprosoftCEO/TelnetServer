package com.comprosoft.games.CraftingEngine;

import java.util.ArrayList;

import com.comprosoft.telnet.io.IOHandler;
import com.comprosoft.telnet.io.TextColor;

/**
 * Represents an element in the crafting engine, with a list of all recepies for the element
 */
public class Element implements Comparable<Element>{

    private final String name;
    private ArrayList<Recipe> allRecipes = new ArrayList<Recipe>();
    private final TextColor ConsoleColor;
   
    /**
     * Create a new element
     * 
     * @param name The name of the element
     * @param recipes The list of recipes of the element.
     * 			<i>Recipes are E1+E2 seperated by semicolons.</i>
     * @param c The color of the element
     * @throws Exception
     */
	public Element(String name, String recipes, TextColor c) throws Exception {
		
		this.name = name;
		this.ConsoleColor = c;
		
		//Parse the recipes
		String recepieList[] = recipes.split(";");
		
		for (String s : recepieList) {
			
			String ingredients[] = s.split("\\+");
			
			if (ingredients.length > 2) {
				throw new Exception("Illegal Crafting Recipe!");
			} else if (ingredients.length > 1) {
				allRecipes.add(new Recipe(ingredients[0], ingredients[1]));
			}
			
		}
		
		
		
	}
	
	/**
	 * Get the name of the element
	 * 
	 * @return The element's name as a string
	 */
	public String GetName() {
		return this.name;
	}
	
	
	/**
	 * Get the color for the element
	 * 
	 * @return The element's color as a TextColor
	 */
	public TextColor GetColor() {
		return this.ConsoleColor;
	}
	
	
	/**
	 * Draw all of the recipes for this element to the console, with
	 * pretty formatting and colors
	 * 
	 * @param io The IOReader object to output to
	 */
	public void DrawRecipes(IOHandler io) {
		
		io.SetTextColor(TextColor.WHITE);
		io.Write("Crafting recepie for ");
		io.SetTextColor(this.ConsoleColor);
		io.WriteLine(Element.toTitleCase(name) + ":");
		io.SetTextColor(TextColor.WHITE);

		//Draw a line comparable to the string width
		for (int i = 0; i < new String("Crafting Recepies for " + name + ":").length(); i++) {
			io.Write("-");
		}
		io.WriteLine("");

		//No recipes for this element
		if (allRecipes.size() == 0) {
			io.SetTextColor(TextColor.MAGENTA);
            io.WriteLine(" *Uncraftable");
            io.SetTextColor(TextColor.WHITE);
		} else {
			
			//Draw all recipes
			for (Recipe r : allRecipes) {
				r.DrawRecipe(io);
			}
		}
		
		io.SetTextColor(TextColor.WHITE);
		io.WriteLine("");;
		
	}
	
	
	
	/**
	 * Test if two elements can be crafted to form another element
	 * 
	 * @param e1 The name of the first element
	 * @param e2 The name of the second element
	 * @return
	 */
	public boolean TestElementCraftable(String e1, String e2) {
		
		//Search through all recipes
		for (Recipe r : allRecipes) {
			if (r.TestRecipe(e1, e2)) {
				return true;
			}		
		}
		
		return false;
	}
    
	
	
	/**
	 * Draw the element to the console with nice formatting and colors
	 * 
	 * @param io The IOReader to write to
	 * @param newline Should a newline be drawn at the end?
	 */
	public void DrawElement(IOHandler io, boolean newline) {
		
		TextColor TempColor = io.GetTextColor();
		io.SetTextColor(this.ConsoleColor);
		if (newline) {
			io.WriteLine(toTitleCase(this.name));		
		} else {
			io.Write(toTitleCase(this.name));
		}
		io.SetTextColor(TempColor);
	}
	
	
	public void DrawElement(IOHandler io) {
		this.DrawElement(io, true);
	}
	
	
	/**
	 * Convert a string to title case
	 * 
	 * @param input The string to convert to title case
	 * @return The formatted string
	 */
	private static String toTitleCase(String input) {
	    StringBuilder titleCase = new StringBuilder();
	    boolean nextTitleCase = true;

	    for (char c : input.toCharArray()) {
	        if (Character.isSpaceChar(c)) {
	            nextTitleCase = true;
	        } else if (nextTitleCase) {
	            c = Character.toTitleCase(c);
	            nextTitleCase = false;
	        }

	        titleCase.append(c);
	    }

	    return titleCase.toString();
	}
	
	
    
    
	@Override
	public int compareTo(Element other) {
		return name.compareTo(other.name);
	}
	
	
	/**
	 * Represents a single recipe in the element
	 */
	private class Recipe {
		String element1;
		String element2;
		
		/**
		 * Create a new recipe object
		 * 
		 * @param one The name of the first element in the crafting recipe
		 * @param two The name of the second element in the crafting recipe
		 */
		public Recipe(String one, String two) {
			this.element1 = one;
			this.element2 = two;
			
		}
		
		/**
		 * Test if the two element names math this recipe (in any order)
		 * 
		 * @param e1 Element 1
		 * @param e2 Element 2
		 * @return These elements match the recipe
		 */
		public boolean TestRecipe(String e1, String e2) {
			
			if ((e1.toLowerCase().equals(element1.toLowerCase()) && e2.toLowerCase().equals(element2.toLowerCase())) ||
				(e1.toLowerCase().equals(element2.toLowerCase()) && e2.toLowerCase().equals(element1.toLowerCase()))) {
					return true;
			} else {
				return false;
			}
			
		}
		
		public String toString() {
			return element1 + "+" + element2;
		}
		
		
		/**
		 * Draw the recipe to the console
		 * 
		 * @param io The IOReader object to write to
		 */
		public void DrawRecipe(IOHandler io) {
			io.SetTextColor(ElementCollection.GetElementColor(element1));
			io.Write(Element.toTitleCase(element1));
			io.SetTextColor(TextColor.WHITE);
			io.Write(" + ");
			io.SetTextColor(ElementCollection.GetElementColor(element2));
			io.WriteLine(Element.toTitleCase(element2));
		}
		
		
	}
	
}
