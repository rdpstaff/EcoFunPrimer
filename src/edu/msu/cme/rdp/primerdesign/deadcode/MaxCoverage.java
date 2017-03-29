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

package edu.msu.cme.rdp.primerdesign.deadcode;

import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.Oligo;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *  This class calculates the max coverage to be used in the graphing portion of Screen Oligos
 * @author xingziye
 */
public class MaxCoverage {
    
    private static Map<Integer, Set<Oligo>> kmerPosMap;
    private static Map<Oligo, Set<String>> oligoTargetMap;
    private static int numSeq;
    
    public MaxCoverage(Map<Integer, Set<Oligo>> kmerPosMap, Map<Oligo, Set<String>> oligoTargetMap, int numSeq) {
        MaxCoverage.kmerPosMap = kmerPosMap;
        MaxCoverage.oligoTargetMap = oligoTargetMap;
        MaxCoverage.numSeq = numSeq;
    }
    
//    
//    public void setKmerPosMap(Map<Integer, Set<Oligo>> kmerPosMap) {
//        this.kmerPosMap = kmerPosMap;
//    }
//    
//    public void setOligoTargetMap(Map<Oligo, Set<String>> oligoTargetMap) {
//        this.oligoTargetMap = oligoTargetMap;
//    }
////    
       public void setNumSeq(int numSeq) {
        this.numSeq = numSeq;
    }
 
    public Map<Integer, Set<Oligo>> getKmerPosMap() {
        return this.kmerPosMap;
    }
    
    public Map<Oligo, Set<String>> getOligoTargetMap() {
        return this.oligoTargetMap;
    }
    
    public int getNumSeq() {
        return this.numSeq;
    }
    
     
    public static double calCoverage(int pos, int num) {
        
        Set<String> domain = new HashSet<>();
        
        for ( Oligo oligo : kmerPosMap.get(pos) ) {
            domain.addAll( oligoTargetMap.get(oligo));
        }
        
        if ( kmerPosMap.get(pos).size() < num ) {
            return domain.size() / (double) numSeq;
        }
        
        Set<String> currentDomain = new HashSet<>(domain);
        
        for (int i = 0; i < num; i++) {
            
            Set<String> minDomain = new HashSet<>(currentDomain);
            for ( Oligo oligo : kmerPosMap.get(pos) ) {
                
                Set<String> tempDomain = new  HashSet<>(currentDomain);
                tempDomain.removeAll( oligoTargetMap.get(oligo) );
                
                if ( tempDomain.isEmpty() ) {
                    return domain.size() / (double) numSeq;
                }
                
                if ( tempDomain.size() < minDomain.size() ) {
                    minDomain = tempDomain;
                }
            }
            
            currentDomain = minDomain;
        }
        
        domain.removeAll(currentDomain);
        
        return domain.size() / (double) numSeq;
    }
    
}
