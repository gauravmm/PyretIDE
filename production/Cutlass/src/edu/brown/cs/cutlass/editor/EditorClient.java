/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.editor;

import edu.brown.cs.cutlass.editor.callgraph.CallGraphEntry;
import edu.brown.cs.cutlass.sys.pyret.AbstractPyretAccess;
import java.util.Collection;

/**
 * An interface describing the services the Editor requires.
 *
 * @author Gaurav Manek
 */
public interface EditorClient {

    /**
     * Notify the editor of a change in the quick navigation data.
     *
     * @param quickNav The QuickNavigation object representing the new state of
     * the quick navigation data.
     */
    public void handleQuickNavigationChange(Collection<CallGraphEntry> quickNav);

    /**
     * Request an accessor object that allows interaction with an instance of
     * Pyret.
     *
     * @return An AbstractPyretAccess object that represents a running instance
     * of Pyret.
     */
    public AbstractPyretAccess getPyretAccess();
 
}
