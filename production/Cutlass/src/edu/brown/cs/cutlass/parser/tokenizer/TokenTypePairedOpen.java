/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser.tokenizer;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Pattern;

/**
 *
 * @author Gaurav Manek
 */
public abstract class TokenTypePairedOpen extends TokenTypePaired {

    public TokenTypePairedOpen(Pattern pattern) {
        super(pattern);
    }
    
    public Collection<? extends TokenType> getIgnoreTokenTypes(){
        return Collections.emptySet();
    }
    
}
