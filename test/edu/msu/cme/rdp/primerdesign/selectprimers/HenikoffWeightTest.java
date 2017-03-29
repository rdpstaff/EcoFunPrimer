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

import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.Oligo;
import edu.msu.cme.rdp.primerdesign.utils.comparator.SeqWeightComparator;
import edu.msu.cme.rdp.primerdesign.selectprimers.algorithm.HenikoffWeight;
import edu.msu.cme.rdp.primerdesign.selectprimers.algorithm.PSSM;
import edu.msu.cme.rdp.readseq.readers.Sequence;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author xingziye
 */
public class HenikoffWeightTest {

    public HenikoffWeightTest() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void test() throws IOException {
//        Sequence seq1 = new Sequence("seq1", "", "GCGTTAGC");
//        Sequence seq2 = new Sequence("seq2", "", "GAGTTGGA");
//        Sequence seq3 = new Sequence("seq3", "", "CGGACTAA");
//        List<Sequence> list = new ArrayList<>();
//        list.add(seq1);
//        list.add(seq2);
//        list.add(seq3);
//        Map<String, Double> result = HenikoffWeight.calSequenceWeight(list);
//        Map<String, Double> expected = new HashMap<>();
//        expected.put("seq1", 0.3125);
//        expected.put("seq2", 0.28125);
//        expected.put("seq3", 0.40625);
//        double sum = 0;
//        for (String seqName : result.keySet()) {
//            sum = sum + result.get(seqName);
//        }
//        assertEquals(1.0, sum, 0.0001);
////        for (String seqName : result.keySet()) {
////            double value = result.get(seqName)/list.size();
////            result.put(seqName, value);
////        }
//        assertEquals(expected, result);
//        
//        Map<String, Double> sortedWeight = new TreeMap<>(new SeqWeightComparator(result));
//        sortedWeight.putAll(result);
//        for (String seq : sortedWeight.keySet()) {
//            System.out.println(seq);
//            System.out.println(result.get(seq));
//        }

        Oligo o1 = new Oligo("GAGGTAAAC");
        Oligo o2 = new Oligo("TCCGTAAGT");
        Oligo o3 = new Oligo("CAGGTTGGA");
        Oligo o4 = new Oligo("ACAGTCAGT");
        Oligo o5 = new Oligo("TAGGTCATT");
        Oligo o6 = new Oligo("TAGGTACTG");
        Oligo o7 = new Oligo("ATGGTAACT");
        Oligo o8 = new Oligo("CAGGTATAC");
        Oligo o9 = new Oligo("TGTGTGAGT");
        Oligo o10 = new Oligo("AAGGTAAGT");
        Oligo o11 = new Oligo("CAGAAACCA");

        List<Oligo> oligoList = new ArrayList<>();

        oligoList.add(o1);
        oligoList.add(o2);
        oligoList.add(o3);
        oligoList.add(o4);
        oligoList.add(o5);
        oligoList.add(o6);
        oligoList.add(o7);
        oligoList.add(o8);
        oligoList.add(o9);
        oligoList.add(o10);
        
        PSSM matrixObj = new PSSM (oligoList);
        matrixObj.buildFrequencyMatrix();
        matrixObj.buildWeightMatrix();
        System.out.println(matrixObj.getSum(o10));
        Double logTest = (Math.log10(0.3/0.25)/ Math.log10(2));
        System.out.println(logTest);

    }
}
