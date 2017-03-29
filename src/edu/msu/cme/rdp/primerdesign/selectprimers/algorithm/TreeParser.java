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



import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import javax.swing.JProgressBar;


/**
 *
 * @author tift
 * Parses the newick portion of a file and builds sequence weight map
 */

public class TreeParser
{

    /** Line (and tree information) termination. */
    private static final char lineTerminator = ';';
          
    private static Map<String, Double> weightMap;
        
    private StreamTokenizer tokenizer;
    /**
     * Root node of the tree being parsed.  Must be initialized outside the tokenizer.
     */
    private TreeNode rootNode;
    /**
     * Root node of the tree being parsed.  Must be initialized outside the tokenizer.
     */
    private static Tree treeObj;
        
    public Map<String, Double> getWeightMap()
    {
        return weightMap;
    }
     public Tree getTree()
    {
        return treeObj;
    }
     
     public void setTree(Tree newTree)
    {
        this.treeObj = newTree;
    }
    
     /**
     * Contains newick file
     * 
     */
    public static TreeParser run(File treeFile)
    {
                    
        long start = System.currentTimeMillis();
        
        
        try
        {
            BufferedReader r = new BufferedReader(new FileReader(treeFile));
            TreeParser tp = new TreeParser(r);
            treeObj = tp.tokenize(treeFile.length(), treeFile.getName());
            for(int i = 1; i < treeObj.getAllTreeNodes().size(); i++) {
                TreeNode node = treeObj.getAllTreeNodes().get(i);
                if(!("".equals(node.getName()))){
                buildWeightMap(node);
                }
            }
            
            
            System.out.println("Parsed in " + ((System.currentTimeMillis() - start)/1000.0) + " s");
            return tp;                                
            
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Couldn't find file: " + treeFile.getName());
        }
        return null;
        
    }
      
  
    /**
     * Initializes parsing of a tree by creating a tokenizer and setting default
     * properties (such as spacing, quoting characters).  
     * {@link #tokenize(long, String, JProgressBar)} is required to start the parsing.
     * @param b Buffered reader that could start in the middle of a nexus file or
     * the start of a newick file (basically the beginning of a newick tree, is run
     * for each tree in a nexus file)
     */
    public TreeParser(BufferedReader b)
    {
        tokenizer = new StreamTokenizer(b);
        
        tokenizer.eolIsSignificant(false);
        
        tokenizer.quoteChar('"');
//        tokenizer.quoteChar('\''); // TODO: check quote layering, quoted quotes
        tokenizer.wordChars('\'', '\''); // quote problem, turn this into a prime symbol?
        // 32 = space
        tokenizer.wordChars('!', '!'); // 33
        // 34 = "
        tokenizer.wordChars('#', '&'); // 35-38
        // 39-41 = '() newick
        tokenizer.wordChars('*', '+'); // 42-43
        // 44 = , newick
        tokenizer.wordChars('-', '/'); // 45-47
        // 48-59 = [0-9]:;
        tokenizer.wordChars('<', '<'); // 60
        // 61 = = nexus
        tokenizer.wordChars('>', '@'); // 62-64
        // 65-90 = [A-Z]
//        tokenizer.wordChars('[', '['); // 91 [ nexus comment character, treat as char
        // 92 = \ (esc, support esc'd spaces)
//      93 = ] nexus comment character
        tokenizer.wordChars('^', '`'); // 93-96
        // 97-122 = [a-z]
        tokenizer.wordChars('{', '~'); // 123-126
        // 127 = del
        weightMap = new HashMap<> ();
    }
    
   
    /**
     * Adds node at the top of the stack to the tree.  TreeNode is already created based
     * on Newick properties.
     * @param name Name of the node.
     * @param nodeStack Stack of nodes that haven't been added to the tree yet.  Nodes are popped when
     * they have names and all children are processed.
     * @return Newly added treeNode linked into the tree. 
     */
    private TreeNode popAndName(String name, Stack nodeStack)
    {
	    TreeNode topNode = (TreeNode)nodeStack.pop();
	    if (name == null)
	    {
	    	topNode.label = "";
	    	topNode.setName("");
	    }
	    else
	    {
	    	topNode.label = name;
	    	topNode.setName(name);
	    }
	    
	    topNode.setExtremeLeaves(); // sets leftmost and rightmost leaf, non-recursive
	    topNode.setNumberLeaves(); // sets number of leaves, non-recursive
	    topNode.linkNodesInPreorder();
	    topNode.linkNodesInPostorder();
	    return topNode;
    }
    
    /**
     * Calculates the weight of an individual node based off of the position in the tree.
     * 
     * @param node node to weight
     *  
     */
    public static void buildWeightMap(TreeNode node) {
        double finalWeight = node.getWeight();
        if (node.getParent() != null) {
         
            TreeNode parent = node.getParent();
   
             do {
                    double lastNodeWeight = parent.getWeight();               
                    double weightChildSplit = lastNodeWeight/(parent.getNumberChildren());
                    finalWeight += weightChildSplit;
                    
                    if(parent != treeObj.getRoot()) {
                        TreeNode grandParent = parent.getParent();
                        parent = grandParent;
                    }
                    
                } while (parent != treeObj.getRoot());
            
             weightMap.put(node.getName(), finalWeight);
             node.setFinalWeightSet();
        } else {
            System.out.println("Null value with this parent node" + node.getName());
        }
              	 
    }
    
    /**
     * Newick tokenizer: converts a string (tree as a string) into a tree object.
     * The stream tokenizer should be initialized before calling this function.
     * @param fileLength Length of the file, for progress bar movements.
     * @param streamName Name of the tree or file that is being loaded.  Nexus files have names ("tree <name> = ((...));", newick trees are named by file name.
     * @param progressBar Reference to a progress bar widgit, embedded perhaps in place of the new canvas for this tree.  If this is null, create a new progress bar here.
     * @return Tree parsed from the stream.
     */
    public Tree tokenize(long fileLength, String streamName)
    {
//    	Frame progFrame = null;
        final char openBracket = '(', closeBracket = ')', childSeparator = ',',
        	treeTerminator = lineTerminator, quote = '\'', doubleQuote = '"', infoSeparator = ':';
                     
        
        rootNode = new TreeNode();
        rootNode.setWeight(0.0);
        rootNode.setName("root");
        
        Tree treeObject = new Tree();
        
        treeObject.setRootNode(rootNode);
        
        treeObject.setFileName(streamName);
        
        treeObject.getAllTreeNodes().add(rootNode);
        
        Stack nodeStack = new Stack();
        
        nodeStack.push(rootNode);
        
        int thisToken;
        StreamTokenizer tempTokenizer = null;
        
        TreeNode lastNamed = null;
        boolean EOT = false;
        boolean nameNext = true;

        try
        {
            while (EOT == false &&
                    (thisToken = tokenizer.nextToken()) != StreamTokenizer.TT_EOF)
            {
            switch (thisToken)
            {

            	case doubleQuote:
            	case StreamTokenizer.TT_WORD:
            	    if (!nameNext) {                       
            	        System.err.println("Error: didn't expect this name here: " + tokenizer.sval);
                      
                    }  
                    String[] tokens = tokenizer.sval.split("[_]+");
                    
            	    lastNamed = popAndName(tokens[0], nodeStack);
                    //lastNamed = popAndName(tokenizer.sval, nodeStack);
                               	
                    nameNext = false;
                    break;
            	case StreamTokenizer.TT_NUMBER:
            		if (nameNext) {
                            tempTokenizer = tokenizer;
                            if(tempTokenizer.nextToken() == StreamTokenizer.TT_WORD) {
                                int tokenNumber = (int) tokenizer.nval;                                    
                                String concatName = Integer.toString(tokenNumber).concat(tempTokenizer.sval);
                                lastNamed = popAndName(concatName, nodeStack);
                                tempTokenizer = null;
                                nameNext = false;
                                break;
                            }
                            
            		    lastNamed = popAndName(tokenizer.sval, nodeStack);
                         
                        }
            		else
            		{
            		    if (lastNamed != null) {
            		        lastNamed.setWeight(tokenizer.nval);
                                try
                                {
                                    TreeNode parent = (TreeNode) nodeStack.peek();
                                    parent.addChild(lastNamed);
                                    lastNamed.setParent(parent);
                                }
                                catch (EmptyStackException e)
                                {
                                    if (lastNamed != rootNode)
                                        System.out.println("Parser error on node " + lastNamed);
                                }
                                treeObject.getAllTreeNodes().add(lastNamed);
                                
                            }   
            		    else
            		        System.err.println("Error: can't set value " + tokenizer.nval + " to a null node");
            		    lastNamed = null;
            		}
            		
            		nameNext = false;
            		break;
            	case infoSeparator:
            	    if (nameNext)
            	        lastNamed = popAndName(null, nodeStack);
            	  
            	    nameNext = false;
            	    break;
            	case treeTerminator:
            	case StreamTokenizer.TT_EOF:
            	    if (nameNext)
            	        lastNamed = popAndName(null, nodeStack);
            	    EOT = true;
            	 
            	    nameNext = false;
            	    break;
            	case openBracket:
            	    nodeStack.push(new TreeNode());
            	 
            	    nameNext = true;
            	    break;
            	case closeBracket:
            	    if (nameNext)
            	        lastNamed = popAndName(null, nodeStack);
            
            	    nameNext = true;
            	    break;
            	case childSeparator:
            	    if (nameNext)
            	        lastNamed = popAndName(null, nodeStack);
            	    nodeStack.push(new TreeNode());
            
            	    nameNext = true;
            	    break;
            	default:
            	    System.out.println("default token problem: " + (char)thisToken);
            		break;
            }

            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        if (!nodeStack.isEmpty())
            System.err.println("Node stack still has " + nodeStack.size() + " things");

        return treeObject;
    }


   
}
