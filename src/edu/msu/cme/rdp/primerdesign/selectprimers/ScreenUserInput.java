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
 * along with screen program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.msu.cme.rdp.primerdesign.selectprimers;

import edu.msu.cme.rdp.primerdesign.screenoligos.ScreenOligosPipeline;
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
public class ScreenUserInput {

    private File sequenceFastaFile;
    private double tempMin;
    private double tempMax;
    private double hairMax;
    private double homoMax;
    private double sodiumConc;
    private double magnesConc;
    private int oligoMinSize;
    private int oligoMaxSize;
    private boolean isNoTEnd;
    private boolean isNo3GCFilter;
    private int polyRunFilterMax;
    private double GCFilterMin;
    private double GCFilterMax;
    private int degenMax;
    private int maxMismatches;
    /**
     *
     * @param commandLine
     * @throws IOException
     */
    public ScreenUserInput() throws IOException {

    }

    public File getSequenceFastaFile() {
        return this.sequenceFastaFile;
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

    public double getTmMin() {
        return this.tempMin;
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
    public double getSodiumConc() {
        return this.sodiumConc;
    }
    public double getMagnesiumConc() {
        return this.magnesConc;
    }
    
    public int getNumMaxDegen() {
        return this.degenMax;
    }
    
    public int getNumMaxMismatches() {
        return this.maxMismatches;
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
        HelpFormatter formatter = new HelpFormatter();
        ScreenUserInput screen = new ScreenUserInput();
        try {
            CommandLine commandLine = parser.parse(options, args);
            if(commandLine.hasOption("h")) {
                formatter.printHelp("java -jar EcoFunPrimer.jar screen (i|--input) <fastafile> -o <outputfile>", options);
                System.exit(1);
            }
            
            if (commandLine.hasOption("input")) {
                String fastaFile = commandLine.getOptionValue("input");
                screen.sequenceFastaFile = new File(fastaFile);

            } else {
                System.err.println("ERROR: " + "Program needs sequence input fasta file to start");
                System.exit(1);
            }

            if (commandLine.hasOption("NoPoly3GCFilter")) {

                String isPolyGC = commandLine.getOptionValue("NoPoly3GCFilter");
                switch (isPolyGC) {
                    case "true":
                        screen.isNo3GCFilter = true;
                        break;
                    case "t":
                        screen.isNo3GCFilter = true;
                        break;
                    case "false":
                        screen.isNo3GCFilter = false;
                        break;
                    case "f":
                        screen.isNo3GCFilter = false;
                        break;
                    default:
                        screen.isNo3GCFilter = true;

                }

            } else {
                screen.isNo3GCFilter = true;
            }

            if (commandLine.hasOption("NoTEndFilter")) {

                String tEnd = commandLine.getOptionValue("NoTEndFilter");
                switch (tEnd) {
                    case "true":
                        screen.isNoTEnd = true;
                        break;
                    case "t":
                        screen.isNoTEnd = true;
                        break;
                    case "false":
                        screen.isNoTEnd = false;
                        break;
                    case "f":
                        screen.isNoTEnd = false;
                        break;
                    default:
                        screen.isNoTEnd = true;

                }

            } else {
                screen.isNoTEnd = true;
            }

            if (commandLine.hasOption("oligoMinSize")) {
                screen.oligoMinSize = Integer.parseInt(commandLine.getOptionValue("oligoMinSize"));
            }

            if (commandLine.hasOption("oligoMaxSize")) {
                screen.oligoMaxSize = Integer.parseInt(commandLine.getOptionValue("oligoMaxSize"));
            }

            if (commandLine.hasOption("tempMin")) {
                screen.tempMin = Double.parseDouble(commandLine.getOptionValue("tempMin"));

            } else {
                //System.err.println("ERROR: " + "Program needs melting temp min, used default: 55");
                screen.tempMin = 55;
            }

            if (commandLine.hasOption("tempMax")) {
                screen.tempMax = Double.parseDouble(commandLine.getOptionValue("tempMax"));
            } else {
                //System.err.println("ERROR: " + "Program needs melting temp max, used default: 63");
                screen.tempMax = 63;
            }

            if (commandLine.hasOption("hairMax")) {
                screen.hairMax = Double.parseDouble(commandLine.getOptionValue("hairMax"));
            } else {
                //System.err.println("ERROR: " + "Program needs hair max, used default: 35");
                screen.hairMax = 35;
            }

            if (commandLine.hasOption("homoMax")) {
                screen.homoMax = Double.parseDouble(commandLine.getOptionValue("homoMax"));
            } else {
                //System.err.println("ERROR: " + "Program needs homo max, used default: 35");
                screen.homoMax = 35;
            }

            if (commandLine.hasOption("PolyRunFilter")) {
                screen.polyRunFilterMax = Integer.parseInt(commandLine.getOptionValue("PolyRunFilter"));
            } else {
               // System.err.println("ERROR: " + "Program needs polyRunFilterMax, used default: 4");
                screen.polyRunFilterMax = 4;
            }

            if (commandLine.hasOption("GCFilterMin")) {
                screen.GCFilterMin = Double.parseDouble(commandLine.getOptionValue("GCFilterMin"));
            } else {
               // System.err.println("ERROR: " + "Program needs GC Content Filter Min, used default: 0.45");
                screen.GCFilterMin = 0.45;
            }

            if (commandLine.hasOption("GCFilterMax")) {
                screen.GCFilterMax = Double.parseDouble(commandLine.getOptionValue("GCFilterMax"));
            } else {
                //System.err.println("ERROR: " + "Program needs GC Content Filter Max, used default: 0.7");
                screen.GCFilterMax = 0.7;
            }

            if (commandLine.hasOption("sodiumConc")) {
                screen.sodiumConc = Double.parseDouble(commandLine.getOptionValue("sodiumConc"));
            } else {
                //System.err.println("ERROR: " + "Program needs Sodium Concentration, used default: 50");
                screen.sodiumConc = 50.0;
            }

            if (commandLine.hasOption("magnesConc")) {
                screen.magnesConc = Double.parseDouble(commandLine.getOptionValue("magnesConc"));
            } else {
                //System.err.println("ERROR: " + "Program needs magnesium concentration, used default: 1.5");
                screen.magnesConc = 1.5;
            }
            
            ScreenOligosPipeline.main(screen);
        }
        catch (ParseException exp) {
            System.err.println("Command Failed: " + exp.getMessage());
            formatter.printHelp("java -jar EcoFunPrimer.jar screen (i|--input) <fastafile> -o <outputfile>", options);
            System.exit(1);
        } 
    }
}
