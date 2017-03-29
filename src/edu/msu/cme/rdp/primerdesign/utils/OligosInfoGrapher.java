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

package edu.msu.cme.rdp.primerdesign.utils;

import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.EnumerateOligos;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Uses the MaxCoverage and PlotWrapper class to graph Enumerate Oligos results in the Screen Oligos Pipeline process
 *
 * @author xingziye, gunturus
 */
public class OligosInfoGrapher {
    //private Map<Integer, Set<Oligo>> kmerStMap;
    
    public static void coverageGrapher(EnumerateOligos eo, String fileName, int maxPrimerNum) throws IOException {
        
        File file = new File("Max60CoverageData.txt");
       // File file2 = new File("EntropyData.txt");
        
        File file2 = new File("/work/leotift/nifDgr3EntropyKmer8Output");
        
        FileWriter fw;
        fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
//        FileWriter fw2;
//        fw2 = new FileWriter(file2.getAbsoluteFile());
//        BufferedWriter bw2 = new BufferedWriter(fw2);
        
        Map<Integer, Double> startCoverageMap = new TreeMap<> ();
//        Map<Integer, Double> startCoverageMap2 = new TreeMap<> ();
        
        
        for (int start : eo.getKmerPosMap().keySet()) {
            startCoverageMap.put(start, eo.calCoverage(start, maxPrimerNum));
        }
        
        for(int start : startCoverageMap.keySet()) {
            bw.write(start + "\t" + startCoverageMap.get(start) + "\t" + eo.getKmerPosMap().get(start).size() + "\n");
        }
        bw.close();
        
//        for (int start : eo.getKmerPosMap().keySet()) {
//            startCoverageMap2.put(start, eo.getEntrMap().get(start));
//        }
//        
//        for(int start : startCoverageMap2.keySet()) {
//            bw2.write(start + "\t" + startCoverageMap2.get(start) + "\n");
//        }
//        bw2.close();
        PlotWrapper wrapper = new PlotWrapper();
        
        wrapper.plot(file.getPath(), file2.getPath(), fileName);
    }
    

}
