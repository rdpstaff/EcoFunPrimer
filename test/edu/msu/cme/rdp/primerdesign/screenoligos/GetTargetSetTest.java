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

package edu.msu.cme.rdp.primerdesign.screenoligos;

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
public class GetTargetSetTest {
    
    public GetTargetSetTest() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void test() throws IOException {
        EnumerateOligos eo = new EnumerateOligos();
        Sequence seq1 = new Sequence("seq1", "" , "CACGTACGTACGTACGTACT");
        Sequence seq2 = new Sequence("seq2", "" , "ACGTACGTACGTACGTACT");
        List<Sequence> seqList = new ArrayList<> ();
        seqList.add(seq1);
        seqList.add(seq2);
        eo.addKmers(seqList);
        
 //       Map<Set<String>, ArrayList<Oligo>> targetSetMap = eo.getTargetSetMap();
        
        System.out.println("PosMap");
        for (int start : eo.getKmerPosMap().keySet()) {
            if (!eo.getKmerPosMap().get(start).isEmpty())
                System.out.println(start);
            for (Oligo oligo : eo.getKmerPosMap().get(start)) {
                System.out.println(oligo.getSeq());
            }
        }
        
    }
    
}
        
//        System.out.println("TargetMap");
//        for (Oligo oligo : eo.getOligoTargetMap().keySet()) {
//            System.out.println(oligo.getSeq());
//            for (String seq : eo.getOligoTargetMap().get(oligo)) {
//                System.out.println(seq);
//            }
//    //    }
        
    //    System.out.println("redundant");
//  //      for (Set<String> targetSet : targetSetMap.keySet()) {
//            for (String seq : targetSet)
//                System.out.print(seq + " ");
//            System.out.println();
//            for (Oligo oligo : targetSetMap.get(targetSet))
//                System.out.println(oligo.getSeq());
//        }
//        
//        targetSetMap = eo.getTargetSetMap(0, targetSetMap);
//        System.out.println("redundant by position");
//        for (Set<String> targetSet : targetSetMap.keySet()) {
//            for (String seq : targetSet)
//                System.out.print(seq + " ");
//            System.out.println();
//            for (Oligo oligo : targetSetMap.get(targetSet))
//                System.out.println(oligo.getSeq());
//        }
//    }
//}
