/*
 * Copyright (C) 2016 Michigan State University Board of Trustees
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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