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

package edu.msu.cme.rdp.primerdesign.screenoligos.filter;

import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.Oligo;

/** Implements OligoFilter interface. GC percentage filter.
 *
 * @author gunturus
 */
public class GCContentFilter implements OligoBaseFilter {
    
    double min;
    double max;
    
    public GCContentFilter(double min, double max) {
        this.min = min;
        this.max = max;
    }
    /**
     * Checks the GC percentage in a sequence based off the min and max
     * 
     * @param oligo - oligo sequence to be evaluated
     * @return boolean - true means the percentage of GC is within min and max
     */
    @Override
        public boolean check(Oligo oligo) {
        String oligoSeq = oligo.getSeq();
        int G = oligoSeq.length() - oligoSeq.replace("G", "").length();
        int C = oligoSeq.length() - oligoSeq.replace("C", "").length();
        double percentGC = (G+C)/(double)oligoSeq.length();
        
        return percentGC >= min && percentGC <= max;
    }
        
  }
