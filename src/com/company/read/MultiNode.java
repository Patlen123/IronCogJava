import java.util.ArrayList;

/* TODO:
   This is super messy stuff, turn to inheritance
*/
public class MultiNode{
	// Is the node a tree or a data element? 0 = data, 1 = tree
	private String nodeMode;

	// ID of the MultiNode
	private String id;
	
	/* Variables if the node is for data */
	// The data of the node
	private String data;

	/* Varibales if the node is a tree */
	// Root node for checking the meme later
	private static MultiNode root;
	// Tree before this one
	private MultiNode lastTree;
	// The tree we're appending things to currently
	private static MultiNode currentTree;

	// Content of the variable
	private ArrayList<MultiNode> content;

	// How much indentation do we have? (for toString formatting)
	private static String indentation = "";

	/* Constructors for storing data */
	private MultiNode(String id, String data){
		this.nodeMode = "node";
		this.id = id;
		this.data = data;
		root = this;
	}
	
	/* Constructors for creating trees */
	// This is the constructor that is used outside to create the
	// root node of the tree.
	public MultiNode(String id){
		this.nodeMode = "tree";
		this.id = id;
		this.lastTree = null;
		currentTree = this;

		this.content = new ArrayList<MultiNode>();
	}

	// This is the constructor that is used inside to add new trees.
	private MultiNode(String id, MultiNode last){
		this.nodeMode = "tree";
		this.id = id;

		this.lastTree = last;
		this.content = new ArrayList<MultiNode>();
	}

	private MultiNode(char classifier, String opt){
		this.nodeMode = "option";
		this.id = opt;
	}

	// public boolean isTree(){
	// 	return (nodeMode.equals("tree"));
	// }

	public String getNodeMode(){
		return nodeMode;
	}
	public void newTree(String id){
		MultiNode tmp = new MultiNode(id, currentTree);
		currentTree.content.add(tmp);
		currentTree = tmp;
	}
	public String getID(){
		return this.id;
	}

	public void leaveTree(){
		currentTree = currentTree.lastTree;
	}
	public void closeTree(){
		currentTree = null;
	}

	public void appendElement(String id, String element){
		currentTree.content.add(new MultiNode(id, element));
	}
	public void appendOption(String opt){
		currentTree.content.add(new MultiNode('o', opt));
	}

	public String toString(){
		String out = "";
		String inTemp = indentation;
		for (MultiNode n : content){
			if (n.getNodeMode().equals("tree")){
				indentation += " ";
				out += inTemp + n.id + "{\n";
				out += n.toString();
				indentation = inTemp;
			} else if (n.getNodeMode().equals("node")) {
				out += indentation + n.id + "," + n.data + "\n";
			} else if (n.getNodeMode().equals("option")) {
				out += indentation + n.id + "\n";
			}
		}
		return out;
	}
		
}
