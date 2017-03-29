///*
// * Copyright (C) 2015 xingziye
// *
// * This program is free software: you can redistribute it and/or modify
// * it under the terms of the GNU General Public License as published by
// * the Free Software Foundation, either version 3 of the License, or
// * (at your option) any later version.
// *
// * This program is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// * GNU General Public License for more details.
// *
// * You should have received a copy of the GNU General Public License
// * along with this program.  If not, see <http://www.gnu.org/licenses/>.
// */
//
//package edu.msu.cme.rdp.primerdesign.screenoligos;
//
//import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.EnumerateOligos;
//import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.Oligo;
//import edu.msu.cme.rdp.readseq.readers.Sequence;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import static org.junit.Assert.assertEquals;
//import org.junit.Test;
//
///**
// *
// * @author xingziye
// */
//public class DiscardRedundantTest {
//    
//    public DiscardRedundantTest() {
//    }
//
//    // TODO add test methods here.
//    // The methods must be annotated with annotation @Test. For example:
//    //
//    // @Test
//    // public void hello() {}
//    @Test
//    public void testRedundant() throws IOException {
//        
//        EnumerateOligos eo = new EnumerateOligos();
//        Sequence seq1 = new Sequence("seq1", "" , "CACGTACGTACGTACGTACT");
//        Sequence seq2 = new Sequence("seq2", "" , "ACGTACGTACGTACGTACT");
//        List<Sequence> seqList = new ArrayList<> ();
//        seqList.add(seq1);
//        seqList.add(seq2);
//        eo.addKmers(seqList);
//        
//        //post filtering is causing the maps to clear...7/15/15
//        //eo.postFiltering(58, 62, 35, 35);
//        Map<Integer, Set<Oligo>> unique = eo.discardRedundant();
//        
//        
//        System.out.println("PosMap");
//        for (int start : eo.getKmerPosMap().keySet()) {
//            if (!eo.getKmerPosMap().get(start).isEmpty())
//                System.out.println(start);
//            for (Oligo oligo : eo.getKmerPosMap().get(start)) {
//                System.out.println(oligo.getSeq());
//            }
//        }
//        
//        System.out.println("TargetMap");
//        for (Oligo oligo : eo.getOligoTargetMap().keySet()) {
//            System.out.println(oligo.getSeq());
//            for (String seq : eo.getOligoTargetMap().get(oligo)) {
//                System.out.println(seq);
//            }
//        }
//        
//        System.out.println("Unique");
//        for (int start : unique.keySet()) {
//            if (!unique.get(start).isEmpty())
//                System.out.println(start);
//            for (Oligo oligo : unique.get(start)) {
//                System.out.println(oligo.getSeq());
//            }
//        }
//        
//        
//        Oligo oligo1 = new Oligo("CACGTACGTACGTACGTAC");
//        Oligo oligo2 = new Oligo("CACGTACGTACGTACGTA");
//        Oligo oligo3 = new Oligo("ACGTACGTACGTACGTAC");
//        
//        Map<Oligo, Set<String>> expectedTargetMap = new HashMap<>();
//        Set<String> targetSet = new HashSet<>();
//        
//        targetSet.add("seq1");
//        targetSet.add("seq2");
//        expectedTargetMap.put(oligo3, targetSet);
//        
//        //assertEquals(expectedTargetMap.get(targetSet), eo.getOligoTargetMap().get(targetSet));
//        
//        Oligo oligo4 = new Oligo("CACGTACGTACGTACGTAC");
//        Oligo oligo5 = new Oligo("CACGTACGTACGTACGTA");
//        Oligo oligo6 = new Oligo("ACGTACGTACGTACGTAC");
//        
//        Map<Oligo, Set<String>> myExpectedTargetMap = new HashMap<>();
//        Set<String> myTargetSet = new HashSet<>();
//        
//        myTargetSet.add("seq1");
//        myTargetSet.add("seq2");
//        myExpectedTargetMap.put(oligo6, myTargetSet);
//        
//        assertEquals(myExpectedTargetMap.get(myTargetSet), eo.getOligoTargetMap().get(myTargetSet));
//        
//        
//        
//        Map< Set<String>, List<Oligo>> expectedRedundant = new HashMap<>();
//        Set<String> keySet = new HashSet<>();
//        List<Oligo> valueList = new ArrayList<>();
//        
//        keySet.add("seq1");
//        valueList.add(oligo1);
//        valueList.add(oligo2);
//        expectedRedundant.put(keySet, valueList);
//        
//        //assertEquals(expectedRedundant.get(keySet), eo.getTargetSetMap().get(keySet));
//        
//    }
//}
