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

package edu.msu.cme.rdp.primerdesign.weighting;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hashsham, gunturus
 */

// Takes in weights from a two column, tab separated file of strings and doubles
public final class ManualWeighting implements Weighting{
    
    private final File file;
    private final Map<String, Double> weightMap = new HashMap();
    
    public ManualWeighting(File f){
        this.file = f;
        this.calcWeights();
    }
    
    @Override
    public void calcWeights() {
        try{
            BufferedReader br = new BufferedReader(new FileReader(this.file));

            while(true){
                String row = br.readLine();
                if(row != null){
                    weightMap.put(row.split("\t")[0], Double.parseDouble(row.split("\t")[1]));
                }
                else{
                    break;
                }
            }
        }
        
        catch(java.io.IOException e){
            System.err.println(e);
        }
    }
    
    @Override
    public Map<String, Double> getWeights(){
        double totalWeight = 0.0;
        for(String key : this.weightMap.keySet()) {
            totalWeight = totalWeight + this.weightMap.get(key);
        }
        
        for(String key : this.weightMap.keySet()) {
            this.weightMap.put(key, this.weightMap.get(key)/totalWeight);
        }
        return this.weightMap;
    }
}
