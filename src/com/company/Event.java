package com.company;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileNotFoundException;;

public class Event extends Object{
	public static void main(String[] args){
		nextTree("../config/test.txt");
	}
	public static void nextTree(String fileName){
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
				System.exit(0);
				Scanner lineIn = new Scanner(currentLine);
			}
				

		}catch(FileNotFoundException e){
			System.err.printf("error: file not found\n");
			System.exit(1);
		}
	}

}
