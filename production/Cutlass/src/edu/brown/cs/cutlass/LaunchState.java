/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs.cutlass;

import edu.brown.cs.cutlass.sys.io.AbstractIdentifier;
import java.util.ArrayList;

/**
 * Holds (and handles serializing/unserializing of) the program state at launch.
 * This includes the files currently open.
 *
 * @author Gaurav Manek
 */
class LaunchState<T extends AbstractIdentifier> {
    
    private final ArrayList<T> openFiles;

    private LaunchState(ArrayList<T> openFiles) {
        this.openFiles = openFiles;
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not yet written");
    }
    
    
    public static LaunchState fromString(String in) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not yet written");
    }
    
    
}
