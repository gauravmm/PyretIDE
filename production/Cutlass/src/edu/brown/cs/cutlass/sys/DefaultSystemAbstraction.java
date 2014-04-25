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
    
    public void setConfigEngine(ConfigEngine ncfg){
        cfg = ncfg;
    }
    
    public String getRacoPath() {
        File user_home = new File(System.getProperty("user.home"));
        final List<String> raco_path = new ArrayList<>();
        if (System.getProperty("os.name").contains("win")) {
            File raco1 = new File(user_home.getAbsolutePath() + "\\Program Files\\Racket\\raco.exe");
            File raco2 = new File(user_home.getAbsolutePath() + "\\Program Files (x86)\\Racket\\raco.exe");
            System.out.println("1 -- " + raco1.getAbsolutePath());
            System.out.println("2 -- " + raco2.getAbsolutePath());
            if (raco1.exists() && raco1.isFile() && raco1.canExecute()) {
                //System.out.println(raco.getAbsolutePath());
                raco_path.add(raco1.getAbsolutePath());
            }
            if (raco2.exists() && raco2.isFile() && raco2.canExecute()) {
                raco_path.add(raco2.getAbsolutePath());
            } else {
                raco_path.add("");
            }
        } else {
            File raco1 = new File(user_home.getAbsolutePath() + "/Racket/bin/raco");
            System.out.println("3 -- " + raco1.getAbsolutePath());
            if (raco1.exists() && raco1.isFile() && raco1.canExecute()) {
                //System.out.println(raco.getAbsolutePath());
                raco_path.add(raco1.getAbsolutePath());
            } else {
                raco_path.add("");
            }
        }
        return raco_path.get(0);
    }
//        if (System.getProperty("os.name").contains("win")) {
//            File[] matchingFiles = user_home.listFiles(new FilenameFilter() {
//                public boolean accept(File dir, String name) {
//                    if (dir.isDirectory()) {
//                        File prog_files = new File(dir.getAbsolutePath() + "/Program Files/");
//                        if (prog_files.exists() && prog_files.isDirectory()) {
//                            File raco = new File(prog_files.getAbsolutePath() + "/Racket/raco.exe");
//                            if (raco.exists() && raco.isFile() && raco.canExecute()) {
//                                //System.out.println(raco.getAbsolutePath());
//                                raco_path.add(raco.getAbsolutePath());
//                                return true;
//                            }
//                        }
//                    }
//                    raco_path.add("");
//                    return false;
//                }
//            });
//        } else {
//            File[] matchingFiles = user_home.listFiles(new FilenameFilter() {
//                public boolean accept(File dir, String name) {
//                    if (dir.isDirectory()) {
//                        File rack = new File(dir.getAbsolutePath() + "/Racket/");
//                        if (rack.exists() && rack.isDirectory()) {
//                            File raco = new File(rack.getAbsolutePath() + "/bin/raco");
//                            if (raco.exists() && raco.isFile() && raco.canExecute()) {
//                                //System.out.println(raco.getAbsolutePath());
//                                raco_path.add(raco.getAbsolutePath());
//                                return true;
//                            }
//                        }
//                    }
//                    raco_path.add("");
//                    return false;
//                }
//            });
//        }
//        return raco_path.get(0);
//    }

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
            return new DiskPyretAccess(identifier,raco_path);
        }
        else{
            throw new IllegalStateException("ConfigEngine has not been set yet");
        }
    }

}
