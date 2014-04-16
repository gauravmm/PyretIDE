/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser.tokenizer;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Gaurav Manek
 */
public class TokenParserOutput {
    private final List<Line> tokenLines;
    private final Map<TokenType, Map<String, List<Token>>> tokenCollected;

    TokenParserOutput(List<Line> tokenLines, Map<TokenType, Map<String, List<Token>>> tokenCollected) {
        this.tokenLines = tokenLines;
        this.tokenCollected = tokenCollected;
    }

    public List<Line> getTokenLines() {
        return tokenLines;
    }

    public Map<TokenType, Map<String, List<Token>>> getTokenCollected() {
        return tokenCollected;
    }
    
    public List<String> reindent(){
        List<String> rv = new LinkedList<>();
        for(Line l : tokenLines){
            rv.add(l.toIndentedString());
        }
        return rv;
    }
}
