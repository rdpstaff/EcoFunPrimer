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

//package edu.msu.cme.rdp.primerdesign.deadcode;
//
//import edu.msu.cme.rdp.primerdesign.selectprimers.primerpair.PrimerPair;
//import edu.msu.cme.rdp.primerdesign.deadcode.EnumeratePrimerPairs;
//import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.EnumerateOligos;
//import edu.msu.cme.rdp.primerdesign.screenoligos.filter.NoPoly3GCFilter;
//import edu.msu.cme.rdp.primerdesign.screenoligos.filter.NoTEndFilter;
//import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.Oligo;
//import edu.msu.cme.rdp.primerdesign.screenoligos.filter.OligoFilter;
//import edu.msu.cme.rdp.primerdesign.screenoligos.filter.PolyRunFilter;
//import edu.msu.cme.rdp.primerdesign.utils.Primer3Wrapper;
//import edu.msu.cme.rdp.readseq.readers.Sequence;
//import java.io.IOException;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
////import static org.junit.Assert.assertEquals;
////import org.junit.Test;

/**
 *
 * @author xingziye
 */
public class EnumeratePairTest {
    
//    public EnumeratePairTest() {
//    }
//
//    // TODO add test methods here.
//    // The methods must be annotated with annotation @Test. For example:
//    //
//    // @Test
//    // public void hello() {}
//    @Test
//    public void testEnumeratePair() throws IOException {
//        
//        Primer3Wrapper primer3 = new Primer3Wrapper();
//        
//        Sequence seq1 = new Sequence("seq1", "" , "CACGTACGTACGTACGTACTTGCTAGCTACAGGACTACGTTGCAACGT"); //revCom: AGTACGTACGTACGTACGTG
//        Sequence seq2 = new Sequence("seq2", "" ,  "ACGTACGTACGTACGTACTGCTAGCTACAGGACTACGTTGCAACGT"); //revCom: AGTACGTACGTACGTACGT
//        
////        EnumerateOligos eo = new EnumerateOligos();
////        eo.addKmers(seq1);
////        eo.addKmers(seq2);
////        //eo.postFiltering(tempMin, tempMax, hairMax, homoMax);
////        eo.postFiltering(58, 62, 35, 35);
////        eo.discardRedundant();
////        
////        
////        EnumerateOligos eo2 = new EnumerateOligos();
////        eo2.setReversed();
////        eo2.addKmers(seq1);
////        eo2.addKmers(seq2);
////        eo2.postFiltering(58, 62, 35, 35);
////        eo2.discardRedundant();
////        
//         List<Integer> fwdPositions = new ArrayList();
//        for(int i = 1; i < 23; i++) {
//            fwdPositions.add(i);
//        }
//        
//        List<Integer> revPositions = new ArrayList();
//        for (int i = 1; i < 29; i++) {
//            revPositions.add(i);
//        }
//        
//        List<Integer> Sizes = new ArrayList<> ();
//        int[] size = {20,21,22,23,24,25,26,27,28};
//        for(int s : size) {
//            Sizes.add(s);
//        }
//        
//        List<OligoFilter> filters = new ArrayList();
//        filters.add(new NoTEndFilter());
//        filters.add(new NoPoly3GCFilter());
//        filters.add(new PolyRunFilter(4));
//        
//             
//       
//        // Forward Enumerate Oligos 
//        EnumerateOligos eoFwd = new EnumerateOligos(primer3, Sizes, fwdPositions, filters, false);
//        
//            
//        eoFwd.addKmers(seq1);
//        eoFwd.addKmers(seq2);
//        
//        eoFwd.postFiltering(58, 62, 35, 35);
//        eoFwd.discardRedundant();
//        
//         // Reverse Enumerate Oligos 
//        EnumerateOligos eoRev = new EnumerateOligos(primer3, Sizes, revPositions, filters, true);
//        
//        eoRev.addKmers(seq1);
//        eoRev.addKmers(seq2);
//       
//        eoRev.postFiltering(58, 62, 35, 35);
//        eoRev.discardRedundant();
//       
//        System.out.println("PosMap");
//        for (int start : eoFwd.getKmerPosMap().keySet()) {
//            if (!eoFwd.getKmerPosMap().get(start).isEmpty())
//                System.out.println(start);
//            for (Oligo oligo : eoFwd.getKmerPosMap().get(start)) {
//                System.out.println(oligo.getSeq());
//            }
//        }
//        
//        System.out.println("TargetMap");
//        for (Oligo oligo : eoFwd.getOligoTargetMap().keySet()) {
//            System.out.println(oligo.getSeq());
//            for (String seq : eoFwd.getOligoTargetMap().get(oligo)) {
//                System.out.println(seq);
//            }
//        }
//        
//        System.out.println();
//        System.out.println("PosMap");
//        for (int start : eoRev.getKmerPosMap().keySet()) {
//            if (!eoRev.getKmerPosMap().get(start).isEmpty())
//                System.out.println(start);
//            for (Oligo oligo : eoRev.getKmerPosMap().get(start)) {
//                System.out.println(oligo.getSeq());
//            }
//        }
//        
//        System.out.println("TargetMap");
//        for (Oligo oligo : eoRev.getOligoTargetMap().keySet()) {
//            System.out.println(oligo.getSeq());
//            for (String seq : eoRev.getOligoTargetMap().get(oligo)) {
//                System.out.println(seq);
//            }
//        }
//        
//      System.out.println("Enums results:"); 
//        EnumeratePrimerPairs ep = new EnumeratePrimerPairs(eoFwd, eoRev, primer3);
//
//        Set<PrimerPair> myPairSet = ep.enumeratePair(fwdPositions, revPositions, 1, 99);
//        for (PrimerPair myPrimerPair : myPairSet) {
//            
//            System.out.println(myPrimerPair.toString());
//        }
//        
//             
//                
//        Oligo o1 = new Oligo("ACGTACGTACGTACGTACT");
//        Oligo o2 = new Oligo("CACGTACGTACGTACGTA");
//        Oligo o3 = new Oligo("CGTACGTACGTACGTACT");
//        PrimerPair pair1 = new PrimerPair(o1, o1);
//        PrimerPair pair2 = new PrimerPair(o2, o3);
//        Set<PrimerPair> expectedSet = new HashSet<>();
//        expectedSet.add(pair1);
//        expectedSet.add(pair2);
//        for (PrimerPair pair : ep.getAllPairs()) {
//            System.out.println(pair.toString());
//        }
//        
//        assertEquals(expectedSet, ep.getAllPairs());
//    }
}
