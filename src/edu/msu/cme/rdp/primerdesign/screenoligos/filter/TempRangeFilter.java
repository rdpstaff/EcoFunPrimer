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

/**
 * Implements the OligoFilter interface
 *
 * @author gunturus
 */
public class TempRangeFilter implements OligoTempFilter {
    
    double min;
    double max;
    
    public TempRangeFilter(double min, double max) {
        this.min = min;
        this.max = max;
    }
     /**
     * Checks an Oligo TempRange based off the min and max
     * 
     * @param oligo - oligo to be evaluated
     * @return boolean - true means the melting Temp is between the min and max
     */
    @Override
    public boolean check(Oligo oligo) {
        return oligo.getTm() >= min & oligo.getTm() <= max;
    }

    
//    public boolean check(String seq) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
}
