/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.sys.io.laskura;

import edu.brown.cs.cutlass.sys.io.AbstractIdentifier;

/**
 *
 * @author Miles Holland
 */
public class LaskuraIdentifier implements AbstractIdentifier {
  
    /**
     * A unique identifier for the particular resource within a user.
     */
    private final String name;

    public LaskuraIdentifier(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }
}
