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

package edu.msu.cme.rdp.primerdesign.selectprimers.primerpair;

import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.Oligo;
import edu.msu.cme.rdp.primerdesign.utils.Primer3Wrapper;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author xingziye
 */
public class PairFactory {
    
    public static PrimerPair createPair(Oligo forward, Oligo reverse, Primer3Wrapper primer3, double tempDelta) {
        PrimerPair pair = new PrimerPair(forward, reverse, tempDelta);
        pair.setHeterodimerTm(primer3.calcHetrodimerTm(forward.getSeq(), reverse.getSeq()));
        return pair;
    }
    
    public static PrimerPair createPair(Oligo forward, Oligo reverse, Map<Oligo, Set<String>> fwdOligoTargetMap, Map<Oligo, Set<String>> revOligoTargetMap, Primer3Wrapper primer3, double tempDelta) {
        
        PrimerPair pair = new PrimerPair(forward, reverse, tempDelta);
        pair.setHeterodimerTm(primer3.calcHetrodimerTm(forward.getSeq(), reverse.getSeq() ) );
        
        Set targetSet = new HashSet(fwdOligoTargetMap.get(forward));
        targetSet.addAll(revOligoTargetMap.get(reverse));
        pair.setTargetSet(targetSet);
        
        return pair;
    }
    
}
