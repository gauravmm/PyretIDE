/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.sys.pyret;

import edu.brown.cs.cutlass.sys.io.AbstractIdentifier;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.SwingWorker;

/**
 *
 * @author Gaurav Manek
 */
public abstract class AbstractPyretAccess<T extends AbstractIdentifier> extends SwingWorker<PyretTerminationValue, PyretOutputValue> implements AutoCloseable {

    @Override
    protected void done() {
        try {
            this.firePyretAccessListener(get());
        } catch (InterruptedException ex) {
            // The code was interrupted. Return nothing.
        } catch (ExecutionException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected void process(List<PyretOutputValue> chunks) {
        for (PyretOutputValue c : chunks) {
            this.firePyretAccessListener(c);
        }
    }

    @Override
    protected abstract PyretTerminationValue doInBackground() throws Exception;

    public enum Stream {

        STDOUT, STDERR;
    }

    private final List<PyretAccessListener> listeners = new ArrayList<>();

    public void addPyretAccessListener(PyretAccessListener l) {
        if (l == null) {
            throw new IllegalArgumentException("A null listener was passed.");
        }
        listeners.add(l);
    }

    public void removePyretAccessListener(PyretAccessListener l) {
        listeners.remove(l);
    }

    protected void firePyretAccessListener(PyretOutputValue returnValue) {
        for (PyretAccessListener l : listeners) {
            l.handlePyretAccessOutput(returnValue);
        }
    }

    protected void firePyretAccessListener(PyretTerminationValue returnValue) {
        for (PyretAccessListener l : listeners) {
            l.handlePyretAccessEnds(returnValue);
        }
    }

    /**
     * Get all the output from the given stream.
     *
     * @param stream The stream to get all the output from.
     */
    public abstract List<PyretOutputValue> getAllOutput(AbstractPyretAccess.Stream stream);

    /**
     * Close the PyretAccessObject. This should send a signal to kill the
     * process and clean up temporary data.
     */
    @Override
    public abstract void close();
}
