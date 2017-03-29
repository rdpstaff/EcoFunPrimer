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

import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.DegenerateCharTable;
import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.DegeneratePair;
import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.Oligo;
import edu.msu.cme.rdp.primerdesign.utils.comparator.OligoTmComparator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author tift
 */
public class DegenerateCharTest {

    public DegenerateCharTest() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void test() throws IOException {
        Oligo o1 = new Oligo("ACTAGCAA");
        Oligo o2 = new Oligo("ACTAGCAA");
        Oligo o3 = new Oligo("CCTAGCAC");
        Oligo o4 = new Oligo("TCTAGCAC");

        Oligo o5 = new Oligo("TACCTGAA");
        Oligo o6 = new Oligo("ACGCGGCA");
        Oligo o7 = new Oligo("CCGCGCAC");
        Oligo o8 = new Oligo("AGCTTCGA");

        Set<Oligo> fwdList = new HashSet<>();
        Set<Oligo> revList = new HashSet<>();
        fwdList.add(o2);
        fwdList.add(o3);
        fwdList.add(o1);
        fwdList.add(o4);

        revList.add(o5);
        revList.add(o6);
        revList.add(o7);
        revList.add(o8);
        
        Set<String> targetSet = new HashSet<> ();
        
        
        DegenerateCharTable table = new DegenerateCharTable();
        
      
//        DegeneratePair pair = new DegeneratePair(new HashMap<Set<String>, ArrayList<Oligo>> (), new HashMap<Set<String>, ArrayList<Oligo>> (), fwdList, revList, targetSet, 40, 0, 300, 8, table);
//        pair.buildDegenOligos();
//       
//        System.out.println(pair.getFwdDegenOligo());
//        System.out.println(pair.getRevDegenOligo());
//        System.out.println(pair.getActualFwdDegen());
//        System.out.println(pair.getActualRevDegen());
        
    }

}
