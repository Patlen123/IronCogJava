import java.util.ArrayList;

public class Tree{
	private Tree root;
	private Tree currentTree;
	private String id;
	private List content;

	private int iterator;
	private int level;
		
	public Tree(String id, Tree root){
		this.root = root;
		this.currentTree;
		this.id = id;
		this.content = new ArrayList<Node>();
		this.iterator = -1;
	}

	public Tree(String id){
		this.root = this;
		this.id = id;
		this.content = new ArrayList<Node>();
	}

	public void addTree(String id){
		content.add(new Tree(id, this));
		iterator++;
		currentTree
	}
			
}
