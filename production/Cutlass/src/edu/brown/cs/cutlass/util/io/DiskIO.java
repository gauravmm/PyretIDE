/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.util.io;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 *
 * @author YOUR NAME HERE
 */
public class DiskIO implements AbstractIO<DiskIdentifier> {

    private static final String cfgPath = "./.cutlass/";
    private static final Charset charset = Charset.forName("UTF8");
    
    @Override
    public List<String> getConfigurationFile(String identifier) throws AbstractIOException {
        return getUserFile(new DiskIdentifier(cfgPath.concat(identifier)));
    }

    @Override
    public void setConfigurationFile(String identifier, Iterable<? extends CharSequence> contents) throws AbstractIOException {
        setUserFile(new DiskIdentifier(cfgPath.concat(identifier)), contents);
    }

    @Override
    public List<String> getUserFile(DiskIdentifier identifier) throws AbstractIOException {
        try {
            return Files.readAllLines(identifier.get(), charset);
        } catch (IOException ex) {
            throw new AbstractIOException(ex.getMessage());
        }
    }

    @Override
    public void setUserFile(DiskIdentifier identifier, Iterable<? extends CharSequence> contents) throws AbstractIOException {
        try {
            Files.write(identifier.get(), contents, charset, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException ex) {
            throw new AbstractIOException(ex.getMessage());
        }
    }

    @Override
    public DiskIdentifier requestUserFileDestination() throws AbstractIOException {
        // You need to return a DiskIdentifier object. Given a java.nio.Path p, use "new DiskIdentifier(p)" to convert it
        // into a DiskIdentifier.
        // See: http://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html#show
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DiskIdentifier requestUserFileSource() throws AbstractIOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
