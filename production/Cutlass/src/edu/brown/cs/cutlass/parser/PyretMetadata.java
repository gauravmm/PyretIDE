/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Holds the output of a PyretParser
 *
 * @author Gaurav Manek
 */
public final class PyretMetadata {

    /**
     * A read-only list of all functions found in the source code.
     */
    public final List<PyretFunction> functions;

    /**
     * A read-only list of all Data definitions found in the source code.
     */
    public final List<PyretData> data;

    /**
     * A read-only map such that the key maps to a list of all functions that
     * call this. (i.e. "Who calls me?"). Function calls that do not appear in
     * any function are not included in this.
     */
    public final Map<PyretFunction, List<PyretFunction>> functionCallGraphFrom;

    /**
     * A read-only map such that the key maps to a list of all functions that
     * this function calls. (i.e. "Whom do I call?"). Function calls that do not
     * appear in any function are not included in this.
     */
    public final Map<PyretFunction, List<PyretFunction>> functionCallGraphTo;

    /**
     * A list of all locations from which a function is called. (i.e. "Where am
     * I called from?"). Function calls that do not appear in any function also
     * appear in this.
     */
    public final Map<PyretFunction, List<PyretFunctionCall>> functionCalls;

    public PyretMetadata(List<PyretFunction> functions, List<PyretData> data, Map<PyretFunction, List<PyretFunction>> functionCallGraphFrom, Map<PyretFunction, List<PyretFunction>> functionCallGraphTo, Map<PyretFunction, List<PyretFunctionCall>> functionCalls) {
        this.functions = Collections.unmodifiableList(functions);
        this.data = Collections.unmodifiableList(data);
        this.functionCallGraphFrom = Collections.unmodifiableMap(functionCallGraphFrom);
        this.functionCallGraphTo = Collections.unmodifiableMap(functionCallGraphTo);
        this.functionCalls = Collections.unmodifiableMap(functionCalls);
    }
}
