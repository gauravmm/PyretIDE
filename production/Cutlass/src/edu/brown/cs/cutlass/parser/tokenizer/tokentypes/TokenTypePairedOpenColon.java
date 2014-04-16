/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser.tokenizer.tokentypes;

import edu.brown.cs.cutlass.parser.tokenizer.Token;
import edu.brown.cs.cutlass.parser.tokenizer.TokenPairedOpening;
import edu.brown.cs.cutlass.parser.tokenizer.TokenType;
import edu.brown.cs.cutlass.parser.tokenizer.TokenTypePairedOpen;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author dilip
 */
public class TokenTypePairedOpenColon extends TokenTypePairedOpen {

    public static TokenTypePairedOpenColon getInstance(){
        return instance;
    }
    
    private static final TokenTypePairedOpenColon instance = new TokenTypePairedOpenColon();
    
    private TokenTypePairedOpenColon(){
        super(Pattern.compile("^:"));
    }
    
    @Override
    public Token constructToken(String value, int offset, int length) {
        return new TokenPairedOpening(value, offset, length, TokenTypePairedOpenColon.getInstance());
    }
    
    @Override
    public boolean toAggregate() {
        return false;
    }
    
}
