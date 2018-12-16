import java.io.Serializable;

public class Item implements Serializable {
	//1 is server
	//2 is client
	//3 is coin
	//4 is obstacle
	private int type;
	public boolean tf = true;
	public Item (int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
	
	public boolean itemExists() {
		return tf;
	}
	
	public void itemSet(boolean tf) {
		this.tf = tf;
	}
}