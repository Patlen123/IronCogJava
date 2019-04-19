// I PUT AT LEAST A FEW HOURS INTO THIS SO I'M COMMITING,
// THIS IS THE ONLY CLASS THAT IS EVEN CLOSE TO BEING READY
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
	private static Color parseColor(String color){
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

	// Reads in everything until a "}" has been scanned, returns
	// all contents as a tree.
	private static Tree parseTree(String input, String name){
		Tree<Node> ouTtree = new Tree<Node>(name);
		boolean reachedEndBracket = false;

		while (!reachedEndBracket) {
			Node newNode = nextNode(input);
			// If nextNode returns null, it means that it scanned an
			// end bracket.
			if (newNode == null) {
				reachedEndBracket = true;
			} else {
				returnTree.add(newNode);
			}
		}
		return returnTree;
	}

	private static Building parseBuilding(String input){
		// Splits everything around ';' into a string array
		String[] p = input.split(";");
		// Construct a new building out of the split parameters
		return new Building(p[0], p[1], p[2], p[3], p[4],
							p[5] p[6]);
		
	}
		

	// TODO idea: some parameters are initialized using n=e not n = e,
	// instead of regex use String.split(), with a [ =] seperator so both
	// are accounted for.
	private static Node nextNode(String input){
		Scanner in = new Scanner(input);
		String[] element = new String[3];
		element[0] = in.next();

		// Number at least once, followed by an '=', '{', 
		// and another number, a space and '}'.
		if (element[0].matches("^[0-9]+={ *[0-9]+ *}$")) {

			// Replaces everything after and including an '='
			String name = input.replaceAll("=.*$", "");
			// Replaces everything before and including an "{ ",
			// then everything after and including an " }"
			String data = input.replaceAll("^[0-9]*=\\{ *", "")
				.replaceAll(" *\\}.*", "");
			Number outNumber = new Number(name, data);

			// Erase the matched string at the beggining
			// You have to be specific, otherwise all '}' will be erased.
			input = input.replaceAll("^[0-9]+={ *[0-9]+ *}[ \t]*", "");
			return outNumber;
		}

		// Matches an ending bracket
		if (element[0].matches("}")) {
			input = input.replaceAll("[ \t]*//}[ \t]*", "");
			// We communicate this by returning null.
			return null;
		}
		
		// "color" keyword indicates next strings will be a color
		if (element[0].matches("color") ||
			element[0].matches("color_ui")) {
			String colorElement = element[0];
			for (int i = 0; i < 6; i++) {
				colorElement = " " + in.next();
			}
			// This looks retarded, but it works and prevents bugs later.
			// Replaces everything that might match what an RGB/HSV
			// color parameter may look like.
			input = input
				.replaceAll("^color[a-z0-9A-Z_]* *= *" +
							"[a-z0-9A-Z]* *{ *[0-9.]+ [0-9.]+ [0-9.] *} *" +
							"[ \t]*", "");
			return parseColor(colorElement);
		}

		// num;word;num.num;*4num means it's a building
		if (element[0]
			.matches("^[0-9]+;[a-zA-Z_]+;([0-9]+\\.[0-9]{2};){4}[0-9]+$")) {

			input.replaceAll("^[0-9]+;[a-zA-Z_]+;([0-9]+\\.[0-9]{2};){4}[0-9]+",
							 "");
			return parseBuilding(element[0]);
		}
							   

		// Cannot analyze what to do based on element[0], continuing.
		element[1] = in.next();
		// If this is not an equals sign, the previous
		// element was an option, therefore return it.
		if (!element[1].matches("^=$")) {
			
			Option outOption = new Option(element[0]);
			input = input.replaceAll("^[a-z0-9A-Z._]+", "");
			return outOption;	
		}

		// Cannot analyze what to do based on element[1], continuing.
		element[3] = in.next();
		if (element[3].equals("{")) {
			// Remove everything at the beggining that might match a tree.
			input = input.replaceAll("^[a-z0-9A-Z_]* *= *{[ \t]*", "");
			// Return whatever parseTree outputs (SPOILER:a tree)
			return parseTree(input, element[0]);
		}

		// We have reached the final case, the regular "n = e"
		Parameter out = new Parameter(element[0], element[3]);
		return out;
	}


	// Converts a file to string. Makes file a single string and
	// increases I/O safety.
	private static fileToString(String file){

		Scanner in = null;
		String out = "";
		try {
			in = new Scanner(new File(file));
			while (in.hasNext()) {
				// Removes commented out text and appends to out
				out += " " + in.next().replaceAll("#.*$", "");
			}
		}catch(FileNotFoundException e){
			System.err.printf("error: file not found\n");
			System.exit(1);
		}

		finally {
			in.close();
		}

		return out;
	}
								
	// Returns the paramater tree created from the file
	public static Tree file(String file){
		String fileString = fileToString(file);

		// Root tree name is the filename, filter to filename only
		Tree root = new Tree(file.replaceAll("^.*/", ""));

		// While the fileString is not empty
		while (!fileString.equals("")) {
			// STRING IS PASS BY REFERENCE!
			// You can erase what you don't need anymore.
			// This method works by that principle.
			root.add(nextNode(fileString));

			
		}
		return fileTree;
	}
}
