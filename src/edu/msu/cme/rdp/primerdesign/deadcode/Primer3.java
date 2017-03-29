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

package edu.msu.cme.rdp.primerdesign.deadcode;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * This is not being used in SelectPrimersPipeline.java - Tift - 7/15/15
 * 
 * @author gunturus
 */

public class Primer3 {
    
    Path primer3Path = Paths.get("/scratch/gunturus/apps/primer3-2.3.6/src/");
    Path oligoTmPath = primer3Path.resolve("oligotm");
    Path ntthalPath = primer3Path.resolve("ntthal");
    Path thermoPath = primer3Path.resolve("primer3_config/");
    HashMap<String,Integer> tm_methods;    
    HashMap<String,Integer> salt_corrections_methods;
    int _mv = 50;
    double _dv = 1.5;
    double _dntp = 0.6;
    int _dna = 50;
    double _temp_c = 37;
    int _max_loop = 30;
    String _tm_method = "santalucia";
    String _salt_corrections_method = "santalucia";
    
    public Primer3() {
        this.tm_methods = new HashMap<>();
        this.salt_corrections_methods = new HashMap<>();
        
        this.tm_methods.put("breslauer",0);
        this.tm_methods.put("santalucia",1);
        
        this.salt_corrections_methods.put("schildkraut",0);
        this.salt_corrections_methods.put("santalucia",1);
        this.salt_corrections_methods.put("owczarzy", 2);
    }
    
    public Primer3(String primer3Path) {
        this.primer3Path = Paths.get(primer3Path);
        this.oligoTmPath = this.primer3Path.resolve("oligotm");
        this.ntthalPath = this.primer3Path.resolve("ntthal");
        this.thermoPath = this.primer3Path.resolve("primer3_config/");
        this.tm_methods = new HashMap<>();
        this.salt_corrections_methods = new HashMap<>();
        
        this.tm_methods.put("breslauer",0);
        this.tm_methods.put("santalucia",1);
        
        this.salt_corrections_methods.put("schildkraut",0);
        this.salt_corrections_methods.put("santalucia",1);
        this.salt_corrections_methods.put("owczarzy", 2);
    }
    
    public double calcTm(String seq) throws IOException{
        double oligoTm = calcTm(seq, _mv, _dv, _dntp, _dna, _tm_method, _salt_corrections_method);
        
        return oligoTm;
    }
    
    public double calcTm(String seq, int mv_conc, double dv_conc, double dntp_conc, 
            int dna_conc, String tm_method, String salt_corrections_method) throws IOException{
        
        String oligoTmPathStr = this.oligoTmPath.toString();
        String mv_conc_str = Integer.toString(mv_conc);
        String dv_conc_str = Double.toString(dv_conc);
        String dntp_conc_str = Double.toString(dntp_conc);
        String dna_conc_str = Integer.toString(dna_conc);
        
        String tm_method_str = Integer.toString(tm_methods.get(tm_method));
        String salt_corrections_method_str = Integer.toString(salt_corrections_methods.get(salt_corrections_method));
        
        String[] args = {oligoTmPathStr,"-mv", mv_conc_str, "-dv", dv_conc_str, "-n", dntp_conc_str, "-d", dna_conc_str, "-tp", tm_method_str, "-sc", salt_corrections_method_str, seq};
        
        double oligoTm = calcTmProcess(args);
        
        return oligoTm;
    }
    
    private double calcTmProcess(String[] command) throws IOException{
        
        ProcessBuilder pb = new ProcessBuilder(command);
       
        Process p = pb.start();
        
        InputStream is = p.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        
        String line = br.readLine();
        line = line.trim();
        
        double result = Double.parseDouble(line);
        
        return result;
    }
    
    public double calcThermo(String seq1, String seq2) throws IOException{
        
        String ntthalPathStr = this.ntthalPath.toString();
        String thermoPathStr = this.thermoPath.toString() + "/";
        
        String[] args = {ntthalPathStr, "-a", "ANY", "-mv", "50", "-dv", "0",
            "-n", "0.8", "-d", "50", "-t", "37", "-maxloop", "30", "-path",
            thermoPathStr, "-s1", seq1, "-s2", seq2, "-r"};  
        
        double tm = calcTmProcess(args);
        
        return tm;
    }
    
    public double calcThermo(String seq1, String seq2, String calc_type, 
            double mv_conc, double dv_conc, double dntp_conc, double dna_conc,  double temp_c,
            int max_loop) throws IOException {
        
        String ntthalPathStr = this.ntthalPath.toString();
        String mv_conc_str = Double.toString(mv_conc);
        String dv_conc_str = Double.toString(dv_conc);
        String dntp_conc_str = Double.toString(dntp_conc);
        String dna_conc_str = Double.toString(dna_conc);
        String temp_c_str = Double.toString(temp_c);
        String max_loop_str = Integer.toString(max_loop);
        String thermoPathStr = this.thermoPath.toString() + "/";
        
        String[] args = {ntthalPathStr, "-a", calc_type, "-mv", mv_conc_str, 
            "-dv", dv_conc_str, "-n", dntp_conc_str, "-d", dna_conc_str, "-t", 
            temp_c_str, "-maxloop", max_loop_str, "-path", thermoPathStr, "-s1",
            seq1, "-s2", seq2, "-r"};

        double tm = calcTmProcess(args);
        
        return tm;
    }
    
    public double calcHairpinTm(String seq) throws IOException{
        
        double hairpinTm = calcHairpinTm(seq, _mv, _dv, _dntp, _dna, _temp_c, _max_loop);
        return hairpinTm;
    }
    
    public double calcHairpinTm(String seq, double mv, double dv, 
            double dntp, double dna, double temp_c, int max_loop)
            throws IOException{
        
        double hairpinTm = calcThermo(seq, seq, "HAIRPIN", mv, dv, 
                dntp, dna, temp_c, max_loop);
        
        return hairpinTm;
    }
    
    public double calcHomodimerTm(String seq) throws IOException {
        
        double homodimerTm = calcHomodimerTm(seq, _mv, _dv, _dntp, _dna, _temp_c, _max_loop);
        
        return homodimerTm;
    }
    
    public double calcHomodimerTm(String seq, double mv_conc, double dv_conc, 
            double dntp_conc, double dna_conc, double temp_c, int max_loop) 
            throws IOException {
        
        double hairpinTm = calcThermo(seq, seq, "ANY", mv_conc, dv_conc, 
                dntp_conc, dna_conc, temp_c, max_loop);
        
        return hairpinTm;
    }
    
    public double calcHeterodimerTm(String seq1, String seq2) throws IOException{
        
        double heterodimerTm = calcHeterodimerTm(seq1, seq2, _mv, _dv, _dntp, _dna, _temp_c, _max_loop);
        
        return heterodimerTm;
    }
    
    public double calcHeterodimerTm(String seq1, String seq2, double mv, 
            double dv, double dntp, double dna, double temp_c, 
            int max_loop) throws IOException{
        
        double heterodimerTm = calcThermo(seq1, seq2, "ANY", mv, dv, 
                dntp, dna, temp_c, max_loop);
        
        return heterodimerTm;
    }
}


