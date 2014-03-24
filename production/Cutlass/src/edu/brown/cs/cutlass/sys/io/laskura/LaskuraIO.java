/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.sys.io.laskura;

import edu.brown.cs.cutlass.sys.io.AbstractIO;
import edu.brown.cs.cutlass.sys.io.AbstractIOException;
import edu.brown.cs.cutlass.sys.io.AbstractIdentifierParser;
import edu.brown.cs.cutlass.sys.io.laskura.ui.PnlLaskuraLogin;
import edu.brown.cs.cutlass.util.Lumberjack;
import edu.brown.cs.cutlass.util.Option;
import edu.brown.cs.cutlass.util.Pair;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;

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

    private LaskuraIO(URL server) throws AbstractIOException {
        this.client = new LaskuraClient(server);
        Option<Pair<String, String>> login = PnlLaskuraLogin.getLogin(server);
        if (login.hasData()) {
            Pair<String, String> logindata = login.getData();
            try {
                sessionId = new Option<>(client.login(logindata.getX(), logindata.getY()));
            } catch (IOException ex) {
                Lumberjack.log(Lumberjack.Level.WARN, ex);
                throw new AbstractIOException("Could not login to server: " + ex.getMessage());
            }
        } else {
            throw new IllegalArgumentException("Login cancelled by user!");
        }
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
                client.create_new(sessionId.getData(), "~" + identifier);
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
        if (sessionId.hasData()) {
            String selFile = (String) JOptionPane.showInputDialog(null, "Name your new file:",
                    "Save File on " + client.getServerAddr().toString(),
                    JOptionPane.QUESTION_MESSAGE);
            if (selFile == null) {
                return new Option<>();
            } else {
                selFile = selFile.trim();
                while (selFile.startsWith("~")) {
                    selFile = selFile.substring(1);
                }
                if (selFile.isEmpty()) {
                    return new Option<>();
                }

                try {
                    List<String> filenames = client.list(sessionId.getData());
                    if (filenames.contains(selFile)) {
                        int n = JOptionPane.showConfirmDialog(null, "A file with this name already exists.\nAre you sure you want to overwrite it?", "Cutlass", JOptionPane.YES_NO_OPTION);
                        if (n == JOptionPane.YES_OPTION) {
                            // If the user is okay with replacing files, then go!
                            return new Option<>(new LaskuraIdentifier(selFile));
                        } else {
                            return new Option<>();
                        }
                    } else {
                        client.create_new(sessionId.getData(), selFile);
                        return new Option<>(new LaskuraIdentifier(selFile));
                    }
                } catch (IOException ex) {
                    Lumberjack.log(Lumberjack.Level.WARN, ex);
                    throw new AbstractIOException(ex);
                }

            }
        } else {
            throw new AbstractIOException("A sessionId does not exist.");
        }
    }

    @Override
    public Option<LaskuraIdentifier> requestUserFileSource() throws AbstractIOException {
        if (sessionId.hasData()) {
            try {
                List<String> filenames_in = client.list(sessionId.getData());
                List<String> filenames_filtered = new ArrayList<>();
                for (String in : filenames_in) {
                    if (!in.startsWith("~")) {
                        filenames_filtered.add(in);
                    }
                }
                String selFile = (String) JOptionPane.showInputDialog(null, "Select a file to open:",
                        "Open File on " + client.getServerAddr().toString(),
                        JOptionPane.QUESTION_MESSAGE,
                        null, filenames_filtered.toArray(), filenames_filtered.get(0));
                if (selFile == null) {
                    return new Option<>();
                } else {
                    return new Option<>(new LaskuraIdentifier(selFile));
                }
            } catch (IOException ex) {
                Lumberjack.log(Lumberjack.Level.WARN, ex);
                throw new AbstractIOException(ex);
            }
        } else {
            throw new AbstractIOException("A sessionId does not exist.");
        }
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
