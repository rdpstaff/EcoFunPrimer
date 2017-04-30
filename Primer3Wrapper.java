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

/**
 *
 * @author gunturus
 */

public class Primer3Wrapper {
    private double _mv = 50.0;
    private double _dv = 0.0;
    private double _dntp = 0.0;
    private double _dna = 50.0;
    private double _temp_c = 37;
    private int _max_loop = 30;
    private int _tm_method = 0;
    private int _salt_method = 0;
    
    public Primer3Wrapper() {
        System.load("/home/gunturus/PrimerDesign/jni/libPrimer3.so");
        initThermoPath("/home/gunturus/PrimerDesign/jni/");
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
        _mv = mv;
    }
    
    public void setDv(double dv) {
        _dv = dv;
    }
    
    public void setDntp(double dntp) {
        _dntp = dntp;
    }
    
    public void setDna(double dna) {
        _dna = dna;
    }
    
    public void setTemp(double temp) {
        _temp_c = temp;
    }
    
    public void setMaxLoop(int maxloop) {
        _max_loop = maxloop;
    }
    
    public static void main(String[] args) {
        
        Primer3Wrapper primer3 = new Primer3Wrapper();
    
        double ret = primer3.calcHomodimerTm("GACGTAGAACAAGATCCGGAT");
        System.out.println(ret);
        
    }
}
