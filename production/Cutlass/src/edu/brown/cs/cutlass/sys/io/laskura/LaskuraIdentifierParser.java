/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.sys.io.laskura;

import edu.brown.cs.cutlass.sys.io.AbstractIdentifierParser;

/**
 *
 * @author Miles Holland
 */
public class LaskuraIdentifierParser implements AbstractIdentifierParser<LaskuraIdentifier> {

    @Override
    public LaskuraIdentifier parse(String in) throws IllegalArgumentException {
        return new LaskuraIdentifier(in);
    }
    
}
