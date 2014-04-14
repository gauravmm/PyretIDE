/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser.tokenizer;

import java.util.Enumeration;
import java.util.regex.Pattern;
import javax.swing.text.AttributeSet;

/**
 *
 * @author Gaurav Manek
 */
public abstract class TokenType implements AttributeSet {
    /*
     Whitespace (but not newlines)
     Keywords,
     Keywords with colons
     Punctuation
     Initial Operators
     Whitespace
     Strings (with ' and " quotes)
     Comments
     Default
     Null/Illegal token type
     */

    public Pattern pattern;

    /**
     *
     * @return true, if tokens of this type should be collected by the parser
     * for later processing. false otherwise;
     */
    public abstract boolean toAggregate();

    @Override
    public int getAttributeCount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isDefined(Object attrName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isEqual(AttributeSet attr) {
        return this == attr;
    }

    @Override
    public AttributeSet copyAttributes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getAttribute(Object key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Enumeration<?> getAttributeNames() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean containsAttribute(Object name, Object value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean containsAttributes(AttributeSet attributes) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AttributeSet getResolveParent() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
