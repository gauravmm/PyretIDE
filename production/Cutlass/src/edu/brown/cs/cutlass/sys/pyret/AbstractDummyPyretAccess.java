/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.sys.pyret;

import edu.brown.cs.cutlass.sys.io.AbstractIdentifier;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Gaurav Manek
 * @param <T>
 */
public class AbstractDummyPyretAccess<T extends AbstractIdentifier> extends AbstractPyretAccess<T> {

    @Override
    protected PyretTerminationValue doInBackground() throws Exception {
        return new PyretTerminationValue();
    }

    @Override
    public List<PyretOutputValue> getAllOutput(Stream stream) {
        return Collections.emptyList();
    }

    @Override
    public void close() {
    }
    
}
