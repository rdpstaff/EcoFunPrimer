/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.msu.cme.rdp.primerdesign.weighting.visitors;

import edu.msu.cme.rdp.primerdesign.weighting.nodes.DistanceNode;
import edu.msu.cme.rdp.taxatree.VisitInfo;
import edu.msu.cme.rdp.taxatree.VisitInfo.VisitType;
import edu.msu.cme.rdp.taxatree.interfaces.TreeVisitor;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hashsham, gunturus
 */
public class DistanceWeightingVisitor implements TreeVisitor<DistanceNode> {
    
    private final Map<String, Double> weightMap = new HashMap<>();
    private final Map<Integer, Double> splitMap = new HashMap<>();


   
    @Override
    public boolean visitNode(VisitInfo<DistanceNode> visitInfo){
        
        DistanceNode node = visitInfo.getTaxon();
        int depth = visitInfo.getDepth();
        
        if(visitInfo.getVisitType() == VisitType.down){
            if(visitInfo.hasChildren()){
                splitMap.put(depth, node.getDistance()/node.getChildCount());
            }
            
            else if (!visitInfo.hasChildren()){
                
                double weight = node.getDistance();
                
                for(double split : splitMap.values()){
                   weight += split;
                }
                
                weightMap.put(node.getName(), weight);
            }
        }
        
        
        else if (visitInfo.getVisitType() == VisitType.up){
            if(splitMap.containsKey(depth)){
                splitMap.remove(depth);
            }
        }
        
        return true;
    }
    
    
    public Map<String, Double> getWeightMap(){
        return weightMap;
    }
}
