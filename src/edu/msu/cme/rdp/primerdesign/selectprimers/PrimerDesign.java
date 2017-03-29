///*
// * Copyright (C) 2016 Michigan State University Board of Trustees
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
package edu.msu.cme.rdp.primerdesign.selectprimers;

import edu.msu.cme.rdp.primerdesign.screenoligos.ScreenOligosPipeline;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.OptionBuilder;

/**
 *
 * @author tift
 */
public class PrimerDesign {

    public static void main(String[] args) throws Exception {

        CommandLineParser parser = new BasicParser();
        Options options = new Options();
        options.addOption(OptionBuilder.withLongOpt("input")
                .withDescription("Input .fasta or .fa file. Contains aligned gene sequences")
                .hasArg()
                .withArgName("input")
                .create("i"));
        options.addOption(OptionBuilder.withLongOpt("treeinput")
                .withDescription("File (.tree) containing phylogentic relationship of the input sequences. In Newick format. Must have if tag -isTreeWeightNeeded is true")
                .hasArg()
                .withArgName("treeinput")
                .create("t"));
        options.addOption(OptionBuilder.withLongOpt("GraphOutput")
                .withDescription("If screen subcommand is ran, then this is the output directory for graph results")
                .hasArg()
                .withArgName("GraphOutput")
                .create("o"));
        options.addOption(OptionBuilder.withLongOpt("SlidingScale")
                .withDescription("SlidingScale (default: true) - If true, then forward and reverse primer pairs are built with a sliding window between the given amplicon product minimum and maximum length."
                        + " If false, the pairs will be built between the forward and reverse given min and max. ")
                .hasArg()
                .withArgName("SlidingScale")
                .create());
        options.addOption(OptionBuilder.withLongOpt("productLengthMin")
                .withDescription("productLengthMin (default: 100) - Minimum amplicon product length. Needed if SlidingScale is true")
                .hasArg()
                .withArgName("productLengthMin")
                .create());
        options.addOption(OptionBuilder.withLongOpt("productLengthMax")
                .withDescription("productLengthMax (default: 200) - Maximum amplicon product length. Needed if SlidingScale is true")
                .hasArg()
                .withArgName("productLengthMax")
                .create());
        options.addOption(OptionBuilder.withLongOpt("forwardMinPos")
                .withDescription("forwardMinPos (default: 100) - Foward oligo minimum position to begin enumeration. Needed if SlidingScale is false")
                .hasArg()
                .withArgName("forwardMinPos")
                .create());
        options.addOption(OptionBuilder.withLongOpt("forwardMaxPos")
                .withDescription("forwardMaxPos (default: 120) - Foward oligo maximum position to end enumeration. Needed if SlidingScale is false")
                .hasArg()
                .withArgName("forwardMaxPos")
                .create());
        options.addOption(OptionBuilder.withLongOpt("reverseMinPos")
                .withDescription("reverseMinPos (default: 350) - Reverse oligo minimum position to begin enumeration. Needed if SlidingScale is false")
                .hasArg()
                .withArgName("reverseMinPos")
                .create());
        options.addOption(OptionBuilder.withLongOpt("reverseMaxPos")
                .withDescription("reverseMaxPos (default: 370) - Reverse oligo maximum position to end enumeration. Needed if SlidingScale is false")
                .hasArg()
                .withArgName("reverseMaxPos")
                .create());
        options.addOption(OptionBuilder.withLongOpt("oligoMinSize")
                .withDescription("oligoMinSize (default: 22) - Minimum oligo size; Recommend no shoter than 15 bp")
                .hasArg()
                .withArgName("oligoMinSize")
                .create());
        options.addOption(OptionBuilder.withLongOpt("oligoMaxSize")
                .withDescription("oligoMaxSize (default: 30) - Maximum oligo size; Recommend no longer than 30 bp")
                .hasArg()
                .withArgName("oligoMaxSize")
                .create());
        options.addOption(OptionBuilder.withLongOpt("NoTEndFilter")
                .withDescription("NoTEndFilter (default: true) - If true, filter to remove any oligo generated with a Thymine base at end")
                .hasArg()
                .withArgName("NoTEndFilter")
                .create());
        options.addOption(OptionBuilder.withLongOpt("NoPoly3GCFilter")
                .withDescription("NoPoly3GCFilter (default: true) - If true, filter to remove any oligo generated with three Guanines or three Cytosines in a row")
                .hasArg()
                .withArgName("NoPoly3GCFilter")
                .create());
        options.addOption(OptionBuilder.withLongOpt("PolyRunFilter")
                .withDescription("PolyRunFilter (default: 4) - Poly Run max filter")
                .hasArg()
                .withArgName("PolyRunFilter")
                .create());
        options.addOption(OptionBuilder.withLongOpt("GCFilterMin")
                .withDescription("GCFilterMin (default: 0.45) - G+C content filter minimum percent. Recommend leaving at default if unsure")
                .hasArg()
                .withArgName("GCFilterMin")
                .create());
        options.addOption(OptionBuilder.withLongOpt("GCFilterMax")
                .withDescription("GCFilterMax (default: 0.70) - G+C content filter maximum percent. Recommend leaving at default if unsure")
                .hasArg()
                .withArgName("GCFilterMax")
                .create());
        options.addOption(OptionBuilder.withLongOpt("maxMismatches")
                .withDescription("maxMismatches (default: 2) - Oligo mismatch maximum. Recommend setting between 0 and 3")
                .hasArg()
                .withArgName("maxMismatches")
                .create());
        options.addOption(OptionBuilder.withLongOpt("tempMin")
                .withDescription("tempMin (default: 55) - Oligo min melting temperature")
                .hasArg()
                .withArgName("tempMin")
                .create());
        options.addOption(OptionBuilder.withLongOpt("tempMax")
                .withDescription("tempMax (default: 63) - Oligo max melting temperature")
                .hasArg()
                .withArgName("tempMax")
                .create());
        options.addOption(OptionBuilder.withLongOpt("hairMax")
                .withDescription("hairMax (default: 35) - Hairpin maximum temperature")
                .hasArg()
                .withArgName("hairMax")
                .create());
        options.addOption(OptionBuilder.withLongOpt("homoMax")
                .withDescription("homoMax (default: 35) - Homodimer maximum temperature")
                .hasArg()
                .withArgName("homoMax")
                .create());
        options.addOption(OptionBuilder.withLongOpt("isHenikoffWeightNeeded")
                .withDescription("isHenikoffWeightNeeded (default: false) - Henikoff Weighting Method. Set to 'true' or 't' if you would like this weighting method results as"
                        + "well as uniform results. Will give the highest weight to unique sequences based on bases at each position.")
                .hasArg()
                .withArgName("isHenikoffWeightNeeded")
                .create());
        options.addOption(OptionBuilder.withLongOpt("isTreeWeightNeeded")
                .withDescription("isTreeWeightNeeded (default: false)- A phylogenetic tree weighting method.  Must include .tree file. Set to 'true' or 't' if you would like this weighting method results as"
                        + "well as uniform results. Will give the highest weight to sequences closest to the root")
                .hasArg()
                .withArgName("isTreeWeightNeeded")
                .create());
        options.addOption(OptionBuilder.withLongOpt("subcommand")
                .withDescription("select subcommand: select - file output of individual degenerate primer pairs with sequences theoretically hit / screen - graph output of the proportional theoretical "
                        + "uniform coverage at each position \n"
                        + "        (default: select)")
                .hasArg()
                .withArgName("subcommand")
                .create());
        options.addOption(OptionBuilder.withLongOpt("os")
                .withDescription("Operating System : linux/mac (default: mac)")
                .hasArg()
                .withArgName("os")
                .create());
        options.addOption(OptionBuilder.withLongOpt("output")
                .withDescription("Full path to output file (for select)")
                .hasArg()
                .withArgName("output")
                .create());
        options.addOption(OptionBuilder.withLongOpt("assayMax")
                .withDescription("assayMax (default: 20) - Maxiumum number of assays allowed - one degenerate primer pair per assay")
                .hasArg()
                .withArgName("assayMax")
                .create());
        options.addOption(OptionBuilder.withLongOpt("degenMax")
                .withDescription("degenMax (default: 8) - Maximum degeneracy per primer pair. Nondegenerate primers = 1. Recommend no higher than 10")
                .hasArg()
                .withArgName("degenMax")
                .create());
        options.addOption(OptionBuilder.withLongOpt("sodiumConc")
                .withDescription("sodiumConc (default: 50) - Monovalent sodium concentration for thermodynamic calculations")
                .hasArg()
                .withArgName("sodiumConc")
                .create());
        options.addOption(OptionBuilder.withLongOpt("magnesConc")
                .withDescription("magnesConc (default: 1.5) - Divalent magnesium concentration for thermodynamic calculations")
                .hasArg()
                .withArgName("magnesConc")
                .create());

        options.addOption("h", "help", false, "Shows this help");

        try {
            CommandLine commandLine = parser.parse(options, args);

            if (commandLine.hasOption("h")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("java -jar PrimerDesign.jar [--help] (-i|--input) <FastaFile> \n"
                        + "  (-o|--onput) <Output> ", options);
                return;
            }

            if (commandLine.hasOption("subcommand")) {

                String com = commandLine.getOptionValue("subcommand");
                switch (com) {
                    case "screen":
                        ScreenUserInput screenUserInput = new ScreenUserInput(commandLine);
                        ScreenOligosPipeline.main(screenUserInput);
                        break;
                    case "select":
                        //User input object
                        SelectUserInput selectUserInput = new SelectUserInput(commandLine);
                        SelectPrimerPipeline.main(selectUserInput);
                        break;
//                        case "selectHi":
//                            SelectHiDegenPipeline.main(userInput);                          
//                            break;
                    default:
                        System.err.println("ERROR: " + "wrong subcommand");
                        System.err.println();

                }
            } else {
                SelectUserInput selectUserInput = new SelectUserInput(commandLine);
                SelectPrimerPipeline.main(selectUserInput);
            }

        } catch (ParseException exp) {
            //System.out.println("error from landing");
            System.err.println("Parsing failed.  Reason: " + exp.getMessage());
        }

    }

}
