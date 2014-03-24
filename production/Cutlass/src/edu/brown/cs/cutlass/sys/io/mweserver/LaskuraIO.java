/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.sys.io.mweserver;

import edu.brown.cs.cutlass.sys.io.AbstractIO;
import edu.brown.cs.cutlass.sys.io.AbstractIOException;
import edu.brown.cs.cutlass.sys.io.AbstractIdentifierParser;
import edu.brown.cs.cutlass.util.Lumberjack;
import edu.brown.cs.cutlass.util.Option;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gaurav Manek
 */
public class LaskuraIO implements AbstractIO<LaskuraIdentifier> {

    private final LaskuraClient client;
    private Option<String> sessionId = new Option<>();

    public LaskuraIO(URL server, String username, String password) throws AbstractIOException {
        this.client = new LaskuraClient(server);
        try {
            sessionId = new Option<>(client.login(username, password));
        } catch (IOException ex) {
            Lumberjack.log(Lumberjack.Level.WARN, ex);
            throw new AbstractIOException("Could not login to server: " + ex.getMessage());
        }
    }

    public LaskuraIO(URL server) {
        this.client = new LaskuraClient(server);
        
        // Show login dialog.
        /*try {
            sessionId = new Option<>(client.login(username, password));
        } catch (IOException ex) {
            Lumberjack.log(Lumberjack.Level.WARN, ex);
            throw new AbstractIOException("Could not login to server: " + ex.getMessage());
        }*/
    }

    @Override
    public List<String> getConfigurationFile(String identifier) throws AbstractIOException {
        if (sessionId.hasData()) {
            try {
                return Arrays.asList(client.read(sessionId.getData(), "~" + identifier).split("\n"));
            } catch (IOException ex) {
                Lumberjack.log(Lumberjack.Level.WARN, ex);
                throw new AbstractIOException(ex);
            }
        } else {
            throw new AbstractIOException("A sessionId does not exist.");
        }
    }

    @Override
    public void setConfigurationFile(String identifier, List<? extends CharSequence> contents) throws AbstractIOException {
        this.setConfigurationFile(identifier, join("\n", contents));
    }

    @Override
    public void setConfigurationFile(String identifier, CharSequence contents) throws AbstractIOException {
        if (sessionId.hasData()) {
            try {
                client.write(sessionId.getData(), "~" + identifier, contents.toString());
            } catch (IOException ex) {
                Lumberjack.log(Lumberjack.Level.WARN, ex);
                throw new AbstractIOException(ex);
            }
        } else {
            throw new AbstractIOException("A sessionId does not exist.");
        }
    }

    @Override
    public List<String> getUserFile(LaskuraIdentifier identifier) throws AbstractIOException {
        if (sessionId.hasData()) {
            try {
                return Arrays.asList(client.read(sessionId.getData(), identifier.getName()).split("\n"));
            } catch (IOException ex) {
                Lumberjack.log(Lumberjack.Level.WARN, ex);
                throw new AbstractIOException(ex);
            }
        } else {
            throw new AbstractIOException("A sessionId does not exist.");
        }
    }

    @Override
    public void setUserFile(LaskuraIdentifier identifier, List<? extends CharSequence> contents) throws AbstractIOException {
        this.setUserFile(identifier, join("\n", contents));
    }

    @Override
    public void setUserFile(LaskuraIdentifier identifier, CharSequence contents) throws AbstractIOException {
        if (sessionId.hasData()) {
            try {
                client.write(sessionId.getData(), identifier.getName(), contents.toString());
            } catch (IOException ex) {
                Lumberjack.log(Lumberjack.Level.WARN, ex);
                throw new AbstractIOException(ex);
            }
        } else {
            throw new AbstractIOException("A sessionId does not exist.");
        }
    }

    @Override
    public Option<LaskuraIdentifier> requestUserFileDestination() throws AbstractIOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Option<LaskuraIdentifier> requestUserFileSource() throws AbstractIOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AbstractIdentifierParser<LaskuraIdentifier> getIdentifierParser() {
        return new LaskuraIdentifierParser();
    }

    private static CharSequence join(String sep, List<? extends CharSequence> contents) {
        StringBuilder co = new StringBuilder();
        for (CharSequence a : contents) {
            co.append(a).append(sep);
        }
        return co;
    }

}
