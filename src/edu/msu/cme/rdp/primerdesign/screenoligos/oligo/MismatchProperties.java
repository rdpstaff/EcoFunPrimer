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

package edu.msu.cme.rdp.primerdesign.screenoligos.oligo;

import edu.msu.cme.rdp.primerdesign.utils.MismatchDuplex;
import edu.msu.cme.rdp.primerdesign.utils.TableRetrieve;
import edu.msu.cme.rdp.readseq.utils.IUBUtilities;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author leotift
 */
public class MismatchProperties {
    
    private Set<Oligo> allPossibleMismatches;
    private Oligo oligoToTest; // If testing a single oligo at a time
    private String template;
    private String revTemplate;
    private final double dnaConc = 3.95;
    private final double Rconstant =  1.9872;
    private final double mono = 50.0; // Conc of monovalent cations
    private final double diva = 1.5; //  Conc of divalent cations
    private final double dntp = 0.8; // ntp concentration
    private final double oligo = 50.0; // DNA concentration
    private final double temp_c = 37; // temp in C
   
    
    public MismatchProperties(Set<Oligo> mismatches, String templateSeq) throws IOException {
        oligoToTest = new Oligo("");
        allPossibleMismatches = mismatches;
        template = templateSeq;       
        revTemplate = IUBUtilities.reverse(template);
    }
    
    public MismatchProperties(Oligo oligo) {
        oligoToTest = oligo;       
        template = IUBUtilities.complement(oligo.getSeq());       
        revTemplate = IUBUtilities.reverse(template);
    }
    
    public double calcPredictedDeltaGs (Oligo oligo) {
            MismatchDuplex myRetrieveObj = MismatchDuplex.getInstance();
            double totalDeltaG;
        
            String mismatchStr = oligo.getSeq().toString();
            totalDeltaG = 0.0;
            
            // Initiation cost is mandatory
            myRetrieveObj.setFirstBase('I');
            myRetrieveObj.setSecondBase('N');
            myRetrieveObj.setThirdBase('I');
            myRetrieveObj.setFourthBase('T');
            TableRetrieve.retrieve(myRetrieveObj, 'G');
            Double deltaGINIT = myRetrieveObj.getValue();
            totalDeltaG += deltaGINIT;
            
            
            //is TERM, SYMM
            
            if (this.isSymmetric(mismatchStr)) {
                myRetrieveObj.setFirstBase('S');
                myRetrieveObj.setSecondBase('Y');
                myRetrieveObj.setThirdBase('M');
                myRetrieveObj.setFourthBase('M');
                TableRetrieve.retrieve(myRetrieveObj,'G');
                Double deltaGSYMM = myRetrieveObj.getValue();
                totalDeltaG += deltaGSYMM;
                
            }
            
            if (this.isATTerm(mismatchStr, this.template)) {
                myRetrieveObj.setFirstBase('T');
                myRetrieveObj.setSecondBase('E');
                myRetrieveObj.setThirdBase('R');
                myRetrieveObj.setFourthBase('M');
                TableRetrieve.retrieve(myRetrieveObj,'G');
                Double deltaGTERM = myRetrieveObj.getValue();
                totalDeltaG += deltaGTERM;
                
            }
            
            for(int i = 0; i < (mismatchStr.length() - 1); i++) {
                
                myRetrieveObj.setFirstBase(mismatchStr.charAt(i));
                myRetrieveObj.setSecondBase(mismatchStr.charAt(i + 1));
                myRetrieveObj.setThirdBase(this.template.charAt(i));
                myRetrieveObj.setFourthBase(this.template.charAt(i + 1));
                
                TableRetrieve.retrieve(myRetrieveObj, 'G');
                Double deltaG1 = myRetrieveObj.getValue();
                totalDeltaG += deltaG1;
                                
            }
            return  totalDeltaG;
//           this.oligoToTest.setDeltaG(totalDeltaG);
        
    }
    
    public void calcDeltaHAndS (Oligo oligo) {
            MismatchDuplex myRetrieveObj = MismatchDuplex.getInstance();
            double totalDeltaH = 0.0;
            double totalDeltaS = 0.0;
        
            String mismatchStr = oligo.getSeq().toString();
            
            String initBegin = "IN" + mismatchStr.charAt(0) + this.template.charAt(0);
            String initEnd = "IN" + mismatchStr.charAt(mismatchStr.length() - 1) + this.template.charAt(mismatchStr.length() - 1);
            
            Set<String> possibleINIT = new HashSet<> ();
            possibleINIT.add("INCG");
            possibleINIT.add("INGC");
            possibleINIT.add("INAT");
            possibleINIT.add("INTA");
            
            if (possibleINIT.contains(initBegin) && possibleINIT.contains(initEnd)) {
                
                myRetrieveObj.setFirstBase('I');
                myRetrieveObj.setSecondBase('N');
                myRetrieveObj.setThirdBase(initBegin.charAt(2));
                myRetrieveObj.setFourthBase(initBegin.charAt(3));
                
                TableRetrieve.retrieve(myRetrieveObj, 'H');
                Double dHINITBegin = myRetrieveObj.getValue();
                totalDeltaH += dHINITBegin;
                
                TableRetrieve.retrieve(myRetrieveObj, 'S');
                Double dSINITBegin = myRetrieveObj.getValue();
                totalDeltaS += dSINITBegin;
                
                myRetrieveObj.setFirstBase('I');
                myRetrieveObj.setSecondBase('N');
                myRetrieveObj.setThirdBase(initEnd.charAt(2));
                myRetrieveObj.setFourthBase(initEnd.charAt(3));
                
                TableRetrieve.retrieve(myRetrieveObj, 'H');
                Double dHINITEnd = myRetrieveObj.getValue();
                totalDeltaH += dHINITEnd;
                                                                            
                TableRetrieve.retrieve(myRetrieveObj, 'S');
                Double dSINITEnd = myRetrieveObj.getValue();
                totalDeltaS += dSINITEnd;
                
                
            }
                                                            
            for(int i = 0; i < (mismatchStr.length() - 1); i++) {
                
                myRetrieveObj.setFirstBase(mismatchStr.charAt(i));
                myRetrieveObj.setSecondBase(mismatchStr.charAt(i + 1));
                myRetrieveObj.setThirdBase(this.template.charAt(i));
                myRetrieveObj.setFourthBase(this.template.charAt(i + 1));
                
                TableRetrieve.retrieve(myRetrieveObj, 'H');
                Double deltaH = myRetrieveObj.getValue();
                totalDeltaH += deltaH;
                
                TableRetrieve.retrieve(myRetrieveObj, 'S');
                Double deltaS = myRetrieveObj.getValue();
                totalDeltaS += deltaS;
                                
            }
           this.oligoToTest.setDeltaH(totalDeltaH);
           this.oligoToTest.setDeltaS(totalDeltaS);
        
    }
    
     public void calcActualDeltaGs (Oligo oligo, double dH, double dS) {
                         
         double tao = 273.15 + this.temp_c;
         
         double monoConc = this.mono + divalent2mono(this.diva, this.dntp);
         monoConc /= 1000;
         
         dS = dS + 0.368 * (oligo.getSeq().length() - 1) * Math.log(monoConc);
         
         double dG = (dH * 1000 - tao * dS) / 1000;
         this.oligoToTest.setDeltaG(dG);
     }
     
     public void calcTm (Oligo oligo, double dH, double dS) {
                
         dH *= 1000;
         
         double oligoConc = this.oligo / 1000000000;
         
         double monoConc = this.mono + divalent2mono(this.diva, this.dntp);
         monoConc /= 1000;
                  
         dS = dS + 0.368 * (oligo.getSeq().length() - 1) * Math.log(monoConc);
         
        int concDivider;
        if(isSymmetric(oligo.getSeq())) {
            concDivider = 1;
        } else {
            concDivider = 4;
        }
         
         double Tm = dH / (dS + this.Rconstant * Math.log(oligoConc / concDivider)) - 273.15;
         this.oligoToTest.setOligoTm(Tm);
     }
     
// Not accurate - commonly used as an estimate 
     
//    public void calcTmFromDeltaG (Oligo oligo) {
//        
//        double DeltaG = oligo.getDeltaG();       
//        int concDivider;
//        if(isSymmetric(oligo.getSeq())) {
//            concDivider = 1;
//        } else {
//            concDivider = 4;
//        }
//        double Tm =  (DeltaG / (this.Rconstant * (Math.log(this.dnaConc/concDivider)))) - 273.15;
//        this.oligoToTest.setOligoTm(Tm);
//        
//    
//    }
    
    public boolean isSymmetric (String mismatch) {
        if(mismatch.equals(this.revTemplate)) {
            return true;
        }
        return false;
    }
    
       
    public boolean isATTerm (String mismatch, String template) {
        if (mismatch.charAt(0) == 'A') {
            if (template.charAt(0) == 'T') {
                return true;
            }
        }
        if (mismatch.charAt(0) == 'T') {
            if (template.charAt(0) == 'A') {
                return true;
            }
        }
        if (mismatch.charAt(mismatch.length() - 1) == 'A') {
            if (template.charAt(template.length() - 1) == 'T') {
                return true;
            }
        }
        
        if (mismatch.charAt(mismatch.length() - 1) == 'T') {
            if (template.charAt(template.length() - 1) == 'A') {
                return true;
            }
        }
        return false;
    }
    
    public double divalent2mono(double divalent, double dntp) {
        
        if (divalent == 0.0)
            dntp = 0.0;
        
        if (divalent < 0.0 || dntp < 0.0) {
            System.out.println("divalent2mono() error - less than 0 - " );
            return 0.0;
        }
        
        if (divalent < dntp) 
            divalent = dntp;
        
        return 120 * (Math.sqrt(divalent - dntp));
        
    }
    
    
    
    public Set<Oligo> getMismatchOligos() {
        return this.allPossibleMismatches;
    }
    public Oligo getOligoToTest() {
        return this.oligoToTest;          
    }
    
    public void setMismatchOligos(Set<Oligo> oligoSet) {
        this.allPossibleMismatches.clear();
        this.allPossibleMismatches.addAll(oligoSet);          
    }
    
    public void setOligoToTest(Oligo oligo) {
        this.oligoToTest = oligo;          
    }
    
    public void setTemplate(String template) {
        this.template = template;          
    }
    public void setRevTemplate(String revTemplate) {
        this.revTemplate = revTemplate;          
    }
            
}
