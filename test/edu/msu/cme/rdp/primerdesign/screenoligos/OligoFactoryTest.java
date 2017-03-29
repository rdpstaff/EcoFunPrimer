/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.msu.cme.rdp.primerdesign.screenoligos;

import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.MismatchProperties;
import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.OligoFactory;
import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.Oligo;
import edu.msu.cme.rdp.primerdesign.utils.Primer3Wrapper;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gunturus
 */
public class OligoFactoryTest {
    
    OligoFactory oligoFactory;
    Oligo testOligo;
    Primer3Wrapper primer3;
    
    public OligoFactoryTest() {
    }
    
    @Before
    public void setUp() throws IOException {
        oligoFactory = new OligoFactory();
        primer3 = new Primer3Wrapper("mac", 50.0, 1.5);
        testOligo = OligoFactory.createOligo("AGTAAGCGAAAGGCAGA", primer3, new MismatchProperties(new Oligo("")), 0);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testOligoTm() {
        
        double tm = new Double(52.682426);
        assertEquals(tm,testOligo.getTm(),4);
    }
    
    @Test
    public void testHairpinTm() {
        assertEquals(44.55018024188678,testOligo.getHairpinTm(),4);
    }
    
    @Test
    public void testHomodimerTm() {
        assertEquals(-18.967870923921822,testOligo.getHomodimerTm(),4);
    }
    
    @Test
    public void testEqualOligos() throws IOException {
        Oligo oligo2 = OligoFactory.createOligo("AGTAAGCGAAAGGCAGA", primer3, new MismatchProperties(new Oligo("")), 0);
        
        assertEquals(true, oligo2.equals(testOligo));
    }
}
