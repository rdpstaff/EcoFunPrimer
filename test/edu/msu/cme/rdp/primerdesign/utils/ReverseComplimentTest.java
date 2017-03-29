/*
 * Copyright (C) 2015 leotift
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
package edu.msu.cme.rdp.primerdesign.utils;

import edu.msu.cme.rdp.primerdesign.utils.comparator.TargetSetComparator;
import edu.msu.cme.rdp.readseq.readers.Sequence;
import edu.msu.cme.rdp.readseq.utils.IUBUtilities;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import org.junit.Test;

/**
 *
 * @author leotift
 */
public class ReverseComplimentTest {

    public ReverseComplimentTest() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // To test the IUB Utility
    @Test
    public void test() {
//        Sequence sequence = new Sequence("seq1", "" , "ACTGAGGCTAGCTTAGTCC");
//        System.out.println(sequence.getSeqString());
//        Sequence comSequence = new Sequence(sequence.getSeqName(),sequence.getDesc(),IUBUtilities.complement(sequence.getSeqString()));
//        System.out.println(comSequence.getSeqString());
//        
//        Sequence sequence2 = new Sequence("seq2", "" , "CTGGCGCGTGTGGTTGGA");
//        System.out.println(sequence2.getSeqString());
//        Sequence comSequence2 = new Sequence(sequence2.getSeqName(),sequence2.getDesc(),IUBUtilities.reverseComplement(sequence2.getSeqString()));
//        System.out.println(comSequence2.getSeqString());

        Set<String> revPrimersToConvert = new HashSet<>();
        String parse = "ATGTGCAGAGCATGGCAGAA\n"
                + "ATGTGCATAGCATGGCAGAA\n"
                + "ATGTGCAGTGCATGGCAGAA\n"
                + "ATGTGCATTGCATGGCAGAA\n"
                + "ATGTGCAGCGCATGGCAGAA\n"
                + "ATGTGCATCGCATGGCAGAA\n"
                + "ATGTGCAGGGCATGGCAGAA\n"
                + "ATGTGCATGGCATGGCAGAA\n"
                + "ATGTGCAGAGCGTGGCAGAA\n"
                + "ATGTGCATAGCGTGGCAGAA\n"
                + "ATGTGCAGTGCGTGGCAGAA\n"
                + "ATGTGCATTGCGTGGCAGAA\n"
                + "ATGTGCAGCGCGTGGCAGAA\n"
                + "ATGTGCATCGCGTGGCAGAA\n"
                + "ATGTGCAGGGCGTGGCAGAA\n"
                + "ATGTGCATGGCGTGGCAGAA";
        
        
        String toLowerCase = "CGCAACGGCAAGGTGAGGGT\n"
                + "CGCGACGGCAAGGTGAGGGT\n"
                + "CGCAACGGCAAGGTCAGGGT\n"
                + "CGCGACGGCAAGGTCAGGGT\n"
                + "CGCAACGGCAAGGTGCGGGT\n"
                + "CGCGACGGCAAGGTGCGGGT\n"
                + "CGCAACGGCAAGGTCCGGGT\n"
                + "CGCGACGGCAAGGTCCGGGT\n"
                + "CGCAACGGCAAGGTGACGGT\n"
                + "CGCGACGGCAAGGTGACGGT\n"
                + "CGCAACGGCAAGGTCACGGT\n"
                + "CGCGACGGCAAGGTCACGGT\n"
                + "CGCAACGGCAAGGTGCCGGT\n"
                + "CGCGACGGCAAGGTGCCGGT\n"
                + "CGCAACGGCAAGGTCCCGGT\n"
                + "CGCGACGGCAAGGTCCCGGT\n"
                + "CGCAACGGCAAGGTGAGCGT\n"
                + "CGCGACGGCAAGGTGAGCGT\n"
                + "CGCAACGGCAAGGTCAGCGT\n"
                + "CGCGACGGCAAGGTCAGCGT\n"
                + "CGCAACGGCAAGGTGCGCGT\n"
                + "CGCGACGGCAAGGTGCGCGT\n"
                + "CGCAACGGCAAGGTCCGCGT\n"
                + "CGCGACGGCAAGGTCCGCGT\n"
                + "CGCAACGGCAAGGTGACCGT\n"
                + "CGCGACGGCAAGGTGACCGT\n"
                + "CGCAACGGCAAGGTCACCGT\n"
                + "CGCGACGGCAAGGTCACCGT\n"
                + "CGCAACGGCAAGGTGCCCGT\n"
                + "CGCGACGGCAAGGTGCCCGT\n"
                + "CGCAACGGCAAGGTCCCCGT\n"
                + "CGCGACGGCAAGGTCCCCGT";

//        String[] seqArray = parse.split("\n");
        String[] seqArray2 = toLowerCase.split("\n");
//        revPrimersToConvert.add("");
//        revPrimersToConvert.add("");
//        revPrimersToConvert.add("");
//        revPrimersToConvert.add("");
//        revPrimersToConvert.add("");
//        revPrimersToConvert.add("");
//        revPrimersToConvert.add("");
//        revPrimersToConvert.add("");
//        revPrimersToConvert.add("");
//        revPrimersToConvert.add("");
//        revPrimersToConvert.add("");
//        revPrimersToConvert.add("");
//        revPrimersToConvert.add("");
//        revPrimersToConvert.add("");
//        revPrimersToConvert.add("");
//        revPrimersToConvert.add("");
//        revPrimersToConvert.add("");
//        revPrimersToConvert.add("");
//        revPrimersToConvert.add("");
//        revPrimersToConvert.add("");

//        for (int i = 0; i < seqArray.length; i++) {
//            String seq = seqArray[i];
//            String newSeq = IUBUtilities.reverseComplement(seq);
//            System.out.println(newSeq);
//        }
        
        for (int i = 0; i < seqArray2.length; i++) {
            String seq = seqArray2[i];
            
            System.out.println(seq.toLowerCase());
        }

    }

}
