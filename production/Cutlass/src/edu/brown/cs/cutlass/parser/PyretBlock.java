/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser;

import java.util.Objects;

/**
 *
 * @author Gaurav Manek
 */
abstract class PyretBlock {
    
    protected final PyretLocation location;
    protected final String name;

    public PyretBlock(PyretLocation location, String name) {
        this.location = location;
        this.name = name;
    }

    public PyretLocation getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Pyret{" + location.token.getOffset() + ", " + name + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.location);
        hash = 89 * hash + Objects.hashCode(this.name);
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
        final PyretBlock other = (PyretBlock) obj;
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
}
