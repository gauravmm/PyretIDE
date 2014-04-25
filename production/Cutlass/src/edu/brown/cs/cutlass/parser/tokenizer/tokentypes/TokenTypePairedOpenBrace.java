/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser.tokenizer.tokentypes;

import edu.brown.cs.cutlass.parser.tokenizer.Token;
import edu.brown.cs.cutlass.parser.tokenizer.TokenPairedOpening;
import edu.brown.cs.cutlass.parser.tokenizer.TokenScope;
import edu.brown.cs.cutlass.parser.tokenizer.TokenType;
import edu.brown.cs.cutlass.parser.tokenizer.TokenTypePaired;
import edu.brown.cs.cutlass.parser.tokenizer.TokenTypePairedOpen;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyle;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStylePunct;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 *
 * @author Gaurav Manek
 */
public class TokenTypePairedOpenBrace extends TokenTypePairedOpen {

    public static TokenTypePairedOpenBrace getInstance() {
        return instance;
    }

    private static final TokenTypePairedOpenBrace instance = new TokenTypePairedOpenBrace();

    private TokenTypePairedOpenBrace() {
        super(Pattern.compile("^\\{"));
    }

    @Override
    public Token constructToken(String value, int offset, int length, TokenScope scope) {
        return new TokenPairedOpening(value, offset, length, scope, TokenTypePairedOpenBrace.getInstance());
    }

    @Override
    public TokenStyle getStyle() {
        return TokenStylePunct.getInstance();
    }

    @Override
    public List<TokenTypePaired> getIgnoreTokenTypes() {
        return Arrays.asList(TokenTypePairedOpenColon.getInstance(), TokenTypePairedOpenCloseColon.getInstance(), TokenTypePairedCloseColonWords.getInstance(), TokenTypePairedCloseColon.getInstance());
    }
    
    

}
