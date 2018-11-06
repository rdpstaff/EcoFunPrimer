/*
 * Copyright (C) 2018  Michigan State University <rdpstaff at msu dot edu>
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
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;
import javax.swing.JProgressBar;


/**
 * @author James
 *
 * Parses the newick portion of a file
 * For nexus files, additional node-number mapping is needed to rename files
 * Identification of a file as either newick or nexus determines contents
 * 
 * */
public class TreeParser
{
        
	/** Line (and tree information) termination. */
	private static final char lineTerminator = ';';

	/**
	 * True: show debug output.  False: suppress printing.
	 */
	private static boolean debugOutput = false;
        private static Map<String, Double> weightMap;
        public Map<String, Double> getWeightMap()
    {
        return weightMap;
    }
    private StreamTokenizer tokenizer;
    /**
     * Root node of the tree being parsed.  Must be initialized outside the tokenizer.
     */
    private TreeNode rootNode;
    
    /**
     * Root node of the tree being parsed.  Must be initialized outside the tokenizer.
     */
    private static Tree treeObj;
    
    /**
     * For returning results inside action listeners.
     */
    private Vector returnVector;  
    
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
    }
    public static TreeParser run(File treeFile)
    {
                    
        long start = System.currentTimeMillis();
        
        
        try
        {
            BufferedReader r = new BufferedReader(new FileReader(treeFile));
            TreeParser tp = new TreeParser(r);
            treeObj = tp.tokenize(treeFile.length(), treeFile.getName());
            
            for(int i = 0; i < treeObj.nodes.size(); i++) {
                TreeNode node = treeObj.getNodeByKey(i);
                System.out.println(node.name);
                System.out.println(node.weight);
                //if(!("".equals(node.getName()))) {
                //    buildWeightMap(node);
                //}
            }
            
        //    for(int i = 1; i < treeObj.getAllTreeNodes().size(); i++) {
        //        TreeNode node = treeObj.getAllTreeNodes().get(i);
        //        if(!("".equals(node.getName()))){
        //        buildWeightMap(node);
        //        }
        //    }
            
            
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
     * Debug printout function.  Avoid using the system calls and use this, and set flag
     * {@link #debugOutput} depending on debugging or not.
     * @param s Display the string, for debugging.
     */
    public void debugOutput(String s)
    {
        if (debugOutput)
            System.out.println(s);
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
	    try
	    {
	    	TreeNode parent = (TreeNode) nodeStack.peek();
	    	parent.addChild(topNode);
	    }
	    catch (EmptyStackException e)
	    {
	        if (topNode != rootNode)
	            System.out.println("Parser error on node " + topNode);
	    }
	    topNode.setExtremeLeaves(); // sets leftmost and rightmost leaf, non-recursive
	    topNode.setNumberLeaves(); // sets number of leaves, non-recursive
	    topNode.linkNodesInPreorder();
	    topNode.linkNodesInPostorder();
	    return topNode;
    }
    /*
    public static void buildWeightMap(TreeNode node) {
        double finalWeight = node.getWeight();
        if (node.parent() != null) {
         
            TreeNode parent = node.parent();
   
             do {
                    double lastNodeWeight = parent.getWeight();               
                    double weightChildSplit = lastNodeWeight/(parent.numberChildren());
                    finalWeight += weightChildSplit;
                    
                    if(parent != treeObj.getRoot()) {
                        TreeNode grandParent = parent.parent();
                        parent = grandParent;
                    }
                    
                } while (parent != treeObj.getRoot());
            
             weightMap.put(node.getName(), finalWeight);
             node.setFinalWeightSet();
        } else {
            System.out.println("Null value with this parent node" + node.getName());
        }
              	 
    }*/
    
    /**
     * Newick tokenizer: converts a string (tree as a string) into a tree object.
     * The stream tokenizer should be initialized before calling this function.
     * @param fileLength Length of the file, for progress bar movements.
     * For nexus files, this would be the relative position of the next semicolon = the size of the tree in bytes.
     * @param streamName Name of the tree or file that is being loaded.  Nexus files have names ("tree <name> = ((...));", newick trees are named by file name.
     * @param progressBar Reference to a progress bar widgit, embedded perhaps in place of the new canvas for this tree.  If this is null, create a new progress bar here.
     * @return Tree parsed from the stream.
     */
    public Tree tokenize(long fileLength, String streamName)
    {
        final char openBracket = '(', closeBracket = ')', childSeparator = ',',
        	treeTerminator = lineTerminator, quote = '\'', doubleQuote = '"', infoSeparator = ':';
        int progress = 0;
        rootNode = new TreeNode();
        Tree t = new Tree();
        t.setRootNode(rootNode);
        t.setFileName(streamName);
        Stack nodeStack = new Stack();
        nodeStack.push(rootNode);
        int thisToken;
        TreeNode lastNamed = null;
        boolean EOT = false;
        boolean nameNext = true;
        int percentage = 0;
	try {
            while (EOT == false &&
                    (thisToken = tokenizer.nextToken()) != StreamTokenizer.TT_EOF)
            {
            switch (thisToken)
            {
//            	case quote:
            	case doubleQuote:
            	case StreamTokenizer.TT_WORD:
            	    if (!nameNext)
            	        System.err.println("Error: didn't expect this name here: " + tokenizer.sval);
            	    lastNamed = popAndName(tokenizer.sval, nodeStack);
            		progress += tokenizer.sval.length();
            		nameNext = false;
            		break;
            	case StreamTokenizer.TT_NUMBER:
            		if (nameNext)
            		    lastNamed = popAndName(tokenizer.sval, nodeStack);
            		else
            		{
            		    if (lastNamed != null)
            		        lastNamed.setWeight(tokenizer.nval);
            		    else
            		        System.err.println("Error: can't set value " + tokenizer.nval + " to a null node");
            		    lastNamed = null;
            		}
            		progress += (new Double(tokenizer.nval).toString()).length();
            		nameNext = false;
            		break;
            	case infoSeparator:
            	    if (nameNext)
            	        lastNamed = popAndName(null, nodeStack);
            	    progress += 1;
            	    nameNext = false;
            	    break;
            	case treeTerminator:
            	case StreamTokenizer.TT_EOF:
            	    if (nameNext)
            	        lastNamed = popAndName(null, nodeStack);
            	    EOT = true;
            	    progress += 1;
            	    nameNext = false;
            	    break;
            	case openBracket:
            	    nodeStack.push(new TreeNode());
            	    progress += 1;
            	    nameNext = true;
            	    break;
            	case closeBracket:
            	    if (nameNext)
            	        lastNamed = popAndName(null, nodeStack);
            	    progress += 1;
            	    nameNext = true;
            	    break;
            	case childSeparator:
            	    if (nameNext)
            	        lastNamed = popAndName(null, nodeStack);
            	    nodeStack.push(new TreeNode());
            	    progress += 1;
            	    nameNext = true;
            	    break;
            	default:
            	    debugOutput("default " + (char)thisToken);
            		break;
            }
        }
        }
        catch (IOException e) {
        }
        if (!nodeStack.isEmpty())
            System.err.println("Node stack still has " + nodeStack.size() + " things");
        t.postProcess();
        return t;
    }
    
    /**
     * Test application function.
     * @param args Program arguments.  Only first argument used (for filename).
     */
    public static void main(String[] args)
    {
        long start = System.currentTimeMillis();
        File f = new File("/work/gunturus/nifPrimers/nifD/nifD_derep_aligned.tree");
        
        TreeParser treePars = TreeParser.run(f);
        
        /*try
        {
            BufferedReader r = new BufferedReader(new FileReader(f));
            TreeParser tp = new TreeParser(r);
            Tree t = tp.tokenize(f.length(), f.getName(), null);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Couldn't find file: " + fileName);
        }
        System.out.println("Parsed in " + ((System.currentTimeMillis() - start)/1000.0) + " s");
        System.exit(0);*/
    }

    
   
}
