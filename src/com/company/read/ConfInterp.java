import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;

// I am forced to leave this in and waste precious bytes.
// 😊😊😊😊 LMAO WHO DID THIS 😊😊😊😊

public class ConfInterp{
	// Scans line, returns count of the words
	public static int wordsOnLine(String line){
		Scanner lineIn = new Scanner(line);
		int words = 0;
		while(lineIn.hasNext()){
			lineIn.next();
			words++;
		}
		return words;
	}
	public static MultiNode file(String file){

		Scanner in = null;
		// The tree of the file
		MultiNode fileTree =
			// Root node id is the filename, filter to filename only
			new MultiNode(file.replaceAll("^.*/", ""));
		try{
			in = new Scanner(new File(file));
			int lines = 0;
			String currentLine = null;

			while(in.hasNextLine()){
				lines++;
				currentLine = in.nextLine();
				// Removes all commented out text
				currentLine = currentLine.replaceAll("#.*$", "");
				
				Scanner lineIn = new Scanner(currentLine);
				// Based on how many words are on a line
				switch(wordsOnLine(currentLine)){
				case 0:
					// Empty line, can be safely ignored
					break;
				case 1:
					// Can either be a tree closing bracket or an option
					String opt = lineIn.next();
					if (opt.equals("}")){
						fileTree.leaveTree();
					} else if (opt.matches("^[a-z0-9A-Z_.]*$")){
						fileTree.appendOption(opt);
					} else {
						throw new InputMismatchException("error: line " + lines +
														 "is incorrectly formatted.");
					}
					break;

				case 3:
					// Can either be a new tree or a variable declaration
					String id = lineIn.next();
					if (!lineIn.next().equals("=")){
						throw new InputMismatchException("error: line " + lines
														 +" incorrectly formatted");
					}
					String data = lineIn.next();
					if (data.equals("{")){
						fileTree.newTree(id);
					} else {
						fileTree.appendElement(id,data);
					}
					break;
					
				default:
					// If none of the above, the file is incorrectly formatted.
					throw new InputMismatchException("error: line " + lines +
													 "is incorrectly formatted.");
				}
			
			}
		}catch(FileNotFoundException e){
			System.err.printf("error: file not found\n");
			System.exit(1);
		}
		return fileTree;
	}
}
