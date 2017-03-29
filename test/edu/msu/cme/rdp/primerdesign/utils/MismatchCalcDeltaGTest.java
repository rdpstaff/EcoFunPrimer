/*
 * Copyright (C) 2015 Michigan State University Board of Trustees
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

import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.MismatchProperties;
import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.Oligo;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

/**
 *
 * @author leotift
 */
public class MismatchCalcDeltaGTest {
    
      public MismatchCalcDeltaGTest() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    //
    
    @Test
    public void test() throws IOException {
      
      Primer3Wrapper primer3 = new Primer3Wrapper("mac", 50.0, 1.5);
      Oligo oligo1 = new Oligo("CGTTGA");
      System.out.println("Primer3 Tm: " + primer3.calcTemp(oligo1.getSeq()));
        
      
      Oligo mismatch = new Oligo("CGTTGA");
      MismatchProperties calcObj = new MismatchProperties(mismatch);
      calcObj.calcDeltaHAndS(mismatch);
      calcObj.calcActualDeltaGs(calcObj.getOligoToTest(), calcObj.getOligoToTest().getDeltaH(), calcObj.getOligoToTest().getDeltaS());
      System.out.println(calcObj.getOligoToTest().getDeltaG());
      
      calcObj.calcTm(calcObj.getOligoToTest(), calcObj.getOligoToTest().getDeltaH(), calcObj.getOligoToTest().getDeltaS());
      System.out.println(calcObj.getOligoToTest().getTm());
      
          
        
        
    }
    
}
