/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.sys.ux;

import java.awt.datatransfer.ClipboardOwner;

/**
 *
 * @author Zachary Zagorski
 */
public interface AbstractClipboard extends ClipboardOwner{

    public void put(CharSequence s);

    public String get();
}
