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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author leotift
 */
public class ExtractProteinSeqs {

    @Test
    public void test() throws FileNotFoundException, IOException {

        List<Sequence> allProtein = SequenceReader.readFully(new File("/home/leotift/Downloads/fungene_8.8_nifD_1388_aligned_protein_seqs.fa"));
        List<Sequence> allDNA = SequenceReader.readFully(new File("/work/leotift/nifDgr3.fasta"));

        Set<String> dnaLocs = new HashSet<>();

        for (Sequence seq : allDNA) {
            String[] comma = seq.getDesc().split(",");
            String[] equals = comma[0].split("=");
            dnaLocs.add(equals[1]);

        }

       
////        
        File newFasta = new File("/work/leotift/nifDgr3ProteinGr3.fasta");
        FastaWriter fastaWriter = new FastaWriter(newFasta);
        for (Sequence seq : allProtein) {
            String[] comma = seq.getDesc().split(",");
            String[] equals = comma[0].split("=");
            if (equals[1].contains("complement")) {
                String[] first = equals[1].split("complement");
                String proteinID = first[1].replaceAll("[()]", "");
                if (dnaLocs.contains(proteinID)) {
                    fastaWriter.writeSeq(seq.getSeqName(), seq.getDesc(), seq.getSeqString());
                }
            } else {
                if (dnaLocs.contains(equals[1])) {
                    fastaWriter.writeSeq(seq.getSeqName(), seq.getDesc(), seq.getSeqString());
                }
            }

        }
        fastaWriter.close();

    }
}
