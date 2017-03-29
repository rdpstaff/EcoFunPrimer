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

package edu.msu.cme.rdp.primerdesign.selectprimers;

import edu.msu.cme.rdp.primerdesign.selectprimers.algorithm.Tree;
import edu.msu.cme.rdp.primerdesign.selectprimers.algorithm.TreeNode;
import edu.msu.cme.rdp.primerdesign.selectprimers.algorithm.TreeParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Test;

/**
 *
 * @author leotift
 */
public class TreeWeightTest {
    
    
        @Test
        public void test() throws IOException {
    
        String fileName = "/home/leotift/Downloads/mocktreeweighttest.tree";
        
        File f = new File(fileName);
        
        try 
        {
            BufferedReader r = new BufferedReader(new FileReader(f));
            TreeParser tp = new TreeParser(r);
            tp.setTree(tp.tokenize(f.length(), f.getName()));
            
            for(int i = 1; i < tp.getTree().getAllTreeNodes().size(); i++) {
                TreeNode node = tp.getTree().getAllTreeNodes().get(i);
                if(!("".equals(node.getName()))){
                tp.buildWeightMap(node);
                }
            
            }
            
            for (String seqName : tp.getWeightMap().keySet()) {
            System.out.println(seqName);
            System.out.println("Value " + tp.getWeightMap().get(seqName));
            }
        
                
                                                      
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Couldn't find file: " + fileName);
        }
            
        
        
        }
    
}
