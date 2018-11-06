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


// Meant to be run using biDirectionDepthFirst() not topDownVisit()

public class CurrentVisitor implements TreeVisitor<WeightNode> {
   
    // Map to store partials voltages, since visitor has no direct access to parent/child structure
    // Stores rootRDown to normalize weights to 1
    
    private final Map<String, Double> weightMap = new HashMap<>(); 
    private final Map<Integer, Double> voltageMap = new HashMap<>();
    private double rootRDown;
    
    // Visits every node twice when run with biDirectionDepthFirst()
    // On down visits, sets the leaf currents using the rootRDown to normalize currents to 1 
    //    and puts into weightMap, sets vDown of intermediate nodes, and enters vDown into 
    //    map to calculate children's vDowns 
    // On up visits, removes voltages at that depth to avoid conflicts with 
    //    unrelated branches at that depth when calculating vDown
    
    @Override
    public boolean visitNode(VisitInfo<WeightNode> visitInfo){
        
        WeightNode node = visitInfo.getTaxon();
        int depth = visitInfo.getDepth();
        
        if(visitInfo.getDepth() == 0){
                rootRDown = node.getRDown();
            }
        
        if(visitInfo.getVisitType() == VisitType.down){
            if(voltageMap.containsKey(visitInfo.getDepth()-1)){
               if(!visitInfo.hasChildren()){
                   node.setCurrent(voltageMap.get(depth-1)/node.getRUp());
                   weightMap.put(node.getName(), node.getCurrent()*rootRDown);
               }
               else{
                   node.setVDown(voltageMap.get(depth-1)*(node.getRDown()/(node.getRUp()+node.getRDown())));
               }
            }
            
            
            if(visitInfo.hasChildren()){
                    voltageMap.put(depth, node.getVDown());   // Not in else statement above to ensure that root node enters vDown into map
                }
        }
        
        else if(visitInfo.getVisitType() == VisitType.up){
            if(voltageMap.containsKey(depth)){
                voltageMap.remove(depth);
            }
        }
        
        return true;
    }
    
    
    public Map<String, Double> getWeightMap(){
        return weightMap;
    }
    
}
