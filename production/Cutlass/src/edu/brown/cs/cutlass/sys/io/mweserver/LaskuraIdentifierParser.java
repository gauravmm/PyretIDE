/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.sys.io.mweserver;

import edu.brown.cs.cutlass.sys.io.AbstractIdentifierParser;
import edu.brown.cs.cutlass.util.Lumberjack;
import edu.brown.cs.cutlass.util.Lumberjack.Level;

/**
 *
 * @author Miles Holland
 */
public class LaskuraIdentifierParser implements AbstractIdentifierParser<LaskuraIdentifier> {

    @Override
    public LaskuraIdentifier parse(String in) throws IllegalArgumentException {
        String[] parts = in.split("\t",2);
        if(parts.length != 2){
            Lumberjack.log(Level.WARN, "MWEServerIDParser could not split the input into parts.");
            throw new IllegalArgumentException("Invalid Path.");
        }
        return new LaskuraIdentifier(parts[0], parts[1]);
    }

    
}
