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

package edu.msu.cme.rdp.primerdesign.utils.comparator;

import java.util.Comparator;
import java.util.Map;

/**
 *
 * @author xingziye
 */
public class SeqWeightComparator implements Comparator<String> {
    
    Map<String, Double> base;
    public SeqWeightComparator(Map<String, Double> base) {
        this.base = base;
    }

    @Override
    public int compare(String o1, String o2) {
        if (base.get(o1) > base.get(o2)) {
            return -1;
        } else {
            return 1;
        }
    }
    
}
