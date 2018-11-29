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
import org.apache.commons.cli.CommandLine;

/**
 *
 * @author gunturus, leotift
 */
public class SelectUserInput {

    private final File sequenceFastaFile;
    private final File outputFile;
    private final File treeParserFile;
    private File customWeightFile = null;
    private final boolean isAmpliconGeneratedSlidingScale;
    private final int productLengthMin;
    private final int productLengthMax;
    private final int forwardMinPos;
    private final int forwardMaxPos;
    private final int reverseMinPos;
    private final int reverseMaxPos;
    private final int oligoMinSize;
    private final int oligoMaxSize;
    private final boolean isNoTEnd;
    private final boolean isNo3GCFilter;
    private final int polyRunFilterMax;
    private final double GCFilterMin;
    private final double GCFilterMax;
    private final boolean isUniformWeightNeeded;
    private final boolean isHenikoffWeightNeeded;
    private final boolean isTreeWeightNeeded;
    private final double tempMin;
    private final double tempMax;
    private final double hairMax;
    private final double homoMax;
    private final double sodiumConc;
    private final double magnesConc;
    private int maxMismatches=0;
    private final int assayMax;
    private final int degenMax;
    private String weightingMethod = "uniform";

    /**
     *
     * @param commandLine
     * @throws IOException
     */
    public SelectUserInput(CommandLine commandLine) throws IOException {

        if (commandLine.hasOption("output")) {

            String outFile = commandLine.getOptionValue("output");
            this.outputFile = new File(outFile);

        } else {
            System.err.println("ERROR: " + "Program needs output file directory to start");
            this.outputFile = null;
        }

        //File to use if tree method weighting is desired
        if (commandLine.hasOption("treeinput")) {

            String treeFile = commandLine.getOptionValue("treeinput");
            this.treeParserFile = new File(treeFile);

        } else {

            this.treeParserFile = null;
        }
        
        if (commandLine.hasOption("weightingMethod")) {
            this.weightingMethod = commandLine.getOptionValue("weightingMethod");
            if (!"uniform".equals(this.weightingMethod.toLowerCase()) 
                    | !"gsc".equals(this.weightingMethod.toLowerCase()) 
                    | !"henikoff".equals(this.weightingMethod.toLowerCase()) 
                    | !"clustalw".equals(this.weightingMethod.toLowerCase()) 
                    | !"voltageflow".equals(this.weightingMethod.toLowerCase()) ) {
                System.err.println("ERROR: " + "invalid weightingMethod option");
                System.exit(0);
            }
        }

        if (commandLine.hasOption("isTreeWeightNeeded")) {

            String isTreeWeight = commandLine.getOptionValue("isTreeWeightNeeded");
            switch (isTreeWeight) {
                case "true":
                    this.isTreeWeightNeeded = true;
                    break;
                case "t":
                    this.isTreeWeightNeeded = true;
                    break;
                case "false":
                    this.isTreeWeightNeeded = false;
                    break;
                case "f":
                    this.isTreeWeightNeeded = false;
                    break;
                default:
                    this.isTreeWeightNeeded = false;
                    //System.err.println("ERROR: " + "Tree Weight method necessity not clear; check argument");

            }

        } else {
            this.isTreeWeightNeeded = false;
        }

        //Should the amplicons be generated from a set min and max product length?
        //If so, fill out the productLengthMin and productLengthMax
        //If not, choose forward and reverse min and max positions
        if (commandLine.hasOption("SlidingScale")) {

            String isSliding = commandLine.getOptionValue("SlidingScale");
            switch (isSliding) {
                case "true":
                    this.isAmpliconGeneratedSlidingScale = true;
                    break;
                case "t":
                    this.isAmpliconGeneratedSlidingScale = true;
                    break;
                case "false":
                    this.isAmpliconGeneratedSlidingScale = false;
                    break;
                case "f":
                    this.isAmpliconGeneratedSlidingScale = false;
                    break;
                default:
                    this.isAmpliconGeneratedSlidingScale = true;
                    //System.err.println("ERROR: " + "Sliding scale necessity not clear; check argument");

            }

        } else {
            this.isAmpliconGeneratedSlidingScale = true;
        }

        if (this.isAmpliconGeneratedSlidingScale) {

            if (commandLine.hasOption("productLengthMin")) {

                this.productLengthMin = Integer.parseInt(commandLine.getOptionValue("productLengthMin"));

            } else {

                //System.err.println("ERROR: " + "Program needed product length minimum, used default: 100");
                this.productLengthMin = 100;

            }
            if (commandLine.hasOption("productLengthMax")) {

                this.productLengthMax = Integer.parseInt(commandLine.getOptionValue("productLengthMax"));

            } else {

                //System.err.println("ERROR: " + "Program needed product length maximum, used default: 200");
                this.productLengthMax = 200;

            }

            this.forwardMaxPos = 120;
            this.forwardMinPos = 100;
            this.reverseMinPos = 350;
            this.reverseMaxPos = 370;

        } else {

            if (commandLine.hasOption("productLengthMin")) {

                this.productLengthMin = Integer.parseInt(commandLine.getOptionValue("productLengthMin"));

            } else {

                //System.err.println("ERROR: " + "Program needed product length minimum, used default: 100");
                this.productLengthMin = 100;

            }
            if (commandLine.hasOption("productLengthMax")) {

                this.productLengthMax = Integer.parseInt(commandLine.getOptionValue("productLengthMax"));

            } else {

                //System.err.println("ERROR: " + "Program needed product length maximum, used default: 200");
                this.productLengthMax = 200;

            }

            if (commandLine.hasOption("forwardMinPos")) {

                this.forwardMinPos = Integer.parseInt(commandLine.getOptionValue("forwardMinPos"));

            } else {

                //System.err.println("ERROR: " + "Program needed forward minimum position, used default: 100");
                this.forwardMinPos = 100;

            }

            if (commandLine.hasOption("forwardMaxPos")) {

                this.forwardMaxPos = Integer.parseInt(commandLine.getOptionValue("forwardMaxPos"));

            } else {
                //System.err.println("ERROR: " + "Program needed forward maximum position, used default: 120");
                this.forwardMaxPos = 120;
            }

            if (commandLine.hasOption("reverseMinPos")) {

                this.reverseMinPos = Integer.parseInt(commandLine.getOptionValue("reverseMinPos"));

            } else {
                //System.err.println("ERROR: " + "Program needed reverse minimum position, used default: 350");
                this.reverseMinPos = 350;
            }

            if (commandLine.hasOption("reverseMaxPos")) {

                this.reverseMaxPos = Integer.parseInt(commandLine.getOptionValue("reverseMaxPos"));

            } else {
                //System.err.println("ERROR: " + "Program needed reverse maximum position, used default: 370");
                this.reverseMaxPos = 370;
            }
        }

        if (commandLine.hasOption("assayMax")) {

            this.assayMax = Integer.parseInt(commandLine.getOptionValue("assayMax"));

        } else {
            //System.err.println("ERROR: " + "Program needs assay max, used default: 20");
            this.assayMax = 20;
        }

        if (commandLine.hasOption("degenMax")) {

            this.degenMax = Integer.parseInt(commandLine.getOptionValue("degenMax"));

        } else {
            //System.err.println("ERROR: " + "Program needs degen max, used default: 8");
            this.degenMax = 8;
        }

        if (commandLine.hasOption("maxMismatches")) {

            this.maxMismatches = Integer.parseInt(commandLine.getOptionValue("maxMismatches"));

        } else {
            //System.err.println("ERROR: " + "Program needs max num mismatches, used default: 2");
            this.maxMismatches = 0;
        }

        // Check input, store, and error output if problem
        //File with reference alligned sequences
        if (commandLine.hasOption("input")) {

            String fastaFile = commandLine.getOptionValue("input");
            this.sequenceFastaFile = new File(fastaFile);

        } else {
            System.err.println("ERROR: " + "Program needs sequence input fasta file to start");
            this.sequenceFastaFile = null;
        }
        
        if (commandLine.hasOption("customWeightInput")) {
            String weightFile = commandLine.getOptionValue("customWeightInput");
            this.customWeightFile = new File(weightFile);
        }
            

        if (commandLine.hasOption("NoPoly3GCFilter")) {

            String isPolyGC = commandLine.getOptionValue("NoPoly3GCFilter");
            switch (isPolyGC) {
                case "true":
                    this.isNo3GCFilter = true;
                    break;
                case "t":
                    this.isNo3GCFilter = true;
                    break;
                case "false":
                    this.isNo3GCFilter = false;
                    break;
                case "f":
                    this.isNo3GCFilter = false;
                    break;
                default:
                    this.isNo3GCFilter = true;
                    //System.err.println("ERROR: " + "Poly GC Filter necessity not clear; check argument");

            }

        } else {
            this.isNo3GCFilter = true;
        }

        if (commandLine.hasOption("NoTEndFilter")) {

            String tEnd = commandLine.getOptionValue("NoTEndFilter");
            switch (tEnd) {
                case "true":
                    this.isNoTEnd = true;
                    break;
                case "t":
                    this.isNoTEnd = true;
                    break;
                case "false":
                    this.isNoTEnd = false;
                    break;
                case "f":
                    this.isNoTEnd = false;
                    break;
                default:
                    this.isNoTEnd = true;
                    //System.err.println("ERROR: " + "T End Filter necessity not clear; check argument");

            }

        } else {
            this.isNoTEnd = true;
        }

        if (commandLine.hasOption("oligoMinSize")) {

            this.oligoMinSize = Integer.parseInt(commandLine.getOptionValue("oligoMinSize"));

        } else {
            //System.err.println("ERROR: " + "Program needs oligo min size, used default: 22");
            this.oligoMinSize = 22;
        }

        if (commandLine.hasOption("oligoMaxSize")) {

            this.oligoMaxSize = Integer.parseInt(commandLine.getOptionValue("oligoMaxSize"));

        } else {
            //System.err.println("ERROR: " + "Program needs oligo max size, used default: 30");
            this.oligoMaxSize = 30;
        }

        if (commandLine.hasOption("tempMin")) {

            this.tempMin = Double.parseDouble(commandLine.getOptionValue("tempMin"));

        } else {
            //System.err.println("ERROR: " + "Program needs melting temp min, used default: 55");
            this.tempMin = 55;
        }

        if (commandLine.hasOption("tempMax")) {

            this.tempMax = Double.parseDouble(commandLine.getOptionValue("tempMax"));

        } else {
            //System.err.println("ERROR: " + "Program needs melting temp max, used default: 63");
            this.tempMax = 63;
        }

        if (commandLine.hasOption("hairMax")) {

            this.hairMax = Double.parseDouble(commandLine.getOptionValue("hairMax"));

        } else {
            //System.err.println("ERROR: " + "Program needs hair max, used default: 35");
            this.hairMax = 35;
        }

        if (commandLine.hasOption("homoMax")) {

            this.homoMax = Double.parseDouble(commandLine.getOptionValue("homoMax"));

        } else {
            //System.err.println("ERROR: " + "Program needs homo max, used default: 35");
            this.homoMax = 35;
        }

        if (commandLine.hasOption("PolyRunFilter")) {

            this.polyRunFilterMax = Integer.parseInt(commandLine.getOptionValue("PolyRunFilter"));

        } else {
            //System.err.println("ERROR: " + "Program needs polyRunFilterMax, used default: 4");
            this.polyRunFilterMax = 4;
        }

        if (commandLine.hasOption("GCFilterMin")) {

            this.GCFilterMin = Double.parseDouble(commandLine.getOptionValue("GCFilterMin"));

        } else {
            //System.err.println("ERROR: " + "Program needs GC Content Filter Min, used default: 0.45");
            this.GCFilterMin = 0.45;
        }

        if (commandLine.hasOption("GCFilterMax")) {

            this.GCFilterMax = Double.parseDouble(commandLine.getOptionValue("GCFilterMax"));

        } else {
            //System.err.println("ERROR: " + "Program needs GC Content Filter Max, used default: 0.7");
            this.GCFilterMax = 0.7;
        }
        
        if (commandLine.hasOption("sodiumConc")) {

            this.sodiumConc = Double.parseDouble(commandLine.getOptionValue("sodiumConc"));

        } else {
            //System.err.println("ERROR: " + "Program needs Sodium Concentration, used default: 50");
            this.sodiumConc = 50.0;
        }
        
        if (commandLine.hasOption("magnesConc")) {

            this.magnesConc = Double.parseDouble(commandLine.getOptionValue("magnesConc"));

        } else {
            //System.err.println("ERROR: " + "Program needs magnesium concentration, used default: 1.5");
            this.magnesConc = 1.5;
        }

        //End
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

    public boolean getIsHenikoff() {
        return this.isHenikoffWeightNeeded;
    }
    
    public boolean getIsUniform() {
        return this.isUniformWeightNeeded;
    }

    public boolean getIsNoTEnd() {
        return this.isNoTEnd;
    }

    public boolean getIsNo3GCFilter() {
        return this.isNo3GCFilter;
    }

    public boolean getIsTreeWeight() {
        return this.isTreeWeightNeeded;
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

}
