/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.msu.cme.rdp.primerdesign.weighting.visitors;

import edu.msu.cme.rdp.primerdesign.weighting.nodes.GSCNode;
import edu.msu.cme.rdp.taxatree.VisitInfo;
import edu.msu.cme.rdp.taxatree.VisitInfo.VisitType;
import edu.msu.cme.rdp.taxatree.interfaces.TreeVisitor;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 *
 * @author hashsham, gunturus
 */
public class GSCWeightingVisitor implements TreeVisitor<GSCNode> {
    
    private final Map<String, Double> weightMap = new HashMap<>();
    private final Stack<GSCNode> stack = new Stack<>();
    
    
    @Override
    public boolean visitNode(VisitInfo<GSCNode> visitInfo){
        
        GSCNode node = visitInfo.getTaxon();
        
        if(visitInfo.getVisitType() == VisitType.down){
            if(visitInfo.hasChildren()){
                stack.push(node);
            }
        }
        
        else if(visitInfo.getVisitType() == VisitType.up){

            if(!visitInfo.hasChildren()){
                weightMap.put(node.getName(), node.getDistance());
                stack.peek().addLeaf(node.getName());
            }
            else{
                
                stack.pop();
                
                double leafSum = 0;
                
                for(String leaf : node.getLeafList()){
                    leafSum += weightMap.get(leaf);
                }
                
                for(String leaf : node.getLeafList()){
                    double delta = node.getDistance()*(weightMap.get(leaf)/leafSum);
                    weightMap.put(leaf, weightMap.get(leaf)+delta);
                    if(!stack.empty()){
                        stack.peek().addLeaf(leaf);
                    }
                }
            }
        }
        
        return true;
    }
    
    public Map<String, Double> getWeightMap(){
        return this.weightMap;
    }
    
}
