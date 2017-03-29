/*
 * Copyright (C) 2016 Michigan State University Board of Trustees
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


import edu.msu.cme.rdp.primerdesign.screenoligos.oligo.DegenerateCharTable.DegenerateArray;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author leotift
 */
public class DegeneratePair{
    
    //For degen char values - passed in
    private DegenerateCharTable table;
    
    /**
     * Pair built 
     */
    private Oligo fwdDegenOligo;     
    private Oligo revDegenOligo;
    
    /**
     * Forward oligos represented by this object. If no degeneracy is needed,
     * possibilities will have a single element of this instance.
     */
    private Set<Oligo> fwdInputOligos;   
    
    /**
     * Reverse oligos represented by this object. If no degeneracy is needed,
     * possibilities will have a single element of this instance.
     */
    private Set<Oligo> revInputOligos;
    
    /**
     * Overlap Maps. Initial maps of overlap sets and the oligos that can bind
     * to them
     */
    private Map<Oligo, Set<String>> fwdTargetSetMap;
    private Map<Oligo, Set<String>> revTargetSetMap;
    
    /**
     * Target Set of Sequence names that can be hit by this degenerate pair. 
     */
    private Set<String> targetSet;
    
    /**
     * From the user input 
     */
    private int maxDegenNum;
    private int actualFwdDegenNum;
    private int actualRevDegenNum;
    private int fwdPos;
    private int revPos;
    
    private int degenOligoSize;
    
    public DegeneratePair () {
    
    }
          
    
    public DegeneratePair(Map<Oligo, Set<String>> fwdMap, Map<Oligo, Set<String>> revMap, Set<Oligo> fwdInputs, Set<Oligo> revInputs, Set<String> initTargets, int maxPrimers, int fwdPos, int revPos,
           DegenerateCharTable table) throws IOException {  
        this.targetSet = initTargets;      
        this.fwdTargetSetMap = fwdMap;  
        this.revTargetSetMap = revMap;
        this.maxDegenNum = maxPrimers;
        this.table = table;
        this.fwdPos = fwdPos;
        this.revPos = revPos;
        this.fwdInputOligos = fwdInputs;
        this.revInputOligos = revInputs;
    }
    
    public DegenerateCharTable getDegenTable() { return this.table;}
    public int getMaxNumPrimer() { return this.maxDegenNum;}
    public int getActualFwdDegen() { return this.actualFwdDegenNum;}
    public int getActualRevDegen() { return this.actualRevDegenNum;}
    public Set<Oligo> getFwdInputOligos() { return this.fwdInputOligos;}
    public Set<Oligo> getRevInputOligos() { return this.revInputOligos;}
    public Map<Oligo, Set<String>> getFwdTargetMap() { return this.fwdTargetSetMap;}
    public Map<Oligo, Set<String>> getRevTargetMap() { return this.revTargetSetMap;}
    public Oligo getFwdDegenOligo() { return this.fwdDegenOligo;}
    public Oligo getRevDegenOligo() { return this.revDegenOligo;}
    public Set<String> getTargetSet() { return this.targetSet;}
    public void setMaxNumPrimer(int maxPrimers) { this.maxDegenNum = maxPrimers;} 
    public void setFwdInputOligos(Set<Oligo> fwdOligos) { this.fwdInputOligos = fwdOligos;}
    public void setRevInputOligos(Set<Oligo> revOligos) { this.revInputOligos = revOligos;}
    public void setTargetSet(Set<String> targets) { this.targetSet = targets;}
    
    /**
     * Builds degenerate oligos
     *
     *       
     */
    public void buildDegenOligos() throws IOException {
        

        
//        StringBuilder fwdSequence = new StringBuilder();
//        StringBuilder revSequence = new StringBuilder();
//        for(int i = 0 ; i < this.degenOligoSize; i++) {             
//            fwdSequence.append(this.getDegenChar(i, this.fwdInputOligos));
//            revSequence.append(this.getDegenChar(i, this.revInputOligos));            
//        }
        this.fwdDegenOligo = new Oligo("");
        this.revDegenOligo = new Oligo("");
        
        this.fwdDegenOligo.setStartPosition(this.fwdPos);
        this.revDegenOligo.setStartPosition(this.revPos);
        
//        this.actualFwdDegenNum = this.calcMaxDegenNum(fwdDegenOligo);
//        this.actualRevDegenNum  = this.calcMaxDegenNum(revDegenOligo);
        
    }  

    
    
    /**
     * Returns degenerate char or A,C,G,T if only instance 
     *
     * @param index
     * @param Set<Oligo>
     * @return Character
     * 
     */
    public Character getDegenChar(int index, Set<Oligo> inputList) {
        
        List<Character> charList = new ArrayList<> ();
        Set<Character> posSet = new HashSet<> ();
        for (Oligo oligo : inputList) {
            char currChar = oligo.getSeq().charAt(index);
            posSet.add(currChar);
        }
               
        for(Character posChar : posSet) {
            charList.add(posChar);           
        }
        if (charList.size() == 1) {
            return charList.get(0);
        }
        char[] positionList = new char[charList.size()];
        for(int i = 0; i < charList.size(); i++) {
            positionList[i] = charList.get(i);
        }        
        
        Arrays.sort(positionList);
          
        DegenerateArray array = table.new DegenerateArray(positionList);
        
        return table.getdegenerates().get(array);
       
    }  

    /**
     * Returns true if this object represents more than one
     * input forward oligo
     *
     * @return boolean
     * 
     */
    public boolean isFwdOligoDegenerate() {
        return fwdNumOligoCount() > 1;
    }
    
    /**
     * Returns true if this object represents more than one
     * input reverse oligo
     *
     * @return boolean
     *
     */
    public boolean isRevOligoDegenerate() {
        return revNumOligoCount() > 1;
    }

             
    /**
     * 
     * @param Oligo degenOligo
     * @return int
     */
    public int calcMaxDegenNum(Oligo degenOligo) {
        int numDegeneracy = 1;       
        String seq = degenOligo.getSeq();
        for (int i = 0; i < seq.length(); i++) {            
             Integer posDegen = table.getDegenValues().get(seq.charAt(i));
             numDegeneracy *= posDegen.intValue();
        }
        
        return numDegeneracy;
    }

    /**
     * @return Returns fwdInput length   
     */
   
    public int fwdNumOligoCount() {
        return fwdInputOligos.size();
    }
    
    /**
     * @return Returns revInput length   
     */
   
    public int revNumOligoCount() {
        return revInputOligos.size();
    }
    
    
    @Override
    public String toString() {
        return "DegeneratePair{" + "fwdOligo=" + this.fwdInputOligos + " revOligo=" + this.revInputOligos + '}';
    }
    

           
}
