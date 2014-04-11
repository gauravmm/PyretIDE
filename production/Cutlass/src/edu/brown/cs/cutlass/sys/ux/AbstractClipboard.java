/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.sys.ux;

/**
 *
 * @author Zachary Zagorski
 */
public interface AbstractClipboard {

    public void put(CharSequence s);

    public String get();
}
