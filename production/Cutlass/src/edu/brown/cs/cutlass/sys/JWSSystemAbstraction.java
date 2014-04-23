/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.sys;

import edu.brown.cs.cutlass.sys.io.AbstractIO;
import edu.brown.cs.cutlass.sys.io.jwsdisk.JWSDiskIO;
import edu.brown.cs.cutlass.sys.io.jwsdisk.JWSDiskIdentifier;
import edu.brown.cs.cutlass.sys.pyret.AbstractDummyPyretAccess;
import edu.brown.cs.cutlass.sys.pyret.AbstractPyretAccess;
import edu.brown.cs.cutlass.sys.ux.AbstractClipboard;
import edu.brown.cs.cutlass.sys.ux.JWSClipboard;

/**
 *
 * @author Gaurav Manek
 */
public class JWSSystemAbstraction implements SystemAbstraction<JWSDiskIdentifier> {

    private final JWSClipboard defaultClipboard;
    private final JWSDiskIO diskIO;

    public JWSSystemAbstraction() {
        defaultClipboard = new JWSClipboard();
        diskIO = new JWSDiskIO();
    }

    @Override
    public AbstractClipboard getClipboard() {
        return defaultClipboard;
    }

    @Override
    public AbstractIO<JWSDiskIdentifier> getIO() {
        return diskIO;
    }

    @Override
    public AbstractPyretAccess<JWSDiskIdentifier> getPyretAccess(JWSDiskIdentifier identifier) {
        return new AbstractDummyPyretAccess<>();
    }

}
