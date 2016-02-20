package oop.ex4.data_structures;
import java.util.Iterator;


/**
 * @author nirwiener
 * this class represents an Avl Tree
 */
public class AvlTree extends Bst{
	
	private final int LEFT_VIOLATION = 2;
	private final int RIGHT_VIOLATION = -2;
	
	
	/**
	 * The default constructor 
	 */
	public AvlTree(){
		super();
	}
	
	/**
	 * A constructor that builds the tree by adding the element in the 
	 * input array one by one. if a value appears more than once the list.
	 * only the first appearance is added. 
	 */
	public AvlTree(int[] data){
		
		super();
		for(int index = 0; index < data.length ; index++){
			add(data[index]);
		}
	}
	
	/**
	 * a copy constructor, receives a tree and copies
	 * it to a new tree
	 * @param tree a tree to copy
	 */
	public AvlTree(AvlTree tree){
        
		 super();
         duplicateTree(tree.getRoot(),this);

	}
	
	/**
	 * Add a new node with the given ket to the tree
	 * @param  newValue value to add to the list
	 * @return true if the value to add is not already in the tree and is 
	 * was successfully added, false otherwise.
	 */
    public boolean add(int newValue){

    	boolean valueWasAdded = super.add(newValue);
    	if (valueWasAdded){
    	balance(this.balanceFather);
    	}
    	return valueWasAdded;
     
    }
   
    /**
     * Cheeks whether the tree contains the given input value.
     * @param searchVal the value to search for 
     * @return the depth of the node (0for the root) with the given value if
     * it was found in the tree, -1 otherwise
     */
    public int contanis(int searchVal){
    	
    	return super.contains(searchVal);
    	   	
    }
    
	/**
	 * Removes the node wuth the given value from the tree.if it exist.
	 * @param toDelete the value to remove from the tree
	 * @return true if the value was found and deleted,false otherwise
	 */
	public boolean delete(int toDelete){ 
		
		boolean valueWasDeleted = super.delete(toDelete);
		if (valueWasDeleted){
		balance(this.balanceFather);
		}
		return valueWasDeleted;
	}
	
    /**
     * @return the number of nodes in the tree.
     */
    public int size(){
    	return super.size();
    }
	
    /*
     * this method recursively runs over a tree and adds its nodes to a specific
     * tree
     */
	private void duplicateTree(Node node, AvlTree newCopyAvlTree){
		
		newCopyAvlTree.add(node.getValue());
		
		if(node.getRightSon() != null)
			duplicateTree(node.getRightSon(), newCopyAvlTree);
		
		if(node.getLeftSon() != null)
			duplicateTree(node.getLeftSon(), newCopyAvlTree);
	}
	
	/*
	 * this method balances the tree to an AVL tree
	 */
	private void balance(Node node){
			
		if (node == null){
			return;
		}
		
		updateTreeNodesHeight(node);
		
		if(node.checkIfTreeIsBalanced() == RIGHT_VIOLATION){
			if(nodeHeight(node.getRightSon().getRightSon())
					>= nodeHeight(node.getRightSon().getLeftSon())){
				rotateLeft(node);
			}else{
				rotateRightLeft(node);
			}
		}
		
		else if(node.checkIfTreeIsBalanced() == LEFT_VIOLATION){
			if(nodeHeight(node.getLeftSon().getLeftSon())
					>=  nodeHeight(node.getLeftSon().getRightSon())){
				rotateRight(node);
			}else{
				rotateLeftRight(node);
			}
		}
			
		if (node.getFather() != null){
			balance(node.getFather());
		}else{
			setRoot(node);	
		}
	}
	
	/*
	 *this method rotates a subtree to the left
	 */
	private void rotateLeft(Node head){
		
		Node newSubHead = head.getRightSon();
		
		newSubHead.setFather(head.getFather());
		head.setRightSon(newSubHead.getLeftSon());
		newSubHead.setLeftSon(head);
		head.setFather(newSubHead);
		
		if(newSubHead.getFather() != null){
			if(newSubHead.getFather().getRightSon() == head){
				newSubHead.getFather().setRightSon(newSubHead);
			}else{
				newSubHead.getFather().setLeftSon(newSubHead);
			}
		}else{
			setRoot(newSubHead);
		}
		updateTreeNodesHeight(head);
	}
	
	/*
	 *this method rotates a subtree to the right
	 */
	private void rotateRight(Node head){
		
		Node newSubHead = head.getLeftSon();
		
		newSubHead.setFather(head.getFather());
		head.setLeftSon(newSubHead.getRightSon());
		newSubHead.setRightSon(head);
		head.setFather(newSubHead);
		
		if(newSubHead.getFather() !=null){
			if(newSubHead.getFather().getRightSon() == head){
				newSubHead.getFather().setRightSon(newSubHead);
			}else{
				newSubHead.getFather().setLeftSon(newSubHead);
			}
		}else{
			setRoot(newSubHead);
		}
		updateTreeNodesHeight(head);
	}
	
	/*
	 * this method rotates a subtree to the right and then to the left
	 */
	private void rotateRightLeft(Node head){
		rotateRight(head.getRightSon());
		rotateLeft(head);
	}
	
	/*
	 * this method rotates a subtree to the left and then to the right
	 */
	private void rotateLeftRight(Node head){
		rotateLeft(head.getLeftSon());
		rotateRight(head);
	}
	
	/*
	 * this method returns the height of a node if exists 
	 *or -1 otherwise
	 */
	private int nodeHeight(Node curNode){
		if(curNode == null)
			return -1;
		return curNode.getHeight();
	}
	
	/**
	 * 
	 * @return an itaretor on the avl tree. the returned itearator iterates over
	 * the tree nodes in an ascending order, and does not implement the remove
	 * method. 
	 */
	public Iterator<Integer> iterator(){
		Node head = (this.getRoot());
		TreeIterator avl =  new TreeIterator(head);
		return avl;
	}
	
	/**
	 * Calculates the minmum number of nodes in an Avl tree of height h
	 * @param h the hieght of the tree(a non negtive number) in qustion
	 * @return the minimum number of nodes in an avl tree of the given hight
	 */
	public static int findMinNodes(int h){
		// the function will run in recustion
		// this method work as we learnd in DAST.
		
		if (h == 0){
			return 1;
		}
		if (h == 1){
			return 2;
		}

		return findMinNodes(h - 1 ) + findMinNodes(h - 2) + 1;
	}
    

}

