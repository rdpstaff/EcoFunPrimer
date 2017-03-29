/*
 * Copyright (C) 2015 leotift
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.msu.cme.rdp.primerdesign.selectprimers.algorithm;

import java.util.*;


/**
 *
 * A public class representing a (phylognenetic) tree.
 * Nodes of the tree are of type TreeNode.
 * 
 * @author tift
 */
public class Tree {

	/** The list of nodes of the tree indexed by their keys, indexed by key */ 
	public ArrayList nodes; 

	/** 
	 * Most internal nodes don't have names. Do we assign a unique
	 * name to each of them? No! each node has a key and the key is unique
	 * for nodes. 
	 */
	private HashMap nodesByName; 
        
        /**
         * List of all TreeNode objects within one tree.
         */
        private List<TreeNode> allTreeNodes;

	/** key should be unique for each tree, set by object that creates trees  */
	private int key;

	/** Leaf counter, for determining grid size, making arrays for tree comparisons */
	private int numLeaves = 0;


	/**
	 * Default tree constructor.  Nodes are created by parser and added in later.
	 *
	 */
	public Tree() {
		root = new TreeNode();
		nodes = new ArrayList();
		nodesByName = new HashMap();
                allTreeNodes = new ArrayList<> ();
	}

	/**
	 * Copy constructor used to create versions of trees that are identical to the supplied input tree.
	 * The copying should also make copies of the nodes in the given tree with the node copy constructor.
	 * (This function isn't working properly)
	 * @param treeToCopy Tree used to make a copy.
	 */
	public Tree(Tree treeToCopy)
	{
		// TODO: make this work with copy constructors (this constructor is only used in matrix mode)
		fileName = treeToCopy.fileName;
		height = treeToCopy.height;
		key = treeToCopy.key;
		nodes = new ArrayList(treeToCopy.nodes);
		nodesByName = new HashMap(treeToCopy.nodesByName);
                allTreeNodes = new ArrayList<> (treeToCopy.allTreeNodes);
		numLeaves = treeToCopy.numLeaves;
		root = treeToCopy.root;
	}

	/**
	 * Clean up method, called when the tree is deleted.
	 * @see TreeNode#close()
	 *
	 */   
	public void close(){
		TreeNode pren = root.leftmostLeaf;		
		for(TreeNode n = pren.preorderNext; n!=null; n=n.preorderNext) {
			n.close();				 
		}
	}

	/**
	 * Calls to #close() when tree is deleted. 
	 */
	protected void finalize() throws Throwable {

		try {
			close();
		}
		finally {
			super.finalize();     
		}
	}

	/**
	 * Returns the number of interior nodes in this tree.  For debugging.
	 * @return Total number of nodes minus the number of leaves.
	 */
	private int getInteriorCount() { return nodes.size() - numLeaves;}
	/**
	 * Returns the node count, for internal and leaf nodes.
	 * @return Size of the {@link #nodes} array, which contains all nodes.
	 */
	protected int getTotalNodeCount() { return nodes.size();}

	/**
	 * Returns the node indexed by the given key.
	 * @param key Key of the node to retrieve.
	 * @return Treenode referenced by the given key.
	 */
	public TreeNode getNodeByKey(int key){ if (key >= nodes.size()) return null; return (TreeNode) nodes.get(key);}
	/**
	 * Returns the node given by the string.
	 * @param s Name/label of node to retrieve.
	 * @return Treenode referenced by the given name.
	 */
	public TreeNode getNodeByName(String s){ 
		return (TreeNode) nodesByName.get(s);
	}
        
        /**
	 * Returns the node given by the string.
	 * 
	 * @return allTreeNodes 
	 */
	public List<TreeNode> getAllTreeNodes(){ 
		return allTreeNodes;
	}


	/**
	 * Height of tree, which is also the longest path from the root to some leaf node.
	 */
	private int height = 0;
	/**
	 * Accessor for height of tree.  This is also the longest path from the root to some leaf node.
	 * @return value of {@link #height}.
	 */
	protected int getHeight() { return height; }

	/** Mutator for key
	 * @param i New value for {@link #key}.
	 */
	public void setKey(int i) {key = i;}
	/** Accessor for key.
	 * @return Value of {@link #key}.
	 */
	public int getKey() {return key;}
	/**
	 * File name accessor.
	 * @return value of {@link #fileName}.
	 */
	public String getName() {return fileName;}
	/** Left most leaf accessor.  This is the "min leaf"
	 * @return root's left most leaf, which is the smallest indexed leaf node in the tree.
	 */
	public TreeNode getLeftmostLeaf() { return root.leftmostLeaf; }
	/** Root accessor.
	 * @return Value of {@link #root}*/
	public TreeNode getRoot() { return root;}
	public void setRootNode(TreeNode newRoot) { this.root = newRoot; }

	/**
	 * File name for this tree.
	 */
	private String fileName = null; // the filename

	/**
	 * Root node of this tree
	 */
	protected TreeNode root=null;

	/**
	 * Sets the file name.  Copies the value for some reason.
	 * @param tn New value for file name.
	 */
	public void setFileName(String tn) {
		fileName = new String(tn);
	}

	/**
	 * Returns the number of leaves in this tree.
	 * @return value of {@link #numLeaves}.
	 */
	public int getLeafCount() {
		return numLeaves;
	}


	/**
	 * Get the leaves under this node.  Used for tree to tree comparison, removing leaf nodes from difference calculations.		
	 * @param node Node to get leaves under.  The root node will return all leaves in the tree, leaves return a list of just themselves.
	 * @return List of leaves under this node.
	 */
	public LinkedList getLeaves(TreeNode node)
	{
		LinkedList leaves = new LinkedList();
		TreeNode currNode = node.leftmostLeaf;
		while (currNode != node.rightmostLeaf)
		{
			if (!currNode.isLeaf()) // internal node?
				currNode = currNode.leftmostLeaf; // descend to minimal leaf
			leaves.add(currNode);
			currNode = currNode.preorderNext;
		}
		leaves.add(node.rightmostLeaf);
		return leaves;
	}
}



