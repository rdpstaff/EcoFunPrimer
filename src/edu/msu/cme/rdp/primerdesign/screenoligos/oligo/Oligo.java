/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.msu.cme.rdp.primerdesign.screenoligos.oligo;

import java.io.IOException;
import java.util.Objects;

/**
 *
 * @author gunturus, xingziye, tift
 */
public class Oligo {
    
    private String seq; //oligo seq
    //private int start;  //start postion in alignment  
    private double oligoTm;  //melting temprature
    private double hairpinTm; //primer 3 - hairpin temperature
    private double homodimerTm; //primer 3 - homodimer temperature
    private double oligoDeltaG; // calculated Delta G
    private double oligoDeltaH; // calculated Delta H
    private double oligoDeltaS; // calculated Delta S
    private int startPosition;
    private int endPosition;
    
    public Oligo(String seq) throws IOException{
        this.seq = seq;
                
    }
    
    public void setOligoTm(double temp) {
        this.oligoTm = temp;
    }
    
    public void setDeltaG(double deltaG) {
        this.oligoDeltaG = deltaG;
    }
    public void setDeltaH(double deltaH) {
        this.oligoDeltaH = deltaH;
    }
    public void setDeltaS(double deltaS) {
        this.oligoDeltaS = deltaS;
    }
    
    public void setHairpinTm(double temp) {
        this.hairpinTm = temp;
    }
    
    public void setHomodimerTm(double temp) {
        this.homodimerTm = temp;
    }
    public void setStartPosition(int position) {
        this.startPosition = position;
    }
    public void setEndPosition(int position) {
        this.endPosition = position;
    }
    
    public int getStartPosition() {
        return this.startPosition;
    }
    
    public int getEndPosition() {
        return this.endPosition;
    }
    
    public double getTm() {
        return this.oligoTm;
    }
    
    public double getDeltaG() {
        return this.oligoDeltaG;
    }
    public double getDeltaH() {
        return this.oligoDeltaH;
    }
    public double getDeltaS() {
        return this.oligoDeltaS;
    }
    
    public double getHairpinTm() {
        return this.hairpinTm;
    }
    
    public double getHomodimerTm() {
        return this.homodimerTm;
    }
    
    public String getSeq() {
        return this.seq;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Oligo other = (Oligo) obj;
        if (!Objects.equals(this.seq, other.seq)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.seq.hashCode();
        return hash;
    }

    @Override
    public String toString() {
        return "Oligo{" + "seq=" + seq + '}' + "{ Tm:" + oligoTm + '}' + "{EndPos:" + endPosition + '}' + '\n';
    }
    
}