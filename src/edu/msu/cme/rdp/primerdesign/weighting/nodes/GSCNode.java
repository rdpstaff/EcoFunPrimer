/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.msu.cme.rdp.primerdesign.weighting.nodes;

import edu.msu.cme.rdp.taxatree.Taxon;
import java.util.ArrayList;

/**
 *
 * @author hashsham, gunturus
 */
public class GSCNode extends Taxon{
    
    private double distance;
    private ArrayList<String> leafList = new ArrayList();
    
    public GSCNode(int taxid, String name, String rank, double distance){
        super(taxid, name, rank);
        this.distance = distance;
    }
    
    public double getDistance(){
        return this.distance;
    }
    
    public ArrayList<String> getLeafList(){
        return this.leafList;
    }
    
    public void setDistance(double d){
        this.distance = d;
    }
    
    public void addLeaf(String s){
        leafList.add(s);
    }
}
