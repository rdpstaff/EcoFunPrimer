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
package edu.msu.cme.rdp.primerdesign.screenoligos.oligo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author leotift
 */
public class DegenerateCharTable {

    private Map<DegenerateArray, Character> degenerates;
    private Map<Character, Integer> degenerateValues;
    private boolean tableLoaded = false;

    public Map<DegenerateArray, Character> getdegenerates() {
        return this.degenerates;
    }

    public Map<Character, Integer> getDegenValues() {
        return this.degenerateValues;
    }

    public DegenerateCharTable() {
        if (!tableLoaded) {
            this.loadTable();
        }
    }

    public void loadTable() {
        this.degenerates = new HashMap<>();
        this.degenerateValues = new HashMap<>();

        char[] W = {'A', 'T'};
        char[] S = {'C', 'G'};
        char[] K = {'G', 'T'};
        char[] M = {'A', 'C'};
        char[] R = {'A', 'G'};
        char[] Y = {'C', 'T'};
        char[] V = {'A', 'C', 'G'};
        char[] H = {'A', 'C', 'T'};
        char[] D = {'A', 'G', 'T'};
        char[] B = {'C', 'G', 'T'};
        char[] N = {'A', 'C', 'G', 'T'};

        degenerates.put(new DegenerateArray(W), 'W');
        degenerates.put(new DegenerateArray(S), 'S');
        degenerates.put(new DegenerateArray(K), 'K');
        degenerates.put(new DegenerateArray(M), 'M');
        degenerates.put(new DegenerateArray(R), 'R');
        degenerates.put(new DegenerateArray(Y), 'Y');
        degenerates.put(new DegenerateArray(V), 'V');
        degenerates.put(new DegenerateArray(H), 'H');
        degenerates.put(new DegenerateArray(D), 'D');
        degenerates.put(new DegenerateArray(B), 'B');
        degenerates.put(new DegenerateArray(N), 'N');

        degenerateValues.put('A', 1);
        degenerateValues.put('C', 1);
        degenerateValues.put('G', 1);
        degenerateValues.put('T', 1);
        degenerateValues.put('W', 2);
        degenerateValues.put('S', 2);
        degenerateValues.put('K', 2);
        degenerateValues.put('M', 2);
        degenerateValues.put('R', 2);
        degenerateValues.put('Y', 2);
        degenerateValues.put('V', 3);
        degenerateValues.put('H', 3);
        degenerateValues.put('D', 3);
        degenerateValues.put('B', 3);
        degenerateValues.put('N', 4);

        this.tableLoaded = true;

    }

    public class DegenerateArray {

        public char[] theArray;
        
        public DegenerateArray(char[] characterArray) {           
            this.theArray = characterArray;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null || !(this.getClass()).isAssignableFrom(obj.getClass())) {
                throw new RuntimeException("Object not of type DegenerateArray");
            }
            return Arrays.equals(this.theArray, ((DegenerateArray) obj).theArray);
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 53 * hash + Arrays.hashCode(this.theArray);
            return hash;
        }
        

    }
}
