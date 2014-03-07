/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs.cutlass;

import edu.brown.cs.cutlass.config.ConfigEngine;
import edu.brown.cs.cutlass.sys.io.AbstractIO;
import edu.brown.cs.cutlass.sys.io.AbstractIOException;
import edu.brown.cs.cutlass.sys.io.DiskIO;
import edu.brown.cs.cutlass.util.Lumberjack;
import edu.brown.cs.cutlass.util.Option;
import edu.brown.cs.cutlass.util.clargs.CLArg;
import edu.brown.cs.cutlass.util.clargs.CLArgs;
import edu.brown.cs.cutlass.util.clargs.IncorrectCommandlineArgumentException;
import java.util.Arrays;
import java.util.List;
import javax.swing.UIManager;

/**
 *
 * @author Gaurav Manek
 */
public class Launcher {

    /**
     * @param args the command line arguments
     */
    public static void main(final String[] args) {

        //<editor-fold defaultstate="collapsed" desc="Look And Feel settings">
        // From NetBeans, see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
        try {
            // Use system default:
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            /*
             for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
             if ("Nimbus".equals(info.getName())) {
             javax.swing.UIManager.setLookAndFeel(info.getClassName());
             break;
             }
             }*/
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            Lumberjack.log(Lumberjack.Level.WARN, ex);
        }
//</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Launcher(args);
            }
        });
    }

    // Commandline Arguments:
    private static final CLArg argHelp = new CLArg("help", new String[]{"-h", "--help"});
    // Logger:
    private static final CLArg argLogAll = new CLArg("verbose", new String[]{"-v", "--verbose"});
    private static final CLArg argLogWarn = new CLArg("warning", new String[]{"-w", "--warnings"});
    private static final CLArg argLogError = new CLArg("error", new String[]{"-e", "--error"});
    // Loading process:
    private static final CLArg argDontLoadPreviousState = new CLArg("freshstart", new String[]{"-f", "--fresh", "--reset"});
    private static final List<CLArg> expectedArgs = Arrays.asList(new CLArg[]{argHelp, argLogAll, argLogWarn, argLogError, argDontLoadPreviousState});

    // IO:
    private AbstractIO io;
    private final String fnLaunchState = "launchstate";
    private final String fnConfig = "config";

    private Launcher(String[] args) {
        CLArgs argParsed = null;
        try {
            argParsed = new CLArgs(args, expectedArgs);
        } catch (IncorrectCommandlineArgumentException e) {
            System.err.println("ERROR: Commandline arguments could not be parsed.");
            System.out.println(generateCLHelp());
            System.exit(ExitCode.CLARGS.getCode());
        }

        if (argParsed.hasArg(argHelp)) {
            System.out.println(generateCLHelp());
            System.exit(ExitCode.OK.getCode());
        }

        // Set logging level, and display the log:
        if (argParsed.hasArg(argLogError)) {
            Lumberjack.setLevel(Lumberjack.Level.ERROR);
            Lumberjack.setDisplayLog(true);
        }
        if (argParsed.hasArg(argLogWarn)) {
            Lumberjack.setLevel(Lumberjack.Level.WARN);
            Lumberjack.setDisplayLog(true);
        }
        if (argParsed.hasArg(argLogAll)) {
            Lumberjack.setLevel(Lumberjack.Level.INFO);
            Lumberjack.setDisplayLog(true);
        }

        // Prepare IO:
        io = new DiskIO();

        // Load configuration engine
        ConfigEngine configEngine = null;

        // Load launch state
        Option<LaunchState> launchState = null;

        // Load previous start state
        if (argParsed.hasArg(argDontLoadPreviousState)) {
            launchState = new Option<>();
        } else {
            // Load the LaunchState from file:
            try {
                List<String> launchStateContents = io.getConfigurationFile(fnLaunchState);
                launchState = new Option<>(LaunchState.fromString(launchStateContents, io.getIdentifierParser()));
            } catch (AbstractIOException | IllegalArgumentException e) {
                // Could not load file (if AbstractIOException) or unparsable (if IllegalArgumentException)
                Lumberjack.log(Lumberjack.Level.WARN, e);
                launchState = new Option<>();
            }
        }
        assert launchState != null;

        // Hand off control to FrmMain
        (new FrmMain(this, configEngine, launchState)).setVisible(true);
    }

    private static String generateCLHelp() {
        String s = "Cutlass\n"
                + "CSCI 0320, Brown University, Fall 2014\n"
                + "Dilip Arumugam, Gaurav Manek, Miles Holland, Zachary Zagorski\n"
                + "\n"
                + "Usage: ./cutlass [OPTIONS] [FILES]\n"
                + "\n"
                + "OPTIONS\n"
                + "  -h --help      Display this help and exit.\n"
                + "  -n --silent    Silent mode, do not output anything on the commandline.\n"
                + "  -v --verbose   Verbose mode, output all logged information.\n"
                + "  -w --warnings  Verbose mode, output all logged warnings and errors.\n"
                + "  -e --error     Verbose mode, output all errors.\n"
                + "  -f --fresh     Discard saved state and start a brand new session.\n"
                + "\n"
                + "FILES\n"
                + "   Paths of files to load on startup.\n"
                + "\n"
                + "Exit status:\n";

        for (ExitCode ec : ExitCode.values()) {
            s += "  " + Integer.toString(ec.getCode()) + "\t" + ec.getHelpText() + "\n";
        }

        return s;
    }

    /**
     * Handle program termination. Save current program state to disk and quit.
     * If necessary, write log files to disk.
     */
    public void quit(LaunchState lState) {

        // Store LaunchState:
        try {
            io.setConfigurationFile(fnLaunchState, lState.toStringArr());
        } catch (AbstractIOException e) {
            Lumberjack.log(Lumberjack.Level.ERROR, "Could not save current LaunchState!");
        }

        System.exit(ExitCode.OK.getCode());
    }
}
