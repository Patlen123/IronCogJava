import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;



public class ConfInterp{
	public class file(String file){
		BufferedReader in = null;
		Scanner in = null;
		try{
			in = new Scanner(new File(file));
			int lines = 0;
			while(in.hasNextLine()){
				currentLine = currentLine.replaceAll("#.*$", "");
				Scanner lineIn = new Scanner(currentLine);
				String id = lineIn.next();
				if !(lineIn.next().equals("=")){
					throw new InputMismatchException("error: line " + lines
													 +" incorrectly formatted");
				}
				String data = lineIn.next();
				if (data.equals("{")){
					// Creates a new level and descends into it
					eventTree.newLevel(id);
				} else if (data.equals("}")) {
					// Goes up a level in the tree
					eventTree.unLevel();
				} else {
					// Adds a new element to the current level
					eventTree.addElement(id, data);
				}
				lines++;
			}


		}catch(FileNotFoundException e){
			System.err.printf("error: file not found\n");
			System.exit(1);
		}
	}
}
