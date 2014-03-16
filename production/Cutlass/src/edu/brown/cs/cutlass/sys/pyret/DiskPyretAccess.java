/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.sys.pyret;

import edu.brown.cs.cutlass.sys.io.DiskIdentifier;

/**
 *
 * @author Dilip Arumugam
 */
public class DiskPyretAccess extends AbstractPyretAccess<DiskIdentifier> {

    /*  Remember to call:
     *  this.firePyretAccessListener(AbstractPyretAccess.Stream.STDOUT, "String Here");
     *  when any response is recieved from the code.
     */
    
    @Override
    public void run(DiskIdentifier identifier) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void getAllOutput(Stream stream) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
