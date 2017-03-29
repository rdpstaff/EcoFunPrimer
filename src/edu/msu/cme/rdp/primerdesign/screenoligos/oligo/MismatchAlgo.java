/*
 * Copyright (C) 2015 leotift
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
import java.util.HashSet;
import java.util.Set;

/**
 * Main class that initiates the mismatch search algorithm.
 * @author tift
 */
public class MismatchAlgo {
    
    private Oligo perfectMatchOligo;
    
    private String primerSeq; // String to construct mismatches off of
    private String templateSeq; // template the primer will bind to
    private Set<Oligo> oligoMismatchList; //mismatch list plus perfect match oligo
    
    
    
    public MismatchAlgo(Oligo perfectMatch, String templateSeq, Set<Oligo> possibleMismatches) throws IOException{
         this.perfectMatchOligo = perfectMatch;
         this.primerSeq = perfectMatch.getSeq();
         this.templateSeq = templateSeq;
         
         
         oligoMismatchList = new HashSet<> ();
         oligoMismatchList.addAll(possibleMismatches);
    }
    
    public String getPrimerSeq() {
        return this.primerSeq;
    }
    
    public String getTemplateSeq() {
        return this.templateSeq;
    }
   
    
    public void setPrimerSeq(String primerSeq) {
        this.primerSeq = primerSeq;
    }
    
    public void setTemplateSeq(String templateSeq) {
        this.templateSeq = templateSeq;
    }
    
    
    public Set<Oligo> findMismatches() throws IOException {
        MismatchProperties calcObj = new MismatchProperties(this.oligoMismatchList, this.templateSeq);   

        Set<Oligo> badMismatches = new HashSet<> ();
        for (Oligo oligoToTest: this.oligoMismatchList) {
            calcObj.setOligoToTest(oligoToTest);
            calcObj.calcDeltaHAndS(calcObj.getOligoToTest());
            calcObj.calcActualDeltaGs(calcObj.getOligoToTest(), calcObj.getOligoToTest().getDeltaH(), calcObj.getOligoToTest().getDeltaS());
            calcObj.calcTm(calcObj.getOligoToTest(), calcObj.getOligoToTest().getDeltaH(), calcObj.getOligoToTest().getDeltaS());
            if (calcObj.getOligoToTest().getDeltaG() > 0 || calcObj.getOligoToTest().getTm() < 0) {
                badMismatches.add(oligoToTest);
            }
        }       
        oligoMismatchList.removeAll(badMismatches);
        return oligoMismatchList;
    }
}
