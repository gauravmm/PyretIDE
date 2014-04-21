/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.editor.callgraph;

import edu.brown.cs.cutlass.editor.EditorJumpTo;
import edu.brown.cs.cutlass.util.Option;

/**
 * Holds the data necessary to render the QuickNavigation box.
 *
 * @author Gaurav Manek
 */
public class CallGraphEntry {

    public final String name;
    public final boolean isCurrent;
    public final boolean callsCurrent;
    public final boolean isCalledByCurrent;
    public final Option<String> dataOf;
    public final EditorJumpTo jumpTo;

    public CallGraphEntry(String name, boolean isCurrent, boolean callsCurrent, boolean isCalledByCurrent, Option<String> dataOf, EditorJumpTo jumpTo) {
        this.name = name;
        this.isCurrent = isCurrent;
        this.callsCurrent = callsCurrent;
        this.isCalledByCurrent = isCalledByCurrent;
        this.dataOf = dataOf;
        this.jumpTo = jumpTo;
    }
    
}
