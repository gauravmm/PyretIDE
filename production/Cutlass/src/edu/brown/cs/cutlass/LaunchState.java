/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs.cutlass;

import edu.brown.cs.cutlass.sys.io.AbstractIdentifier;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds (and handles serializing/unserializing of) the program state at launch.
 * This includes the files currently open.
 *
 * @author Gaurav Manek
 * T is an arrPointer
 */
class LaunchState<T extends AbstractIdentifier> {
    
	// T is an AbstractIdentifer that identifies a unique resource on the 
	// current I/O media (Ex. a path object to a space in disk).
    private final ArrayList<T> openFiles;

    private LaunchState(ArrayList<T> openFiles) {
        this.openFiles = openFiles;
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not yet written");
    }
    
    /**Work in progress: Specs of fromString input string:
     * Must include:
     * file path representation
     * anything we decide on later.
     */
    
    
    /** 
     * 
     * @param in A string representation of the launch state. Includes:
     * 
     * @return A LaunchState object
     * @throws IllegalArgumentException
     */
    public static LaunchState fromString(List<String> in) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not yet written");
    }
    
    
}
