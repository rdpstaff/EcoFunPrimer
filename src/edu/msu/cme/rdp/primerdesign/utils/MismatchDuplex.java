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

package edu.msu.cme.rdp.primerdesign.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author leotift
 */
public class MismatchDuplex implements TableKeyToValue {
    
    //Bases of the current duplex acting as the key
    private final DuplexArray keyBases = new DuplexArray();
       
    // Table value
    private double thermoValue;
    
    private boolean dataLoaded;
    
    
    private Map <DuplexArray,Double> deltaGValueMap;
    private Map <DuplexArray,Double> deltaHValueMap;
    private Map <DuplexArray,Double> deltaSValueMap;
    
      
    //Singleton design 
    private static MismatchDuplex instance = new MismatchDuplex ();
          
    private MismatchDuplex(){}
    
    public static MismatchDuplex getInstance() {
        return instance; 
    }
    
     /**
     * Will return boolean if this type of key has had data loaded yet
     * @return boolean
     */
    @Override
    public boolean isDataLoaded() { return dataLoaded;};
    
   
     /**
     * Will load data for this specific key type in both maps
     */
    @Override
    public void loadData(){
        this.deltaGValueMap = new HashMap<DuplexArray,Double> ();
        this.deltaGValueMap.put(new DuplexArray("AATT".toCharArray()), -1.00);
        this.deltaGValueMap.put(new DuplexArray("TTAA".toCharArray()), -1.00);
        this.deltaGValueMap.put(new DuplexArray("ATTA".toCharArray()), -0.88);
        this.deltaGValueMap.put(new DuplexArray("TAAT".toCharArray()), -0.58);
        this.deltaGValueMap.put(new DuplexArray("CAGT".toCharArray()), -1.45);
        this.deltaGValueMap.put(new DuplexArray("TGAC".toCharArray()), -1.45);
        this.deltaGValueMap.put(new DuplexArray("GTCA".toCharArray()), -1.44);
        this.deltaGValueMap.put(new DuplexArray("ACTG".toCharArray()), -1.44);
        this.deltaGValueMap.put(new DuplexArray("CTGA".toCharArray()), -1.28);
        this.deltaGValueMap.put(new DuplexArray("AGTC".toCharArray()), -1.28);
        this.deltaGValueMap.put(new DuplexArray("GACT".toCharArray()), -1.30);
        this.deltaGValueMap.put(new DuplexArray("TCAG".toCharArray()), -1.30);
        this.deltaGValueMap.put(new DuplexArray("CGGC".toCharArray()), -2.17);
        this.deltaGValueMap.put(new DuplexArray("GCCG".toCharArray()), -2.24);
        this.deltaGValueMap.put(new DuplexArray("GGCC".toCharArray()), -1.84);
        this.deltaGValueMap.put(new DuplexArray("CCGG".toCharArray()), -1.84);
        this.deltaGValueMap.put(new DuplexArray("INIT".toCharArray()), 1.96);
        this.deltaGValueMap.put(new DuplexArray("TERM".toCharArray()), 0.05);
        this.deltaGValueMap.put(new DuplexArray("SYMM".toCharArray()), 0.43);
        this.deltaGValueMap.put(new DuplexArray("GACA".toCharArray()), 0.17);
        this.deltaGValueMap.put(new DuplexArray("ACAG".toCharArray()), 0.17);
        this.deltaGValueMap.put(new DuplexArray("GCCA".toCharArray()), 0.47);
        this.deltaGValueMap.put(new DuplexArray("ACCG".toCharArray()), 0.47);
        this.deltaGValueMap.put(new DuplexArray("GGCA".toCharArray()), -0.52);
        this.deltaGValueMap.put(new DuplexArray("ACGG".toCharArray()), -0.52);
        this.deltaGValueMap.put(new DuplexArray("GACC".toCharArray()), 0.81);
        this.deltaGValueMap.put(new DuplexArray("CCAG".toCharArray()), 0.81);
        this.deltaGValueMap.put(new DuplexArray("GCCC".toCharArray()), 0.79);
        this.deltaGValueMap.put(new DuplexArray("CCCG".toCharArray()), 0.79);
        this.deltaGValueMap.put(new DuplexArray("GTCC".toCharArray()), 0.98);
        this.deltaGValueMap.put(new DuplexArray("CCTG".toCharArray()), 0.98);
        this.deltaGValueMap.put(new DuplexArray("GACG".toCharArray()), -0.25);
        this.deltaGValueMap.put(new DuplexArray("GCAG".toCharArray()), -0.25);
        this.deltaGValueMap.put(new DuplexArray("GGCG".toCharArray()), -1.11);
        this.deltaGValueMap.put(new DuplexArray("GCGG".toCharArray()), -1.11);
        this.deltaGValueMap.put(new DuplexArray("GTCG".toCharArray()), -0.59);
        this.deltaGValueMap.put(new DuplexArray("GCTG".toCharArray()), -0.59);
        this.deltaGValueMap.put(new DuplexArray("GCCT".toCharArray()), 0.62);
        this.deltaGValueMap.put(new DuplexArray("TCCG".toCharArray()), 0.62);
        this.deltaGValueMap.put(new DuplexArray("GGCT".toCharArray()), 0.08);
        this.deltaGValueMap.put(new DuplexArray("TCGG".toCharArray()), 0.08);
        this.deltaGValueMap.put(new DuplexArray("GTCT".toCharArray()), 0.45);
        this.deltaGValueMap.put(new DuplexArray("TCTG".toCharArray()), 0.45);
        this.deltaGValueMap.put(new DuplexArray("CAGA".toCharArray()), 0.43);
        this.deltaGValueMap.put(new DuplexArray("AGAC".toCharArray()), 0.43);
        this.deltaGValueMap.put(new DuplexArray("CCGA".toCharArray()), 0.79);
        this.deltaGValueMap.put(new DuplexArray("AGCC".toCharArray()), 0.79);
        this.deltaGValueMap.put(new DuplexArray("CGGA".toCharArray()), 0.11);
        this.deltaGValueMap.put(new DuplexArray("AGGC".toCharArray()), 0.11);
        this.deltaGValueMap.put(new DuplexArray("CAGC".toCharArray()), 0.75);
        this.deltaGValueMap.put(new DuplexArray("CGAC".toCharArray()), 0.75);
        this.deltaGValueMap.put(new DuplexArray("CCGC".toCharArray()), 0.70);
        this.deltaGValueMap.put(new DuplexArray("CGCC".toCharArray()), 0.70);
        this.deltaGValueMap.put(new DuplexArray("CTGC".toCharArray()), 0.40);
        this.deltaGValueMap.put(new DuplexArray("CGTC".toCharArray()), 0.40);
        this.deltaGValueMap.put(new DuplexArray("CAGG".toCharArray()), 0.03);
        this.deltaGValueMap.put(new DuplexArray("GGAC".toCharArray()), 0.03);
        this.deltaGValueMap.put(new DuplexArray("CGGG".toCharArray()), -0.11);
        this.deltaGValueMap.put(new DuplexArray("GGGC".toCharArray()), -0.11);
        this.deltaGValueMap.put(new DuplexArray("CTGG".toCharArray()), -0.32);
        this.deltaGValueMap.put(new DuplexArray("GGTC".toCharArray()), -0.32);
        this.deltaGValueMap.put(new DuplexArray("CCGT".toCharArray()), 0.62);
        this.deltaGValueMap.put(new DuplexArray("TGCC".toCharArray()), 0.62);
        this.deltaGValueMap.put(new DuplexArray("CGGT".toCharArray()), -0.47);
        this.deltaGValueMap.put(new DuplexArray("TGGC".toCharArray()), -0.47);
        this.deltaGValueMap.put(new DuplexArray("CTGT".toCharArray()), -0.12);
        this.deltaGValueMap.put(new DuplexArray("TGTC".toCharArray()), -0.12);
        this.deltaGValueMap.put(new DuplexArray("AATA".toCharArray()), 0.61);
        this.deltaGValueMap.put(new DuplexArray("ATAA".toCharArray()), 0.61);
        this.deltaGValueMap.put(new DuplexArray("ACTA".toCharArray()), 0.77);
        this.deltaGValueMap.put(new DuplexArray("ATCA".toCharArray()), 0.77);
        this.deltaGValueMap.put(new DuplexArray("AGTA".toCharArray()), 0.02);
        this.deltaGValueMap.put(new DuplexArray("ATGA".toCharArray()), 0.02);
        this.deltaGValueMap.put(new DuplexArray("AATC".toCharArray()), 0.88);
        this.deltaGValueMap.put(new DuplexArray("CTAA".toCharArray()), 0.88);
        this.deltaGValueMap.put(new DuplexArray("ACTC".toCharArray()), 1.33);
        this.deltaGValueMap.put(new DuplexArray("CTCA".toCharArray()), 1.33);
        this.deltaGValueMap.put(new DuplexArray("ATTC".toCharArray()), 0.73);
        this.deltaGValueMap.put(new DuplexArray("CTTA".toCharArray()), 0.73);
        this.deltaGValueMap.put(new DuplexArray("AATG".toCharArray()), 0.14);
        this.deltaGValueMap.put(new DuplexArray("GTAA".toCharArray()), 0.14);
        this.deltaGValueMap.put(new DuplexArray("AGTG".toCharArray()), -0.13);
        this.deltaGValueMap.put(new DuplexArray("GTGA".toCharArray()), -0.13);
        this.deltaGValueMap.put(new DuplexArray("ATTG".toCharArray()), 0.07);
        this.deltaGValueMap.put(new DuplexArray("GTTA".toCharArray()), 0.07);
        this.deltaGValueMap.put(new DuplexArray("ACTT".toCharArray()), 0.64);
        this.deltaGValueMap.put(new DuplexArray("TTCA".toCharArray()), 0.64);
        this.deltaGValueMap.put(new DuplexArray("AGTT".toCharArray()), 0.71);
        this.deltaGValueMap.put(new DuplexArray("TTGA".toCharArray()), 0.71);
        this.deltaGValueMap.put(new DuplexArray("ATTT".toCharArray()), 0.69);
        this.deltaGValueMap.put(new DuplexArray("TTTA".toCharArray()), 0.69);
        this.deltaGValueMap.put(new DuplexArray("TAAA".toCharArray()), 0.69);
        this.deltaGValueMap.put(new DuplexArray("AAAT".toCharArray()), 0.69);
        this.deltaGValueMap.put(new DuplexArray("TCAA".toCharArray()), 1.33);
        this.deltaGValueMap.put(new DuplexArray("AACT".toCharArray()), 1.33);
        this.deltaGValueMap.put(new DuplexArray("TGAA".toCharArray()), 0.74);
        this.deltaGValueMap.put(new DuplexArray("AAGT".toCharArray()), 0.74);
        this.deltaGValueMap.put(new DuplexArray("TAAC".toCharArray()), 0.92);
        this.deltaGValueMap.put(new DuplexArray("CAAT".toCharArray()), 0.92);
        this.deltaGValueMap.put(new DuplexArray("TCAC".toCharArray()), 1.05);
        this.deltaGValueMap.put(new DuplexArray("CACT".toCharArray()), 1.05);
        this.deltaGValueMap.put(new DuplexArray("TTAC".toCharArray()), 0.75);
        this.deltaGValueMap.put(new DuplexArray("CATT".toCharArray()), 0.75);
        this.deltaGValueMap.put(new DuplexArray("TAAG".toCharArray()), 0.42);
        this.deltaGValueMap.put(new DuplexArray("GAAT".toCharArray()), 0.42);
        this.deltaGValueMap.put(new DuplexArray("TGAG".toCharArray()), 0.44);
        this.deltaGValueMap.put(new DuplexArray("GAGT".toCharArray()), 0.44);
        this.deltaGValueMap.put(new DuplexArray("TTAG".toCharArray()), 0.34);
        this.deltaGValueMap.put(new DuplexArray("GATT".toCharArray()), 0.34);
        this.deltaGValueMap.put(new DuplexArray("TCAT".toCharArray()), 0.97);
        this.deltaGValueMap.put(new DuplexArray("TACT".toCharArray()), 0.97);
        this.deltaGValueMap.put(new DuplexArray("TGAT".toCharArray()), 0.43);
        this.deltaGValueMap.put(new DuplexArray("TAGT".toCharArray()), 0.43);
        this.deltaGValueMap.put(new DuplexArray("TTAT".toCharArray()), 0.68);
        this.deltaGValueMap.put(new DuplexArray("TATT".toCharArray()), 0.68);
        
        //Delta H
        this.deltaHValueMap = new HashMap<DuplexArray,Double> ();
        this.deltaHValueMap.put(new DuplexArray("AATT".toCharArray()), -7.9);
        this.deltaHValueMap.put(new DuplexArray("TTAA".toCharArray()), -7.9);
        this.deltaHValueMap.put(new DuplexArray("ATTA".toCharArray()), -7.2);
        this.deltaHValueMap.put(new DuplexArray("TAAT".toCharArray()), -7.2);
        this.deltaHValueMap.put(new DuplexArray("CAGT".toCharArray()), -8.5);
        this.deltaHValueMap.put(new DuplexArray("TGAC".toCharArray()), -8.5);
        this.deltaHValueMap.put(new DuplexArray("GTCA".toCharArray()), -8.4);
        this.deltaHValueMap.put(new DuplexArray("ACTG".toCharArray()), -8.4);
        this.deltaHValueMap.put(new DuplexArray("CTGA".toCharArray()), -7.8);
        this.deltaHValueMap.put(new DuplexArray("AGTC".toCharArray()), -7.8);
        this.deltaHValueMap.put(new DuplexArray("GACT".toCharArray()), -8.2);
        this.deltaHValueMap.put(new DuplexArray("TCAG".toCharArray()), -8.2);
        this.deltaHValueMap.put(new DuplexArray("CGGC".toCharArray()), -10.6);
        this.deltaHValueMap.put(new DuplexArray("GCCG".toCharArray()), -9.8);
        this.deltaHValueMap.put(new DuplexArray("GGCC".toCharArray()), -8.0);
        this.deltaHValueMap.put(new DuplexArray("CCGG".toCharArray()), -8.0);
        this.deltaHValueMap.put(new DuplexArray("INCG".toCharArray()), 0.1);
        this.deltaHValueMap.put(new DuplexArray("INGC".toCharArray()), 0.1);
        this.deltaHValueMap.put(new DuplexArray("INAT".toCharArray()), 2.3);
        this.deltaHValueMap.put(new DuplexArray("INTA".toCharArray()), 2.3);
        this.deltaHValueMap.put(new DuplexArray("AATA".toCharArray()), 1.2);
        this.deltaHValueMap.put(new DuplexArray("ATAA".toCharArray()), 1.2);
        this.deltaHValueMap.put(new DuplexArray("CAGA".toCharArray()), -0.9);
        this.deltaHValueMap.put(new DuplexArray("AGAC".toCharArray()), -0.9);
        this.deltaHValueMap.put(new DuplexArray("GACA".toCharArray()), -2.9);
        this.deltaHValueMap.put(new DuplexArray("ACAG".toCharArray()), -2.9);
        this.deltaHValueMap.put(new DuplexArray("TAAA".toCharArray()), 4.7);
        this.deltaHValueMap.put(new DuplexArray("AAAT".toCharArray()), 4.7);
        this.deltaHValueMap.put(new DuplexArray("ACTC".toCharArray()), 0.0);
        this.deltaHValueMap.put(new DuplexArray("CTCA".toCharArray()), 0.0);
        this.deltaHValueMap.put(new DuplexArray("CCGC".toCharArray()), -1.5);
        this.deltaHValueMap.put(new DuplexArray("CGCC".toCharArray()), -1.5);
        this.deltaHValueMap.put(new DuplexArray("GCCC".toCharArray()), 3.6);
        this.deltaHValueMap.put(new DuplexArray("CCCG".toCharArray()), 3.6);
        this.deltaHValueMap.put(new DuplexArray("TCAC".toCharArray()), 6.1);
        this.deltaHValueMap.put(new DuplexArray("CACT".toCharArray()), 6.1);
        this.deltaHValueMap.put(new DuplexArray("AGTG".toCharArray()), -3.1);
        this.deltaHValueMap.put(new DuplexArray("GTGA".toCharArray()), -3.1);
        this.deltaHValueMap.put(new DuplexArray("CGGG".toCharArray()), -4.9);
        this.deltaHValueMap.put(new DuplexArray("GGGC".toCharArray()), -4.9);
        this.deltaHValueMap.put(new DuplexArray("GGCG".toCharArray()), -6.0);
        this.deltaHValueMap.put(new DuplexArray("GCGG".toCharArray()), -6.0);
        this.deltaHValueMap.put(new DuplexArray("TGAG".toCharArray()), 1.6);
        this.deltaHValueMap.put(new DuplexArray("GAGT".toCharArray()), 1.6);
        this.deltaHValueMap.put(new DuplexArray("ATTT".toCharArray()), -2.7);
        this.deltaHValueMap.put(new DuplexArray("TTTA".toCharArray()), -2.7);
        this.deltaHValueMap.put(new DuplexArray("CTGT".toCharArray()), -5.0);
        this.deltaHValueMap.put(new DuplexArray("TGTC".toCharArray()), -5.0);
        this.deltaHValueMap.put(new DuplexArray("GTCT".toCharArray()), -2.2);
        this.deltaHValueMap.put(new DuplexArray("TCTG".toCharArray()), -2.2);
        this.deltaHValueMap.put(new DuplexArray("TTAT".toCharArray()), 0.2);
        this.deltaHValueMap.put(new DuplexArray("TATT".toCharArray()), 0.2);
        this.deltaHValueMap.put(new DuplexArray("AGTT".toCharArray()), 1.0);
        this.deltaHValueMap.put(new DuplexArray("TTGA".toCharArray()), 1.0);
        this.deltaHValueMap.put(new DuplexArray("ATTG".toCharArray()), -2.5);
        this.deltaHValueMap.put(new DuplexArray("GTTA".toCharArray()), -2.5);
        this.deltaHValueMap.put(new DuplexArray("CGGT".toCharArray()), -4.1);
        this.deltaHValueMap.put(new DuplexArray("TGGC".toCharArray()), -4.1);
        this.deltaHValueMap.put(new DuplexArray("CTGG".toCharArray()), -2.8);
        this.deltaHValueMap.put(new DuplexArray("GGTC".toCharArray()), -2.8);
        this.deltaHValueMap.put(new DuplexArray("GGCT".toCharArray()), 3.3);
        this.deltaHValueMap.put(new DuplexArray("TCGG".toCharArray()), 3.3);
        this.deltaHValueMap.put(new DuplexArray("GGTT".toCharArray()), 5.8);
        this.deltaHValueMap.put(new DuplexArray("TTGG".toCharArray()), 5.8);
        this.deltaHValueMap.put(new DuplexArray("GTCG".toCharArray()), -4.4);
        this.deltaHValueMap.put(new DuplexArray("GCTG".toCharArray()), -4.4);
        this.deltaHValueMap.put(new DuplexArray("GTTG".toCharArray()), 4.1);
        this.deltaHValueMap.put(new DuplexArray("GTTG".toCharArray()), 4.1);
        this.deltaHValueMap.put(new DuplexArray("TGAT".toCharArray()), -0.1);
        this.deltaHValueMap.put(new DuplexArray("TAGT".toCharArray()), -0.1);
        this.deltaHValueMap.put(new DuplexArray("TGGT".toCharArray()), -1.4);
        this.deltaHValueMap.put(new DuplexArray("TGGT".toCharArray()), -1.4);
        this.deltaHValueMap.put(new DuplexArray("TTAG".toCharArray()), -1.3);
        this.deltaHValueMap.put(new DuplexArray("GATT".toCharArray()), -1.3);
        this.deltaHValueMap.put(new DuplexArray("AATG".toCharArray()), -0.6);
        this.deltaHValueMap.put(new DuplexArray("GTAA".toCharArray()), -0.6);
        this.deltaHValueMap.put(new DuplexArray("AGTA".toCharArray()), -0.7);
        this.deltaHValueMap.put(new DuplexArray("ATGA".toCharArray()), -0.7);
        this.deltaHValueMap.put(new DuplexArray("CAGG".toCharArray()), -0.7);
        this.deltaHValueMap.put(new DuplexArray("GGAC".toCharArray()), -0.7);
        this.deltaHValueMap.put(new DuplexArray("CGGA".toCharArray()), -4.0);
        this.deltaHValueMap.put(new DuplexArray("AGGC".toCharArray()), -4.0);
        this.deltaHValueMap.put(new DuplexArray("GACG".toCharArray()), -0.6);
        this.deltaHValueMap.put(new DuplexArray("GCAG".toCharArray()), -0.6);
        this.deltaHValueMap.put(new DuplexArray("GGCA".toCharArray()), 0.5);
        this.deltaHValueMap.put(new DuplexArray("ACGG".toCharArray()), 0.5);
        this.deltaHValueMap.put(new DuplexArray("TAAG".toCharArray()), 0.7);
        this.deltaHValueMap.put(new DuplexArray("GAAT".toCharArray()), 0.7);
        this.deltaHValueMap.put(new DuplexArray("TGAA".toCharArray()), 3.0);
        this.deltaHValueMap.put(new DuplexArray("AAGT".toCharArray()), 3.0);
        this.deltaHValueMap.put(new DuplexArray("ACTT".toCharArray()), 0.7);
        this.deltaHValueMap.put(new DuplexArray("TTCA".toCharArray()), 0.7);
        this.deltaHValueMap.put(new DuplexArray("ATTC".toCharArray()), -1.2);
        this.deltaHValueMap.put(new DuplexArray("CTTA".toCharArray()), -1.2);
        this.deltaHValueMap.put(new DuplexArray("CCGT".toCharArray()), -0.8);
        this.deltaHValueMap.put(new DuplexArray("TGCC".toCharArray()), -0.8);
        this.deltaHValueMap.put(new DuplexArray("CTGC".toCharArray()), -1.5);
        this.deltaHValueMap.put(new DuplexArray("CGTC".toCharArray()), -1.5);
        this.deltaHValueMap.put(new DuplexArray("GCCT".toCharArray()), 2.3);
        this.deltaHValueMap.put(new DuplexArray("TCCG".toCharArray()), 2.3);
        this.deltaHValueMap.put(new DuplexArray("GTCC".toCharArray()), 5.2);
        this.deltaHValueMap.put(new DuplexArray("CCTG".toCharArray()), 5.2);
        this.deltaHValueMap.put(new DuplexArray("TCAT".toCharArray()), 1.2);
        this.deltaHValueMap.put(new DuplexArray("TACT".toCharArray()), 1.2);
        this.deltaHValueMap.put(new DuplexArray("TTAC".toCharArray()), 1.0);
        this.deltaHValueMap.put(new DuplexArray("CATT".toCharArray()), 1.0);
        this.deltaHValueMap.put(new DuplexArray("AATC".toCharArray()), 2.3);
        this.deltaHValueMap.put(new DuplexArray("CTAA".toCharArray()), 2.3);
        this.deltaHValueMap.put(new DuplexArray("ACTA".toCharArray()), 5.3);
        this.deltaHValueMap.put(new DuplexArray("ATCA".toCharArray()), 5.3);
        this.deltaHValueMap.put(new DuplexArray("CAGC".toCharArray()), 1.9);
        this.deltaHValueMap.put(new DuplexArray("CGAC".toCharArray()), 1.9);
        this.deltaHValueMap.put(new DuplexArray("CCGA".toCharArray()), 0.6);
        this.deltaHValueMap.put(new DuplexArray("AGCC".toCharArray()), 0.6);
        this.deltaHValueMap.put(new DuplexArray("GACC".toCharArray()), 5.2);
        this.deltaHValueMap.put(new DuplexArray("CCAG".toCharArray()), 5.2);
        this.deltaHValueMap.put(new DuplexArray("GCCA".toCharArray()), -0.7);
        this.deltaHValueMap.put(new DuplexArray("ACCG".toCharArray()), -0.7);
        this.deltaHValueMap.put(new DuplexArray("TAAC".toCharArray()), 3.4);
        this.deltaHValueMap.put(new DuplexArray("CAAT".toCharArray()), 3.4);
        this.deltaHValueMap.put(new DuplexArray("TCAA".toCharArray()), 7.6);
        this.deltaHValueMap.put(new DuplexArray("AACT".toCharArray()), 7.6);
        
        //DeltaS Map
        this.deltaSValueMap = new HashMap<DuplexArray,Double> ();
        this.deltaSValueMap.put(new DuplexArray("AATT".toCharArray()), -22.2);
        this.deltaSValueMap.put(new DuplexArray("TTAA".toCharArray()), -22.2);
        this.deltaSValueMap.put(new DuplexArray("ATTA".toCharArray()), -20.4);
        this.deltaSValueMap.put(new DuplexArray("TAAT".toCharArray()), -21.3);
        this.deltaSValueMap.put(new DuplexArray("CAGT".toCharArray()), -22.7);
        this.deltaSValueMap.put(new DuplexArray("TGAC".toCharArray()), -22.7);
        this.deltaSValueMap.put(new DuplexArray("GTCA".toCharArray()), -22.4);
        this.deltaSValueMap.put(new DuplexArray("ACTG".toCharArray()), -22.4);
        this.deltaSValueMap.put(new DuplexArray("CTGA".toCharArray()), -21.0);
        this.deltaSValueMap.put(new DuplexArray("AGTC".toCharArray()), -21.0);
        this.deltaSValueMap.put(new DuplexArray("GACT".toCharArray()), -22.2);
        this.deltaSValueMap.put(new DuplexArray("TCAG".toCharArray()), -22.2);
        this.deltaSValueMap.put(new DuplexArray("CGGC".toCharArray()), -27.2);
        this.deltaSValueMap.put(new DuplexArray("GCCG".toCharArray()), -24.4);
        this.deltaSValueMap.put(new DuplexArray("GGCC".toCharArray()), -19.9);
        this.deltaSValueMap.put(new DuplexArray("CCGG".toCharArray()), -19.9);
        this.deltaSValueMap.put(new DuplexArray("INCG".toCharArray()), -2.8);
        this.deltaSValueMap.put(new DuplexArray("INGC".toCharArray()), -2.8);
        this.deltaSValueMap.put(new DuplexArray("INAT".toCharArray()), 4.1);
        this.deltaSValueMap.put(new DuplexArray("INTA".toCharArray()), 4.1);
        this.deltaSValueMap.put(new DuplexArray("SYMM".toCharArray()), -1.4);
        this.deltaSValueMap.put(new DuplexArray("AATA".toCharArray()), 1.7);
        this.deltaSValueMap.put(new DuplexArray("ATAA".toCharArray()), 1.7);
        this.deltaSValueMap.put(new DuplexArray("CAGA".toCharArray()), -4.2);
        this.deltaSValueMap.put(new DuplexArray("AGAC".toCharArray()), -4.2);
        this.deltaSValueMap.put(new DuplexArray("GACA".toCharArray()), -9.8);
        this.deltaSValueMap.put(new DuplexArray("ACAG".toCharArray()), -9.8);
        this.deltaSValueMap.put(new DuplexArray("TAAA".toCharArray()), 12.9);
        this.deltaSValueMap.put(new DuplexArray("AAAT".toCharArray()), 12.9);
        this.deltaSValueMap.put(new DuplexArray("ACTC".toCharArray()), -4.4);
        this.deltaSValueMap.put(new DuplexArray("CTCA".toCharArray()), -4.4);
        this.deltaSValueMap.put(new DuplexArray("CCGC".toCharArray()), -7.2);
        this.deltaSValueMap.put(new DuplexArray("CGCC".toCharArray()), -7.2);
        this.deltaSValueMap.put(new DuplexArray("GCCC".toCharArray()), 8.9);
        this.deltaSValueMap.put(new DuplexArray("CCCG".toCharArray()), 8.9);
        this.deltaSValueMap.put(new DuplexArray("TCAC".toCharArray()), 16.4);
        this.deltaSValueMap.put(new DuplexArray("CACT".toCharArray()), 16.4);
        this.deltaSValueMap.put(new DuplexArray("AGTG".toCharArray()), -9.5);
        this.deltaSValueMap.put(new DuplexArray("GTGA".toCharArray()), -9.5);
        this.deltaSValueMap.put(new DuplexArray("CGGG".toCharArray()), -15.3);
        this.deltaSValueMap.put(new DuplexArray("GGGC".toCharArray()), -15.3);
        this.deltaSValueMap.put(new DuplexArray("GGCG".toCharArray()), -15.8);
        this.deltaSValueMap.put(new DuplexArray("GCGG".toCharArray()), -15.8);
        this.deltaSValueMap.put(new DuplexArray("TGAG".toCharArray()), 3.6);
        this.deltaSValueMap.put(new DuplexArray("GAGT".toCharArray()), 3.6);
        this.deltaSValueMap.put(new DuplexArray("ATTT".toCharArray()), -10.8);
        this.deltaSValueMap.put(new DuplexArray("TTTA".toCharArray()), -10.8);
        this.deltaSValueMap.put(new DuplexArray("CTGT".toCharArray()), -15.8);
        this.deltaSValueMap.put(new DuplexArray("TGTC".toCharArray()), -15.8);
        this.deltaSValueMap.put(new DuplexArray("GTCT".toCharArray()), -8.4);
        this.deltaSValueMap.put(new DuplexArray("TCTG".toCharArray()), -8.4);
        this.deltaSValueMap.put(new DuplexArray("TTAT".toCharArray()), -1.5);
        this.deltaSValueMap.put(new DuplexArray("TATT".toCharArray()), -1.5);
        this.deltaSValueMap.put(new DuplexArray("AGTT".toCharArray()), 0.9);
        this.deltaSValueMap.put(new DuplexArray("TTGA".toCharArray()), 0.9);
        this.deltaSValueMap.put(new DuplexArray("ATTG".toCharArray()), -8.3);
        this.deltaSValueMap.put(new DuplexArray("GTTA".toCharArray()), -8.3);
        this.deltaSValueMap.put(new DuplexArray("CGGT".toCharArray()), -11.7);
        this.deltaSValueMap.put(new DuplexArray("TGGC".toCharArray()), -11.7);
        this.deltaSValueMap.put(new DuplexArray("CTGG".toCharArray()), -8.0);
        this.deltaSValueMap.put(new DuplexArray("GGTC".toCharArray()), -8.0);
        this.deltaSValueMap.put(new DuplexArray("GGCT".toCharArray()), 10.4);
        this.deltaSValueMap.put(new DuplexArray("TCGG".toCharArray()), 10.4);
        this.deltaSValueMap.put(new DuplexArray("GGTT".toCharArray()), 16.3);
        this.deltaSValueMap.put(new DuplexArray("TTGG".toCharArray()), 16.3);
        this.deltaSValueMap.put(new DuplexArray("GTCG".toCharArray()), -12.3);
        this.deltaSValueMap.put(new DuplexArray("GCTG".toCharArray()), -12.3);
        this.deltaSValueMap.put(new DuplexArray("GTTG".toCharArray()), 9.5);
        this.deltaSValueMap.put(new DuplexArray("GTTG".toCharArray()), 9.5);
        this.deltaSValueMap.put(new DuplexArray("TGAT".toCharArray()), -1.7);
        this.deltaSValueMap.put(new DuplexArray("TAGT".toCharArray()), -1.7);
        this.deltaSValueMap.put(new DuplexArray("TGGT".toCharArray()), -6.2);
        this.deltaSValueMap.put(new DuplexArray("TGGT".toCharArray()), -6.2);
        this.deltaSValueMap.put(new DuplexArray("TTAG".toCharArray()), -5.3);
        this.deltaSValueMap.put(new DuplexArray("GATT".toCharArray()), -5.3);
        this.deltaSValueMap.put(new DuplexArray("AATG".toCharArray()), -2.3);
        this.deltaSValueMap.put(new DuplexArray("GTAA".toCharArray()), -2.3);
        this.deltaSValueMap.put(new DuplexArray("AGTA".toCharArray()), -2.3);
        this.deltaSValueMap.put(new DuplexArray("ATGA".toCharArray()), -2.3);
        this.deltaSValueMap.put(new DuplexArray("CAGG".toCharArray()), -2.3);
        this.deltaSValueMap.put(new DuplexArray("GGAC".toCharArray()), -2.3);
        this.deltaSValueMap.put(new DuplexArray("CGGA".toCharArray()), -13.2);
        this.deltaSValueMap.put(new DuplexArray("AGGC".toCharArray()), -13.2);
        this.deltaSValueMap.put(new DuplexArray("GACG".toCharArray()), -1.0);
        this.deltaSValueMap.put(new DuplexArray("GCAG".toCharArray()), -1.0);
        this.deltaSValueMap.put(new DuplexArray("GGCA".toCharArray()), 3.2);
        this.deltaSValueMap.put(new DuplexArray("ACGG".toCharArray()), 3.2);
        this.deltaSValueMap.put(new DuplexArray("TAAG".toCharArray()), 0.7);
        this.deltaSValueMap.put(new DuplexArray("GAAT".toCharArray()), 0.7);
        this.deltaSValueMap.put(new DuplexArray("TGAA".toCharArray()), 7.4);
        this.deltaSValueMap.put(new DuplexArray("AAGT".toCharArray()), 7.4);
        this.deltaSValueMap.put(new DuplexArray("ACTT".toCharArray()), 0.2);
        this.deltaSValueMap.put(new DuplexArray("TTCA".toCharArray()), 0.2);
        this.deltaSValueMap.put(new DuplexArray("ATTC".toCharArray()), -6.2);
        this.deltaSValueMap.put(new DuplexArray("CTTA".toCharArray()), -6.2);
        this.deltaSValueMap.put(new DuplexArray("CCGT".toCharArray()), -4.5);
        this.deltaSValueMap.put(new DuplexArray("TGCC".toCharArray()), -4.5);
        this.deltaSValueMap.put(new DuplexArray("CTGC".toCharArray()), -6.1);
        this.deltaSValueMap.put(new DuplexArray("CGTC".toCharArray()), -6.1);
        this.deltaSValueMap.put(new DuplexArray("GCCT".toCharArray()), 5.4);
        this.deltaSValueMap.put(new DuplexArray("TCCG".toCharArray()), 5.4);
        this.deltaSValueMap.put(new DuplexArray("GTCC".toCharArray()), 13.5);
        this.deltaSValueMap.put(new DuplexArray("CCTG".toCharArray()), 13.5);
        this.deltaSValueMap.put(new DuplexArray("TCAT".toCharArray()), 0.7);
        this.deltaSValueMap.put(new DuplexArray("TACT".toCharArray()), 0.7);
        this.deltaSValueMap.put(new DuplexArray("TTAC".toCharArray()), 0.7);
        this.deltaSValueMap.put(new DuplexArray("CATT".toCharArray()), 0.7);
        this.deltaSValueMap.put(new DuplexArray("AATC".toCharArray()), 4.6);
        this.deltaSValueMap.put(new DuplexArray("CTAA".toCharArray()), 4.6);
        this.deltaSValueMap.put(new DuplexArray("ACTA".toCharArray()), 14.6);
        this.deltaSValueMap.put(new DuplexArray("ATCA".toCharArray()), 14.6);
        this.deltaSValueMap.put(new DuplexArray("CAGC".toCharArray()), 3.7);
        this.deltaSValueMap.put(new DuplexArray("CGAC".toCharArray()), 3.7);
        this.deltaSValueMap.put(new DuplexArray("CCGA".toCharArray()), -0.6);
        this.deltaSValueMap.put(new DuplexArray("AGCC".toCharArray()), -0.6);
        this.deltaSValueMap.put(new DuplexArray("GACC".toCharArray()), 14.2);
        this.deltaSValueMap.put(new DuplexArray("CCAG".toCharArray()), 14.2);
        this.deltaSValueMap.put(new DuplexArray("GCCA".toCharArray()), -3.8);
        this.deltaSValueMap.put(new DuplexArray("ACCG".toCharArray()), -3.8);
        this.deltaSValueMap.put(new DuplexArray("TAAC".toCharArray()), 8.0);
        this.deltaSValueMap.put(new DuplexArray("CAAT".toCharArray()), 8.0);
        this.deltaSValueMap.put(new DuplexArray("TCAA".toCharArray()), 20.2);
        this.deltaSValueMap.put(new DuplexArray("AACT".toCharArray()), 20.2);
      
        
   
        
        dataLoaded = true;
    };
    
           
    /**
     * finds value with the given table index in the map
     * 
     *  
     */  
    
    @Override
    public void findTableValue(char type) {
        Double mapDouble;
        switch (type)
        {
            case ('G'):
                mapDouble = this.deltaGValueMap.get(this.keyBases);
                break;
            case ('H'):
                mapDouble = this.deltaHValueMap.get(this.keyBases);
                break;
            case ('S'):
                mapDouble = this.deltaSValueMap.get(this.keyBases);
                break;
            default:
                mapDouble = null;
        }
        
        
        if(mapDouble != null){
            this.thermoValue = mapDouble.doubleValue();
        }else {
            System.out.println("Key: " + this.keyBases.theArray + " not in" + type + "duplexValueMap, MismatchDuplex.java");
            
         }
             
    };
    
    public double getValue() {
        return this.thermoValue; 
    }
    
    public char getFirstBase() {
        return this.keyBases.theArray[0];
    }   
    public char getSecondBase() {
        return this.keyBases.theArray[1];
    }
    public char getThirdBase() {
        return this.keyBases.theArray[2];
    }
    public char getFourthBase() {
        return this.keyBases.theArray[3];
    } 
    public void setFirstBase(char firstChar) {
        this.keyBases.theArray[0] = firstChar;
    }    
    public void setSecondBase(char secondChar) {
        this.keyBases.theArray[1] = secondChar;
    }
    public void setThirdBase(char thirdChar) {
        this.keyBases.theArray[2] = thirdChar;
    }
    public void setFourthBase(char fourthChar) {
        this.keyBases.theArray[3] = fourthChar;
    }
       
    private class DuplexArray {
        
        
        public char[] theArray;
        
        public DuplexArray () {
            this.theArray = new char[4];
        }
       
        public DuplexArray (char[] characterArray) {
            if(characterArray.length != 4) {
               throw new RuntimeException("DuplexArray not of size 4");  
            }
            this.theArray = characterArray;
        }
        
        @Override
        public boolean equals(Object obj) {
            if(obj == null || !(this.getClass()).isAssignableFrom(obj.getClass())) {
                throw new RuntimeException("Object not of type DuplexArray"); 
            }
            
            return Arrays.equals(this.theArray, ((DuplexArray) obj).theArray);                       
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 47 * hash + Arrays.hashCode(this.theArray);
            return hash;
        }
            
    
    }
      
}
