/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.sys.io.mweserver;

import edu.brown.cs.cutlass.sys.io.AbstractIdentifier;

/**
 *
 * @author Miles Holland
 */
public class LaskuraIdentifier implements AbstractIdentifier {

    /**
     * A unique identifier for the particular user.
     */
    private final String user_id;
    
    /**
     * A unique identifier for the particular resource within a user.
     */
    private final String name;

    public LaskuraIdentifier(String user_id, String name) {
        this.user_id = user_id;
        this.name = name;
    }
    
    @Override
    public String toString() {
        return user_id + "~" + name;
    }
}
