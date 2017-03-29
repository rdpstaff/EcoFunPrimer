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

package edu.msu.cme.rdp.primerdesign.screenoligos;

import edu.msu.cme.rdp.primerdesign.screenoligos.filter.NoPoly3GCFilter;
import edu.msu.cme.rdp.primerdesign.screenoligos.filter.GCContentFilter;
import edu.msu.cme.rdp.primerdesign.screenoligos.filter.NoTEndFilter;
import edu.msu.cme.rdp.primerdesign.screenoligos.filter.PolyRunFilter;
import edu.msu.cme.rdp.primerdesign.screenoligos.filter.OligoBaseFilter;
import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.OligoFactory;
import java.io.IOException;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author xingziye
 */
public class OligoFilterTest {

    @Test
    public void testGCContentFilter() throws IOException {
        OligoBaseFilter filter = new GCContentFilter(0.25, 0.75);
        assertTrue(filter.check(OligoFactory.createOligo("CGATATCC")));
        assertFalse(filter.check(OligoFactory.createOligo("CATTATGAT")));
    }
    
    @Test
    public void testNoPoly3GCFilter() throws IOException {
        OligoBaseFilter filter = new NoPoly3GCFilter();
        assertTrue(filter.check(OligoFactory.createOligo("CGATCGATATCGATCG")));
        assertFalse(filter.check(OligoFactory.createOligo("AACGTTAGGCCTAGGG")));
    }
    
    @Test
    public void testNoTEndFilter() throws IOException {
        OligoBaseFilter filter = new NoTEndFilter();
        assertTrue(filter.check(OligoFactory.createOligo("CGATCGATATCGATCG")));
        assertFalse(filter.check(OligoFactory.createOligo("AACCCCGTTAGGCCTT")));
    }
    
    @Test
    public void testPolyRunFilter() throws IOException {
        OligoBaseFilter filter = new PolyRunFilter(4);
        assertTrue(filter.check(OligoFactory.createOligo("CGATGATTAACGATCG")));
        assertFalse(filter.check(OligoFactory.createOligo("ACCCGTTTTAGGCCTA")));
    }
}
