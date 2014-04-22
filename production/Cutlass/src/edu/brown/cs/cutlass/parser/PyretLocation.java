/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser;

import edu.brown.cs.cutlass.parser.tokenizer.Token;
import java.util.Objects;

/**
 *
 * @author Gaurav Manek
 */
public class PyretLocation {

    /**
     * The number of characters from the start of file that this location points
     * to.
     */
    public final Token token;

    public PyretLocation(Token token) {
        this.token = token;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.token);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PyretLocation other = (PyretLocation) obj;
        if (!Objects.equals(this.token, other.token)) {
            return false;
        }
        return true;
    }
    
}
