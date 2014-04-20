/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser;

import edu.brown.cs.cutlass.parser.tokenizer.TokenPairedOpening;
import edu.brown.cs.cutlass.util.Option;

/**
 * Represents
 *
 * @author Gaurav Manek
 */
public class PyretFunction extends PyretBlock {

    private final TokenPairedOpening scopeOpen;
    private final boolean isDataVariant;
    private final Option<PyretDataVariant> constructorOf;

    public PyretFunction(PyretLocation location, String name, TokenPairedOpening scopeOpen) {
        super(location, name);
        this.scopeOpen = scopeOpen;
        this.constructorOf = new Option<>();
        this.isDataVariant = false;
    }

    public PyretFunction(PyretDataVariant dataVariant) {
        super(dataVariant.location, dataVariant.name);
        this.scopeOpen = null;
        this.constructorOf = new Option<>(dataVariant);
        this.isDataVariant = true;
    }

    public TokenPairedOpening getScopeOpen() {
        return scopeOpen;
    }

    public Option<PyretDataVariant> getConstructorOf() {
        return constructorOf;
    }

    public boolean isDataVariant() {
        return isDataVariant;
    }

}
