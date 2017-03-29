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

import edu.msu.cme.rdp.primerdesign.selectprimers.PrimerDesign;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Used to plot the Screen Oligos results in the first process Used in
 * OligosInfoGrapher.java
 *
 * @author xingziye
 */
public class PlotWrapper {

    private static String sourse;

    public static void plot(String coverageFile, String entropyFile, String outputFile) throws IOException {

        String figWidth = Integer.toString(20);
        String figHeight = Integer.toString(10);
        String figDPI = Integer.toString(135);

        ProcessBuilder pb = new ProcessBuilder("python", sourse, coverageFile, entropyFile, outputFile, figWidth, figHeight, figDPI);
        Process p = pb.start();
        InputStream is = p.getErrorStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }

    public PlotWrapper() {

        URL url = PrimerDesign.class.getProtectionDomain().getCodeSource().getLocation();
        String pathString = null;
        try {
            pathString = new File(url.toURI()).getParent();
        } catch (URISyntaxException ex) {
            Logger.getLogger(PlotWrapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        sourse = pathString + "/primer3/primersplot.py";
    }

}
