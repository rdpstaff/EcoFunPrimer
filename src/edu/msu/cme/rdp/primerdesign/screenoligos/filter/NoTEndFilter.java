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
 * Implements the OligoFilter interface. NoTEndFilter
 * @author gunturus
 */
public class NoTEndFilter implements OligoBaseFilter{
    /**
     * Checks a sequence for T at the end 
     * @param oligo - oligo to be evaluated 
     * @return boolean - true means that there is not a base of T at the end
     */
        @Override
        public boolean check(Oligo oligo) {
        String seq = oligo.getSeq();
        return !seq.substring(seq.length() - 1).equals("T");
    }
}
