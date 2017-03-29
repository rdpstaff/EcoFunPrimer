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
import edu.msu.cme.rdp.primerdesign.screenoligos.filter.NoTEndFilter;
import edu.msu.cme.rdp.primerdesign.screenoligos.filter.OligoBaseFilter;
import edu.msu.cme.rdp.primerdesign.screenoligos.filter.OligoTempFilter;
import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.MismatchProperties;
import edu.msu.cme.rdp.primerdesign.utils.Primer3Wrapper;
import edu.msu.cme.rdp.readseq.readers.Sequence;
import edu.msu.cme.rdp.readseq.readers.SequenceReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author xingziye
 */
public class EnumerateOligosTest {
    Sequence seq1, seq2;
    File file1, file2;
    Primer3Wrapper primer3;
    
    @Before
    public void setUp() {
        seq1 = new Sequence("", "", "ACGAGTATAC----GTAT---TGGCA");
        seq2 = new Sequence("", "", "ACGAGTATAC----GTAT---TGGCATCAATACGTGATCT");
        file1 = new File("/work/xingziye/NetBeansProjects/PrimerDesign/test/EnumerationTestSeqs.fasta");
        file2 = new File("/work/xingziye/NetBeansProjects/PrimerDesign/test/EnumerateTest.fasta");
        primer3 = new Primer3Wrapper("mac", 50, 1.5);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testDefault() throws IOException {
        EnumerateOligos eo = new EnumerateOligos();
        List<Sequence> seqList = new ArrayList<> ();
        seqList.add(seq1);
        eo.addKmers(seqList);
        
        Map<Integer, Set<Oligo>> expectedMap = new HashMap<> ();
        Set<Oligo> set1 = new HashSet();
        set1.add(new Oligo("ACGAGTATACGTATTGGC"));
        set1.add(new Oligo("ACGAGTATACGTATTGGCA"));
        expectedMap.put(0, set1);
        
        Set<Oligo> set2 = new HashSet();
        set2.add(new Oligo("CGAGTATACGTATTGGCA"));
        expectedMap.put(1, set2);
        
        for (int pos = 2; pos <= 25; pos++) {
            expectedMap.put(pos, new HashSet());
        }
        
        assertEquals(expectedMap, eo.getKmerPosMap());
    }
    
    @Test
    public void testNonDefault() throws IOException {
        List<Integer> positions = new ArrayList();
        int[] pos = {21, 22};
        for(int start : pos) {
            positions.add(start);
        }
        
        List<Integer> sizes = new ArrayList();
        sizes.add(10);
        sizes.add(11);
        
        List<OligoBaseFilter> baseFilters = new ArrayList();
        List<OligoTempFilter> tempFilters = new ArrayList();
        baseFilters.add(new NoTEndFilter());
        
        EnumerateOligos eo = new EnumerateOligos(primer3, sizes, positions, tempFilters, baseFilters, false, 2, new MismatchProperties(new Oligo("")));
        List<Sequence> seqList = new ArrayList<> ();
        seqList.add(seq2);
        eo.addKmers(seqList);
        
        Map<Integer, Set<Oligo>> expectedMap = new HashMap<> ();
        Set<Oligo> set1 = new HashSet();
        Set<Oligo> set2 = new HashSet();
        set1.add(new Oligo("TGGCATCAATA"));
        expectedMap.put(21, set1);
        set2.add(new Oligo("GGCATCAATA"));
        set2.add(new Oligo("GGCATCAATAC"));
        expectedMap.put(22, set2);
        
        assertEquals(expectedMap, eo.getKmerPosMap());
        /*
        for (int i: eo.getKmerPosMap().keySet()) {
            for (Oligo kmer : eo.getKmerPosMap().get(i)) {
                System.out.println(i + " " + kmer.getSeq());
            }
        }*/
    }
    
    @Test
    public void testReverse() throws IOException {
        List<Integer> positions = new ArrayList();
        int[] pos = {17, 18};
        for(int start : pos) {
            positions.add(start);
        }
        
        List<Integer> sizes = new ArrayList();
        sizes.add(10);
        sizes.add(11);
        
        List<OligoBaseFilter> baseFilters = new ArrayList();
        List<OligoTempFilter> tempFilters = new ArrayList();
        baseFilters.add(new NoTEndFilter());
        
        EnumerateOligos eo = new EnumerateOligos(primer3, sizes, positions, tempFilters, baseFilters, true, 2, new MismatchProperties(new Oligo("")));
        List<Sequence> seqList = new ArrayList<> ();
        seqList.add(seq2);
        eo.addKmers(seqList);
        
        Map<Integer, Set<Oligo>> expectedMap = new HashMap<> ();
        Set<Oligo> set1 = new HashSet();
        Set<Oligo> set2 = new HashSet();
        set1.add(new Oligo("TGGCATCAATA"));
        expectedMap.put(21, set1);
        set2.add(new Oligo("GGCATCAATA"));
        set2.add(new Oligo("GGCATCAATAC"));
        expectedMap.put(22, set2);
        
        assertEquals(expectedMap, eo.getKmerPosMap());
        /*
        for (int i: eo.getKmerPosMap().keySet()) {
            for (Oligo kmer : eo.getKmerPosMap().get(i)) {
                System.out.println(i + " " + kmer.getSeq());
            }
        }*/
    }
    
    @Test
    public void testTargetSet() throws IOException {
        List<Sequence> seqs = SequenceReader.readFully(file1);
        
        List<Integer> Sizes = new ArrayList<> ();
        int[] sizes = {22};
        for(int size : sizes) {
            Sizes.add(size);
        }
        
        List<Integer> positions = new ArrayList();
        int[] pos = {19};
        for(int start : pos) {
            positions.add(start);
        }
        /*
        for (int i = 1; i < seqs.get(0).getSeqString().length(); i++) {
            positions.add(i);
        }*/
        
        List<OligoBaseFilter> baseFilters = new ArrayList();
        List<OligoTempFilter> tempFilters = new ArrayList();
        
        
        EnumerateOligos eo = new EnumerateOligos(primer3, Sizes, positions, tempFilters, baseFilters, false, 2, new MismatchProperties(new Oligo("")));
        eo.addKmers(seqs);
        
        
        //eo.postFiltering(50, 65, 35, 35);
        
        Map<Oligo, Set<String>> expectedMap = new HashMap<> ();
        Set<String> set = new HashSet();
        set.add("AB289348");
        set.add("AB289349");
        expectedMap.put(new Oligo("TAATAGTAGTTGCAGTTAACTC"), set);
        /*
        System.out.println(eo.getKmerPosMap().size());
        System.out.println(eo.getOligoTargetMap().size());
        int count = 0;
        for (Oligo kmer : eo.getOligoTargetMap().keySet()) {
            if (eo.getOligoTargetMap().get(kmer).size() > 1) {
                count++;
            }
        }
        System.out.println(count);*/
        
        assertEquals(expectedMap, eo.getOligoTargetMap());
        
        /*
        for (int i: eo.getKmerPosMap().keySet()) {
            for (Oligo kmer : eo.getKmerPosMap().get(i)) {
                System.out.println(i + " " + kmer.getSeq());
            }
        }
        
        for (Oligo kmer : eo.getOligoTargetMap().keySet()) {
            System.out.println(kmer.getSeq() + " " + eo.getOligoTargetMap().get(kmer));
        }*/
    }
}
