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

package edu.msu.cme.rdp.primerdesign.selectprimers;

import edu.msu.cme.rdp.primerdesign.selectprimers.redundantpair.EnumerateDegeneratePair;
import edu.msu.cme.rdp.primerdesign.selectprimers.redundantpair.RedundantPair;
import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.EnumerateOligos;
import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.Oligo;
import edu.msu.cme.rdp.readseq.readers.Sequence;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Test;

/**
 *
 * @author xingziye
 */
public class RedundantPairTest {
    
    public RedundantPairTest() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void test() throws IOException, CloneNotSupportedException {
        
//        EnumerateOligos eo1 = new EnumerateOligos();
//        Sequence seq1 = new Sequence("seq1", "" , "CACGTACGTACGTACGTACT");
//        Sequence seq2 = new Sequence("seq2", "" , "ACGTACGTACGTACGTACT");
//        List<Sequence> seqList = new ArrayList<> ();
//        seqList.add(seq1);
//        seqList.add(seq2);
//        eo1.addKmers(seqList);
//        Map<Set<String>, ArrayList<Oligo>> fwdTargetSetMap = eo1.getTargetSetMap();
//        
//        System.out.println("redundant");
//        for (Set<String> targetSet : fwdTargetSetMap.keySet()) {
//            System.out.println(targetSet);
//            for (Oligo oligo : fwdTargetSetMap.get(targetSet))
//                System.out.println(oligo.getSeq());
//        }
//        
//        EnumerateOligos eo2 = new EnumerateOligos();
//        eo2.setReversed();
//        eo2.addKmers(seqList);
//        
//        Map<Set<String>, ArrayList<Oligo>> revTargetSetMap = eo2.getTargetSetMap();
//        
//        System.out.println("redundant");
//        for (Set<String> targetSet : revTargetSetMap.keySet()) {
//            for (String seq : targetSet)
//                System.out.print(seq + " ");
//            System.out.println();
//            for (Oligo oligo : revTargetSetMap.get(targetSet))
//                System.out.println(oligo.getSeq());
//        }
//        
//        EnumerateDegeneratePair ep = new EnumerateDegeneratePair(eo1, eo2, 150, 200, 60);
//   //     ep.enumerateSlidingScale();
//        
//     //   Map<Set<String>, Set<RedundantPair>> redundantMap = ep.getPairRedundantMap();
//               
//        System.out.println("Redundant Map");
//        for (Set<String> targetSet : redundantMap.keySet()) {
//                       
//            for (String seq : targetSet)
//                System.out.print(seq + " ");
//            System.out.println();
//            for (RedundantPair pair : redundantMap.get(targetSet)) {
//                System.out.println(pair);
//            }
//            
//          
//        }        
                  
      
    }
}
