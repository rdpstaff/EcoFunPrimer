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


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author leotift
 */
public class PrimerDesignViewer {
    
    private static Map<File, List<Integer>> fileInfoKeyMap;
    private static Map<Integer, String> infoDescMap;
    private int currentInfoDescInteger;
    
    public PrimerDesignViewer () {
        this.currentInfoDescInteger = -1;
        this.fileInfoKeyMap = new HashMap <> ();
        this.infoDescMap = new HashMap <> ();
    }

    public void printAllToFile(File file) throws IOException {

        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        List<Integer> infoKeyList = this.fileInfoKeyMap.get(file);
        try {            
                for (int i = 0; i < infoKeyList.size(); i++) {
                    String info = this.infoDescMap.get(infoKeyList.get(i));
                    bw.write(info);
                    bw.write('\n');
                }

            bw.close();

        } catch (IOException e) {
            System.out.println("Error: Printing" + e);
        }

    }
    
  
    
    public void addInfoToFile (File file, String info){
        List<Integer> tempIntList;
        
        if(this.fileInfoKeyMap.containsKey(file)){
            tempIntList = this.fileInfoKeyMap.get(file);
            tempIntList.add(this.getNextInfoDesc());
            this.fileInfoKeyMap.put(file, tempIntList);
            this.infoDescMap.put(this.currentInfoDescInteger, info);       
        } else {
            tempIntList = new ArrayList<> ();
            tempIntList.add(this.getNextInfoDesc());
            this.fileInfoKeyMap.put(file, tempIntList);
            this.infoDescMap.put(this.currentInfoDescInteger, info);  
            
        }
    
    }
    
    private int getNextInfoDesc (){
        this.currentInfoDescInteger++;
        return this.currentInfoDescInteger;
       
    }
    
    public Map<File, List<Integer>> getFileInfoMap () {
            return this.fileInfoKeyMap;
    }
    
    public Map<Integer, String> getInfoDescMap () {
            return this.infoDescMap;
    }
}
