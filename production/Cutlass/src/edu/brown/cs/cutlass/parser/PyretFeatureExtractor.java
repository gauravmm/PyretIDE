/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser;

import edu.brown.cs.cutlass.parser.tokenizer.Token;
import edu.brown.cs.cutlass.parser.tokenizer.TokenParserOutput;
import edu.brown.cs.cutlass.parser.tokenizer.TokenType;
import edu.brown.cs.cutlass.parser.tokenizer.tokentypes.TokenTypeKeywordAggregate;
import edu.brown.cs.cutlass.util.Option;
import java.util.List;
import java.util.Map;

/**
 * Understand Pyret source code and provide the IDE with metadata of program.
 *
 * @author Gaurav Manek
 */
public class PyretFeatureExtractor {
    public PyretMetadata extract(TokenParserOutput tpo){
        // Keywords
        Map<String, List<Token>> keywords = tpo.getTokenCollected().get(TokenTypeKeywordAggregate.getInstance());
        if(keywords == null){
            throw new IllegalStateException("Collected tokens not properly set-up.");
        }
        
        // Extract function definitions:
        List<Token> funLocs = keywords.get("fun");
        for (Token tok : funLocs){
            
        }
        
        return null;
    }
    
    private Option<Token> getNextToken(Token start, TokenType type, boolean inSameLine){
        Token tok = start;
        while(true){
            if(tok.hasNextToken()){
                
            } else {
                return new Option<>();
            }
        }
        return null;
    }
}
