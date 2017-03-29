/*
 * Copyright (C) 2015  RDPStaff gunturus at msu dot edu>
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

import edu.msu.cme.rdp.primerdesign.selectprimers.PrimerDesign;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gunturus
 */

public class Primer3Wrapper {
    private double _mv ; // Conc of monovalent cations
    private double _dv; //  Conc of divalent cations
    private double _dntp = 0.8; // ntp concentration
    private double _dna = 50.0; // DNA concentration
    private double _temp_c = 37; //
    private int _max_loop = 30; //
    private int _tm_method = 1; //
    private int _salt_method = 1; //
    private String osType;
    
    public Primer3Wrapper(String os, double sodiumMv, double magnesDv)  {
        
        this._mv = sodiumMv;
        this._dv = magnesDv;
        
       
        URL url = PrimerDesign.class.getProtectionDomain().getCodeSource().getLocation();
        
        String pathString = null;
        try {
            pathString = new File(url.toURI()).getParent();
        } catch (URISyntaxException ex) {
            Logger.getLogger(Primer3Wrapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if("mac".equals(os)){
            System.load(pathString + "/primer3/libPrimer3.dylib");
        } else if("linux".equals(os)) {
            System.load(pathString + "/primer3/libPrimer3.so");
        }   
        
                
        
        initThermoPath(pathString + "/primer3_config/");
    }
    
    private static native void initThermoPath(String path);
    
    private native double calcTm(String seq, double d, double mv, double dv, double n, int tm_method, int salt_method);
    
    private native double calcThermo(String seq1, String seq2, int maxLoop, double mv, double dv, double dntp, double dna_conc, double temp, int temponly, int dimer, int aligntype);
    
    public double calcTemp(String seq) {
        return calcTm(seq, _dna, _mv, _dv, _dntp, _tm_method, _salt_method);
    }
    
    public double calcSpecial(String seq1, String seq2, String type) {
        if (type.equals("HAIRPIN")) {
            return calcThermo(seq1, seq2, _max_loop, _mv, _dv, _dntp, _dna, _temp_c, 1, 0, 4);
        }
        else {
            return calcThermo(seq1, seq2, _max_loop, _mv, _dv, _dntp, _dna, _temp_c, 1, 0, 1);
        }
    }
    
    public double calcHairpinTm(String seq) {
        return calcSpecial(seq,seq,"HAIRPIN");
    }
    
    public double calcHomodimerTm(String seq) {
        return calcSpecial(seq,seq,"ANY");
    }
    
    public double calcHetrodimerTm(String seq1, String seq2) {
        return calcSpecial(seq1, seq2, "ANY");
    }
    
    public void setMv(int mv) {
        this._mv = mv;
    }
    
    public void setDv(double dv) {
        this._dv = dv;
    }
    
    public void setDntp(double dntp) {
        this._dntp = dntp;
    }
    
    public void setDna(double dna) {
        this._dna = dna;
    }
    
    public void setTempC(double tempC) {
        this._temp_c = tempC;
    }
    
    public void setMaxLoop(int maxloop) {
        this._max_loop = maxloop;
    }
    
    public void setTmMeth(int tmMeth) {
        this._tm_method = tmMeth;
    }
    
    public void setSaltMeth(int saltMeth) {
        this._salt_method = saltMeth;
    }
    
     public double getMv() {
        return _mv;
    }
    
    public double getDv() {
        return _dv;
    }
    
    public double getDntp() {
        return _dntp;
    }
    
    public double getDna() {
        return _dna;
    }
    
    public double getTempC() {
        return _temp_c;
    }
    
    public int getMaxLoop() {
        return _max_loop;
    }
    
    public int getTmMeth() {
        return _tm_method;
    }
    
    public int getSaltMeth() {
        return _salt_method;
    }
    public String getOSType() {
        return osType;
    }
    
    
}
