import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;

// I am forced to leave this in and waste precious bytes.
// 😊😊😊😊 LMAO WHO DID THIS 😊😊😊😊

public class ConfInterp{

	// Scans a string that is predetermined to be of a colour. 
	// If preparing a string, make sure it's at least 5 words long,
	// otherwise it might be too short
	private static ColorNode parseColor(String color){
		Scanner in = new Scanner(color);
		String name  = in.next();
		// Second element is always an "=", otherwise fail
		if (!in.next().equals("=")){
			throw new InputMismatchException("error: color parameter not properly formatted.");
		}

		String colorModel = in.next();
		// This might be interpretted incorrectly if color model is not
		// present. If this is not the case, skips the one after.
		if (colorModel.equals("{")) {
			String colorModel = "rgb";
		} else {
			if (!in.next().equals("{")) {
				throw new InputMismatchException("error: color parameter not properly formatted.");
			}
		}
		
		if (colorModel.equals("rgb")) {
			// RGB is formatted in 3 ints
			int[] rgbColor = new int[3];
			for (int i = 0; i < 3; i++) {
				rgbColor = in.nextInt();
			}
			// One final check whether the string is right
			if (!in.next().equals("}")) {
				throw new InputMismatchException("error: color parameter improperly formatted.");
			}
			return new ColorNode(name, colorModel,
								 rgbColor[0],
								 rgbColor[1],
								 rgbColor[2]);
		}

		if (colorModel.equals("HSV")) {
			// HSV is formatted in 3 doubles
			double[] hsvColor = new double[3];
			for (int i = 0; i < 3; i++) {
				hsvColor[i] = in.nextDouble();
			}
			// One final check whether the string is right
			if (!in.next().equals("}")) {
				throw new InputMismatchException("error: color parameter improperly formatted.");
			}
			return new ColorNode(name, colorModel,
								 hsvColor[0],
								 hsvColor[1],
								 hsvColor[2]);
		}

		// We can't identify the color model, we can't read it in
		throw new InputMismatchException("error: unknown color model \"" +
										 colorModel + "\"");


	}

	private static Node nextParameter(String input){
		Scanner in = new Scanner(input);
		String[] element = new String[3];
		element[0] = in.next();

		// Number at least once, followed by an '=', '{', 
		// and another number, a space and '}'.
		if (element[0].matches("^[0-9]+={ *[0-9]+ *}$")) {
			// make a TreeNode of that type
			return returnTreeNode;
		}
		// Matches an ending bracket
		if (element[0].matches("}")) {
			// I have no idea how to communicate this,
			// but think of something.
			// Idea: make it so the only time that this returns null,
			// the tree has ended and we need to jump out of it.
			// Idea: if you see a {, call a recursive method and return
			// the entire tree
			return null;
		}
		
		// color keyword indicates next strings will be a color
		if (element[0].matches("color") ||
			element[0].matches("color_ui")) {
			String colorElement = element[0];
			for (int i = 0; i < 6; i++) {
				colorElement = " " + in.next();
			}
			return parseColor(colorElement);
		}


		// Cannot analyze what to do based on element[0], continuing.
		element[1] = in.next();
		// If this is not an equals sign, the previous
		// element was an option, therefore return it.
		if (!element[1].matches("^=$")) {
			// make an Option of element[0]
			return returnTreeNode;	
		}

		// Cannot analyze what to do based on element[1], continuing.
		element[3] = in.next();
		if (element[3].equals("{")) {
			// haha recursion but much more complex:
			// parseTree uses this method and recurviely calls itself
			// if a tree is in change, uses null to find end of tree
			parseTree(in);
		}

		
		

		

		
		
	}

	
	// Returns the paramater tree created from the file
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
