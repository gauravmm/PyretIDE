/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.sys;

import edu.brown.cs.cutlass.sys.io.AbstractIO;
import edu.brown.cs.cutlass.sys.io.laskura.LaskuraIO;
import edu.brown.cs.cutlass.sys.io.laskura.LaskuraIdentifier;
import edu.brown.cs.cutlass.sys.pyret.AbstractDummyPyretAccess;
import edu.brown.cs.cutlass.sys.pyret.AbstractPyretAccess;
import edu.brown.cs.cutlass.sys.ux.AbstractClipboard;
import edu.brown.cs.cutlass.sys.ux.DefaultClipboard;

/**
 *
 * @author Gaurav Manek
 */
public class LaskuraSystemAbstraction implements SystemAbstraction<LaskuraIdentifier> {

    private final DefaultClipboard defaultClipboard;
    private final LaskuraIO io;

    public LaskuraSystemAbstraction(LaskuraIO io) {
        defaultClipboard = new DefaultClipboard();
        this.io = io;
    }

    @Override
    public AbstractClipboard getClipboard() {
        return defaultClipboard;
    }

    @Override
    public AbstractIO<LaskuraIdentifier> getIO() {
        return io;
    }

    @Override
    public AbstractPyretAccess<LaskuraIdentifier> getPyretAccess(LaskuraIdentifier identifier) {
        return new AbstractDummyPyretAccess<>();
    }

}
