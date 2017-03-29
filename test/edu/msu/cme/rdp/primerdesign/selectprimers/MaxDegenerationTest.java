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
import edu.msu.cme.rdp.primerdesign.selectprimers.algorithm.MaxDegenerationAlgo;
import edu.msu.cme.rdp.primerdesign.selectprimers.algorithm.HenikoffWeight;
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
public class MaxDegenerationTest {
    
    public MaxDegenerationTest() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void test() throws IOException, CloneNotSupportedException {
        
        EnumerateOligos eo1 = new EnumerateOligos();
        Sequence seq1 = new Sequence("seq1", "", "CACGTACGTACGTACGTA");
        Sequence seq2 = new Sequence("seq2", "", "ACGTACGTACGTACGTAT");
        Sequence seq3 = new Sequence("seq3", "", "CACGTACGTACGTACGTA");
        List<Sequence> seqList = new ArrayList<> ();
        seqList.add(seq1);
        seqList.add(seq2);
        seqList.add(seq3);
        eo1.addKmers(seqList);
    }
}
//        Map<Set<String>, ArrayList<Oligo>> fwdTargetSetMap = eo1.getTargetSetMap();
//        
//        System.out.println("fwd redundant");
//        for (Set<String> targetSet : fwdTargetSetMap.keySet()) {
//            System.out.println(targetSet);
//            for (Oligo oligo : fwdTargetSetMap.get(targetSet))
//                System.out.println(oligo.getSeq());
//        }
//        
//        EnumerateOligos eo2 = new EnumerateOligos();
//        eo2.setReversed();
//        eo2.addKmers(seqList);
//        Map<Set<String>, ArrayList<Oligo>> revTargetSetMap = eo2.getTargetSetMap();
//        
//        System.out.println("rev redundant");
//        for (Set<String> targetSet : revTargetSetMap.keySet()) {
//            System.out.println(targetSet);
//            for (Oligo oligo : revTargetSetMap.get(targetSet))
//                System.out.println(oligo.getSeq());
//        }
//        
//        EnumerateDegeneratePair ep = new EnumerateDegeneratePair(eo1, eo2, 150, 200, 60);
//   //     ep.enumerateAllProductLengths();
//        
////        Map<Set<String>, Set<RedundantPair>> redundantMap = ep.getPairRedundantMap();
//        
////        System.out.println("Redundant Map");
////        for (Set<String> targetSet : redundantMap.keySet()) {
////            System.out.println(targetSet);
////            for (RedundantPair pair : redundantMap.get(targetSet)) {
////                System.out.println(pair);
////            }
////        }
////        System.out.println("My results1:");
//   //     System.out.println(MaxDegenerationAlgo.calMaxDegeneration(ep, 2, eo1.getPrimer3Wrapper(), seqList.get(0).getSeqString().length()));
//    }
//    
//    @Test
//    public void testWeighted() throws IOException, CloneNotSupportedException {
//        EnumerateOligos eo1 = new EnumerateOligos();
//        Sequence seq1 = new Sequence("seq1", "", "CACGTACGTACGTACGTAT");
//        Sequence seq2 = new Sequence("seq2", "", "ACGTACGTACGTACGTATC");
//        Sequence seq3 = new Sequence("seq3", "", "CACGTACGTACGTACGTAC");
//        List<Sequence> seqList = new ArrayList<> ();
//        seqList.add(seq1);
//        seqList.add(seq2);
//        seqList.add(seq3);
//        eo1.addKmers(seqList);
//        Map<Set<String>, ArrayList<Oligo>> fwdTargetSetMap = eo1.getTargetSetMap();
//        /*
//        System.out.println("fwd redundant");
//        for (Set<String> targetSet : fwdTargetSetMap.keySet()) {
//            System.out.println(targetSet);
//            for (Oligo oligo : fwdTargetSetMap.get(targetSet))
//                System.out.println(oligo.getSeq());
//        }*/
//        
//        EnumerateOligos eo2 = new EnumerateOligos();
//        eo2.setReversed();
//        eo2.addKmers(seqList);
//       
//        Map<Set<String>, ArrayList<Oligo>> revTargetSetMap = eo2.getTargetSetMap();
//        /*
//        System.out.println("rev redundant");
//        for (Set<String> targetSet : revTargetSetMap.keySet()) {
//            System.out.println(targetSet);
//            for (Oligo oligo : revTargetSetMap.get(targetSet))
//                System.out.println(oligo.getSeq());
//        }*/
//        
//        EnumerateDegeneratePair ep = new EnumerateDegeneratePair(eo1, eo2, 150, 200, 60);
//        
////        ep.enumerateSlidingScale();
////        
////        Map<Set<String>, Set<RedundantPair>> redundantMap = ep.getPairRedundantMap();
////        
////        System.out.println("Redundant Map");
////        for (Set<String> targetSet : redundantMap.keySet()) {
////            System.out.println(targetSet);
////            for (RedundantPair pair : redundantMap.get(targetSet)) {
////                System.out.println(pair);
////            }
//        }
////        List<Sequence> seqs = new ArrayList<>();
////        seqs.add(seq3);
////        seqs.add(seq2);
////        seqs.add(seq1);
////        Map weight = HenikoffWeight.calSequenceWeight(seqs);
////        System.out.println(weight);
////        System.out.println("My results:");
////       // System.out.println(MaxDegenerationAlgo.calWeightDegeneration(ep, 2, weight, eo1.getPrimer3Wrapper(), seqList.get(0).getSeqString().length()));
    
    

