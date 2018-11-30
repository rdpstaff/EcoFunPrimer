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

import java.io.File;
import java.io.IOException;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author gunturus, leotift
 */
public class SelectUserInput {

    public File sequenceFastaFile;
    public File outputFile;
    public File treeParserFile;
    public File customWeightFile = null;
    public boolean isAmpliconGeneratedSlidingScale;
    public int productLengthMin;
    public int productLengthMax;
    public int forwardMinPos;
    public int forwardMaxPos;
    public int reverseMinPos;
    public int reverseMaxPos;
    public int oligoMinSize;
    public int oligoMaxSize;
    public boolean isNoTEnd;
    public boolean isNo3GCFilter;
    public int polyRunFilterMax;
    public double GCFilterMin;
    public double GCFilterMax;
    public double tempMin;
    public double tempMax;
    public double hairMax;
    public double homoMax;
    public double sodiumConc;
    public double magnesConc;
    public int maxMismatches=0;
    public int assayMax;
    public int degenMax;
    public String weightingMethod = "uniform";

    /**
     *
     * @param commandLine
     * @throws IOException
     */
    public SelectUserInput() throws IOException {

    }

    public String getWeightingMethod() {
        return this.weightingMethod;
    }
    
    public File getSequenceFastaFile() {
        return this.sequenceFastaFile;
    }

    public File getOutFile() {
        return this.outputFile;
    }

    public File getTreeParserFile() {
        return this.treeParserFile;
    }
    
    public File getCustomWeightFile() {
        return this.customWeightFile;
    }

    public boolean getIsProductSlidingScale() {
        return this.isAmpliconGeneratedSlidingScale;
    }

    public boolean getIsNoTEnd() {
        return this.isNoTEnd;
    }

    public boolean getIsNo3GCFilter() {
        return this.isNo3GCFilter;
    }

    public int getOligoMinSize() {
        return this.oligoMinSize;
    }

    public int getPolyRunMax() {
        return this.polyRunFilterMax;
    }

    public double getGCFilterMin() {
        return this.GCFilterMin;
    }

    public double getGCFilterMax() {
        return this.GCFilterMax;
    }

    public int getOligoMaxSize() {
        return this.oligoMaxSize;
    }

    public int getForwardMinPos() {
        return this.forwardMinPos;
    }

    public int getForwardMaxPos() {
        return this.forwardMaxPos;
    }

    public int getReverseMinPos() {
        return this.reverseMinPos;
    }

    public int getReverseMaxPos() {
        return this.reverseMaxPos;
    }

    public int getProductLengthMin() {
        return this.productLengthMin;
    }

    public int getProductLengthMax() {
        return this.productLengthMax;
    }

    public double getTmMin() {
        return this.tempMin;
    }
    public double getSodiumConc() {
        return this.sodiumConc;
    }
    public double getMagnesiumConc() {
        return this.magnesConc;
    }

    public double getTmMax() {
        return this.tempMax;
    }

    public double getHairpinTemp() {
        return this.hairMax;
    }

    public double getHomoDimerTemp() {
        return this.homoMax;
    }
    
    public int getNumMaxMismatches() {
        return this.maxMismatches;
    }

    public int getNumMaxAssays() {
        return this.assayMax;
    }

    public int getNumMaxDegen() {
        return this.degenMax;
    }
    
    public static void main(String[] args) throws Exception {
        
        CommandLineParser parser = new BasicParser();
        Options options = new Options();
        
        Option input   = OptionBuilder.withLongOpt("input")
                                .withArgName( "fastafile" )
                                .isRequired()
                                .hasArg()
                                .withDescription(  "Input fasta file with aligned reference seqeunces" )
                                .create( "i" );
        
        Option treefile   = OptionBuilder.withLongOpt("tree")
                                .withArgName( "newickfile" )
                                .hasArg()
                                .withDescription(  "Phylogentic tree of input sequence in newick format. Required for tree weighting methods" )
                                .create();
        
        Option minSize = OptionBuilder.withLongOpt("oligoMinSize")
                .isRequired()
                .hasArg()
                .create("m");
        
        Option maxSize = OptionBuilder.withLongOpt("oligoMaxSize")
                .isRequired()
                .hasArg()
                .create("x");
        
        Option NoTEndfilter = OptionBuilder.withLongOpt("NoTEndFilter")
                .withDescription("(default: true) - If true, filter to remove any oligo generated with a Thymine base at end")
                .hasArg()
                .withArgName("boolean")
                .create();
        
        Option NoPoly3GCFilter = OptionBuilder.withLongOpt("NoPoly3GCFilter")
                .withDescription("(default: true) - If true, filter to remove any oligo generated with three Guanines or three Cytosines in a row")
                .hasArg()
                .withArgName("boolean")
                .create();
        
        Option PolyRunFilter = OptionBuilder.withLongOpt("PolyRunFilter")
                .withDescription("(default: 4) - Poly Run max filter")
                .hasArg()
                .create();
        
        Option GCFilterMin =  OptionBuilder.withLongOpt("GCFilterMin")
                .withDescription("(default: 0.45) - G+C content filter minimum percent. Recommend leaving at default if unsure")
                .hasArg()
                .withArgName("boolean")
                .create();       
        
        Option tempMin =  OptionBuilder.withLongOpt("tempMin")
                .withDescription("tempMin (default: 55) - Oligo min melting temperature")
                .hasArg()
                .create();       
                
        Option tempMax =  OptionBuilder.withLongOpt("tempMax")
                .withDescription("tempMax (default: 63) - Oligo max melting temperature")
                .hasArg()
                .create();
        
        Option hairMax =  OptionBuilder.withLongOpt("hairMax")
                .withDescription("(default: 35) - Hairpin maximum temperature")
                .create();
        
        Option homoMax =  OptionBuilder.withLongOpt("homoMax")
                .withDescription("(default: 35) - Homodimer maximum temperature")
                .hasArg()
                .create();
        
        Option sodiumConc =  OptionBuilder.withLongOpt("sodiumConc")
                .withDescription("(default: 50uM) - Monovalent sodium concentration for thermodynamic calculations")
                .hasArg()
                .create();
                
        Option magnesConc =  OptionBuilder.withLongOpt("magnesConc")
                .withDescription("(default: 1.5) - Divalent magnesium concentration for thermodynamic calculations")
                .hasArg()
                .create();
        
        Option weightingMethod =  OptionBuilder.withLongOpt("weightingMethod")
                .withDescription("Sequence weighting method to remove bias in reference sequences. "
                        + "Options: gsc, henikoff, clustalw, thg")
                .hasArg()
                .create();
        
        Option customWeightInput =  OptionBuilder.withLongOpt("customWeightInput")
                .withDescription("File with custom weights for the input sequences. The file has two columns: unique sequence name before the first whitespace on header of the input alignment file and the respective weight for the sequence.")
                .hasArg()
                .withArgName("file")
                .create();
        
        Option output =  OptionBuilder.withLongOpt("output")
                .withDescription("Full path to output file")
                .hasArg()
                .withArgName("file")
                .create();
        
        Option assayMax =  OptionBuilder.withLongOpt("assayMax")
                .withDescription("(default: 10) - Maxiumum number of assays allowed - one degenerate primer pair per assay")
                .hasArg()
                .create();
        
        Option degenMax =  OptionBuilder.withLongOpt("degenMax")
                .withDescription("(default: 4) - Maximum degeneracy per primer pair. Nondegenerate primers = 1. Recommend no higher than 10")
                .hasArg()
                .create();
                
        Option SlidingScale =  OptionBuilder.withLongOpt("SlidingScale")
                .withDescription("(default: true) - If true, then forward and reverse primer pairs are built with a sliding window between the given amplicon product minimum and maximum length."
                        + " If false, the pairs will be built between the forward and reverse positions")
                .hasArg()
                .create();
                
        Option productLengthMin =  OptionBuilder.withLongOpt("productLengthMin")
                .withDescription("(default: 100) - Minimum amplicon product length. Required if SlidingScale is true")
                .hasArg()
                .isRequired()
                .create();
        
        Option productLengthMax =  OptionBuilder.withLongOpt("productLengthMax")
                .withDescription("(default: 200) - Maximum amplicon product length. Required if SlidingScale is true")
                .hasArg()
                .isRequired()
                .create();
                
        Option forwardMinPos =  OptionBuilder.withLongOpt("forwardMinPos")
                .withDescription("Foward oligo minimum position to begin enumeration. Required if SlidingScale is false")
                .hasArg()
                .create();
                
        Option forwardMaxPos =  OptionBuilder.withLongOpt("forwardMaxPos")
                .withDescription("Foward oligo maximum position to end enumeration. Required if SlidingScale is false")
                .hasArg()
                .create();
        Option reverseMinPos =  OptionBuilder.withLongOpt("reverseMinPos")
                .withDescription("Reverse oligo minimum position to begin enumeration. Required if SlidingScale is false")
                .hasArg()
                .create();
                
        Option reverseMaxPos =  OptionBuilder.withLongOpt("reverseMaxPos")
                .withDescription("Reverse oligo maximum position to end enumeration. Required if SlidingScale is false")
                .hasArg()
                .create();
        
        options.addOption("h", "help", false, "Shows screen help");
        options.addOption(magnesConc);
        options.addOption(sodiumConc);
        options.addOption(homoMax);
        options.addOption(GCFilterMin);
        options.addOption(NoTEndfilter);
        options.addOption(NoPoly3GCFilter);
        options.addOption(PolyRunFilter);
        options.addOption(hairMax);
        options.addOption(maxSize);
        options.addOption(minSize);
        options.addOption(tempMax);
        options.addOption(tempMin);
        options.addOption(input);
        options.addOption(reverseMaxPos);
        options.addOption(reverseMinPos);
        options.addOption(forwardMaxPos);
        options.addOption(productLengthMax);
        options.addOption(productLengthMin);
        options.addOption(SlidingScale);
        options.addOption(degenMax);
        options.addOption(assayMax);
        options.addOption(customWeightInput);
        
        
        HelpFormatter formatter = new HelpFormatter();
        SelectUserInput select = new SelectUserInput();
        try {
            CommandLine commandLine = parser.parse(options, args);
            if(commandLine.hasOption("h")) {
                formatter.printHelp("java -jar EcoFunPrimer.jar select <options>", options);
                System.exit(1);
            }
            if (commandLine.hasOption("output")) {
                String outFile = commandLine.getOptionValue("output");
                select.outputFile = new File(outFile);

            } else {
                System.err.println("ERROR: " + "Program needs output file directory to start");
                formatter.printHelp("java -jar EcoFunPrimer.jar select <options>", options);
                System.exit(1);
                select.outputFile = null;
            }

            //File to use if tree method weighting is desired
            if (commandLine.hasOption("treeinput")) {

                String treeFile = commandLine.getOptionValue("treeinput");
                select.treeParserFile = new File(treeFile);

            } else {

                select.treeParserFile = null;
            }

            if (commandLine.hasOption("weightingMethod")) {
                select.weightingMethod = commandLine.getOptionValue("weightingMethod");
                if (!"uniform".equals(select.weightingMethod.toLowerCase()) 
                        | !"gsc".equals(select.weightingMethod.toLowerCase()) 
                        | !"henikoff".equals(select.weightingMethod.toLowerCase()) 
                        | !"clustalw".equals(select.weightingMethod.toLowerCase()) 
                        | !"thg".equals(select.weightingMethod.toLowerCase()) ) {
                    System.err.println("ERROR: " + "invalid weightingMethod option");
                    formatter.printHelp("java -jar EcoFunPrimer.jar select <options>", options);
                    System.exit(1);
                }
            }



            //Should the amplicons be generated from a set min and max product length?
            //If so, fill out the productLengthMin and productLengthMax
            //If not, choose forward and reverse min and max positions
            if (commandLine.hasOption("SlidingScale")) {

                String isSliding = commandLine.getOptionValue("SlidingScale");
                switch (isSliding) {
                    case "true":
                        select.isAmpliconGeneratedSlidingScale = true;
                        break;
                    case "t":
                        select.isAmpliconGeneratedSlidingScale = true;
                        break;
                    case "false":
                        select.isAmpliconGeneratedSlidingScale = false;
                        break;
                    case "f":
                        select.isAmpliconGeneratedSlidingScale = false;
                        break;
                    default:
                        select.isAmpliconGeneratedSlidingScale = true;
                        //System.err.println("ERROR: " + "Sliding scale necessity not clear; check argument");

                }

            } else {
                select.isAmpliconGeneratedSlidingScale = true;
            }

            if (select.isAmpliconGeneratedSlidingScale) {
                if (commandLine.hasOption("productLengthMin")) {
                    select.productLengthMin = Integer.parseInt(commandLine.getOptionValue("productLengthMin"));

                } else {
                    System.err.println("ERROR: " + "Projuct Minimum Length Required");
                    formatter.printHelp("java -jar EcoFunPrimer.jar select <options>", options);
                    System.exit(1);
                    select.productLengthMin = 100;

                }
                if (commandLine.hasOption("productLengthMax")) {
                    select.productLengthMax = Integer.parseInt(commandLine.getOptionValue("productLengthMax"));

                } else {
                    System.err.println("ERROR: " + "Product Maximum length Required");
                    formatter.printHelp("java -jar EcoFunPrimer.jar select <options>", options);
                    System.exit(1);
                    select.productLengthMax = 200;

                }
            } else {
                if (commandLine.hasOption("productLengthMin")) {
                    select.productLengthMin = Integer.parseInt(commandLine.getOptionValue("productLengthMin"));

                } else {
                    System.err.println("ERROR: " + "Projuct minimum Length Required");
                    formatter.printHelp("java -jar EcoFunPrimer.jar select <options>", options);
                    System.exit(1);
                    select.productLengthMin = 100;

                }
                if (commandLine.hasOption("productLengthMax")) {

                    select.productLengthMax = Integer.parseInt(commandLine.getOptionValue("productLengthMax"));

                } else {

                    System.err.println("ERROR: " + "Projuct Maximum Length Required");
                    formatter.printHelp("java -jar EcoFunPrimer.jar select <options>", options);
                    System.exit(1);
                    select.productLengthMax = 200;

                }

                if (commandLine.hasOption("forwardMinPos")) {

                    select.forwardMinPos = Integer.parseInt(commandLine.getOptionValue("forwardMinPos"));

                } else {

                    System.err.println("ERROR: " + "Program requires forward minimum position");
                    formatter.printHelp("java -jar EcoFunPrimer.jar select <options>", options);
                    System.exit(1);
                    select.forwardMinPos = 100;

                }

                if (commandLine.hasOption("forwardMaxPos")) {

                    select.forwardMaxPos = Integer.parseInt(commandLine.getOptionValue("forwardMaxPos"));

                } else {
                    System.err.println("ERROR: " + "Program requires forward maximum position");
                    formatter.printHelp("java -jar EcoFunPrimer.jar select <options>", options);
                    System.exit(1);
                    select.forwardMaxPos = 120;
                }

                if (commandLine.hasOption("reverseMinPos")) {

                    select.reverseMinPos = Integer.parseInt(commandLine.getOptionValue("reverseMinPos"));

                } else {
                    System.err.println("ERROR: " + "Program needed reverse minimum position");
                    formatter.printHelp("java -jar EcoFunPrimer.jar select <options>", options);
                    System.exit(1);
                    select.reverseMinPos = 350;
                }

                if (commandLine.hasOption("reverseMaxPos")) {

                    select.reverseMaxPos = Integer.parseInt(commandLine.getOptionValue("reverseMaxPos"));

                } else {
                    System.err.println("ERROR: " + "Program needed reverse maximum position");
                    formatter.printHelp("java -jar EcoFunPrimer.jar select <options>", options);
                    System.exit(1);
                    select.reverseMaxPos = 370;
                }
            }

            if (commandLine.hasOption("assayMax")) {

                select.assayMax = Integer.parseInt(commandLine.getOptionValue("assayMax"));

            } else {
                //System.err.println("ERROR: " + "Program needs assay max, used default: 20");
                select.assayMax = 10;
            }

            if (commandLine.hasOption("degenMax")) {

                select.degenMax = Integer.parseInt(commandLine.getOptionValue("degenMax"));

            } else {
                select.degenMax = 4;
            }

            if (commandLine.hasOption("maxMismatches")) {

                select.maxMismatches = Integer.parseInt(commandLine.getOptionValue("maxMismatches"));

            } else {
                select.maxMismatches = 0;
            }

            if (commandLine.hasOption("input")) {

                String fastaFile = commandLine.getOptionValue("input");
                select.sequenceFastaFile = new File(fastaFile);

            } else {
                System.err.println("ERROR: " + "Program needs sequence input fasta file to start");
                formatter.printHelp("java -jar EcoFunPrimer.jar select <options>", options);
                System.exit(1);
                select.sequenceFastaFile = null;
            }

            if (commandLine.hasOption("customWeightInput")) {
                String weightFile = commandLine.getOptionValue("customWeightInput");
                select.customWeightFile = new File(weightFile);
            }
            else {
                select.customWeightFile = null;
            }

            if (commandLine.hasOption("NoPoly3GCFilter")) {
                String isPolyGC = commandLine.getOptionValue("NoPoly3GCFilter");
                switch (isPolyGC) {
                    case "true":
                        select.isNo3GCFilter = true;
                        break;
                    case "t":
                        select.isNo3GCFilter = true;
                        break;
                    case "false":
                        select.isNo3GCFilter = false;
                        break;
                    case "f":
                        select.isNo3GCFilter = false;
                        break;
                    default:
                        select.isNo3GCFilter = true;
                        //System.err.println("ERROR: " + "Poly GC Filter necessity not clear; check argument");

                }

            } else {
                select.isNo3GCFilter = true;
            }

            if (commandLine.hasOption("NoTEndFilter")) {

                String tEnd = commandLine.getOptionValue("NoTEndFilter");
                switch (tEnd) {
                    case "true":
                        select.isNoTEnd = true;
                        break;
                    case "t":
                        select.isNoTEnd = true;
                        break;
                    case "false":
                        select.isNoTEnd = false;
                        break;
                    case "f":
                        select.isNoTEnd = false;
                        break;
                    default:
                        select.isNoTEnd = true;
                        //System.err.println("ERROR: " + "T End Filter necessity not clear; check argument");

                }

            } else {
                select.isNoTEnd = true;
            }

            if (commandLine.hasOption("oligoMinSize")) {

                select.oligoMinSize = Integer.parseInt(commandLine.getOptionValue("oligoMinSize"));

            } else {
                //System.err.println("ERROR: " + "Program needs oligo min size, used default: 22");
                select.oligoMinSize = 22;
            }

            if (commandLine.hasOption("oligoMaxSize")) {

                select.oligoMaxSize = Integer.parseInt(commandLine.getOptionValue("oligoMaxSize"));

            } else {
                //System.err.println("ERROR: " + "Program needs oligo max size, used default: 30");
                select.oligoMaxSize = 30;
            }

            if (commandLine.hasOption("tempMin")) {

                select.tempMin = Double.parseDouble(commandLine.getOptionValue("tempMin"));

            } else {
                //System.err.println("ERROR: " + "Program needs melting temp min, used default: 55");
                select.tempMin = 55;
            }

            if (commandLine.hasOption("tempMax")) {

                select.tempMax = Double.parseDouble(commandLine.getOptionValue("tempMax"));

            } else {
                //System.err.println("ERROR: " + "Program needs melting temp max, used default: 63");
                select.tempMax = 63;
            }

            if (commandLine.hasOption("hairMax")) {

                select.hairMax = Double.parseDouble(commandLine.getOptionValue("hairMax"));

            } else {
                //System.err.println("ERROR: " + "Program needs hair max, used default: 35");
                select.hairMax = 35;
            }

            if (commandLine.hasOption("homoMax")) {

                select.homoMax = Double.parseDouble(commandLine.getOptionValue("homoMax"));

            } else {
                //System.err.println("ERROR: " + "Program needs homo max, used default: 35");
                select.homoMax = 35;
            }

            if (commandLine.hasOption("PolyRunFilter")) {

                select.polyRunFilterMax = Integer.parseInt(commandLine.getOptionValue("PolyRunFilter"));

            } else {
                //System.err.println("ERROR: " + "Program needs polyRunFilterMax, used default: 4");
                select.polyRunFilterMax = 4;
            }

            if (commandLine.hasOption("GCFilterMin")) {

                select.GCFilterMin = Double.parseDouble(commandLine.getOptionValue("GCFilterMin"));

            } else {
                //System.err.println("ERROR: " + "Program needs GC Content Filter Min, used default: 0.45");
                select.GCFilterMin = 0.45;
            }

            if (commandLine.hasOption("GCFilterMax")) {

                select.GCFilterMax = Double.parseDouble(commandLine.getOptionValue("GCFilterMax"));

            } else {
                //System.err.println("ERROR: " + "Program needs GC Content Filter Max, used default: 0.7");
                select.GCFilterMax = 0.7;
            }

            if (commandLine.hasOption("sodiumConc")) {

                select.sodiumConc = Double.parseDouble(commandLine.getOptionValue("sodiumConc"));

            } else {
                //System.err.println("ERROR: " + "Program needs Sodium Concentration, used default: 50");
                select.sodiumConc = 50.0;
            }

            if (commandLine.hasOption("magnesConc")) {

                select.magnesConc = Double.parseDouble(commandLine.getOptionValue("magnesConc"));

            } else {
                //System.err.println("ERROR: " + "Program needs magnesium concentration, used default: 1.5");
                select.magnesConc = 1.5;
            }
        } catch (ParseException exp) {
            System.err.println("Parsing failed.  Reason: " + exp.getMessage());
            formatter.printHelp("java -jar EcoFunPrimer.jar select <options>", options);

                    
        }
                
    }
}
