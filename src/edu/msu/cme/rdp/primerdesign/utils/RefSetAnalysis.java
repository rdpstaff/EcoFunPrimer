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

package edu.msu.cme.rdp.primerdesign.utils;

import edu.msu.cme.rdp.readseq.readers.Sequence;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author leotift
 */
public class RefSetAnalysis {
    
       /**
     *  Map to hold character that occurs the least at each position
     */
    private final Map<Integer, List<Character>> leastOccurBaseMap;
    
    public RefSetAnalysis() {
        leastOccurBaseMap = new HashMap<> ();
    }
    
    public void buildBasePositionMap(List<Sequence> seqs){
        int seqNum = seqs.size();
        Integer[] length = new Integer[seqNum];
        for (int i=0; i < seqNum; i++) {
            length[i] = seqs.get(i).getSeqString().length();
        }
        Set<Integer> lenSet = new HashSet<>(Arrays.asList(length));
        if (lenSet.size() > 1) {
            throw new IndexOutOfBoundsException("Given sequences are different in length.");
        }
        
        
        for (int pos = 0; pos < length[0]; pos++) {
            
            char[] base = new char[seqNum];
            
            Map<Character, Integer> distribution = new HashMap<>();
            
            for (int i = 0; i < seqNum; i++) {
                
                base[i] = seqs.get(i).getSeqString().charAt(pos);
                
                if(base[i] == '-' || base[i] == '.')
                    continue;
                
                base[i] = Character.toUpperCase(base[i]);
                
                if (distribution.containsKey(base[i])) {
                    int count = distribution.get(base[i]) + 1;
                    distribution.put(base[i], count);
                } else {
                    distribution.put(base[i], 1);
                }
            }
           List<Character> charList = new ArrayList<>();
           // if all '-' for all positions
           if (distribution.keySet().isEmpty()) {
               charList.add('-');
               charList.add('-');
               this.leastOccurBaseMap.put(pos, charList);
               continue;
           }         
           List<Character> possCharList = new ArrayList<>();
           possCharList.add('A');
           possCharList.add('T');
           possCharList.add('C');
           possCharList.add('G');
           
           Character leastOccurChar = null;
           Character mostOccurChar = null;
           int leastNumOccur = seqNum;
           int mostNumOccur = 0;
           
           for (char nucBase : distribution.keySet()) {
               
               int baseOccurNum = distribution.get(nucBase);
               if(baseOccurNum == seqNum) {                
                 mostOccurChar = nucBase; 
                  break;
               }
               
               if(mostNumOccur < baseOccurNum) {
                   mostNumOccur = baseOccurNum;
                   mostOccurChar = nucBase;
               }
           }
           if (!distribution.keySet().containsAll(possCharList)) {
                possCharList.removeAll(distribution.keySet());
                leastOccurChar = possCharList.get(0);
           }else {
                for (char nucBase : distribution.keySet()) {

                    int baseOccurNum = distribution.get(nucBase);
                    if(baseOccurNum == seqNum) {
                       if (nucBase == 'A') {
                           leastOccurChar = 'T';
                       }else if (nucBase == 'T') {
                           leastOccurChar = 'C';
                       }else if (nucBase == 'C') {
                           leastOccurChar = 'G';
                       }else if (nucBase == 'G') {
                           leastOccurChar = 'A';
                       }

                       break;
                    }
                    if(leastNumOccur > baseOccurNum) {
                        leastNumOccur = baseOccurNum;
                        leastOccurChar = nucBase;
                    }

                }
           }
           charList.add(mostOccurChar);
           charList.add(leastOccurChar);
           this.leastOccurBaseMap.put(pos, charList);
                
        }
             
    }
    
    public Map<Integer, List<Character>> getBasePositionMap() {
        return this.leastOccurBaseMap;
    }
    
}
