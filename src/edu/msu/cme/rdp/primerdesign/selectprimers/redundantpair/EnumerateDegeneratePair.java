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

import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.DegenerateCharTable;
import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.DegeneratePair;
import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.EnumerateOligos;
import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.Oligo;
import edu.msu.cme.rdp.primerdesign.selectprimers.algorithm.MaxDegenerationAlgo;
import edu.msu.cme.rdp.primerdesign.selectprimers.algorithm.PSSM;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author xingziye, tift
 */
public class EnumerateDegeneratePair {

    private EnumerateOligos eoFwd;
    private EnumerateOligos eoRev;

    private Map<Set<String>, Set<DegeneratePair>> pairDegenerateMap;
    private Set<DegeneratePair> pairDegenerateSet;
    private int productLengthMin;
    private int productLengthMax;
    private int maxDegenNum;

    //For degen char values - new for this variable
    private DegenerateCharTable table;

    public EnumerateOligos getEOFwd() {
        return this.eoFwd;
    }

    public EnumerateOligos getEORev() {
        return this.eoRev;
    }

    public int getProductLengthMin() {
        return this.productLengthMin;
    }

    public int getProductLengthMax() {
        return this.productLengthMax;
    }

    public Map<Set<String>, Set<DegeneratePair>> getPairDegenerateMap() {
        return this.pairDegenerateMap;
    }

    public Set<DegeneratePair> getPairDegenerateSet() {
        return this.pairDegenerateSet;
    }

    public void setPairDegenerateMap(Map<Set<String>, Set<DegeneratePair>> degenMap) {
        this.pairDegenerateMap = new HashMap<>(degenMap);
    }

    public void setE0Fwd(EnumerateOligos eoFwd) {
        this.eoFwd = eoFwd;
    }

    public void setE0Rev(EnumerateOligos eoRev) {
        this.eoRev = eoRev;
    }

    public int getMaxNumPrimer() {
        return this.maxDegenNum;
    }

    public void setMaxNumPrimer(int maxPrimers) {
        this.maxDegenNum = maxPrimers;
    }

    public void setProductLengthMin(int min) {
        this.productLengthMin = min;
    }

    public void setProductLengthMax(int max) {
        this.productLengthMax = max;
    }

    public EnumerateDegeneratePair(EnumerateOligos eo1, EnumerateOligos eo2, int prodLengthMin, int prodLengthMax, int maxDegen) {
        eoFwd = eo1;
        eoRev = eo2;
        productLengthMin = prodLengthMin;
        productLengthMax = prodLengthMax;
        pairDegenerateMap = new HashMap<>();
        pairDegenerateSet = new HashSet<>();
        maxDegenNum = maxDegen;
        table = new DegenerateCharTable();

    }

    public void enumerateAllProductLengthsWDegeneracy() throws IOException, CloneNotSupportedException {

//        Map<Set<String>, ArrayList<Oligo>> fwdTargetSetMap = eoFwd.getTargetSetMap();
//        Map<Set<String>, ArrayList<Oligo>> revTargetSetMap = eoRev.getTargetSetMap();
        
        

        List<Integer> fwdPosList = eoFwd.getPositions();
        List<Integer> revPosList = eoRev.getPositions();
        
        int seqLength = eoFwd.getSequenceLength();

        for (Integer fwdPos : fwdPosList) {
            
            Map<Oligo, Set<String>> fwdTargetSetMap = eoFwd.getTargetSetMap(fwdPos);
            
            int revMin = seqLength - productLengthMax - fwdPos;
            int revMax = seqLength - productLengthMin - fwdPos;

            if (revMax > (seqLength - 1)) {
                revMax = seqLength - 1;
            }
            if (revMin < 0) {
                revMin = 0;
            }
            
            for (Integer revPos : revPosList) {
                
                if (revPos >= revMin && revPos <= revMax) {
                    Map<Oligo, Set<String>> revTargetSetMap = eoRev.getTargetSetMap(revPos);
                    this.buildDegenerateSet(fwdTargetSetMap, revTargetSetMap, fwdPos, revPos);
                }
                            
            }
        }

    }

    public void enumerateSlidingScaleWDegeneracy() throws IOException, CloneNotSupportedException {

        List<Integer> fwdPositions = eoFwd.getPositions();

        int seqLength = eoFwd.getSequenceLength();

        for (Integer fwdPos : fwdPositions) {

            int revMin = seqLength - productLengthMax - fwdPos;
            int revMax = seqLength - productLengthMin - fwdPos;

            if (revMax > (seqLength - 1)) {
                revMax = seqLength - 1;
            }
            if (revMin < 0) {
                revMin = 0;
            }

            Map<Oligo, Set<String>> fwdTargetSetMap = eoFwd.getTargetSetMap(fwdPos);

            if (fwdTargetSetMap.isEmpty()) {
                continue;
            }

            for (int revPos = revMin; revPos < revMax; revPos++) {

                Map<Oligo, Set<String>> revTargetSetMap = eoRev.getTargetSetMap(revPos);

                if (revTargetSetMap.isEmpty()) {
                    continue;
                }
                this.buildDegenerateSet(fwdTargetSetMap, revTargetSetMap, fwdPos, revPos);
            }

        }

    }

    private void buildDegenerateSet(Map<Oligo, Set<String>> fwdTargetSetMap, Map<Oligo, Set<String>> revTargetSetMap,
            int fwdPos, int revPos) throws IOException {

        Set<String> fwdTargetSet = new HashSet<>();
        Set<String> revTargetSet = new HashSet<>();

        for (Set<String> fwdSet : fwdTargetSetMap.values()) {
            fwdTargetSet.addAll(fwdSet);
        }

        for (Set<String> revSet : revTargetSetMap.values()) {
            revTargetSet.addAll(revSet);

        }
        Set<String> targetSet = new HashSet<>(fwdTargetSet);
        targetSet.retainAll(revTargetSet);

        if (targetSet.isEmpty()) {
            return;
        }

        DegeneratePair degenPair = new DegeneratePair(fwdTargetSetMap, revTargetSetMap, new HashSet<Oligo>(), new HashSet<Oligo>(), targetSet, this.maxDegenNum, fwdPos, revPos, this.table);
        degenPair.buildDegenOligos();
        pairDegenerateSet.add(degenPair);

    }

    public void selectDegeneratePrimers(Set<String> targetsHit) throws IOException {

        if (!pairDegenerateMap.isEmpty()) {
            pairDegenerateMap.clear();
        }

        for (DegeneratePair pair : pairDegenerateSet) {

            if (!targetsHit.isEmpty()) {
                Set<String> updatedTargetSet = new HashSet<>(pair.getTargetSet());
                updatedTargetSet.removeAll(targetsHit);
                if (updatedTargetSet.isEmpty()) {
                    continue;
                }
                pair.setTargetSet(new HashSet<>(updatedTargetSet));
            }

            Set<String> uncoveredTargets = pair.getTargetSet();
            Set<String> overlapDomain = new HashSet<>(uncoveredTargets);

            Map<Set<String>, ArrayList<Oligo>> fwdTargetMap = new HashMap<>();
            Map<Set<String>, ArrayList<Oligo>> revTargetMap = new HashMap<>();

            for (Oligo oligo : pair.getFwdTargetMap().keySet()) {

                Set<String> targetSet = pair.getFwdTargetMap().get(oligo);
                if (targetSet.isEmpty()) {
                    continue;
                }
                if (!fwdTargetMap.containsKey(targetSet)) {
                    fwdTargetMap.put(targetSet, new ArrayList<Oligo>());
                }

                ArrayList<Oligo> fwdOligos = fwdTargetMap.get(targetSet);
                fwdOligos.add(oligo);
                fwdTargetMap.put(targetSet, fwdOligos);

            }

            for (Oligo oligo : pair.getRevTargetMap().keySet()) {

                Set<String> targetSet = pair.getRevTargetMap().get(oligo);
                if (targetSet.isEmpty()) {
                    continue;
                }
                if (!revTargetMap.containsKey(targetSet)) {
                    revTargetMap.put(targetSet, new ArrayList<Oligo>());
                }

                ArrayList<Oligo> revOligos = revTargetMap.get(targetSet);
                revOligos.add(oligo);
                revTargetMap.put(targetSet, revOligos);

            }

            Set<Oligo> finalFwdOligos = new HashSet<>();
            Set<Oligo> finalRevOligos = new HashSet<>();

            int degenNum = 0;
            while ((overlapDomain.size()) != 0 && degenNum < pair.getMaxNumPrimer()) {
                ArrayList<Oligo> fwdOligos = new ArrayList<>();
                ArrayList<Oligo> revOligos = new ArrayList<>();
                int maxSize = 0;
                Set<String> targetsCovered = new HashSet<>();

                for (Set<String> fwdSet : fwdTargetMap.keySet()) {

                    try {

                        Set<String> fwdTempSet = new HashSet<>(fwdSet);

                        for (Set<String> revSet : revTargetMap.keySet()) {

                            Set<String> revTempSet = new HashSet<>(revSet);
                            Set<String> tempSet = new HashSet<>(fwdTempSet);
                            tempSet.retainAll(revTempSet);
                            tempSet.retainAll(overlapDomain);

                            if (tempSet.isEmpty()) {
                                continue;
                            }

                            int tempSize = tempSet.size();

                            if (tempSize > maxSize) {
                                fwdOligos = fwdTargetMap.get(fwdSet);
                                revOligos = revTargetMap.get(revSet);
                                maxSize = tempSize;
                                targetsCovered = tempSet;

                            }

                        }

                    } catch (Exception e) {

                    }
                }

                PSSM fwdMatrixObj = new PSSM(fwdOligos);
                fwdMatrixObj.buildFrequencyMatrix();
                fwdMatrixObj.buildWeightMatrix();
                double topOligoSum = 0.0;
                Oligo topOligo = new Oligo("");
                for (Oligo ol : fwdOligos) {
                    double tempSum = fwdMatrixObj.getSum(ol);
                    if (tempSum > topOligoSum) {
                        topOligo = ol;
                    }
                }
                finalFwdOligos.add(topOligo);

                PSSM revMatrixObj = new PSSM(revOligos);
                revMatrixObj.buildFrequencyMatrix();
                revMatrixObj.buildWeightMatrix();
                double topRevOligoSum = 0.0;
                Oligo topRevOligo = new Oligo("");
                for (Oligo ol : revOligos) {
                    double tempSum = revMatrixObj.getSum(ol);
                    if (tempSum > topRevOligoSum) {
                        topRevOligo = ol;
                    }
                }
                finalRevOligos.add(topRevOligo);

                overlapDomain.removeAll(targetsCovered);
                degenNum++;

            }

            uncoveredTargets.removeAll(overlapDomain);
            pair.setFwdInputOligos(new HashSet<>(finalFwdOligos));
            pair.setRevInputOligos(new HashSet<>(finalRevOligos));
            pair.setTargetSet(uncoveredTargets);

            if (!pairDegenerateMap.containsKey(uncoveredTargets)) {
                pairDegenerateMap.put(uncoveredTargets, new HashSet<DegeneratePair>());
            }
            Set<DegeneratePair> degenSet = pairDegenerateMap.get(uncoveredTargets);
            degenSet.add(pair);
            pairDegenerateMap.put(uncoveredTargets, degenSet);

        }

    }

}
