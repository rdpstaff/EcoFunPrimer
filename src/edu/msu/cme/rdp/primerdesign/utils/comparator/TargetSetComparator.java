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
import java.util.Set;

/**
 *
 * @author xingziye
 */
public class TargetSetComparator implements Comparator<Set<String>> {

    @Override
    public int compare(Set<String> o1, Set<String> o2) {
        Integer int1 = o1.size();
        Integer int2 = o2.size();
        if (int1.equals(int2)) {
            int1 = o1.hashCode();
            int2 = o2.hashCode();
        }
        return int2.compareTo(int1);
    }
    
}
