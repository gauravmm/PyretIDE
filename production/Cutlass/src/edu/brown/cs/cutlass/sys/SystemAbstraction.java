/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.sys;

import edu.brown.cs.cutlass.sys.io.AbstractIO;
import edu.brown.cs.cutlass.sys.io.AbstractIdentifier;
import edu.brown.cs.cutlass.sys.pyret.AbstractPyretAccessFactory;
import edu.brown.cs.cutlass.sys.ux.AbstractClipboard;

/**
 *
 * @author Gaurav Manek
 * @param <T>
 */
public interface SystemAbstraction<T extends AbstractIdentifier> extends AbstractPyretAccessFactory<T> {
    
    public AbstractIO<T> getIO();
    
    public AbstractClipboard getClipboard();
    
}
