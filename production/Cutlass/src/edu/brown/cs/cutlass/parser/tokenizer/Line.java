/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser.tokenizer;

import java.util.Collections;
import java.util.List;
import javax.swing.text.AttributeSet;
import javax.swing.text.Document;
import javax.swing.text.Element;

/**
 *
 * @author Gaurav Manek
 */
public class Line implements Element {
    
    final int offset;
    final int length;
    final int expectedIndentation;
    final List<Token> contents;

    public Line(int expectedIndentation, List<Token> contents) {
        this.expectedIndentation = expectedIndentation;
        this.contents = contents;
        if(contents.isEmpty()){
            throw new IllegalStateException();
        }
        
        this.offset = contents.get(0).getOffset();
        this.length = contents.get(contents.size() - 1).getEndOffset() - this.offset;
    }

    public Line(int offset, int expectedIndentation) {
        this.offset = offset;
        this.expectedIndentation = expectedIndentation;
        this.contents = Collections.emptyList();
        this.length = 0;
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
        return false;
    }
    
}
