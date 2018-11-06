/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.msu.cme.rdp.primerdesign.weighting;

import edu.msu.cme.rdp.primerdesign.weighting.visitors.DistanceWeightingVisitor;
import edu.msu.cme.rdp.primerdesign.weighting.visitors.ChildCountVisitor;
import edu.msu.cme.rdp.primerdesign.weighting.nodes.DistanceNode;
import edu.msu.cme.rdp.taxatree.TaxonHolder;
import edu.msu.cme.rdp.taxatree.utils.NewickTreeBuilder;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author hashsham, gunturus
 */
public class DistanceWeightingTest {
    
    @Test
    public void testDistanceWeighting() throws IOException{
        
        DistanceNode root = new DistanceNode(0, "root", "root_rank", 0);
        
        String newick = "(B:2,((G:1,H:2)D:0.5,E:1,F:1.5)C:1)A;"; // has node with three children
        InputStream is = new ByteArrayInputStream(newick.getBytes("UTF-8"));
        
        NewickTreeBuilder builder = new NewickTreeBuilder(root, is, new NewickTreeBuilder.NewickTaxonFactory<DistanceNode>() {

            @Override
            public DistanceNode buildTaxon(int taxid, String name, float distance) {
                return new DistanceNode(taxid, name, "", distance);
            }
        });
        
         
        TaxonHolder<DistanceNode> rootNode = builder.getRoot().getRootTaxonHodler();
        DistanceWeightingVisitor dv = new DistanceWeightingVisitor();
        ChildCountVisitor cv = new ChildCountVisitor();
        
        rootNode.biDirectionDepthFirst(cv);
        rootNode.biDirectionDepthFirst(dv);
        
        assertEquals(2.0, dv.getWeightMap().get("B"), 0.001);
        assertEquals(1.333, dv.getWeightMap().get("E"), 0.001);
        assertEquals(1.833, dv.getWeightMap().get("F"), 0.001);
        assertEquals(1.583, dv.getWeightMap().get("G"), 0.001);
        assertEquals(2.583, dv.getWeightMap().get("H"), 0.001);

        System.out.println(dv.getWeightMap());
    }
}
