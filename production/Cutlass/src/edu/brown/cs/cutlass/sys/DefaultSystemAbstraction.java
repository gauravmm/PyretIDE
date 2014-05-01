/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.sys;

import edu.brown.cs.cutlass.config.ConfigEngine;
import edu.brown.cs.cutlass.sys.io.AbstractIO;
import edu.brown.cs.cutlass.sys.io.disk.DiskIO;
import edu.brown.cs.cutlass.sys.io.disk.DiskIdentifier;
import edu.brown.cs.cutlass.sys.pyret.AbstractPyretAccess;
import edu.brown.cs.cutlass.sys.pyret.DiskPyretAccess;
import edu.brown.cs.cutlass.sys.ux.AbstractClipboard;
import edu.brown.cs.cutlass.sys.ux.DefaultClipboard;
import edu.brown.cs.cutlass.util.Option;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gaurav Manek
 */
public class DefaultSystemAbstraction implements SystemAbstraction<DiskIdentifier> {

    private final DefaultClipboard defaultClipboard;
    private final DiskIO diskIO;
    private ConfigEngine cfg = null;

    public DefaultSystemAbstraction() {
        defaultClipboard = new DefaultClipboard();
        diskIO = new DiskIO();
    }

    public void setConfigEngine(ConfigEngine ncfg) {
        cfg = ncfg;
    }

    public Option<String> getRacoPath() {
        File user_home = new File(System.getProperty("user.home"));
        if (System.getProperty("os.name").contains("Windows")) {
            File raco = new File(System.getenv("ProgramFiles") + "\\Racket\\raco.exe");
            if (raco.exists() && raco.isFile() && raco.canExecute()) {
                return new Option<>(raco.getAbsolutePath());
            }
            String programFilesPath = System.getenv("ProgramFiles(x86)");
            if (programFilesPath != null) {
                // If the variable is defined (meaning this is a 64-bit system)
                raco = new File(programFilesPath + "\\Racket\\raco.exe");
                if (raco.exists() && raco.isFile() && raco.canExecute()) {
                    return new Option<>(raco.getAbsolutePath());
                }
            }
        } else {
            File raco1 = new File(user_home.getAbsolutePath() + "/Racket/bin/raco");
            File raco2 = new File("/local/projects/racket/6.0/x86_64/bin/raco");
            if (raco1.exists() && raco1.isFile() && raco1.canExecute()) {
                return new Option<>(raco1.getAbsolutePath());
            }
            if (raco2.exists() && raco2.isFile() && raco2.canExecute()) {
                return new Option<>(raco2.getAbsolutePath());
            }
        }

        return new Option<>();
    }

    @Override
    public AbstractClipboard getClipboard() {
        return defaultClipboard;
    }

    @Override
    public AbstractIO<DiskIdentifier> getIO() {
        return diskIO;
    }

    @Override
    public AbstractPyretAccess<DiskIdentifier> getPyretAccess(DiskIdentifier identifier) {
        if (cfg != null) {
            String raco_path = cfg.getString("raco.path");
            return new DiskPyretAccess(identifier, raco_path);
        } else {
            throw new IllegalStateException("ConfigEngine has not been set yet");
        }
    }

}
