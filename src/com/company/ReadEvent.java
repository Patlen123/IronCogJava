package com.company;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class ReadEvent extends Object{
	String strFilename;


	public Event (String filename)	{
		strFilename = filename;
		nextTree(strFilename);
	}

	public static void nextTree(String fileName)	{
		Scanner in = null;
		try{
			in = new Scanner(new File(fileName));
			int lines = 0;
			// Read in one line, use second scanner to parse. 
			while(in.hasNextLine()){
				String currentLine = in.nextLine();
				lines++;
				// Matches commented out code and removes it.
				currentLine = currentLine.replaceAll("#.*$", "");
				if (currentLine.contains("{"))	{
					Scanner lineIn = new Scanner(currentLine);
				} else if (currentLine.contains("}"))	{

				}


			}
				

		}catch(FileNotFoundException e){
			System.err.printf("error: file not found\n");
			System.exit(1);
		}
	}

}
