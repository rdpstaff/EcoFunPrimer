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
package edu.msu.cme.rdp.primerdesign.selectprimers.algorithm;

import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.Oligo;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author leotift
 */
public class PSSM {

    private Map<Integer, Map<Character, Double>> frequencyMatrix;
    private Map<Integer, Map<Character, Double>> weightMatrix;
    private List<Oligo> inputOligos;
    private int maxLength;

    public Map<Integer, Map<Character, Double>> getFreqMatrix() {
        return frequencyMatrix;
    }

    public Map<Integer, Map<Character, Double>> getWeightMatrix() {
        return weightMatrix;
    }

    /**
     *
     * @param oligos
     * @throws IOException
     */
    public PSSM(List<Oligo> oligos) throws IOException {

        this.inputOligos = oligos;
        this.frequencyMatrix = new HashMap<>();
        this.weightMatrix = new HashMap<>();

        this.maxLength = 0;
        int seqNum = this.inputOligos.size();

        for (int i = 0; i < seqNum; i++) {
            int tempLength = this.inputOligos.get(i).getSeq().length();
            if (tempLength > this.maxLength) {
                this.maxLength = tempLength;
            }
        }
    }

    public void buildFrequencyMatrix() {

        int seqNum = this.inputOligos.size();

        for (int pos = 0; pos < maxLength; pos++) {

            char[] base = new char[seqNum];

            Map<Character, Integer> distribution = new HashMap<>();

            distribution.put('A', 0);
            distribution.put('C', 0);
            distribution.put('G', 0);
            distribution.put('T', 0);

            for (int i = 0; i < seqNum; i++) {

                if (this.inputOligos.get(i).getSeq().length() < (pos + 1)) {
                    continue;
                }

                base[i] = this.inputOligos.get(i).getSeq().charAt(pos);

                if (base[i] == '-' || base[i] == '.') {
                    continue;
                }

                base[i] = Character.toUpperCase(base[i]);

                if (distribution.containsKey(base[i])) {
                    int count = distribution.get(base[i]) + 1;
                    distribution.put(base[i], count);
                } else {
                    distribution.put(base[i], 1);
                }
            }
            double totalNucsAtPos = 0.0;
            for (Character nuc : distribution.keySet()) {
                totalNucsAtPos += distribution.get(nuc);
            }
            Map<Character, Double> tempDist = new HashMap<>();
            for (Character nuc : distribution.keySet()) {
                tempDist.put(nuc, (distribution.get(nuc) / totalNucsAtPos));
            }

            this.frequencyMatrix.put(pos, tempDist);

        }

    }

    public void buildWeightMatrix() {

        for (int position = 0; position < this.maxLength; position++) {

            Map<Character, Double> tempDist = new HashMap<>();

            if (this.frequencyMatrix.get(position).get('A') == 0.0) {
                tempDist.put('A', 0.0);
            } else if (this.frequencyMatrix.get(position).get('A') == 1.0) {
                tempDist.put('A', 1.0);
            } else {
                Double logLikelihood = (Math.log10(this.frequencyMatrix.get(position).get('A') / 0.25) / Math.log10(2));
                tempDist.put('A', logLikelihood);
            }

            if (this.frequencyMatrix.get(position).get('C') == 0.0) {
                tempDist.put('C', 0.0);
            } else if (this.frequencyMatrix.get(position).get('C') == 1.0) {
                tempDist.put('C', 1.0);
            } else {
                Double logLikelihood = (Math.log10(this.frequencyMatrix.get(position).get('C') / 0.25) / Math.log10(2));
                tempDist.put('C', logLikelihood);
            }

            if (this.frequencyMatrix.get(position).get('G') == 0.0) {
                tempDist.put('G', 0.0);
            } else if (this.frequencyMatrix.get(position).get('G') == 1.0) {
                tempDist.put('G', 1.0);
            } else {
                Double logLikelihood = (Math.log10(this.frequencyMatrix.get(position).get('G') / 0.25) / Math.log10(2));
                tempDist.put('G', logLikelihood);
            }

            if (this.frequencyMatrix.get(position).get('T') == 0.0) {
                tempDist.put('T', 0.0);
            } else if (this.frequencyMatrix.get(position).get('T') == 1.0) {
                tempDist.put('T', 1.0);
            } else {
                Double logLikelihood = (Math.log10(this.frequencyMatrix.get(position).get('T') / 0.25) / Math.log10(2));
                tempDist.put('T', logLikelihood);
            }

            this.weightMatrix.put(position, tempDist);
        }
    }

    public Double getSum(Oligo oligo) {
        Double sum = 0.0;
        String seq = oligo.getSeq();
        char[] characters = seq.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            
            sum += this.weightMatrix.get(i).get(characters[i]);

        }
        return sum;

    }

}
