/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.sys.pyret;

import edu.brown.cs.cutlass.sys.io.AbstractIdentifier;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gaurav Manek
 */
public abstract class AbstractPyretAccess<T extends AbstractIdentifier> implements AutoCloseable {

    public enum Stream {
        STDOUT, STDERR;
    }

    private List<PyretAccessListener> listeners = new ArrayList<>();

    public void addPyretAccessListener(PyretAccessListener l) {
        if (l == null) {
            throw new IllegalArgumentException("A null listener was passed.");
        }
        listeners.add(l);
    }

    public void removePyretAccessListener(PyretAccessListener l) {
        listeners.remove(l);
    }

    protected void firePyretAccessListener(AbstractPyretAccess.Stream stream, String out) {
        for (PyretAccessListener l : listeners) {
            l.handlePyretAccessOut(stream, out);
        }
    }

    public abstract void run(T identifier);

    /**
     * Get all the output from the given stream.
     *
     * @param stream The stream to get all the output from.
     */
    public abstract void getAllOutput(AbstractPyretAccess.Stream stream);

    /**
     * Close the PyretAccessObject. This should send a signal to kill the
     * process and clean up temporary data.
     */
    public abstract void close();
}
