package oop.ex4.data_structures;
import java.util.Iterator;
import java.util.NoSuchElementException;



/**
 *  @author nirwiener
 * this class represent a binary search tree.
 *
 */
public class Bst {
	
	//initaliaz all the field in the class
	private Node root;
	private Node deleteNode = null;
	//this Node(balanceFather) is not defined in order to be 
	//accable in other classes that are in this package
	Node balanceFather = null;
	
	private int treeSize = 0;
	private final static int LEFT = -1;
	private final static int RIGHT = 1;
	private static final int NEW_NODE_HEIGHT = 0;
	private static final int VALUE_IS_NOT_IN_LIST = -1;
	
	/**
	 * a default constructor
	 */
	public Bst(){
		this.root = null;
	}
	
	/**
	 * a constructor that receives data  
	 * @param data an array of integers
	 */
	public Bst(int[] data) {
		this.root = null;
		for(int i=0; i<data.length; i++){
			add(data[i]);
		}
	}
	
	/**
	 * try to add a new node with key value to the tree
	 * @param value the value to add to the tree
	 * @return false if value already exist in the tree
	 */
	public boolean add(int value){
		
		if(contains(value) != VALUE_IS_NOT_IN_LIST){
			return false;
		}
		this.treeSize++;
		Node currentNode = this.root;
		Node nodeToAddValueTo = this.root;
		int directionInTree = 0;
		
		//if the tree is empty adding the value to the root
		if(this.root == null){
			this.root = new Node(null,NEW_NODE_HEIGHT,value);
			return true;
		}
		while (nodeToAddValueTo != null){
			if (currentNode.getValue() < value){
				
				directionInTree = RIGHT;
				nodeToAddValueTo  = currentNode.getRightSon();
			
			}else{
				
				directionInTree = LEFT;
				nodeToAddValueTo  = currentNode.getLeftSon();
			}
			
			if(nodeToAddValueTo != null){
				
				currentNode = nodeToAddValueTo;
			}
		}
		
		if(directionInTree == LEFT){
			currentNode.setLeftSon(new Node(currentNode,NEW_NODE_HEIGHT,value));
		
		}else{
			currentNode.setRightSon(new Node(currentNode,NEW_NODE_HEIGHT,value));
		}
		
		
		balanceFather = currentNode;
		updateTreeNodesHeight(currentNode);
		return true;
		
	}
	
	/*
	 *updates the heights of all the nodes that may have changed
	 *due to add/erase of an element
	 */
	void updateTreeNodesHeight(Node node){
		
		final int ADD_HEIGTH  = 1; 
		boolean notRoot = true;
		
		do{
		
			if((node.getRightSon() == null) && (node.getLeftSon() == null)){
				node.setHeight(NEW_NODE_HEIGHT);
			}
			
			else if((node.getRightSon() == null) && (node.getLeftSon() != null)){
				node.setHeight(node.getLeftSon().getHeight() + ADD_HEIGTH );
			}
			else if((node.getRightSon() != null) && (node.getLeftSon() == null)){
				node.setHeight(node.getRightSon().getHeight() + ADD_HEIGTH );
			}else{
				node.setHeight(Math.max(node.getLeftSon().getHeight(),
						node.getRightSon().getHeight()) + ADD_HEIGTH);
			}
			if(node == this.root){
				notRoot = false;
			}else{
				node = node.getFather();
			}
		}while(notRoot);
	}
	
	/*
	 * changes the root of the tree
	 * @param newRoot the new root
	 */
	void setRoot(Node newRoot) {
		this.root = newRoot;
	}
	
	//this method returns the root of the tree
	Node getRoot(){
		return this.root;
	}
	
	/**
	 * 
	 * @return size the number of elements in the tree
	 */
	public int size(){
		return this.treeSize;
	}
	
	/**
	 * Checks if a value exist in a tree
	 * @param value the value to search for
	 * @return the depth of the value if exist or -1 if not
	 */
	public int contains(int value){
		
		//if the tree is empty
		if(root == null)
			return VALUE_IS_NOT_IN_LIST;
		
		int treeDepth = 0;
		Node currentNode = this.root;
		
		while(treeDepth != VALUE_IS_NOT_IN_LIST){
			
			//case the value is bigger than the current value
			if((currentNode.getValue() < value)
					&& currentNode.getRightSon() != null){
				
				currentNode = currentNode.getRightSon();
				treeDepth++;
			}
			//case the value is smaller than the current value
			else if((currentNode.getValue() > value)
					&& currentNode.getLeftSon() != null){
				
				currentNode = currentNode.getLeftSon();
				treeDepth++;
			}
			//case the value is found
			else if(currentNode.getValue() == value){
				
				deleteNode = currentNode;
				if(deleteNode != null){
					balanceFather = deleteNode.getFather();
				}
				return treeDepth ;
			}else{
			//case the value wasn't found
				treeDepth  = VALUE_IS_NOT_IN_LIST;
			}
		}
		return treeDepth ;
	}
	

	/**
	 * Remove a node from the tree if exist
	 * @param toDelete a value to delete
	 * @return true if toDelete is found and deleted
	 */
	public boolean delete(int toDelete){
		
		if(contains(toDelete) != VALUE_IS_NOT_IN_LIST){
			this.treeSize--;
			
			if((deleteNode.getLeftSon() == null) 
					&& (deleteNode.getRightSon() == null)){
				//case the value to delete is a leaf
				deleteALeaf();
			}
			
			else if((deleteNode.getLeftSon() != null) 
					&& (deleteNode.getRightSon() == null)){
				//case the value as only a left son
				deleteOnlyLeftChildParent();
			
			}else if((deleteNode.getLeftSon() == null) 
					&& (deleteNode.getRightSon() != null)){
				//case the value as only a right son
				deleteOnlyRightChildParent();
			}else{
				deleteRightAndLeftChildParent();
			}
			return true;
		}
		return false;
	}
	
	/*
	 * delete the leaf from the tree.
	 */
	private void deleteALeaf(){
		
		Node father = null;
		if(deleteNode == this.root){
			this.root = null;
		}
		else{
			
			father = deleteNode.getFather();
			if(father.getLeftSon() == deleteNode)
				father.setLeftSon(null);
			else
				father.setRightSon(null);
		}
		balanceFather = father;
	}
	
	/*
	 * delete the father of an only left child from the tree.
	 */
	private void deleteOnlyLeftChildParent(){
		
		Node father = null;
		if(deleteNode == this.root){
			this.root = this.root.getLeftSon();
		}else{
			father = deleteNode.getFather();
			if(father.getLeftSon() == deleteNode){
				father.setLeftSon(deleteNode.getLeftSon());
				father.getLeftSon().setFather(father);
			}else{
				father.setRightSon(deleteNode.getLeftSon());
				father.getRightSon().setFather(father);
			}
		}
		balanceFather = father;
	}
	
	/*
	 * delete the father of an only right child from the tree.
	 */
	private void deleteOnlyRightChildParent(){
		
		Node father = null;
		if(deleteNode == root){
			root = root.getRightSon();
		}else{
			father = deleteNode.getFather();
			
			if(father.getLeftSon() == deleteNode){
				father.setLeftSon(deleteNode.getRightSon());
				father.getLeftSon().setFather(father);
			}else{
				father.setRightSon(deleteNode.getRightSon());
				father.getRightSon().setFather(father);
			}
		}
		balanceFather = father;
	}
	
	/*
	 * delete the father of right and left child from the tree.
	 */
	private void deleteRightAndLeftChildParent(){
		
		Node successor = deleteNode.getRightSon();
		while(successor.getLeftSon() != null){
			successor = successor.getLeftSon();
		}
		deleteNode.setValue(successor.getValue());
	    Node father = successor.getFather();
		if(father.getLeftSon() == successor){
			father.setLeftSon(null);
		}else{
			father.setRightSon(null);
		}
		balanceFather = father;
		
	}
	
    /*
     * this method returns the successor of an element
     */ 
    private Node successor(Node node){
        
        if (this == null){
            return null;
        }
        
        Node successor = node;
        if (successor.getRightSon() != null){
            
        	return findMinNodeInTree(successor.getRightSon());
        }else{
            while ((successor.getFather() != null)
                   && (successor == successor.getFather().getRightSon())){
                
            	successor = successor.getFather();
            }
            return successor.getFather();
        }
    }
    
    /*
     * finds the minnmum node in the given tree
     *
     */
    private Node findMinNodeInTree(Node root){
        Node successor = root;
        while (successor.getLeftSon() != null){
            successor = successor.getLeftSon();
        }
        return successor;	
    }
    
    
    /**
	 * 
	 * @author nirwiener
	 * the class geanarte an itaerator that will return all the nodes
	 * that are in the tree
	 */
	class TreeIterator implements Iterator<Integer>{
		
		private boolean firstIteration = true;
		private Node node;
		
		/**
		 * a constructor to the Iterator 
		 * @param node
		 */
		TreeIterator(Node node){
			this.node = findMinNodeInTree(node);
		}
		
		/**
		 * @return true if a successor exists false else.
		 */
		public boolean hasNext() {
			if(successor(node) != null)
				return true;
			return false;
		}

		/**
		 * @return the successor if exists if it the there is
		 *  no more nodes in the tree return a NoSuchElementException
		 */
		public Integer next() throws NoSuchElementException  {
			
			if(firstIteration){
				firstIteration = false;
				return node.getValue();
			}
			if(!hasNext()){
				throw new NoSuchElementException();
			}
			node = successor(node);
			return node.getValue();
			
		}

		/**
		 * Unimplemented method(here for implementation reasons)(non-Javadoc)
		 * @see java.util.Iterator#remove()
		 */
		public void remove() throws UnsupportedOperationException{
			  throw new  UnsupportedOperationException();
		}
	}

}