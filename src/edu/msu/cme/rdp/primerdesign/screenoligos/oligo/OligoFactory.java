/*
 * Copyright (C) 2015  Santosh Gunturu <gunturus at msu dot edu>
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

package edu.msu.cme.rdp.primerdesign.screenoligos.oligo;

import java.io.IOException;
import edu.msu.cme.rdp.primerdesign.utils.Primer3Wrapper;
import java.util.HashMap;
/**
 *  Creates Oligos
 * @author gunturus, tift
 */
public class OligoFactory {
      
    private static final HashMap<String, Oligo> oligoMap = new HashMap();
    
    public static Oligo getOligoFromFactory(String seq, Primer3Wrapper primer3, MismatchProperties calcObj, int start) throws IOException{
    
        Oligo oligoFromMap = (Oligo)oligoMap.get(seq);
        
        if(oligoFromMap == null) {
            oligoFromMap = createOligo(seq, primer3, calcObj, start);
        }
        return oligoFromMap;
    }
    
    public static Oligo createOligo(String seq, Primer3Wrapper primer3, MismatchProperties calcObj, int start) throws IOException {
        
        
        Oligo oligo = new Oligo(seq);
//        calcObj.setOligoToTest(oligo);
//        calcObj.setTemplate(IUBUtilities.complement(seq));
//        calcObj.setRevTemplate(IUBUtilities.reverseComplement(seq));
//        calcObj.calcDeltaHAndS(calcObj.getOligoToTest());
//        calcObj.calcTm(calcObj.getOligoToTest(), calcObj.getOligoToTest().getDeltaH(), calcObj.getOligoToTest().getDeltaS());
        oligo.setOligoTm(primer3.calcTemp(seq));
     //   oligo.setOligoTm(calcObj.getOligoToTest().getTm());
        oligo.setHairpinTm(primer3.calcHairpinTm(seq));
        oligo.setHomodimerTm(primer3.calcHomodimerTm(seq));
        oligoMap.put(seq, oligo);
        
        return oligo;
    }
    
    
    public static Oligo createOligo(String seq, double tm, double hairTm, double homoTm) throws IOException {
        Oligo oligo = new Oligo(seq);
        oligo.setOligoTm(tm);
        oligo.setHairpinTm(hairTm);
        oligo.setHomodimerTm(homoTm);
        oligoMap.put(seq, oligo);
        
        return oligo;
    }
    
    public static Oligo createOligo(String seq) throws IOException {
        Oligo oligo = new Oligo(seq);
        oligoMap.put(seq, oligo);
        
        return oligo;
    }
}
