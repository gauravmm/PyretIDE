/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser;

/**
 * Understand Pyret source code and provide the IDE with metadata of program.
 *
 * @author Gaurav Manek
 */
public interface PyretParser {
    public PyretMetadata parse(String s);
}
