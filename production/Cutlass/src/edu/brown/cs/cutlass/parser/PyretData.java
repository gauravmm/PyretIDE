/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Gaurav Manek
 */
public class PyretData extends PyretBlock {

    private final List<PyretDataVariant> variants = new LinkedList<>();
    
    public PyretData(PyretLocation location, String name) {
        super(location, name);
    }

    public int size() {
        return variants.size();
    }

    public Iterator<PyretDataVariant> iterator() {
        return variants.iterator();
    }

    public boolean add(PyretDataVariant e) {
        return variants.add(e);
    }
    
}
