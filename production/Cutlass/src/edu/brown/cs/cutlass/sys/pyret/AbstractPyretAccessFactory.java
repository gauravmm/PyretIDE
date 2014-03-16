/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.sys.pyret;

import edu.brown.cs.cutlass.sys.io.AbstractIdentifier;

/**
 * A common interface that provides access to PyretAccess objects.
 * @author Gaurav Manek
 */
public interface AbstractPyretAccessFactory<T extends AbstractIdentifier> {
    public AbstractPyretAccess<T> getPyretAccess();
}
