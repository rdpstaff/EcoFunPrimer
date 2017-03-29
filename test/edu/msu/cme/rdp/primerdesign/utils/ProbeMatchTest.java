/*
 * Copyright (C) 2017 Michigan State University Board of Trustees
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

/**
 *
 * @author leotift
 */
public class ProbeMatchTest {

    public ProbeMatchTest() {
    }

    @Test
    public void test() throws FileNotFoundException, IOException {

        File forwardFile = new File("/work/leotift/nifDgr3EntropyKmer8");
        
        File outputFile = new File("/work/leotift/nifDgr3EntropyKmer8Output");
        
        FileWriter fw;
        fw = new FileWriter(outputFile.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        
        FileReader forReader = new FileReader(forwardFile);
        BufferedReader forBuff = new BufferedReader(forReader);
        
        String fwdstrLine;
        int start = 0;
        
        //Read File Line By Line
        while ((fwdstrLine = forBuff.readLine()) != null) {
            String[] array = new String[5];
            array = fwdstrLine.split("\t");
            bw.write(start + "\t" + array[0] + "\n");
            start++;
        }
              
                
        bw.close();
        forBuff.close();
//        File reverseFile = new File("/work/leotift/nifD16DegenR1");
//
//        Set<String> forSet = new HashSet<>();
//        Set<String> revSet = new HashSet<>();
//        
//        FileReader forReader = new FileReader(forwardFile);
//        BufferedReader forBuff = new BufferedReader(forReader);
//        
//        FileReader revReader = new FileReader(reverseFile);
//        BufferedReader revBuff = new BufferedReader(revReader);
//
//        String fwdstrLine;
//        String revstrLine;
//        //Read File Line By Line
//        while ((fwdstrLine = forBuff.readLine()) != null) {
//            String[] array = new String[20];
//            array = fwdstrLine.split("\t");
//            forSet.add(array[0]);
//        }
//        
//        while ((revstrLine = revBuff.readLine()) != null) {
//            String[] array = new String[20];
//            array = revstrLine.split("\t");
//            revSet.add(array[0]);
//        }
//        
//        System.out.println(forSet.size());
//        System.out.println(revSet.size());
//        revSet.retainAll(forSet);
//        System.out.println(revSet.size());
//        
//        for(String name : revSet) {
//            System.out.println(name);
//        }
//      
        
        
        
    }
}

