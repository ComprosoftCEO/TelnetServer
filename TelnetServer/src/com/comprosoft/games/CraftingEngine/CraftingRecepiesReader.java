package com.comprosoft.games.CraftingEngine;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

import com.comprosoft.telnet.io.TextColor;

public abstract class CraftingRecepiesReader {

	public static ArrayList<Element> ReadFile() throws IOException {
		
		ArrayList<Element> list = new ArrayList<Element>();
		//FileReader fr = new FileReader(System.getProperty("user.dir") +"\\CraftingRecepies.txt");
		InputStream is = CraftingRecepiesReader.class.getResourceAsStream("/CraftingRecepies.txt");
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
	
}
