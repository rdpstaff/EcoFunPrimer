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

import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.DegeneratePair;
import edu.msu.cme.rdp.primerdesign.selectprimers.PrimerDesignViewer;
import edu.msu.cme.rdp.primerdesign.selectprimers.primerpair.SelectPrimerPairs;
import edu.msu.cme.rdp.primerdesign.selectprimers.redundantpair.EnumerateDegeneratePair;
import edu.msu.cme.rdp.primerdesign.utils.Primer3Wrapper;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author xingziye, gunturus, tift
 */
public class MaxDegenerationAlgo {

    

    public MaxDegenerationAlgo() {
       
    }

       
    public static List<Set<String>> calUniformDegenerate(EnumerateDegeneratePair ep, int numPrimerDegen, int seqLength, File file, PrimerDesignViewer printBuild) {

        Set<String> domain = new HashSet<>();

        List<Set<String>> TargetSets = new ArrayList<>();
        List<Set<String>> Cover = new ArrayList<>();

        for (Set<String> tempSet : ep.getPairDegenerateMap().keySet()) {
            if (tempSet.size() > 0) {
                TargetSets.add(tempSet);
            }
        }

        for (Set<String> targetSet : TargetSets) {
            domain.addAll(targetSet);
        }

        int maxElement = -1;
        int primerCount = 0;
        SelectPrimerPairs select = new SelectPrimerPairs(ep);
        while ((domain.size()) != 0 && (primerCount < numPrimerDegen)) {

            int maxSize = 0;
            DegeneratePair finalPair = null;

            for (int j = 0; j < TargetSets.size(); j++) {

                try {

                    Set<String> tempSet = new HashSet<>(TargetSets.get(j));

                    tempSet.retainAll(domain);

                    if (tempSet.isEmpty()) {
                        continue;
                    }
      
                    int tempSize = tempSet.size();
                  

                    if (tempSize > maxSize) {
                        maxElement = j;
                        maxSize = tempSize;
                    
                    }

                } catch (Exception e) {

                }
            }

            
            finalPair = select.selectDegenerate(TargetSets.get(maxElement));
            printBuild.addInfoToFile(file,"Primer Pair: " + finalPair.toString());
        
            primerCount++;

            Cover.add(TargetSets.get(maxElement));
            domain.removeAll(TargetSets.get(maxElement));

        }

        return Cover;

    }

    public static List<Set<String>> calWeightDegeneration(EnumerateDegeneratePair ep, int numPrimerDegen, Map<String, Double> weight, Primer3Wrapper primer3, int seqLength, File file, PrimerDesignViewer printBuild) {

        Set<String> domain = new HashSet<>();
        List<Set<String>> TargetSets = new ArrayList<>();
        List<Set<String>> Cover = new ArrayList<>();

        double maxHeterodimerTm = 35.0;
        SelectPrimerPairs selectPrimPair = new SelectPrimerPairs(ep);

        for (Set<String> tempSet : ep.getPairDegenerateMap().keySet()) {

            TargetSets.add(tempSet);
        }
        for (Set<String> targetSet : TargetSets) {
            domain.addAll(targetSet);
        }
        Set<String> weightDomain = new HashSet<>();
        for (String seqToHit: weight.keySet()) {
            weightDomain.add(seqToHit);
        }
        domain.retainAll(weightDomain);
        int primerCount = 0;

        while ((domain.size()) != 0 && (primerCount < numPrimerDegen)) {

            int maxSize = 0;
            double maxWeight = 0.0;

            int maxElement = -1;
            DegeneratePair pair = null;

            for (int j = 0; j < TargetSets.size(); j++) {

                Set<String> tempSet = new HashSet<>(TargetSets.get(j));

                double tempWeight = 0.0;

                tempSet.retainAll(domain);

                for (String seqName : tempSet) {
                    tempWeight += weight.get(seqName);

                }

                int tempSize = tempSet.size();

                if (tempWeight > maxWeight) {
                    maxElement = j;
                    maxSize = tempSize;
                    maxWeight = tempWeight;

                } else if (tempWeight == maxWeight && tempSize > maxSize) {
                    maxElement = j;
                    maxSize = tempSize;
                    maxWeight = tempWeight;

                }

            }
            pair = selectPrimPair.selectDegenerate(TargetSets.get(maxElement));

            if (pair != null) {
                
                printBuild.addInfoToFile(file, (primerCount + 1) + " Primer Pair: " + pair);
                
                Cover.add(TargetSets.get(maxElement));
                primerCount++;
            }
            domain.removeAll(TargetSets.get(maxElement));

        }

        return Cover;
    }

}
