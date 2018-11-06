/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.msu.cme.rdp.primerdesign.weighting;

import edu.msu.cme.rdp.primerdesign.weighting.visitors.GSCWeightingVisitor;
import edu.msu.cme.rdp.primerdesign.weighting.nodes.GSCNode;
import edu.msu.cme.rdp.taxatree.TaxonHolder;
import edu.msu.cme.rdp.taxatree.utils.NewickTreeBuilder;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author hashsham
 */
public class GSCWeightingTest {
    
    @Test
    public void testGSCWeighting() throws IOException{
        
        GSCNode root = new GSCNode(0, "root", "rank", 0);
        
        String newick = "(4:8,(3:5,(1:2,2:2)5:3)6:3)7;"; 
        InputStream is = new ByteArrayInputStream(newick.getBytes("UTF-8"));
        
        NewickTreeBuilder builder = new NewickTreeBuilder(root, is, new NewickTreeBuilder.NewickTaxonFactory<GSCNode>() {

            public GSCNode buildTaxon(int taxid, String name, float distance) {
                return new GSCNode(taxid, name, "", distance);
            }
        });
        
        TaxonHolder<GSCNode> rootNode = builder.getRoot().getRootTaxonHodler();
        
        GSCWeightingVisitor gv = new GSCWeightingVisitor();
        
        rootNode.biDirectionDepthFirst(gv);

        assertEquals(4.375, gv.getWeightMap().get("1"), 0.001);
        assertEquals(4.375, gv.getWeightMap().get("2"), 0.001);
        assertEquals(6.25, gv.getWeightMap().get("3"), 0.001);
        assertEquals(8.0, gv.getWeightMap().get("4"), 0.001);
        
        System.out.println(gv.getWeightMap());
    }
}
