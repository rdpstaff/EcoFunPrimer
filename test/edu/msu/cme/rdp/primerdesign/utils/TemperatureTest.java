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

import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.MismatchProperties;
import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.Oligo;
import edu.msu.cme.rdp.readseq.utils.IUBUtilities;
import java.io.IOException;
import org.junit.Test;

/**
 *
 * @author xingziye
 */
public class TemperatureTest {
    
    public TemperatureTest() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void testTm() throws IOException {
        Primer3Wrapper primer3 = new Primer3Wrapper("mac", 50.0, 1.5);
        Oligo oligo1 = new Oligo("TTCTCCTGCGAAGGCTA");
        Oligo oligo2 = new Oligo("TTCTCCTGCGAGGGCTA");
        Oligo oligo3 = new Oligo("TTTAGCTGTGAAGGATA");
        Oligo oligo4 = new Oligo("TTTAGCTGTGAAGGATA");
        Oligo oligo5 = new Oligo("AACTCCTGCGAAGGCTA");
        Oligo oligo6 = new Oligo("TTTCCTTGTGAAGGATA");
        Oligo oligo7 = new Oligo("TTCAGCTGTGAAGGCTA");
        Oligo oligo8 = new Oligo("TTCCCTTGTGAAGGATA");
        Oligo oligo9 = new Oligo("TTTAGTTGTGAAGGCTA");
        Oligo oligo10 = new Oligo("TTCTCCTGTGAAGGCTA");
        Oligo oligo11 = new Oligo("TTCAGCTGCGAAGGCTA");
        Oligo oligo12 = new Oligo("TTTAGCTGTGAAGGTTA");
        Oligo oligo13 = new Oligo("TTTAGCTGTGAAGGCTA");
        Oligo oligo14 = new Oligo("TTTGACTGTGAAGGGTA");
        Oligo oligo15 = new Oligo("TTCAACTGCGAGGGCTA");
       
        
        
        System.out.println(primer3.calcTemp(oligo1.getSeq()));
        System.out.println(primer3.calcTemp(oligo2.getSeq()));
        System.out.println(primer3.calcTemp(oligo3.getSeq()));
        System.out.println(primer3.calcTemp(oligo4.getSeq()));
        System.out.println(primer3.calcTemp(oligo5.getSeq()));
        System.out.println(primer3.calcTemp(oligo6.getSeq()));
        System.out.println(primer3.calcTemp(oligo7.getSeq()));
        System.out.println(primer3.calcTemp(oligo8.getSeq()));
        System.out.println(primer3.calcTemp(oligo9.getSeq()));
        System.out.println(primer3.calcTemp(oligo10.getSeq()));
        System.out.println(primer3.calcTemp(oligo11.getSeq()));
        System.out.println(primer3.calcTemp(oligo12.getSeq()));
        System.out.println(primer3.calcTemp(oligo13.getSeq()));
        System.out.println(primer3.calcTemp(oligo14.getSeq()));
        System.out.println(primer3.calcTemp(oligo15.getSeq()));
        
        














//        System.out.println(primer3.calcHairpinTm(oligo1.getSeq()));
//        System.out.println(primer3.calcHomodimerTm(oligo1.getSeq()));
        
//        System.out.println("New method");
//        Oligo oligo2 = new Oligo("GCTAGCCCTAGAGATCATA");
//       
//        MismatchProperties calcObj = new MismatchProperties(oligo2);
//        calcObj.calcDeltaHAndS(calcObj.getOligoToTest());
//        calcObj.calcActualDeltaGs(calcObj.getOligoToTest(), calcObj.getOligoToTest().getDeltaH(), calcObj.getOligoToTest().getDeltaS());
//        calcObj.calcTm(calcObj.getOligoToTest(), calcObj.getOligoToTest().getDeltaH(), calcObj.getOligoToTest().getDeltaS());
//        System.out.println("Predicted Delta G: " + calcObj.calcPredictedDeltaGs(calcObj.getOligoToTest()));
//        System.out.println("Actual Delta G: " + calcObj.getOligoToTest().getDeltaG());
//        System.out.println("Tm: " + calcObj.getOligoToTest().getTm());
    }
}
