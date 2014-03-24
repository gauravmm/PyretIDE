/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.util;

import java.util.Objects;

/**
 * This is a generic pair class that can be easily subclassed to make a
 * use-specific pair.
 *
 * @author Gaurav Manek
 */
public class Pair<X, Y> {
    private final X x;
    private final Y y;

    public X getX() {
        return x;
    }

    public Y getY() {
        return y;
    }

    public Pair(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.x);
        hash = 47 * hash + Objects.hashCode(this.y);
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
        final Pair<?, ?> other = (Pair<?, ?>) obj;
        if (!Objects.equals(this.x, other.x)) {
            return false;
        }
        if (!Objects.equals(this.y, other.y)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Pair{" + "x=" + x + ", y=" + y + '}';
    }
    
}
