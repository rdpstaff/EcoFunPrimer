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

import edu.msu.cme.rdp.primerdesign.weighting.nodes.WeightNode;
import edu.msu.cme.rdp.primerdesign.weighting.visitors.CurrentVisitor;
import edu.msu.cme.rdp.primerdesign.weighting.visitors.ResistanceVisitor;
import edu.msu.cme.rdp.taxatree.TaxonHolder;
import edu.msu.cme.rdp.taxatree.utils.NewickTreeBuilder;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hashsham, gunturus
 */
public class OhmWeighting implements Weighting {
    
    private final File file;
    private final Map<String, Double> weightMap = new HashMap();
    
    public OhmWeighting(File f){
        this.file = f;
        this.calcWeights();
    }
    
    
    @Override
    public final void calcWeights(){
        
        try{
            WeightNode root = new WeightNode(0, "root", "root_rank", 0, 0, 1, 0);
            InputStream is = new FileInputStream(this.file);
            
            NewickTreeBuilder builder = new NewickTreeBuilder(root, is, new NewickTreeBuilder.NewickTaxonFactory<WeightNode>() {
                @Override
                public WeightNode buildTaxon(int taxid, String name, float distance) {
                    return new WeightNode(taxid, name, "", distance, 0, 0, 0);
                }
            });
            
            TaxonHolder<WeightNode> rootNode = builder.getRoot().getRootTaxonHodler();
            
            ResistanceVisitor rv = new ResistanceVisitor();
            CurrentVisitor cv = new CurrentVisitor();
            
            rootNode.biDirectionDepthFirst(rv);
            rootNode.biDirectionDepthFirst(cv);

            this.weightMap.putAll(cv.getWeightMap()); 
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
