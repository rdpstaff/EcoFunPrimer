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
 * @author leotift
 */
public class ScreenUserInput {

    private final String dirToParserGraph;
    private final File sequenceFastaFile;
    private final double tempMin;
    private final double tempMax;
    private final double hairMax;
    private final double homoMax;
    private final double sodiumConc;
    private final double magnesConc;
    private final int oligoMinSize;
    private final int oligoMaxSize;
    private final boolean isNoTEnd;
    private final boolean isNo3GCFilter;
    private final int polyRunFilterMax;
    private final double GCFilterMin;
    private final double GCFilterMax;
    private final int degenMax;
    private final int maxMismatches;
    private final String osType;
    /**
     *
     * @param commandLine
     * @throws IOException
     */
    public ScreenUserInput(CommandLine commandLine) throws IOException {
        
        if (commandLine.hasOption("degenMax")) {

            this.degenMax = Integer.parseInt(commandLine.getOptionValue("degenMax"));

        } else {
            //System.err.println("ERROR: " + "Program needs degen max, used default: 8");
            this.degenMax = 8;
        }
        
        if (commandLine.hasOption("os")) {

            this.osType = commandLine.getOptionValue("os");

        } else {
           // System.err.println("ERROR: " + "Program needs os type, used default: mac");
            this.osType = "mac";
        }


        if (commandLine.hasOption("maxMismatches")) {

            this.maxMismatches = Integer.parseInt(commandLine.getOptionValue("maxMismatches"));

        } else {
            //System.err.println("ERROR: " + "Program needs max num mismatches, used default: 2");
            this.maxMismatches = 2;
        }

        if (commandLine.hasOption("GraphOutput")) {
            this.dirToParserGraph = commandLine.getOptionValue("GraphOutput");
            File testFile = new File(this.dirToParserGraph);

        } else {
            System.err.println("ERROR: " + "No valid graph output directory given");
            this.dirToParserGraph = null;

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
                   // System.err.println("ERROR: " + "Poly GC Filter necessity not clear; check argument");

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
                   // System.err.println("ERROR: " + "T End Filter necessity not clear; check argument");

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
           // System.err.println("ERROR: " + "Program needs oligo max size, used default: 30");
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
           // System.err.println("ERROR: " + "Program needs polyRunFilterMax, used default: 4");
            this.polyRunFilterMax = 4;
        }

        if (commandLine.hasOption("GCFilterMin")) {

            this.GCFilterMin = Double.parseDouble(commandLine.getOptionValue("GCFilterMin"));

        } else {
           // System.err.println("ERROR: " + "Program needs GC Content Filter Min, used default: 0.45");
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

    }

    public File getSequenceFastaFile() {
        return this.sequenceFastaFile;
    }

    public String getGraphDirectory() {
        return this.dirToParserGraph;
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
    public String getOSType() {
        return this.osType;
    }

}
