/*
 * Copyright (C) 2017 Michigan State University Board of Trustees
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

import edu.msu.cme.rdp.readseq.readers.Sequence;
import edu.msu.cme.rdp.readseq.readers.SequenceReader;
import edu.msu.cme.rdp.readseq.writers.FastaWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;

/**
 *
 * @author leotift
 */
public class RemoveGaps {

    @Test
    public void test() throws FileNotFoundException, IOException {

        List<Sequence> allSequences = SequenceReader.readFully(new File("/work/leotift/nifDNew236Output.fasta"));
//        int numSeqs = allSequences.size();
//        File outputFile = new File("/work/leotift/nifDgr3ProteinNOGAPS.txt");
//        
//        FileWriter fw;
//        fw = new FileWriter(outputFile.getAbsoluteFile());
//        BufferedWriter bw = new BufferedWriter(fw);
//        List<Integer> pos = new ArrayList <> ();
//        for(int i = 0; i < 1977; i++) {
//            
//            pos.add(i);
//        }
//        
//        for(Integer i : pos) {
//            int numBases = 0;
//            for (Sequence seq : allSequences) {
//                if (seq.getSeqString().charAt(i) != '-' && seq.getSeqString().charAt(i) != '.') {
//                    numBases++;
//                }
//            }
//            
//            double perct = (double) numBases / numSeqs;
//            System.out.print(i + "\t" + perct + "\n");
//        
//        }
        int maxLength = allSequences.get(0).getSeqString().length();
//        List<Integer> badPositions = new ArrayList <> ();
//        boolean notGap;
//        for (int i = 0; i < maxLength; i++) {
//            notGap = false;
//            for (int j = 0; j < allSequences.size(); j++) {
//                if (allSequences.get(j).getSeqString().charAt(i) == '-' || allSequences.get(j).getSeqString().charAt(i) == '.') {
//                    continue;
//                } else {
//                    notGap = true;
//                    break;
//                }
//            }
//            if (!notGap) {
//                badPositions.add(i);
//            }
//        }
        
//        for (Sequence seq: allSequences) {
//            StringBuilder sb = new StringBuilder ();
//            for (int i = 0; i < maxLength; i++) {
//                if (!badPositions.contains(i)) {
//                    sb.append(seq.getSeqString().charAt(i));
//                }
//            }
//            seq.setSeqString(sb.toString());
//        }
        
        for (Sequence seq: allSequences) {
            StringBuilder sb = new StringBuilder ();
            for (int i = 0; i < maxLength; i++) {
                if (seq.getSeqString().charAt(i) != '-' && seq.getSeqString().charAt(i) != '.') {
                    sb.append(seq.getSeqString().charAt(i));
                }
            }
            seq.setSeqString(sb.toString());
        }
        
        
        
        File newFasta = new File("/work/leotift/nifDgr3NEWUnaligned.fasta");
        FastaWriter fastaWriter = new FastaWriter(newFasta);
        for (Sequence seq : allSequences) {
            fastaWriter.writeSeq(seq.getSeqName(), seq.getDesc(), seq.getSeqString());

        }
        fastaWriter.close();

    }

}
