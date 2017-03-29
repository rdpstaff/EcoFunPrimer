/*
 * Copyright (C) 2016 Michigan State University Board of Trustees
 *
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

import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.DegeneratePair;
import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.Oligo;
import edu.msu.cme.rdp.primerdesign.selectprimers.redundantpair.EnumerateDegeneratePair;
import java.util.Set;

/**
 *
 * @author tift
 */
public class SelectPrimerPairs {

   
    private final EnumerateDegeneratePair enumPair;
   

    public SelectPrimerPairs(EnumerateDegeneratePair enumPair) {
           this.enumPair = enumPair;
    }


    public DegeneratePair selectDegenerate(Set<String> targetSet) {
        Set<DegeneratePair> degenSet = enumPair.getPairDegenerateMap().get(targetSet);

        double minTempDelta = 100000;
        DegeneratePair finalPair = null;
        for (DegeneratePair dPair : degenSet) {

            double tempDiff = calcAveTempDiff(dPair.getFwdInputOligos(), dPair.getRevInputOligos());
            if (tempDiff < minTempDelta) {
                minTempDelta = tempDiff;
                finalPair = dPair;
            }
        }
        
        return finalPair;
    }

    private double calcAveTempDiff(Set<Oligo> fwdList, Set<Oligo> revList) {
        double fwdAve = 0.0;
        double revAve = 0.0;

        for (Oligo fwdOligo : fwdList) {
            fwdAve += fwdOligo.getTm();
        }
        fwdAve /= fwdList.size();
        for (Oligo revOligo : revList) {
            revAve += revOligo.getTm();
        }
        revAve /= revList.size();

        return Math.abs(fwdAve - revAve);

    }

}
