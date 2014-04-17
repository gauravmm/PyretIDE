/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser.tokenizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Gaurav Manek
 */
public class Line {
    public final static String LINE_TERMINATOR = "\n";
    public final static String LINE_INDENT = "  ";
    
    final int offset;
    final int length;
    final int number;
    final int expectedIndentation;
    final List<Token> contents;

    public Line(int n, int offset, int length, int expectedIndentation, List<Token> contents) {
        this.expectedIndentation = expectedIndentation;
        if(contents == null){
            throw new IllegalStateException("Null contents passed.");
        }
        this.contents = Collections.unmodifiableList(new ArrayList<>(contents));
        for(Token c : this.contents){
            c.setLine(this);
        }
        
        this.offset = offset;
        this.length = length;
        this.number = n;
    }

    @Override
    public String toString() {
        return String.format("%d\t%d\t%s%s", number, expectedIndentation, contents.toString(), LINE_TERMINATOR);
    }
    
    public List<Token> getContents(){
        return contents;
    }
    
    public String toIndentedString() {
        StringBuilder sb = new StringBuilder();
        int eI = expectedIndentation;
        while(eI-- > 0){
            sb.append(LINE_INDENT);
        }
        Iterator<Token> ci = contents.iterator();
        boolean start = true;
        while(ci.hasNext()){
            Token tok = ci.next();
            if(start && (TokenTypes.isWhitespaceTokenType(tok.getType()))){
                start = false;
                continue;
            }
            sb.append(tok.getValue());
            start = false;
        }
        sb.append(LINE_TERMINATOR);
        return sb.toString();
    }
    
}
