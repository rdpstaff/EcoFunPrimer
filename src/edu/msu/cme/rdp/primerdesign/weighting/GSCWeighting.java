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

import edu.msu.cme.rdp.primerdesign.weighting.nodes.GSCNode;
import edu.msu.cme.rdp.primerdesign.weighting.visitors.GSCWeightingVisitor;
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
public class GSCWeighting implements Weighting {
    
    private final File file;
    private final Map<String, Double> weightMap = new HashMap();
    
    public GSCWeighting(File f){
        this.file = f;
        this.calcWeights();
    }
    
    @Override
    public final void calcWeights(){
        
        try{
            GSCNode root = new GSCNode(0, "root", "rank", 0);
            InputStream is = new FileInputStream(file);
            
            NewickTreeBuilder builder = new NewickTreeBuilder(root, is, new NewickTreeBuilder.NewickTaxonFactory<GSCNode>() {
                @Override
                public GSCNode buildTaxon(int taxid, String name, float distance) {
                 return new GSCNode(taxid, name, "", distance);
                }
            });
            
            TaxonHolder<GSCNode> rootNode = builder.getRoot().getRootTaxonHodler();
            GSCWeightingVisitor gv = new GSCWeightingVisitor();
        
            rootNode.biDirectionDepthFirst(gv);
            
            this.weightMap.putAll(gv.getWeightMap());
        }
        
        catch(java.io.IOException e){
            System.err.println(e);
        }
        double totalWeight = 0.0;
        for(String key : weightMap.keySet()) {
            totalWeight = totalWeight + weightMap.get(key);
        }
        
        for(String key : weightMap.keySet()) {
            weightMap.put(key, weightMap.get(key)/totalWeight);
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
