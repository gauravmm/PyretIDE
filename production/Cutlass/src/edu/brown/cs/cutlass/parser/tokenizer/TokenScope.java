/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser.tokenizer;

import java.util.Iterator;

/**
 * Immutable singly-linked
 *
 * @author Gaurav Manek
 */
public class TokenScope implements Iterable<Token> {

    private final Token first;
    private final TokenScope rest;

    private TokenScope(Token first, TokenScope rest) {
        this.first = first;
        this.rest = rest;
    }

    public TokenScope() {
        first = null;
        rest = null;
    }

    public Token getFirst() {
        if (first == null) {
            throw new IllegalStateException("Cannot get data from an empty node.");
        }
        return first;
    }

    public TokenScope getRest() {
        if (rest == null) {
            throw new IllegalStateException("Cannot get data from an empty node.");
        }
        return rest;
    }

    public boolean hasData() {
        return first != null;
    }

    public TokenScope push(Token token) {
        if (token == null) {
            throw new IllegalStateException("Cannot push an empty node onto the stack.");
        }
        return new TokenScope(token, this);
    }

    @Override
    public Iterator<Token> iterator() {
        return new IteratorTokenScope(this);
    }

    private static class IteratorTokenScope implements Iterator<Token> {

        private TokenScope tokenScope;

        public IteratorTokenScope(TokenScope tokenScope) {
            this.tokenScope = tokenScope;
        }

        @Override
        public boolean hasNext() {
            return tokenScope.hasData();
        }

        @Override
        public Token next() {
            Token f = tokenScope.getFirst();
            tokenScope = tokenScope.getRest();
            return f;
        }

        @Override
        @Deprecated
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public String toString() {
        if (hasData()) {
            return "{" + first + ", " + rest + '}';
        } else {
            return "-";
        }
    }

}
