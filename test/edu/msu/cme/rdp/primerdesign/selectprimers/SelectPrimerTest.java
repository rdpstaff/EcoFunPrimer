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

package edu.msu.cme.rdp.primerdesign.selectprimers;

import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.Oligo;
import edu.msu.cme.rdp.primerdesign.selectprimers.primerpair.PairFactory;
import edu.msu.cme.rdp.primerdesign.selectprimers.primerpair.PrimerPair;
import edu.msu.cme.rdp.primerdesign.utils.Primer3Wrapper;
import edu.msu.cme.rdp.primerdesign.utils.comparator.OligoTmComparator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;

/**
 *
 * @author leotift
 */
public class SelectPrimerTest {
    
    
      
    public SelectPrimerTest() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void testTm() throws IOException {
        
        Oligo oligo1 = new Oligo("GCTAGCCCTAGAGATCATA");       
        Oligo oligo2 = new Oligo("GCTAGCCCTAGAGATCATA");
        Oligo oligo3 = new Oligo("GCTAGCCCTAGAGATCATA");
        Oligo oligo4 = new Oligo("GCTAGCCCTAGAGATCATA");
        Oligo oligo5 = new Oligo("GCTAGCCCTAGAGATCATA");
        Oligo oligo6 = new Oligo("GCTAGCCCTAGAGATCATA");
        Oligo oligo7 = new Oligo("GCTAGCCCTAGAGATCATA");
        Oligo oligo8 = new Oligo("GCTAGCCCTAGAGATCATA");
        
        oligo1.setOligoTm(55);
        oligo2.setOligoTm(67);
        oligo3.setOligoTm(60);
        oligo4.setOligoTm(73);
        oligo5.setOligoTm(45);
        oligo6.setOligoTm(57);
        oligo7.setOligoTm(66);
        oligo8.setOligoTm(54.5);
        
        
        List<Oligo> fwdList = new ArrayList<> ();
        fwdList.add(oligo1);
        fwdList.add(oligo2);
        fwdList.add(oligo3);
        fwdList.add(oligo4);
        
        List<Oligo> revList = new ArrayList<> ();
        revList.add(oligo5);
        revList.add(oligo6);
        revList.add(oligo7);
        revList.add(oligo8);
        
        Set<String> targetSet = new HashSet<>();
        
               
        Primer3Wrapper primer3 = new Primer3Wrapper("mac", 50.0, 1.5);
                      
        Collections.sort(fwdList, new OligoTmComparator());
        Collections.sort(revList, new OligoTmComparator());
       
        double tempDelta = 1000000; 
        double currTemp;
        List<Integer> bestCombo = new ArrayList<>();
        
        bestCombo.add(0);
        bestCombo.add(0);
        
        Set<List<Integer>> comboList = new HashSet<>();
        for (int i = 0; i < fwdList.size(); i++) {
             for (int j = 0; j < revList.size(); j++) {
                 List<Integer> combo = new ArrayList<>();
                 combo.add(i);
                 combo.add(j);
                 comboList.add(combo);
             }      
        }
        
        while (!comboList.isEmpty()) {
            
            tempDelta = 1000000;  
                    
            for (List<Integer> combo : comboList) {
                currTemp = Math.abs(fwdList.get(combo.get(0)).getTm() - revList.get(combo.get(1)).getTm());  
                if (currTemp < tempDelta) {
                    tempDelta = currTemp;
                    bestCombo = combo;
                }     
            }
            PrimerPair pair = 
                    PairFactory.createPair(fwdList.get(bestCombo.get(0)), revList.get(bestCombo.get(1)), primer3, Math.abs(fwdList.get(bestCombo.get(0)).getTm() - revList.get(bestCombo.get(1)).getTm()));
            if (pair.getHeterodimerTm() > 35.0) {
                comboList.remove(bestCombo);
                continue;
            }
            pair.setTargetSet(targetSet);
            break;
            
        }
               
       
               
        System.out.println("Temp Delta: " + tempDelta);
       
    }
    
}
