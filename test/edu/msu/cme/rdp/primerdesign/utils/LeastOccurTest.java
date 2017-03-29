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

package edu.msu.cme.rdp.primerdesign.utils;

import edu.msu.cme.rdp.readseq.readers.Sequence;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author leotift
 */
public class LeastOccurTest {
    
       public LeastOccurTest() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    //
    
    @Test
    public void test() throws IOException {
      
      System.out.print("Position");
      System.out.print('\t');
      System.out.println("[Highest % Occur,Lowest % Occur]");
      
      Sequence oligo1 = new Sequence("seq1","","CGTTGA");
      Sequence oligo2 = new Sequence("seq1","","CGTTGA");
      Sequence oligo3 = new Sequence("seq1","","CATTGT");
      Sequence oligo4 = new Sequence("seq1","","GTATAT");
      List<Sequence> allSeqs = new ArrayList();
      allSeqs.add(oligo1);
      allSeqs.add(oligo2);
      allSeqs.add(oligo3);
      allSeqs.add(oligo4);
      RefSetAnalysis analysis = new RefSetAnalysis();
      analysis.buildBasePositionMap(allSeqs);
      
      for(int position : analysis.getBasePositionMap().keySet()) {
            System.out.print(position);
            System.out.print('\t');
            System.out.print('\t');
            System.out.print('\t');
            System.out.println(analysis.getBasePositionMap().get(position));
        }
      
          
        
        
    }
    
}
