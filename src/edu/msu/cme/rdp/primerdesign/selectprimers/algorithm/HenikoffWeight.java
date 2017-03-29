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

package edu.msu.cme.rdp.primerdesign.selectprimers.algorithm;

import edu.msu.cme.rdp.readseq.readers.Sequence;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author xingziye, tift
 */
public class HenikoffWeight {
        
    public static Map<String, Double> calSequenceWeight(List<Sequence> seqs) {
        int seqNum = seqs.size();
        Integer[] length = new Integer[seqNum];
        for (int i=0; i < seqNum; i++) {
            length[i] = seqs.get(i).getSeqString().length();
        }
        Set<Integer> lenSet = new HashSet<>(Arrays.asList(length));
        if (lenSet.size() > 1) {
            throw new IndexOutOfBoundsException("Given sequences are different in length.");
        }
        
        Double[][] weightMatrix = new Double[length[0]][seqNum];
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

            for (int i = 0; i < seqNum; i++) {
                
                if(base[i] == '-' || base[i] == '.') {
                     weightMatrix[pos][i] = 0.0;
                     continue;
                }                                        
                weightMatrix[pos][i] = 1.0/(distribution.size() * distribution.get(base[i]));
                
            }

        }
        
        Map<String, Double> weight = new HashMap<>();
        for (int pos = 0; pos < length[0]; pos++) {
            
            for (int i = 0; i < seqNum; i++) {
                
                String seqName = seqs.get(i).getSeqName();

                if (weight.containsKey(seqName)) {
                    double value = weight.get(seqName) + weightMatrix[pos][i];
                    weight.put(seqName, value);
                } else {
                    weight.put(seqName, weightMatrix[pos][i]);
                }               

            }
        }
        
        double sumAllSeqs = 0.0;
               
        for (Sequence seq : seqs) {
            sumAllSeqs += weight.get(seq.getSeqName());
        }
        for (Sequence seq : seqs) {
            double value = weight.get(seq.getSeqName())/sumAllSeqs;
            weight.put(seq.getSeqName(), value);

        }
        return weight;
    }

    
}

