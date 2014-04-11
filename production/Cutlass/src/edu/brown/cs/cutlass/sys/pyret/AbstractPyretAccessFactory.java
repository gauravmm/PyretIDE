/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.sys.pyret;

import edu.brown.cs.cutlass.sys.io.AbstractIdentifier;

/**
 * A common interface that provides access to PyretAccess objects.
 *
 * @author Gaurav Manek
 * @param <T> The type of AbstractIdentifier for Pyret to run on.
 */
public interface AbstractPyretAccessFactory<T extends AbstractIdentifier> {

    /**
     * 
     * @param identifier The identifier for Pyret to run on.
     * @return 
     */
    public AbstractPyretAccess<T> getPyretAccess(T identifier);
}
