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
//import edu.msu.cme.rdp.primerdesign.deadcode.MaxCoverage;
//import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.EnumerateOligos;
//import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.Oligo;
//import edu.msu.cme.rdp.readseq.readers.Sequence;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
///**
// *
// * @author xingziye
// */
//public class MaxCoverageTest {
//    
//    public MaxCoverageTest() {
//    }
//
//    // TODO add test methods here.
//    // The methods must be annotated with annotation @Test. For example:
//    //
//    // @Test
//    // public void hello() {}
//    
//    @Test
//    public void testMaxCoverge() throws IOException {
//        
//        EnumerateOligos eo = new EnumerateOligos();
//        Sequence seq1 = new Sequence("seq1", "" , "CACTACTTGTTCA--TAATAT");
//        Sequence seq2 = new Sequence("seq2", "" , "CACTACGTGTTCA--TAATAT");
//        Sequence seq3 = new Sequence("seq3", "" , "CACTACGTGTTCA--TAATAA");
//        List<Sequence> seqList = new ArrayList<> ();
//        seqList.add(seq1);
//        seqList.add(seq2);
//        seqList.add(seq3);
//        eo.addKmers(seqList);
//        
//        Map<Integer, Set<Oligo>> unique = eo.discardRedundant();
//        
//        for (int pos : eo.getKmerPosMap().keySet()) {
//            System.out.println(pos);
//            for (Oligo oligo : eo.getKmerPosMap().get(pos)) {
//                System.out.println(oligo.getSeq());
//            }
//        }
//        
//        for (Oligo oligo : eo.getOligoTargetMap().keySet()) {
//            System.out.println(oligo.getSeq());
//            System.out.println("detected");
//            for (String seq : eo.getOligoTargetMap().get(oligo)) {
//                System.out.println(seq);
//            }
//            System.out.println("-----------");
//        }
//        
//        MaxCoverage mc= new MaxCoverage(unique, eo.getOligoTargetMap(), eo.getNumSeq());
//        double cover = MaxCoverage.calCoverage(0, 1);
//        System.out.println(cover);
//        assertEquals(2.0/3.0, cover, 0.0001);
//        cover = MaxCoverage.calCoverage(0, 2);
//        
//        System.out.println(cover);
//        assertEquals(1.0, cover, 0.0001);
//       
//    }
//}
