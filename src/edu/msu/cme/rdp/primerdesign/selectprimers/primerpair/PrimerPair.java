/*
 * Copyright (C) 2015 xingziye
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

package edu.msu.cme.rdp.primerdesign.selectprimers.primerpair;

import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.Oligo;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author xingziye
 */
public class PrimerPair {
    private final double tempDelta;
    private final Oligo forward;
    private final Oligo reverse;
    private double heterodimerTm;
    private Set<String> targetSet;
    
    public double getTempDelta() {return tempDelta;}
    public double getHeterodimerTm() {return heterodimerTm;}
    public Set<String> getTargetSet() {return targetSet;}
    public Oligo getFwdOligo() {return forward;}
    public Oligo getRevOligo() {return reverse;}
    
    
    public PrimerPair(Oligo forward, Oligo reverse, double tempDeltaOligo) {
        this.forward = forward;
        this.reverse = reverse;
        this.targetSet = new HashSet<> ();
        this.tempDelta = tempDeltaOligo;
    }
    
    public void setHeterodimerTm(double temp) {
        this.heterodimerTm = temp;
    }
    
    public void setTargetSet(Set<String> targetSet) {
        this.targetSet = targetSet;
    }

    @Override
    public String toString() {
        return "PrimerPair{" + "forward=" + forward + ", reverse=" + reverse + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.forward);
        hash = 89 * hash + Objects.hashCode(this.reverse);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PrimerPair other = (PrimerPair) obj;
        if (!Objects.equals(this.forward, other.forward)) {
            return false;
        }
        return true;
    }
}
