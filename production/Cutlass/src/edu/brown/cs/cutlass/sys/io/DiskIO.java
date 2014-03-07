/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.sys.io;

import edu.brown.cs.cutlass.util.Option;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author YOUR NAME HERE
 */
public class DiskIO implements AbstractIO<DiskIdentifier> {

    private static final Path cfgPath = Paths.get(System.getProperty("user.home") + File.separator + ".cutlass" + File.separator);
    private static final Charset charset = Charset.forName("UTF8");
    private final JFileChooser fileChooser = new JFileChooser(); // Keep a single fileChooser object, keeps memory of last directory opened/saved.

    public DiskIO() {
        // Set up the JFileChooser
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Pyret Source File", "arr");
        fileChooser.setFileFilter(filter);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    }

    @Override
    public List<String> getConfigurationFile(String identifier) throws AbstractIOException {
        return getUserFile(new DiskIdentifier(cfgPath.resolve(identifier)));
    }

    @Override
    public void setConfigurationFile(String identifier, Iterable<? extends CharSequence> contents) throws AbstractIOException {
        // Create folder if it does not exist.
        if (!Files.exists(cfgPath)) {
            try {
                Files.createDirectory(cfgPath);
            } catch (IOException ex) {
                throw new AbstractIOException("Could not create config directory!");
            }
        }

        setUserFile(new DiskIdentifier(cfgPath.resolve(identifier)), contents);
    }

    @Override
    public List<String> getUserFile(DiskIdentifier identifier) throws AbstractIOException {
        try {
            return Files.readAllLines(identifier.getId(), charset);
        } catch (IOException ex) {
            throw new AbstractIOException(ex.getMessage());
        }
    }

    @Override
    public void setUserFile(DiskIdentifier identifier, Iterable<? extends CharSequence> contents) throws AbstractIOException {
        try {
            Files.write(identifier.getId(), contents, charset, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException ex) {
            throw new AbstractIOException(ex.getMessage());
        }
    }

    @Override
    public Option<DiskIdentifier> requestUserFileDestination() throws AbstractIOException {
        // You need to return a DiskIdentifier object. Given a java.nio.Path p, use "new DiskIdentifier(p)" to convert it
        // into a DiskIdentifier.
        // See: http://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html#show
        return requestUserFile(false);
    }

    @Override
    public Option<DiskIdentifier> requestUserFileSource() throws AbstractIOException {
        return requestUserFile(true);
    }

    /**
     * requestUserFile - shows a JFileChooser to the user and returns their
     * selection (if they do select one);
     *
     * @param requestLoad If true, shows a "Load File" dialog box.
     * @return An Option containing a DiskIdentifier object.
     * @throws AbstractIOException If any exception occurs when requesting the
     * file.
     */
    private Option<DiskIdentifier> requestUserFile(boolean requestLoad) throws AbstractIOException {
        int dialogRv = requestLoad ? fileChooser.showOpenDialog(fileChooser) : fileChooser.showSaveDialog(fileChooser);
        if (JFileChooser.APPROVE_OPTION == dialogRv) {
            try {
                return new Option<>(new DiskIdentifier(fileChooser.getSelectedFile().getCanonicalPath()));
            } catch (IOException ex) {
                throw new AbstractIOException(ex);
            }
        } else {
            return new Option<>();
        }
    }
}
