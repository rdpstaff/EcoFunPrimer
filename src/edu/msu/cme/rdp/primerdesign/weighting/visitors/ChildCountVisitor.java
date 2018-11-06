/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.msu.cme.rdp.primerdesign.weighting.visitors;

import edu.msu.cme.rdp.primerdesign.weighting.nodes.DistanceNode;
import edu.msu.cme.rdp.taxatree.Taxon;
import edu.msu.cme.rdp.taxatree.VisitInfo;
import edu.msu.cme.rdp.taxatree.VisitInfo.VisitType;
import edu.msu.cme.rdp.taxatree.interfaces.TreeVisitor;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hashsham, gunturus
 */
public class ChildCountVisitor implements TreeVisitor<DistanceNode> {
    
    public Map<Integer, Integer> childCountMap = new HashMap<>();
    
    @Override
    public boolean visitNode(VisitInfo<DistanceNode> visitInfo){
        DistanceNode node = visitInfo.getTaxon();
        int depth = visitInfo.getDepth();
        
        if(visitInfo.getVisitType() == VisitType.down){
            if(visitInfo.hasChildren()){
                childCountMap.put(depth, 0);
            }
            if(childCountMap.containsKey(depth-1)){
                childCountMap.put(depth-1, childCountMap.get(depth-1)+1);
            }
        }
        
        if(visitInfo.getVisitType() == VisitType.up){
            if(childCountMap.containsKey(depth)){
                node.setChildCount(childCountMap.get(depth));
                childCountMap.remove(depth);
            }
        }
        
        return true;
    }
}
