package oop.ex4.data_structures;
/**
 * This class represents a node object
 * @author nirwiner
 */
public class Node {
	
	private int NodeValue;
	private int NodeHeight;
	private Node rightSon;
	private Node leftSon;
	private Node father;

	/**
	 * a constructor that receives the father of a node
	 * and it's height.
	 * @param father the father of this node
	 * @param height the height of this node
	 */
	public Node(Node father, int height, int value) {
		this.father = father;
		this.NodeHeight = height;
		this.NodeValue = value;
	}
	
	/**
	 * a default constructor for a node.
	 */
	public Node(int value){
		
		this.father = null;
		this.rightSon = null;
		this.leftSon = null;
		this.NodeValue = value;
		this.NodeHeight = 0;
	}
	
	
	/**
	 * a setter for the node's father
	 * @param father the new father
	 */
	public void setFather(Node father) {
		this.father = father;
	}
	
	
	/**
	 * a setter for the right son
	 * @param rightSon the new right son
	 */
	public void setRightSon(Node newRightSon) {
		this.rightSon = newRightSon;
		if (this.rightSon != null)
			// set the father of the newLeftSon to be this node
			rightSon.setFather(this);
	}
	
	
	/**
	 * a setter for the left son
	 * @param leftSon the new left son
	 */
	public void setLeftSon(Node newLeftSon) {
		this.leftSon = newLeftSon;
		if(this.leftSon != null)
			// set the father of the newLeftSon to be this node
			leftSon.setFather(this);
	}
	
	/**
	 * a setter for the node's value
	 * @param value the new value
	 */
	public void setValue(int value) {
		this.NodeValue = value;
	}
	
	/**
	 * a setter for the node's height in the tree
	 * @param height the new height 
	 */
	public void setHeight(int height) {
		this.NodeHeight = height;
	}
	
	/**
	 * a getter for the father field
	 * @return father the father of the node
	 */
	public Node getFather() {
		return this.father;
	}
	
	/**
	 * a getter for the right son
	 * @return rightSon the right son
	 */
	public Node getRightSon() {
		return this.rightSon;
	}
	
	/**
	 * a getter for the left son
	 * @return
	 */
	public Node getLeftSon() {
		return this.leftSon;
	}
	
	/**
	 * a getter for the node's value
	 * @return value the value of the node.
	 */
	public int getValue() {
		return NodeValue;
	}
	
	/**
	 * @return height this method is a getter for the tree's height
	 */
	public int getHeight() {
		return NodeHeight;
	}
	
	/**
	 * this method returns the height difference of a node
	 * @return difference the difference in heights
	 */
	public int checkIfTreeIsBalanced() {
		
		final int MIN_HEIHGT = 0;
		final int NO_SON = -1;
		int rightSonHeihgt = -1;
		int leftSonHeihgt = -1;
		if(getLeftSon() != null)
			leftSonHeihgt = getLeftSon().getHeight();
		if(getRightSon() != null)
			rightSonHeihgt = getRightSon().getHeight();
		if((leftSonHeihgt == NO_SON ) && (rightSonHeihgt == NO_SON ))
			return MIN_HEIHGT;
		return (leftSonHeihgt - rightSonHeihgt);
	}
		
}