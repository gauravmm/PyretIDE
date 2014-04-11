/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.sys;

import edu.brown.cs.cutlass.sys.io.AbstractIO;
import edu.brown.cs.cutlass.sys.io.disk.DiskIO;
import edu.brown.cs.cutlass.sys.io.disk.DiskIdentifier;
import edu.brown.cs.cutlass.sys.pyret.AbstractPyretAccess;
import edu.brown.cs.cutlass.sys.pyret.DiskPyretAccess;
import edu.brown.cs.cutlass.sys.ux.AbstractClipboard;
import edu.brown.cs.cutlass.sys.ux.DefaultClipboard;

/**
 *
 * @author Gaurav Manek
 */
public class DefaultSystemAbstraction implements SystemAbstraction<DiskIdentifier> {

    private final DefaultClipboard defaultClipboard;
    private final DiskIO diskIO;

    public DefaultSystemAbstraction() {
        defaultClipboard = new DefaultClipboard();
        diskIO = new DiskIO();
    }
    
    @Override
    public AbstractClipboard getClipboard() {
        return defaultClipboard;
    }

    @Override
    public AbstractIO<DiskIdentifier> getIO() {
        return diskIO;
    }

    @Override
    public AbstractPyretAccess<DiskIdentifier> getPyretAccess(DiskIdentifier identifier) {
        return new DiskPyretAccess(identifier);
    }

}
