/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.sys.io;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Gaurav Manek
 */
public class DiskIdentifier extends AbstractIdentifier<Path> {

    public DiskIdentifier(Path id) {
        super(id);
    }

    public DiskIdentifier(String id) {
        super(Paths.get(id));
    }
}
