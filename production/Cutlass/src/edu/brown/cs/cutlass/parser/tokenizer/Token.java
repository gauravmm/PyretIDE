/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser.tokenizer;

import javax.swing.text.AttributeSet;
import javax.swing.text.Document;
import javax.swing.text.Element;

/**
 *
 * @author Gaurav Manek
 */
public class Token implements Element {

    private final String value;
    private final int length;
    private final int offset;
    private final TokenType type;
    
    public Token next = null;
    public Token previous = null;
    
    public Token(String value, int length, int offset, TokenType type) {
        this.value = value;
        this.length = length;
        this.offset = offset;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public int getLength() {
        return length;
    }

    public int getOffset() {
        return offset;
    }

    public TokenType getType() {
        return type;
    }

    @Override
    public Document getDocument() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Element getParentElement() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AttributeSet getAttributes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getStartOffset() {
        return offset;
    }

    @Override
    public int getEndOffset() {
        return offset + length;
    }

    @Override
    public int getElementIndex(int offset) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getElementCount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Element getElement(int index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public String toString() {
        return "(" + type.getClass().getSimpleName() + " " + offset + " " + value + ')';
    }
}
