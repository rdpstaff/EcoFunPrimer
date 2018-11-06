package edu.msu.cme.rdp.primerdesign.weighting.nodes;

import edu.msu.cme.rdp.taxatree.Taxon;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hashsham, gunturus
 */
public class DistanceNode extends Taxon{
    
    private double distance;
    private int childCount;
            
    public DistanceNode(int taxid, String name, String rank, double distance){
        super(taxid, name, rank, false);
        this.distance = distance;
    }
    
    
    public double getDistance(){
        return this.distance;
    }
    
    public int getChildCount(){
        return this.childCount;
    }
    
    
    public void setDistance(double d){
        this.distance = d;
    }
    
    public void setChildCount(int c){
        this.childCount = c;
    }
    
}
