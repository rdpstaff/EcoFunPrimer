/*
 * Copyright (C) 2018  Michigan State University <rdpstaff at msu dot edu>
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

import edu.msu.cme.rdp.readseq.readers.Sequence;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author gunturus
 */
public final class UniformWeighting implements Weighting{
    
    private final List<Sequence> seqs;
    private final Map<String, Double> weightMap = new HashMap();
    
    public UniformWeighting(List<Sequence> s) {
        this.seqs = s;
        this.calcWeights();
    }
    
    @Override
    public void calcWeights() {
        
        double uniformWeight = 1.0/seqs.size();
        for(int j = 0; j < seqs.size(); j++) {
            weightMap.put(seqs.get(j).getSeqName(),uniformWeight);
        }
    }

    @Override
    public Map<String, Double> getWeights() {
        return weightMap;
    }
    
    
    
}
