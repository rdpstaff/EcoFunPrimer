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

package edu.msu.cme.rdp.primerdesign.deadcode;

import edu.msu.cme.rdp.primerdesign.deadcode.RDPStringBuilder;

/**
 *
 * @author leotift
 */
public class RDPStringClone extends RDPStringBuilder{
    
    private static RDPStringClone instance = new RDPStringClone();
          
    private RDPStringClone(){}
    
    public static RDPStringClone getInstance() {
        return instance; 
    }
        
    public RDPStringClone getRDPStringBuilder() {      
        return (RDPStringClone) this.clone();
    }
    
}
