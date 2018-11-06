/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.msu.cme.rdp.primerdesign.weighting.nodes;

import edu.msu.cme.rdp.taxatree.Taxon;
import edu.msu.cme.rdp.taxatree.Taxon;

/**
 *
 * @author hashsham, gunturus
 */
public class WeightNode extends Taxon{
    
    private double current;
    private double rDown;
    private double rUp;
    private double vDown;
    
    public WeightNode(int taxid, String name, String rank, double rUp, double rDown, double vDown, double current){
        
        super(taxid, name, rank, false);
        this.current = current;
        this.rDown = rDown;
        this.rUp = rUp;
        this.vDown = vDown;
    }
    
    
    public void setCurrent(double I){
        this.current = I;
    }
    
    public void setRDown(double r){
        this.rDown = r;
    }
    
    public void setRUp(double r){
        this.rUp = r;
    }
    
    public void setVDown(double v){
        this.vDown = v;
    }
    
    public double getCurrent(){
        return this.current;
    }
    
    public double getRDown(){
        return this.rDown;
    }
    
    public double getRUp(){
        return this.rUp;
    }
    
    public double getVDown(){
        return this.vDown;
    }
}
