/*
 * Copyright (C) 2016 Michigan State University Board of Trustees
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

import edu.msu.cme.rdp.primerdesign.screenoligos.filter.GCContentFilter;
import edu.msu.cme.rdp.primerdesign.screenoligos.filter.HairpinTempFilter;
import edu.msu.cme.rdp.primerdesign.screenoligos.filter.HomodimerTempFilter;
import edu.msu.cme.rdp.primerdesign.screenoligos.filter.NoPoly3GCFilter;
import edu.msu.cme.rdp.primerdesign.screenoligos.filter.NoTEndFilter;
import edu.msu.cme.rdp.primerdesign.screenoligos.filter.OligoBaseFilter;
import edu.msu.cme.rdp.primerdesign.screenoligos.filter.OligoTempFilter;
import edu.msu.cme.rdp.primerdesign.screenoligos.filter.PolyRunFilter;
import edu.msu.cme.rdp.primerdesign.selectprimers.redundantpair.EnumerateDegeneratePair;
import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.EnumerateOligos;
import edu.msu.cme.rdp.primerdesign.selectprimers.algorithm.MaxDegenerationAlgo;
import edu.msu.cme.rdp.primerdesign.screenoligos.filter.TempRangeFilter;
import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.MismatchEnumeration;
import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.MismatchProperties;
import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.Oligo;
import edu.msu.cme.rdp.primerdesign.utils.Primer3Wrapper;
import edu.msu.cme.rdp.primerdesign.weighting.*;
import edu.msu.cme.rdp.readseq.readers.Sequence;
import edu.msu.cme.rdp.readseq.readers.SequenceReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class contains the main function to run the second and final pipeline
 * process USER may modify the fwdStartPos, fwdEndPos, revStartPos, and
 * revEndPos positions based off the graph results from the Screen Oligos
 * process
 *
 * @author gunturus, xingziye, tift
 */
public class SelectPrimerPipeline {

    /**
     *
     * @param userInput
     * @throws java.io.IOException
     * @throws java.lang.CloneNotSupportedException
     */
    public static void main(SelectUserInput userInput) throws IOException, CloneNotSupportedException {

        //Read in from FASTA File  
        List<Sequence> allSequences = SequenceReader.readFully(userInput.getSequenceFastaFile());
        int allSeqsLength = allSequences.get(0).getSeqString().length();
        
        //Remove sequences with N or n
        for (int i = 0; i < allSequences.size(); i++) {
            String thisSeq = allSequences.get(i).getSeqString();
            String seqName = allSequences.get(i).getSeqName();
            for (int j = 0; j < thisSeq.length(); j++) {
                if (thisSeq.charAt(j) == 'N' || thisSeq.charAt(j) == 'n') {
                    allSequences.remove(allSequences.get(i));                
                    break;
                }
            }
        }
        
        List<Integer> fwdPositions = new ArrayList();
        List<Integer> revPositions = new ArrayList();
        int fwdStartPos;
        int fwdEndPos;
        int revStartPos;
        int revEndPos;
        
        if (userInput.getIsProductSlidingScale()) {
            //Positions for a sliding scale
            fwdStartPos = 0;
            fwdEndPos = allSeqsLength - userInput.getProductLengthMin();

            revStartPos = userInput.getProductLengthMin();
            revEndPos = allSeqsLength;

        } else {
            fwdStartPos = userInput.getForwardMinPos();
            fwdEndPos = userInput.getForwardMaxPos();

            revStartPos = allSeqsLength - userInput.getReverseMaxPos();
            revEndPos = allSeqsLength - userInput.getReverseMinPos();

        }

        //Build forward positions based on ScreenOligosPipeline graph results        
        for (int i = fwdStartPos; i < fwdEndPos; i++) {
            fwdPositions.add(i);
        }

        //Build reverse positions based on ScreenOligosPipeline graph results
        for (int i = revStartPos; i < revEndPos; i++) {
            revPositions.add(i);
        }

        //Acceptable sizes
        List<Integer> Sizes = new ArrayList<>();
        for (int k = userInput.getOligoMinSize(); k < userInput.getOligoMaxSize(); k++) {
            Sizes.add(k);
        }

        List<OligoTempFilter> tempFilters = new ArrayList<>();
        List<OligoBaseFilter> baseFilters = new ArrayList<>();

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

        //Primer object 
        Primer3Wrapper primer3 = new Primer3Wrapper(userInput.getSodiumConc(), userInput.getMagnesiumConc());
        MismatchProperties calcObj = new MismatchProperties(new Oligo(""));

        DateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date1 = new Date();
        System.out.println("Oligos enumeration START at " + dateformat.format(date1));
        System.out.println("Oligos enumeration FWD at " + dateformat.format(date1));
        //Initialize the forward EnumerateOligos object; last argument of "false"  means forward oligo
        EnumerateOligos eoFwd1 = new EnumerateOligos(primer3, Sizes, fwdPositions,
                tempFilters, baseFilters, false, userInput.getNumMaxMismatches(), calcObj);
        System.out.println("Oligos enumeration REV at " + dateformat.format(date1));
        //Initialize the reverse EnumerateOligos object; last argument of "true"  means reverse oligo 
        EnumerateOligos eoRev1 = new EnumerateOligos(primer3, Sizes, revPositions,
                tempFilters, baseFilters, true, userInput.getNumMaxMismatches(), calcObj);

        eoFwd1.addKmers(allSequences);
        eoRev1.addKmers(allSequences);

        /*
        if (userInput.getNumMaxMismatches() != 0) {
            //Will incorporate mismatches

            eoFwd1 = MismatchEnumeration.includeMismatches(eoFwd1);
            eoRev1 = MismatchEnumeration.includeMismatches(eoRev1);
        }
        */        
        //Track time
        Date date2 = new Date();
        long time = (date2.getTime() - date1.getTime()) / 1000;
        System.out.println("Oligos enumeration DONE in " + time + " secs");
        System.out.println();

       EnumerateDegeneratePair enumDegenPair = new EnumerateDegeneratePair(eoFwd1, eoRev1,
               userInput.getProductLengthMin(), userInput.getProductLengthMax(),
               userInput.getNumMaxDegen(), primer3);

        if (userInput.getIsProductSlidingScale()) {
            enumDegenPair.enumerateSlidingScaleWDegeneracy();
        } else {
            enumDegenPair.enumerateAllProductLengthsWDegeneracy();
        }

        // Print initial settings
        PrimerDesignViewer printBuildToView = new PrimerDesignViewer();
        
        printBuildToView.addInfoToFile(userInput.getOutFile(), "Michigan State University: RDP Primer Design");
        printBuildToView.addInfoToFile(userInput.getOutFile(), "--------------------------------------------");
        printBuildToView.addInfoToFile(userInput.getOutFile(), "Program User Settings");
        printBuildToView.addInfoToFile(userInput.getOutFile(), "--------------------------------------------");
        printBuildToView.addInfoToFile(userInput.getOutFile(), "Fasta file: " + userInput.getSequenceFastaFile().getAbsolutePath());

        //if (userInput.getIsTreeWeight()) {
     //       printBuildToView.addInfoToFile(userInput.getOutFile(), "Tree file: " + userInput.getTreeParserFile().getAbsolutePath());
     //   }

        printBuildToView.addInfoToFile(userInput.getOutFile(), "Sliding Scale - t or f? " + userInput.getIsProductSlidingScale());
      //  printBuildToView.addInfoToFile(userInput.getOutFile(), "Tree Weighted - t or f? " + userInput.getIsTreeWeight());
      //  printBuildToView.addInfoToFile(userInput.getOutFile(), "Henikoff Weighted - t or f? " + userInput.getIsHenikoff());

        if (userInput.getIsProductSlidingScale()) {
            printBuildToView.addInfoToFile(userInput.getOutFile(), "Product Min Length: " + userInput.getProductLengthMin());
            printBuildToView.addInfoToFile(userInput.getOutFile(), "Product Max Length: " + userInput.getProductLengthMax());

        } else {
            printBuildToView.addInfoToFile(userInput.getOutFile(), "Forward Start Position: " + userInput.getForwardMinPos());
            printBuildToView.addInfoToFile(userInput.getOutFile(), "Forward End Position: " + userInput.getForwardMaxPos());
            printBuildToView.addInfoToFile(userInput.getOutFile(), "Reverse Start Position: " + userInput.getReverseMinPos());
            printBuildToView.addInfoToFile(userInput.getOutFile(), "Reverse End Position: " + userInput.getReverseMaxPos());
        }
        printBuildToView.addInfoToFile(userInput.getOutFile(), "Filters: T end: " + userInput.getIsNoTEnd() + ", No Poly3GC: " + userInput.getIsNo3GCFilter() + ", Poly Run: " + userInput.getPolyRunMax() + ", GC Content:" + userInput.getGCFilterMin() + " - " + userInput.getGCFilterMax());
        printBuildToView.addInfoToFile(userInput.getOutFile(), "Oligo Min Size: " + userInput.getOligoMinSize());
        printBuildToView.addInfoToFile(userInput.getOutFile(), "Oligo Max Size: " + userInput.getOligoMaxSize());
        printBuildToView.addInfoToFile(userInput.getOutFile(), "Max Number Mismatches: " + userInput.getNumMaxMismatches());
        printBuildToView.addInfoToFile(userInput.getOutFile(), "Melting Temp Min: " + userInput.getTmMin());
        printBuildToView.addInfoToFile(userInput.getOutFile(), "Melting Temp Max: " + userInput.getTmMax());
        printBuildToView.addInfoToFile(userInput.getOutFile(), "Homodimer Max: " + userInput.getHomoDimerTemp());
        printBuildToView.addInfoToFile(userInput.getOutFile(), "Hairpin Max: " + userInput.getHairpinTemp());
        printBuildToView.addInfoToFile(userInput.getOutFile(), "Sodium Conc: " + userInput.getSodiumConc());
        printBuildToView.addInfoToFile(userInput.getOutFile(), "Magnesium Conc: " + userInput.getMagnesiumConc());
        printBuildToView.addInfoToFile(userInput.getOutFile(), "Max Degeneracy for each primer: " + userInput.getNumMaxDegen());
        printBuildToView.addInfoToFile(userInput.getOutFile(), "Max Num of Assays: " + userInput.getNumMaxAssays());
        printBuildToView.addInfoToFile(userInput.getOutFile(), "Sequence Length: " + eoFwd1.getSequenceLength());
        printBuildToView.addInfoToFile(userInput.getOutFile(), "--------------------------------------------");
        
        //Done sending initial settings to out file
        
        Map<String, Double> weights = new HashMap<>();
        Map<String, Double> weightMethodMap = new HashMap<>();
        Weighting weightingType;
        if(userInput.getWeightingMethod().equals("uniform")){
            weightingType = new UniformWeighting(allSequences);
            weightMethodMap = weightingType.getWeights();
        }
        else if(userInput.getWeightingMethod().equals("gsc") && userInput.getTreeParserFile() != null) {
            weightingType = new GSCWeighting(userInput.getTreeParserFile());
            weightMethodMap = weightingType.getWeights();
        }
        else if(userInput.getWeightingMethod().equals("clustalw") && userInput.getTreeParserFile() != null) {
            weightingType = new DistanceWeighting(userInput.getTreeParserFile());
            weightMethodMap = weightingType.getWeights();
        }
        else if(userInput.getWeightingMethod().equals("voltage") && userInput.getTreeParserFile() != null) {
            weightingType = new OhmWeighting(userInput.getTreeParserFile());
            weightMethodMap = weightingType.getWeights();
        }
        else if(userInput.getWeightingMethod().equals("henikoff") && userInput.getTreeParserFile() != null) {
            weightingType = new HenikoffWeighting(allSequences);
            weightMethodMap = weightingType.getWeights();
        }
        if(userInput.getCustomWeightFile() != null) {
            Map<String, Double> customWeight;
            ManualWeighting mw = new ManualWeighting(userInput.getCustomWeightFile());
            customWeight = mw.getWeights();
            weights = amplifier(customWeight, weightMethodMap);
        }
        else {
            weights = weightMethodMap;
        }
        
        double overallCoverage;
        Set<String> seqsHit = new HashSet<>();
        int numDegTest = 0;
        double weightSum = 0.0;
        while (numDegTest < userInput.getNumMaxAssays() && weightSum < 1.0) {
            enumDegenPair.selectDegeneratePrimers(seqsHit);
            List<Set<String>> cover = MaxDegenerationAlgo.calWeightDegeneration(enumDegenPair, 1, weights, primer3, allSeqsLength, userInput.getOutFile(), printBuildToView);
            Set<String> coverageSet = new HashSet<>();

            for (Set<String> topSet : cover) {
                coverageSet.addAll(topSet);
            }
            
            numDegTest++;
            printBuildToView.addInfoToFile(userInput.getOutFile(), "Sequences hit");
            for (String seqHit : coverageSet) {
                    weightSum += weights.get(seqHit);
                    weights.remove(seqHit);
                    printBuildToView.addInfoToFile(userInput.getOutFile(), seqHit);
                    seqsHit.add(seqHit);
            }
            
            overallCoverage = seqsHit.size() / (double) allSequences.size();
            printBuildToView.addInfoToFile(userInput.getOutFile(), "--------------------------------------------");
            printBuildToView.addInfoToFile(userInput.getOutFile(), userInput.getWeightingMethod() + " weighted coverage: " + Double.toString(weightSum));
            printBuildToView.addInfoToFile(userInput.getOutFile(), userInput.getWeightingMethod() + " Seq coverage: " + Double.toString(overallCoverage));
            printBuildToView.addInfoToFile(userInput.getOutFile(), "--------------------------------------------");
        }

        Date date3 = new Date();
        long time3 = (date3.getTime() - date2.getTime()) / 1000;
        System.out.println("Pair enumeration and selection DONE in " + time3 + " secs");
        long totalTime = (date3.getTime() - date1.getTime()) / 1000;
        printBuildToView.addInfoToFile(userInput.getOutFile(), "Total Runtime: " + totalTime + " seconds");
        printBuildToView.addInfoToFile(userInput.getOutFile(), "Finished at: " + dateformat.format(date3));
        printBuildToView.printAllToFile(userInput.getOutFile());
    }
    
    public static Map<String, Double> amplifier(Map<String, Double> weight, Map<String, Double> weight2) throws FileNotFoundException {
        double totalWeight = 0.0;
      
        for(String key : weight.keySet()) {
            weight.put(key, weight2.get(key) * weight.get(key));
            totalWeight = totalWeight + weight.get(key);
        }
        
        for(String key : weight.keySet()) {
            weight.put(key, weight.get(key)/totalWeight);
        }
        
        return weight;
    } 
}
