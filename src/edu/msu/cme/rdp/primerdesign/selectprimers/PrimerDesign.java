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
import java.util.Arrays;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.OptionBuilder;

/**
 *
 * @author gunturus, tift
 */
public class PrimerDesign {
    
    private static void printUsageAndExit() {
        System.err.println("USAGE: java -jar EcoFunPrimer.jar <subcommand> <options>");
        System.err.println("\tscreen     - Screen for primer regions with for the best coverage at each position");
        System.err.println("\tselect     - Select primer pairs using  slidescale or user defined fwd and rev primer positions");
        System.exit(1);
    }

    public static void main(String[] args) throws Exception {
        if(args.length < 1) {
            printUsageAndExit();
        }
        
        String subcommand = args[0];
        
        args = Arrays.copyOfRange(args, 1, args.length);
        
        if(subcommand.equals("screen")) {
            ScreenUserInput.main(args);
        }
        else if(subcommand.equals("select")) {
            SelectUserInput.main(args);
        }
        else {
            printUsageAndExit();
        }
    }

}