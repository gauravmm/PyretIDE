/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.ui;

/**
 *
 * @author Gaurav Manek
 */
public interface FindClient {
    
    public boolean findNext(FrmFinder.FindType type, boolean matchCase, boolean forwards, boolean wholeWords, String find);
    public boolean replaceNext(FrmFinder.FindType type, boolean matchCase, boolean forwards, boolean wholeWords, String find, String replace);
    public boolean replaceAll(FrmFinder.FindType type, boolean matchCase, boolean forwards, boolean wholeWords, String find, String replace);
}
