/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.sys.io;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Miles Holland
 */
public class DiskIdentifier extends AbstractIdentifier<Path> {

    public DiskIdentifier(Path id) {
        super(id);
    }

    public DiskIdentifier(String id) {
        super(Paths.get(id));
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
