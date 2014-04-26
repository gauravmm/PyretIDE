/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs.cutlass;

import edu.brown.cs.cutlass.config.ConfigEngine;
import edu.brown.cs.cutlass.sys.DefaultSystemAbstraction;
import edu.brown.cs.cutlass.sys.LaskuraSystemAbstraction;
import edu.brown.cs.cutlass.sys.SystemAbstraction;
import edu.brown.cs.cutlass.sys.io.AbstractIO;
import edu.brown.cs.cutlass.sys.io.AbstractIOException;
import edu.brown.cs.cutlass.sys.io.laskura.LaskuraIO;
import edu.brown.cs.cutlass.sys.io.laskura.ui.PnlLaskuraLogin;
import edu.brown.cs.cutlass.util.Lumberjack;
import edu.brown.cs.cutlass.util.Option;
import edu.brown.cs.cutlass.util.Pair;
import edu.brown.cs.cutlass.util.clargs.CLArg;
import edu.brown.cs.cutlass.util.clargs.CLArgs;
import edu.brown.cs.cutlass.util.clargs.IncorrectCommandlineArgumentException;
import java.awt.Dimension;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
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
    private static final CLArg argServerIO = new CLArg("serverio", new String[]{"--server", "--laskura"}, 1);

    // Logger:
    private static final CLArg argLogAll = new CLArg("verbose", new String[]{"-v", "--verbose"});
    private static final CLArg argLogWarn = new CLArg("warning", new String[]{"-w", "--warnings"});
    private static final CLArg argLogError = new CLArg("error", new String[]{"-e", "--error"});
    // Loading process:
    private static final CLArg argDontLoadPreviousState = new CLArg("freshstart", new String[]{"-f", "--fresh", "--reset"});
    private static final List<CLArg> expectedArgs = Arrays.asList(new CLArg[]{argHelp, argLogAll, argLogWarn, argLogError, argDontLoadPreviousState, argServerIO});

    // IO:
    private AbstractIO io;
    private final String fnLaunchState = "launchstate";
    private final String fnConfig = "config";

    // Config:
    private final ConfigEngine config;

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

        SystemAbstraction sys = null;
        // Prepare IO:
        if (argParsed.hasArg(argServerIO) && argParsed.getArg(argServerIO).hasData()) {
            Lumberjack.log(Lumberjack.Level.INFO, "Starting ServerIO...");
            List<String> arg = argParsed.getArg(argServerIO).getData();
            if (arg.isEmpty()) {
                Lumberjack.log(Lumberjack.Level.ERROR, "ServerIO Server not specified in CLI arguments.");
                System.exit(ExitCode.CLARGS.getCode());
            }
            try {
                URL server = new URL(argParsed.getArg(argServerIO).getData().get(0));
                Option<Pair<String, String>> login = PnlLaskuraLogin.getLogin(server);

                if (!login.hasData()) {
                    Lumberjack.log(Lumberjack.Level.INFO, "Login cancelled.");
                    System.exit(ExitCode.OK.getCode());
                }
                try {
                    sys = new LaskuraSystemAbstraction(new LaskuraIO(server, login.getData().getX(), login.getData().getY()));
                    io = sys.getIO();
                } catch (AbstractIOException ex) {
                    Lumberjack.log(Lumberjack.Level.WARN, "Login failed.");
                    JOptionPane.showMessageDialog(null, "Login failed!\nThe server returned the following error:\n" + ex.getMessage(), "Cutlass", JOptionPane.ERROR_MESSAGE);
                    System.exit(ExitCode.OK.getCode());
                }
                Lumberjack.log(Lumberjack.Level.INFO, "Started ServerIO.");
            } catch (MalformedURLException ex) {
                Lumberjack.log(Lumberjack.Level.WARN, "Malformed URL.");
                JOptionPane.showMessageDialog(null, "The server URL is malformed.", "Cutlass", JOptionPane.ERROR_MESSAGE);
                System.exit(ExitCode.OK.getCode());
            }
        } else {
            Lumberjack.log(Lumberjack.Level.INFO, "Starting DiskIO...");
            sys = new DefaultSystemAbstraction();
            io = sys.getIO();
            Lumberjack.log(Lumberjack.Level.INFO, "Started DiskIO.");
        }

        // Load configuration engine:
        ConfigEngine tmpConfig = null;
        try {
            tmpConfig = ConfigEngine.fromString(io.getConfigurationFile(fnConfig));
        } catch (AbstractIOException | IllegalArgumentException e) {
            // Could not load file (if AbstractIOException) or unparsable (if IllegalArgumentException)
            Lumberjack.log(Lumberjack.Level.ERROR, e);
            // Manually initialize the ConfigEngine:
            tmpConfig = new ConfigEngine();
            tmpConfig.setDimension("ui.toolbar.iconsize", new Dimension(60, 40));
            // Post set-up for DefaultSystemAbstraction
            if (sys instanceof DefaultSystemAbstraction) {
                Option<String> racoPath = ((DefaultSystemAbstraction) sys).getRacoPath();
                if (racoPath.hasData()) {
                    tmpConfig.setString("raco.path", racoPath.getData());
                } else {
                    tmpConfig.setString("raco.path", "<UNKNOWN>");
                    JOptionPane.showMessageDialog(null, "The path to raco could not be automatically determined.\n"
                            + "You need to manually set the value in \"~/.cutlass/config.cfg\" before re-lauching Cutlass.", "raco Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        }

        config = tmpConfig;

        if (sys instanceof DefaultSystemAbstraction) {
            ((DefaultSystemAbstraction) sys).setConfigEngine(config);
        }

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
        assert sys != null;

        // Hand off control to FrmMain
        (new FrmMain(this, config, launchState, sys)).setVisible(true);
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
                + "\n"
                + "  -n --silent    Silent mode, do not output anything on the commandline.\n"
                + "  -v --verbose   Verbose mode, output all logged information.\n"
                + "  -w --warnings  Verbose mode, output all logged warnings and errors.\n"
                + "  -e --error     Verbose mode, output all errors.\n"
                + "\n"
                + "  -f --fresh     Discard saved state and start a brand new session.\n"
                + "  --laskura URL  Connect to a Laskura server at URL instead of using the local disk.\n"
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
     *
     * @param lState The current program state
     */
    public void quit(LaunchState lState) {

        // Store LaunchState:
        try {
            io.setConfigurationFile(fnLaunchState, lState.toStringArr());
        } catch (AbstractIOException e) {
            Lumberjack.log(Lumberjack.Level.ERROR, "Could not save current LaunchState!");
        }

        // Store ConfigEngine:
        if (config.isChanged()) {
            try {
                io.setConfigurationFile(fnConfig, config.toString());
            } catch (AbstractIOException e) {
                Lumberjack.log(Lumberjack.Level.ERROR, "Could not save current ConfigEngine!");
            }
        }

        System.exit(ExitCode.OK.getCode());
    }
}
