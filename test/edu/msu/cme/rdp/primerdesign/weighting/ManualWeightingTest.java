/*
 * Copyright (C) 2018 hashsham
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

import java.io.File;
import org.junit.Test;

/**
 *
 * @author hashsham
 */
public class ManualWeightingTest {
    
    @Test
    public void testManualWeighting(){
        File f = new File("/home/hashsham/Desktop/testWeights.txt");
        
        ManualWeighting mw = new ManualWeighting(f);
        
        System.out.println(mw.getWeights());
    }
}
