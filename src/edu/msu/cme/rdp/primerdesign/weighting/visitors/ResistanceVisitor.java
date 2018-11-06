/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.msu.cme.rdp.primerdesign.weighting.visitors;

import edu.msu.cme.rdp.taxatree.VisitInfo;
import edu.msu.cme.rdp.taxatree.VisitInfo.VisitType;
import edu.msu.cme.rdp.primerdesign.weighting.nodes.WeightNode;
import edu.msu.cme.rdp.taxatree.interfaces.TreeVisitor;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hashsham, gunturus
 */


// Meant to be run using biDirectionDepthFirst(), not topDownVisit()

public class ResistanceVisitor implements TreeVisitor<WeightNode>{
    
    // Map to store partials resistances, since visitor has no direct access to parent/child structure
    
    private final Map<Integer, Double> rPartMap = new HashMap<>();

    // Visits each node twice when run using biDirectionDepthFirst()
    // On down visits, creates a key,value pair to store partial resistances at that depth
    // On up visits, sets a node's rDown if it finds an rPart at that depth then adds to it's parent's rPart and 
    //    removes itself to avoid conflicts between unrelated branches at that depth when calculating rDown 
    
    @Override
    public boolean visitNode(VisitInfo<WeightNode> visitInfo){
        
        WeightNode node = visitInfo.getTaxon();

        if(visitInfo.getVisitType() == VisitType.down){
            if(visitInfo.hasChildren()){
                rPartMap.put(visitInfo.getDepth(), 0.0);
            }
        }
        
        else if(visitInfo.getVisitType() == VisitType.up){
                         
             if(rPartMap.containsKey(visitInfo.getDepth())){
                 node.setRDown(1/rPartMap.get(visitInfo.getDepth()));
             }
             
             if(rPartMap.containsKey(visitInfo.getDepth()-1)){
                 rPartMap.put(visitInfo.getDepth()-1, rPartMap.get(visitInfo.getDepth()-1)+1/(node.getRUp()+node.getRDown()));
                 rPartMap.remove(visitInfo.getDepth());
             }
         }
        
        return true;
    }
}
