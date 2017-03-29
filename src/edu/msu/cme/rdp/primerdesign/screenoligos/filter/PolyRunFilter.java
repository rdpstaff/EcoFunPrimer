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
import java.util.ArrayList;
import java.util.List;

/**
 *  Implements the OligoFilter interface. PolyRunFilter
 * @author gunturus
 */
public class PolyRunFilter implements OligoBaseFilter{
    int polylen;
    public PolyRunFilter(int polylen) {
        this.polylen = polylen;
    }
    /**
     * Checks for A, C, G, or T poly runs
     * @param oligo - oligo to be evaluated
     * @return boolean - true if sequence does not contain poly runs
     */
    @Override
    public boolean check(Oligo oligo) {
        String seq = oligo.getSeq();
        List<String> polys = new ArrayList<> ();
        
        polys.add(new String(new char[polylen]).replace('\0', 'A'));
        polys.add(new String(new char[polylen]).replace('\0', 'G'));
        polys.add(new String(new char[polylen]).replace('\0', 'T'));
        polys.add(new String(new char[polylen]).replace('\0', 'C'));
    
        for(String ploy : polys) {
            if(seq.contains(ploy))
                return false;
        }
        return true;
    }    
}
