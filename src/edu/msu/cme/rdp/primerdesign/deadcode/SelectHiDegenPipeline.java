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
package edu.msu.cme.rdp.primerdesign.deadcode;

import edu.msu.cme.rdp.primerdesign.screenoligos.filter.GCContentFilter;
import edu.msu.cme.rdp.primerdesign.screenoligos.filter.HairpinTempFilter;
import edu.msu.cme.rdp.primerdesign.screenoligos.filter.HomodimerTempFilter;
import edu.msu.cme.rdp.primerdesign.screenoligos.filter.NoPoly3GCFilter;
import edu.msu.cme.rdp.primerdesign.screenoligos.filter.NoTEndFilter;
import edu.msu.cme.rdp.primerdesign.screenoligos.filter.OligoBaseFilter;
import edu.msu.cme.rdp.primerdesign.screenoligos.filter.OligoTempFilter;
import edu.msu.cme.rdp.primerdesign.screenoligos.filter.PolyRunFilter;
import edu.msu.cme.rdp.primerdesign.screenoligos.filter.TempRangeFilter;
import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.EnumerateOligos;
import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.MismatchEnumeration;
import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.MismatchProperties;
import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.Oligo;
import edu.msu.cme.rdp.primerdesign.selectprimers.PrimerDesignViewer;
import edu.msu.cme.rdp.primerdesign.selectprimers.SelectUserInput;
import edu.msu.cme.rdp.primerdesign.selectprimers.algorithm.HenikoffWeight;
import edu.msu.cme.rdp.primerdesign.selectprimers.algorithm.MaxDegenerationAlgo;
import edu.msu.cme.rdp.primerdesign.selectprimers.algorithm.TreeParser;
import edu.msu.cme.rdp.primerdesign.selectprimers.redundantpair.EnumerateDegeneratePair;
import edu.msu.cme.rdp.primerdesign.utils.Primer3Wrapper;
import edu.msu.cme.rdp.readseq.readers.Sequence;
import edu.msu.cme.rdp.readseq.readers.SequenceReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author leotift
 */
class SelectHiDegenPipeline {

    /**
     *
     * @param userInput
     * @throws java.io.IOException
     * @throws java.lang.CloneNotSupportedException
     */
    public static void main(SelectUserInput userInput) throws IOException, CloneNotSupportedException {

        //      Read in from FASTA File  
        List<Sequence> allSequences = SequenceReader.readFully(userInput.getSequenceFastaFile());
        int allSeqsLength = allSequences.get(0).getSeqString().length();

//        //Remove n or N
        char n = 'n';
        char N = 'N';
        boolean valid;
        for (int i = 0; i < allSequences.size(); i++) {
            valid = true;
            String thisSeq = allSequences.get(i).getSeqString();
            String seqName = allSequences.get(i).getSeqName();
            for (int j = 0; j < thisSeq.length(); j++) {
                if (thisSeq.charAt(j) == N || thisSeq.charAt(j) == n) {
                    allSequences.remove(allSequences.get(i));
                    valid = false;
                    break;
                }
            }
            if (!valid) {
                System.err.println("ERROR: " + "N char present in " + seqName + "; check sequence");
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

            revStartPos = 0 + userInput.getProductLengthMin();
            revEndPos = allSeqsLength;

        } else {
            fwdStartPos = userInput.getForwardMinPos();
            fwdEndPos = userInput.getForwardMaxPos();

            revStartPos = userInput.getReverseMinPos();
            revEndPos = userInput.getReverseMaxPos();

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
        Primer3Wrapper primer3 = new Primer3Wrapper("mac", 50.0, 1.5);
        MismatchProperties calcObj = new MismatchProperties(new Oligo(""));

//        Track time of process
        DateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date1 = new Date();
        System.out.println("Oligos enumeration START at " + dateformat.format(date1));

        //Initialize the forward EnumerateOligos object; last argument of "false"  means forward oligo
        EnumerateOligos eoFwd1 = new EnumerateOligos(primer3, Sizes, fwdPositions, tempFilters, baseFilters, false, userInput.getNumMaxMismatches(), calcObj);

        //Initialize the reverse EnumerateOligos object; last argument of "true"  means reverse oligo 
        EnumerateOligos eoRev1 = new EnumerateOligos(primer3, Sizes, revPositions, tempFilters, baseFilters, true, userInput.getNumMaxMismatches(), calcObj);

        eoFwd1.addKmers(allSequences);
        eoRev1.addKmers(allSequences);

        if (userInput.getNumMaxMismatches() != 0) {
            //Will incorporate mismatches

            eoFwd1 = MismatchEnumeration.includeMismatches(eoFwd1);
            eoRev1 = MismatchEnumeration.includeMismatches(eoRev1);
        }

//        //Track time
        Date date2 = new Date();
        long time = (date2.getTime() - date1.getTime()) / 1000;
        System.out.println("Oligos enumeration DONE in " + time + " secs");
        System.out.println();

        EnumerateDegeneratePair enumDegenPair = new EnumerateDegeneratePair(eoFwd1, eoRev1, userInput.getProductLengthMin(), userInput.getProductLengthMax(), userInput.getNumMaxDegen());

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

        if (userInput.getIsTreeWeight()) {
            printBuildToView.addInfoToFile(userInput.getOutFile(), "Tree file: " + userInput.getTreeParserFile().getAbsolutePath());
        }

        printBuildToView.addInfoToFile(userInput.getOutFile(), "Sliding Scale - t or f? " + userInput.getIsProductSlidingScale());
        printBuildToView.addInfoToFile(userInput.getOutFile(), "Tree Weighted - t or f? " + userInput.getIsTreeWeight());
        printBuildToView.addInfoToFile(userInput.getOutFile(), "Henikoff Weighted - t or f? " + userInput.getIsHenikoff());

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
        printBuildToView.addInfoToFile(userInput.getOutFile(), "Max Degeneracy for each primer: " + userInput.getNumMaxDegen());
        printBuildToView.addInfoToFile(userInput.getOutFile(), "Max Num of Assays: " + userInput.getNumMaxAssays());
        printBuildToView.addInfoToFile(userInput.getOutFile(), "--------------------------------------------");

        //Done sending initial settings to out file
        Set<String> uniformSeqsHit = new HashSet<>();

        int numDegTest = 0;

        while (numDegTest < userInput.getNumMaxAssays() && uniformSeqsHit.size() < allSequences.size()) {

            enumDegenPair.selectDegeneratePrimers(uniformSeqsHit);

            //     
            //      List<Set<String>> listUniform = MaxDegenerationAlgo.calMaxDegeneration(enumRedunPair, 30, primer3, allSequences.get(0).getSeqString().length());
            List<Set<String>> listUniform = MaxDegenerationAlgo.calUniformDegenerate(enumDegenPair, 1, allSeqsLength, userInput.getOutFile(), printBuildToView);

            Set<String> coverageSetUniform = new HashSet<>();

            for (Set<String> topSet : listUniform) {

                coverageSetUniform.addAll(topSet);
            }

            if (coverageSetUniform.removeAll(uniformSeqsHit)) {
                System.out.print("error emptying");
            }

            uniformSeqsHit.addAll(coverageSetUniform);
            numDegTest++;

            printBuildToView.addInfoToFile(userInput.getOutFile(), "Sequences hit");

            for (String seqHit : coverageSetUniform) {
                printBuildToView.addInfoToFile(userInput.getOutFile(), seqHit);
            }

            printBuildToView.addInfoToFile(userInput.getOutFile(), "--------------------------------------------");

        }

        Date date3 = new Date();
        long time3 = (date3.getTime() - date2.getTime()) / 1000;
        System.out.println("Pair enumeration and selection DONE in " + time3 + " secs");
        System.out.println();

        double overallCoverage = uniformSeqsHit.size() / (double) allSequences.size();
        printBuildToView.addInfoToFile(userInput.getOutFile(), "Overall uniform coverage: " + Double.toString(overallCoverage));

        // Tree Weighted Method
        if (userInput.getIsTreeWeight()) {

            printBuildToView.addInfoToFile(userInput.getOutFile(), "--------------------------------------------");
            printBuildToView.addInfoToFile(userInput.getOutFile(), "Phylogenetic Tree Weight Results: ");
            printBuildToView.addInfoToFile(userInput.getOutFile(), "--------------------------------------------");
            TreeParser treePars = TreeParser.run(userInput.getTreeParserFile());
            double totalPossibleTreeWeight = 0.0;
            for (String seq : treePars.getWeightMap().keySet()) {

                totalPossibleTreeWeight += treePars.getWeightMap().get(seq);

            }

            int totalTreeWeightAssays = 0;
            double treeWeightSum = 0.0;

            Set<String> treeSeqsHit = new HashSet<>();

            while (totalTreeWeightAssays < userInput.getNumMaxAssays() && treeWeightSum < totalPossibleTreeWeight) {

                enumDegenPair.selectDegeneratePrimers(treeSeqsHit);

                List<Set<String>> listTree = MaxDegenerationAlgo.calWeightDegeneration(enumDegenPair, totalTreeWeightAssays, treePars.getWeightMap(), primer3, allSeqsLength, userInput.getOutFile(), printBuildToView);

                Set<String> coverageSetTree = new HashSet<>();

                for (Set<String> topSet : listTree) {
                    coverageSetTree.addAll(topSet);
                }

                totalTreeWeightAssays++;

                printBuildToView.addInfoToFile(userInput.getOutFile(), "Sequences hit");

                for (String seqName : coverageSetTree) {
                    treeWeightSum += treePars.getWeightMap().get(seqName);
                    treePars.getWeightMap().remove(seqName);
                    printBuildToView.addInfoToFile(userInput.getOutFile(), seqName);
                    treeSeqsHit.add(seqName);
                }

                printBuildToView.addInfoToFile(userInput.getOutFile(), "--------------------------------------------");

            }

            double overallTreeCoverage = treeWeightSum / totalPossibleTreeWeight;
            printBuildToView.addInfoToFile(userInput.getOutFile(), "Overall phylogenetic tree coverage: " + Double.toString(overallTreeCoverage));

        }

        // Establish HenikoffWeight
        if (userInput.getIsHenikoff()) {

            printBuildToView.addInfoToFile(userInput.getOutFile(), "--------------------------------------------");
            printBuildToView.addInfoToFile(userInput.getOutFile(), "Henikoff Weight Results: ");
            printBuildToView.addInfoToFile(userInput.getOutFile(), "--------------------------------------------");
            Map<String, Double> weight = HenikoffWeight.calSequenceWeight(allSequences);
            Set<String> heniSeqsHit = new HashSet<>();

            int totalHenWeightAssays = 0;
            double henikoffWeightSum = 0.0;

            while (totalHenWeightAssays < userInput.getNumMaxAssays() && henikoffWeightSum < 1.0) {

                enumDegenPair.selectDegeneratePrimers(heniSeqsHit);

                List<Set<String>> listHenikoff = MaxDegenerationAlgo.calWeightDegeneration(enumDegenPair, 1, weight, primer3, allSeqsLength, userInput.getOutFile(), printBuildToView);

                Set<String> coverageSetHenikoff = new HashSet<>();

                for (Set<String> topSet : listHenikoff) {
                    coverageSetHenikoff.addAll(topSet);
                }

                totalHenWeightAssays++;

                printBuildToView.addInfoToFile(userInput.getOutFile(), "Sequences hit");

                for (String seqName : coverageSetHenikoff) {
                    henikoffWeightSum += weight.get(seqName);
                    weight.remove(seqName);
                    heniSeqsHit.add(seqName);
                    printBuildToView.addInfoToFile(userInput.getOutFile(), seqName);
                }

                printBuildToView.addInfoToFile(userInput.getOutFile(), "--------------------------------------------");

            }

            printBuildToView.addInfoToFile(userInput.getOutFile(), "Overall Henikoff weighted coverage: " + Double.toString(henikoffWeightSum));

        }

        Date date4 = new Date();

        System.out.println("Finished at: " + dateformat.format(date4));
        System.out.println();
        long totalTime = (date4.getTime() - date1.getTime()) / 1000;
        printBuildToView.addInfoToFile(userInput.getOutFile(), "Total Runtime: " + totalTime + " seconds");
        printBuildToView.addInfoToFile(userInput.getOutFile(), "Finished at: " + dateformat.format(date4));

        printBuildToView.printAllToFile(userInput.getOutFile());

    }
}
