/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.msu.cme.rdp.primerdesign.weighting;

import edu.msu.cme.rdp.primerdesign.weighting.visitors.ResistanceVisitor;
import edu.msu.cme.rdp.primerdesign.weighting.visitors.CurrentVisitor;
import edu.msu.cme.rdp.taxatree.ConcretRoot;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import static org.junit.Assert.*;
import edu.msu.cme.rdp.primerdesign.weighting.nodes.WeightNode;
import edu.msu.cme.rdp.taxatree.utils.NewickTreeBuilder;

/**
 *
 * @author hashsham
 */
public class OhmWeightingTest {
    @Test
    public void testOhmWeighting() throws IOException{
        
        WeightNode root = new WeightNode(0, "root", "root_rank", 0, 0, 1, 0);
        String newick = "(B:2,(D:0.5,E:1)C:1)A;" ;
        InputStream is = new ByteArrayInputStream(newick.getBytes("UTF-8"));

        
//        File f = new File("/home/hashsham/Desktop/test.tree");
//        InputStream is = new FileInputStream(f);

        
        NewickTreeBuilder builder = new NewickTreeBuilder(root, is, new NewickTreeBuilder.NewickTaxonFactory<WeightNode>() {

            public WeightNode buildTaxon(int taxid, String name, float distance) {
                return new WeightNode(taxid, name, "", distance, 0, 0, 0);
            }
        });
        
        
        ConcretRoot<WeightNode> rootNode = builder.getRoot();
        
        ResistanceVisitor rv = new ResistanceVisitor();
        rootNode.getRootTaxonHodler().biDirectionDepthFirst(rv);
        
        
        assertEquals(0.8, rootNode.getChildTaxon(1).getRDown(), 0.001);
        assertEquals(1.0/3, rootNode.getChildTaxon(3).getRDown(), 0.001);
   
        
        CurrentVisitor cv = new CurrentVisitor();
        rootNode.getRootTaxonHodler().biDirectionDepthFirst(rv);

        assertEquals(0.4, cv.getWeightMap().get("B"), 0.001);
        assertEquals(0.4, cv.getWeightMap().get("D"), 0.001);
        assertEquals(0.2, cv.getWeightMap().get("E"), 0.001);
        
        double rootWeight = 1.0;
        double leafWeightSum = 0.0;
        
        for(String key : cv.getWeightMap().keySet()){
            leafWeightSum += cv.getWeightMap().get(key);
        }
        
        assertEquals(rootWeight, leafWeightSum, 0.001);
        System.out.println(leafWeightSum);
        System.out.println(cv.getWeightMap());
    }
}
