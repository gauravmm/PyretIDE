/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.sys.io.jwsdisk;

import edu.brown.cs.cutlass.sys.io.AbstractIO;
import edu.brown.cs.cutlass.sys.io.AbstractIOException;
import edu.brown.cs.cutlass.sys.io.AbstractIdentifierParser;
import edu.brown.cs.cutlass.util.Option;
import java.util.List;

/**
 *
 * @author Gaurav Manek
 */
public class JWSDiskIO implements AbstractIO<JWSDiskIdentifier> {

    @Override
    public List<String> getConfigurationFile(String identifier) throws AbstractIOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setConfigurationFile(String identifier, List<? extends CharSequence> contents) throws AbstractIOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setConfigurationFile(String identifier, CharSequence contents) throws AbstractIOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getUserFile(JWSDiskIdentifier identifier) throws AbstractIOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setUserFile(JWSDiskIdentifier identifier, List<? extends CharSequence> contents) throws AbstractIOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setUserFile(JWSDiskIdentifier identifier, CharSequence contents) throws AbstractIOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Option<JWSDiskIdentifier> requestUserFileDestination() throws AbstractIOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Option<JWSDiskIdentifier> requestUserFileSource() throws AbstractIOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AbstractIdentifierParser<JWSDiskIdentifier> getIdentifierParser() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
