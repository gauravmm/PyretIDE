/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.editor.augmentedscroll;

/**
 *
 * @author Gaurav Manek
 */
public abstract class ScrollAugmentation {

    /**
     * Get the String to show when the mouse hovers over this augmentation for
     * some time.
     *
     * @return The help text as a String.
     */
    public abstract String getToolTipText();

    // Some kind of drawing function or data.
}
