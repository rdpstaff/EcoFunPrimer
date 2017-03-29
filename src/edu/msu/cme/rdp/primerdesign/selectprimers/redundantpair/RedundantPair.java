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

package edu.msu.cme.rdp.primerdesign.selectprimers.redundantpair;

import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.Oligo;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author xingziye, tift
 */
public class RedundantPair implements Cloneable{
    
    public RedundantPair() {}
    
    private static Map<String, RedundantPair> prototypes = new HashMap<String, RedundantPair>();
 
        static
        {
            prototypes.put("pairClone", new RedundantPair());
            
           
        }
      
    private Set<String> targetSet;
    private List<Oligo> fwdOligos;
    private List<Oligo> revOligos;

    public Set<String> getTargetSet() {
        return targetSet;
    }
    public List<Oligo> getFwdOligos() {
        return fwdOligos;
    }
    
    public List<Oligo> getRevOligos() {
        return revOligos;
    }
    
    public void setTargetSet(Set<String> tSet) {
        this.targetSet = tSet;
    }
    public void setFwdOligos(List<Oligo> fwdOligos) {
        this.fwdOligos = fwdOligos;
    }
    
    public void setRevOligos(List<Oligo> revOligos) {
        this.revOligos = revOligos;
    }
 
     RedundantPair(Set<String> fwdTargetSet, Set<String> revTargetSet, List<Oligo> fwdOligos, List<Oligo> revOligos) {
        this.fwdOligos = fwdOligos;
        this.revOligos = revOligos; 
        
        targetSet = new HashSet<> (fwdTargetSet);
        targetSet.retainAll(revTargetSet);
    }


    @Override
    public String toString() {
        return "RedundantPair{" + "fwdRedundant=" + fwdOligos + ", revRedundant=" + revOligos + '}';
    }
    
    public static RedundantPair getNewInstance(String s) throws CloneNotSupportedException {
          return ((RedundantPair) prototypes.get(s).clone());
      }
       
    @Override
    public Object clone() {
    Object clone = null;

    try {
        clone = (RedundantPair) super.clone();

     } catch (CloneNotSupportedException e) {
              System.out.println("Cloning sequence problem");
       }

       return clone;
     }

    
}
