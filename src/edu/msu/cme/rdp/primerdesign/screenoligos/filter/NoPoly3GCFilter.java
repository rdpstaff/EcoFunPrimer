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
 * Implements OligoFilter interface. NoPoly3GCFilter
 *
 * @author gunturus
 */
public class NoPoly3GCFilter implements OligoBaseFilter {
    /**
     * Checks a sequence for 3 G or C substrings 
     * @param oligo - oligo to be evaluated 
     * @return boolean - true means that there are no poly 3 G or C substrings in the sequence
     */
        @Override
        public boolean check(Oligo oligo) {
        String seq = oligo.getSeq();
        return !seq.substring(seq.length() - 3).equals("GGG") && !seq.substring(seq.length() - 3).equals("CCC");
    }
}
