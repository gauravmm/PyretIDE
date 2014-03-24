/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.sys.io.disk;

import edu.brown.cs.cutlass.sys.io.AbstractIdentifierParser;
import edu.brown.cs.cutlass.util.Lumberjack;
import edu.brown.cs.cutlass.util.Lumberjack.Level;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Miles Holland
 */
public class DiskIdentifierParser implements AbstractIdentifierParser<DiskIdentifier> {

    @Override
    public DiskIdentifier parse(String in) throws IllegalArgumentException {
        try{
            Path input = Paths.get(in);    
            return new DiskIdentifier(input);
        }
        catch(InvalidPathException | NullPointerException e){
            Lumberjack.log(Level.WARN, "DiskIDParser recieved an invalid path.");
            throw new IllegalArgumentException("Invalid Path.");
        }
        //need testing to assure that this leads to a legitimate place
        
    }
    
}
