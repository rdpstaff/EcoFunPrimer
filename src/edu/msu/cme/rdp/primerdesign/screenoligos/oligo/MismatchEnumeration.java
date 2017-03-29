/*
 * Copyright (C) 2015 Michigan State University Board of Trustees
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

import edu.msu.cme.rdp.readseq.utils.IUBUtilities;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author tift
 */
public class MismatchEnumeration {
    
   
    /**
     * 
     *Will include mismatches - updates oligoTargetMap and kmerPosMap
     * @param enumerOligoObj
     * @return 
     * @throws java.io.IOException
     */
    public static EnumerateOligos includeMismatches(EnumerateOligos enumerOligoObj) throws IOException {
        
          
        Map<Oligo, Set<String>> oligoMap = new HashMap <> ();
        
        for(Integer position : enumerOligoObj.getKmerPosMap().keySet()) {
            
            for (Integer kmerSize: enumerOligoObj.getKmerSizes()) {
                Set<Oligo> oligosToTest = new HashSet<> ();
                for (Oligo oligo : enumerOligoObj.getKmerPosMap().get(position)) {
                    if (oligo.getSeq().length() == kmerSize) {
                        oligosToTest.add(oligo);
                    }
                }
                
                for (Oligo refOligo : oligosToTest) {
                    Set<Oligo> finalOligos = buildPossibleMismatches(refOligo,oligosToTest, enumerOligoObj.getMismatches());
                    
                    String compliment = IUBUtilities.complement(refOligo.getSeq().toString());
                    MismatchAlgo mismatchOligos = new MismatchAlgo(refOligo, compliment, finalOligos);
                    Set<Oligo> allOligosForCurrPos = mismatchOligos.findMismatches();
                    Set<String> refOligoTargets = enumerOligoObj.getOligoTargetMap().get(refOligo);
                    Set<String> tempTargetSet;
                    for (Oligo validMismatch: allOligosForCurrPos) {
                        oligoMap.put(validMismatch, refOligoTargets);
                        tempTargetSet = enumerOligoObj.getOligoTargetMap().get(validMismatch);
                        tempTargetSet.addAll(refOligoTargets);
                        enumerOligoObj.getOligoTargetMap().put(validMismatch, tempTargetSet);
                        
                    }
                }
            }
            
        
        }
        enumerOligoObj.setOligoTargetMap(oligoMap);
                      
       return enumerOligoObj;
    }
    
    public static Set<Oligo> buildPossibleMismatches(Oligo refOligo, Set<Oligo> oligosToTest, int maxMismatches) {
        
        Set<Oligo> finalOligos = new HashSet<> ();
        
        for (Oligo possibleMismatch: oligosToTest) {
             if (validMismatch(refOligo, possibleMismatch, maxMismatches)) {
                   finalOligos.add(possibleMismatch);
             }
        }
        return finalOligos;
    }
    
     /**
     * 
     * @param perfectMatch
     * @param possibleMismatch
     * @param maxMismatches
     * @return boolean if mismatch qualifies with reference oligo
     */
    public static boolean validMismatch(Oligo perfectMatch, Oligo possibleMismatch, int maxMismatches) {
        
            int numMismatches = 0;
            String oligoString = possibleMismatch.getSeq().toString();
            String templateString = perfectMatch.getSeq().toString();
            boolean lastPosMismatch = false;
            char currentChar;
            char templateChar;
           
            for (int i = 0; i < oligoString.length(); i++) {
                currentChar = oligoString.charAt(i);
                templateChar = templateString.charAt(i);
                
                if(currentChar != templateChar) {
                    
                        //No terminal mismatches
                        if ( i <= 2 || i >= (oligoString.length() - 3))
                            return false;
                        
//                        if(!checkPurinePyrmPair(templateChar, currentChar))
//                            return false;
                            
                        numMismatches++;
                        if (lastPosMismatch) {
                            return false;
                        }
                        lastPosMismatch = true;
                }else {
                    lastPosMismatch = false;
                }
                
                if (numMismatches > maxMismatches)
                    return false;
             } 
                      
             return true;
           
    }
  /*
    This method checks to see if the mismatch is a valid purine with a pyrimidine pair
    */
  public static boolean checkPurinePyrmPair(char template, char mismatch) {
//      if (template == 'C' && mismatch == 'G')
//          return true;
//      
      int numPur = 0;
      int numPyr = 0;
      
      if(template == 'A' || template == 'G') {
          numPur++;
      }else {
          numPyr++;
      }
      
      if(mismatch == 'A' || mismatch == 'G') {
          numPur++;
      }else {
          numPyr++;
      }
      
      if (numPur == 1 && numPyr == 1){
          return false; 
      }else {
          return true;
      }
    
  }  
}
