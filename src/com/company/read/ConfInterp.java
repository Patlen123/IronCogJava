import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
                                                                                
public class ConfInterp{
	// Scans over 3 tokens, returns integer of the length of the next one
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
				currentLine = currentLine.replaceAll("#.*$", "");
				
				Scanner lineIn = new Scanner(currentLine);
				int words = wordsOnLine(currentLine);
				switch(words){
				case 0:
					
					break;
				case 1:
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
					System.out.println(words);
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
