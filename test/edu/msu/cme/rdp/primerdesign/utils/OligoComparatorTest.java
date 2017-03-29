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

import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.Oligo;
import edu.msu.cme.rdp.primerdesign.utils.comparator.OligoTmComparator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author xingziye
 */
public class OligoComparatorTest {
    
    public OligoComparatorTest() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void test() throws IOException {
        Oligo o1 = new Oligo("GACCT");
        Oligo o2 = new Oligo("ACG");
        Oligo o3 = new Oligo("TCTAGCAC");
        o1.setOligoTm(50.4);
        o2.setOligoTm(49.5);
        
        Comparator c = new OligoTmComparator();
        assertEquals(-1 ,c.compare(o1, o2));
        
        o2.setOligoTm(50.4);
        assertEquals(0, c.compare(o1, o2));
        
        o2.setOligoTm(51.6);
        assertEquals(1, c.compare(o1, o2));
        
        List<Oligo> list = new ArrayList<>();
        list.add(o2);
        list.add(o3);
        list.add(o1);
        
        Collections.sort(list, new OligoTmComparator());
        for (Oligo oligo : list) {
            System.out.println(oligo.getSeq() + " " + oligo.getTm());
        }
    }
}
