/*
 * Copyright (C) 2015  Santosh Gunturu <gunturus at msu dot edu>
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

import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.EnumerateOligos;
import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.Oligo;
import edu.msu.cme.rdp.primerdesign.selectprimers.primerpair.PairFactory;
import edu.msu.cme.rdp.primerdesign.selectprimers.primerpair.PrimerPair;
import edu.msu.cme.rdp.primerdesign.utils.Primer3Wrapper;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Map;

/**
 *  Currently not being used in SelectPrimersPipeline.java - Tift - 7/15/15
 * @author gunturus, xingziye
 */
public class EnumeratePrimerPairs {
    
    private final Set<PrimerPair> allPairs;
    private final EnumerateOligos eoFwd;
    private final EnumerateOligos eoRev;
    private final Primer3Wrapper primer3;
    
    public Set<PrimerPair> getAllPairs() {return allPairs;}
    
    public EnumeratePrimerPairs(EnumerateOligos eoFwd, EnumerateOligos eoRev, Primer3Wrapper primer3) {
        this.allPairs = new HashSet<>();
        this.eoFwd = eoFwd;
        this.eoRev = eoRev;
        this.primer3 = primer3;
    }
    
    public Set<PrimerPair> enumeratePair( List<Integer> fwdPos, List<Integer> revPos, double maxDelta, double maxHeterodimerTm) {
        
         Map <Integer, Set<Oligo>> fwdOligoMap = eoFwd.getUniqueKmerPosMap();
         Map <Integer, Set<Oligo>> revOligoMap = eoRev.getUniqueKmerPosMap();
         List<Oligo> nonNullFwdOligos = new ArrayList<> ();
         List<Oligo> nonNullRevOligos = new ArrayList<> ();
         
        for (Integer fwdStart : fwdPos) {
            
              if (fwdOligoMap.containsKey(fwdStart)) {
                  for (Oligo oligo : fwdOligoMap.get(fwdStart)) {
                        nonNullFwdOligos.add(oligo);
                  }
              }else 
                 continue;
                                                      
            for (Integer revStart : revPos) {
                
                  if (revOligoMap.containsKey(revStart)) {
                      for (Oligo oligo : revOligoMap.get(revStart)) {
                            nonNullRevOligos.add(oligo);
                      }
                        
                  }else 
                    continue;
                                                                                                                       
                for (Oligo fwdOligo : nonNullFwdOligos) {
                    for (Oligo revOligo : nonNullRevOligos) {
                                                                       
                       
                        int fwdRedundantIndex = 0;
                        int revRedundantIndex = 0;
                        boolean isCompatible = true;
                        
                        while ( abs(fwdOligo.getTm() - revOligo.getTm()) > maxDelta ) {
                               
                            if ( (fwdOligo.getTm() >= revOligo.getTm()) ) {
                                Set<String> targetSet = eoFwd.getOligoTargetMap().get(fwdOligo);
                                if (fwdRedundantIndex < eoFwd.getTargetSetMap().get(targetSet).size()) {
           //                         fwdOligo = eoFwd.getTargetSetMap().get(targetSet).get(fwdRedundantIndex);
                                }
                                else {
                                    System.out.println("Fwd Oligo and target set not compatible in EnumeratePrimerPairs.java");
                                    isCompatible = false;
                                    break;
                                }
                                fwdRedundantIndex++;
                            } else {
                                Set<String> targetSet = eoRev.getOligoTargetMap().get(revOligo);
                                if (revRedundantIndex < eoRev.getTargetSetMap().get(targetSet).size()) {
  //                                  revOligo = eoRev.getTargetSetMap().get(targetSet).get(revRedundantIndex);
                                }
                                else {
                                    System.out.println("Rev Oligo and target set not compatible in EnumeratePrimerPairs.java");
                                    isCompatible = false;
                                    break;
                                }
                                revRedundantIndex++;
                            }
                            
                        }
                        
                        if(!isCompatible)
                            continue;
                        
                        PrimerPair pair = PairFactory.createPair(fwdOligo, revOligo, eoFwd.getOligoTargetMap(), eoRev.getOligoTargetMap(), primer3, 35.0);
                        if (pair.getHeterodimerTm() < maxHeterodimerTm) {
                           
                            allPairs.add(pair);
                        }
                        
                       }
                   }
                                                                     
            }
        }
     
        return allPairs;
    }
                
}
                
              

