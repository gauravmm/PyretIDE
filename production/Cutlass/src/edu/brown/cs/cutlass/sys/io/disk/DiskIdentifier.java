/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.sys.io.disk;

import edu.brown.cs.cutlass.sys.io.AbstractIdentifier;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Miles Holland
 */
public class DiskIdentifier implements AbstractIdentifier {

    private final Path id;

    public DiskIdentifier(Path id0) {
        id = id0;
    }

    public Path getId() {
        return id;
    }

    public DiskIdentifier(String id0) {
        this(Paths.get(id0));
    }

    @Override
    public String toString() {
        return id.toString();
    }

    @Override
    public String getDisplayName() {
        return id.getFileName().toString();
    }
    
}
