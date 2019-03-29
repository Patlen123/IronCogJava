public class Node{
	private String id;
	private String data;

	public Node(String id, String data){
		this.id = id;
		this.data = data;
	}

	// Getters
	public String getID(){
		return id;
	}
	public String getData(){
		return data;
	}

	// Setters
	public void setID(String id){
		this.id = id;
	}
	public void setData(String data){
		this.data = data;
	}
