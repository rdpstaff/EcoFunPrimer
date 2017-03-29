/*
 * Copyright (C) 2015  Santosh Gunturu <gunturus at msu dot edu>
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

import edu.msu.cme.rdp.primerdesign.screenoligos.filter.GCContentFilter;
import edu.msu.cme.rdp.primerdesign.screenoligos.filter.HairpinTempFilter;
import edu.msu.cme.rdp.primerdesign.screenoligos.filter.HomodimerTempFilter;
import edu.msu.cme.rdp.primerdesign.screenoligos.filter.NoPoly3GCFilter;
import edu.msu.cme.rdp.primerdesign.screenoligos.filter.NoTEndFilter;
import edu.msu.cme.rdp.primerdesign.screenoligos.filter.OligoBaseFilter;
import edu.msu.cme.rdp.primerdesign.screenoligos.filter.OligoTempFilter;
import edu.msu.cme.rdp.primerdesign.screenoligos.filter.PolyRunFilter;
import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.EnumerateOligos;
import edu.msu.cme.rdp.primerdesign.utils.OligosInfoGrapher;
import edu.msu.cme.rdp.primerdesign.screenoligos.filter.TempRangeFilter;
import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.MismatchProperties;
import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.Oligo;
import edu.msu.cme.rdp.primerdesign.selectprimers.ScreenUserInput;
import edu.msu.cme.rdp.primerdesign.utils.Primer3Wrapper;
import edu.msu.cme.rdp.readseq.readers.Sequence;
import edu.msu.cme.rdp.readseq.readers.SequenceReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class contains the main function to run the first pipeline process 
 * that will display a graph of coverage results by position.
 * USER will pick the two best coverage areas for the forward and reverse EnumerateOligos 
 * object in the second process, SelectPrimersPipeline.java
 *
 * @author gunturus, xingziye, tift
 */
public class ScreenOligosPipeline {

    /**
     * @param userInput
     * @throws java.io.IOException
     */
    public static void main(ScreenUserInput userInput) throws IOException {
        // TODO code application logic here
        
        
        //Read file
       
        List<Sequence> allSequences = SequenceReader.readFully(userInput.getSequenceFastaFile());
        
        List<Integer> fwdPositions = new ArrayList();
        
        int fwdStartPos = 0;
        int fwdEndPos = allSequences.get(0).getSeqString().length();
                
           
        //Build forward positions       
                    
        for(int i = fwdStartPos; i < fwdEndPos; i++) {
            fwdPositions.add(i);
        }
        
       
        //Acceptable sizes
        List<Integer> Sizes = new ArrayList<> ();
        for (int k = userInput.getOligoMinSize(); k < userInput.getOligoMaxSize(); k++) {
            Sizes.add(k);
        }
      
        //mandatory - Taking place of postFiltering()
        List<OligoTempFilter> tempFilters = new ArrayList<> ();
        List<OligoBaseFilter> baseFilters = new ArrayList<> ();   
        
        //mandatory - Taking place of postFiltering()
        tempFilters.add(new HairpinTempFilter(userInput.getHairpinTemp()));
        tempFilters.add(new HomodimerTempFilter(userInput.getHomoDimerTemp()));
        tempFilters.add(new TempRangeFilter(userInput.getTmMin(), userInput.getTmMax()));

        
        if (userInput.getIsNoTEnd()) {
            baseFilters.add(new NoTEndFilter());
        }
        if (userInput.getIsNo3GCFilter()) {
            baseFilters.add(new NoPoly3GCFilter());
        }
        baseFilters.add(new PolyRunFilter(userInput.getPolyRunMax()));
        baseFilters.add(new GCContentFilter(userInput.getGCFilterMin(), userInput.getGCFilterMax()));
        
        
        System.out.println("DONE WITH READING THE FILE");
                   
        
        //Primer object 
        Primer3Wrapper primer3 = new Primer3Wrapper(userInput.getOSType(), userInput.getSodiumConc(), userInput.getMagnesiumConc());
        MismatchProperties calcObj = new MismatchProperties(new Oligo(""));
        
         //Track time of process
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date date = new Date();
        System.out.println("Starting to Enumerate: " + df.format(date));
                
        //Initialize the forward EnumerateOligos object; last argument of "false" means forward direction
        EnumerateOligos eo = new EnumerateOligos(primer3, Sizes, fwdPositions, tempFilters, baseFilters, false, userInput.getNumMaxMismatches(), calcObj);
        System.out.println("STARTING...");
                       
        eo.addKmers(allSequences); 
        //eo.calEntropy(allSequences);
        
        System.out.println("Starting to compile data points: " + df.format(date));
        
//        //Graphing
        OligosInfoGrapher.coverageGrapher(eo, userInput.getGraphDirectory(), userInput.getNumMaxDegen());
        
        System.out.println("End compile data points: " + df.format(date));
        

    }
}
