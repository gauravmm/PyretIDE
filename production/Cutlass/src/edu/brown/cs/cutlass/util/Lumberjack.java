/*
 * A very simple project-wide logger Implementation
 * Gaurav Manek
 */
package edu.brown.cs.cutlass.util;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Gaurav Manek
 */
public class Lumberjack {

    public enum Level {

        INFO(0, "Information"), WARN(1, "Warning"), ERROR(2, "Error"), NONE(3, "None");

        private final int lvl;
        private final String name;

        Level(int l, String s) {
            lvl = l;
            name = s;
        }
    }

    private static Level level = Level.ERROR;
    private static boolean keepLog = false;
    private static boolean displayLog = true;
    private static LinkedList<String> log;
    private static int sz;
    private static int maxSize = 10;

    /**
     * Sets the minimum level of severity of the logger. This logger will only
     * keep/display messages that are at least as severe as indicated here, the
     * rest are dropped.
     *
     * @param level The Lumberjack.Level of messages to keep.
     */
    public static void setLevel(Level level) {
        Lumberjack.level = level;
        Lumberjack.log(Level.INFO, "Lumberjack set to log at " + level.name + " severity.");
    }

    public static void setKeepLog(boolean log) {
        Lumberjack.keepLog = log;
        Lumberjack.log(Level.INFO, "Lumberjack set to " + (log ? "" : "not ") + "keep log.");
    }

    /**
     * If set to true, the logger will send all kept logs to System.err.
     *
     * @param log If true, display all kept logs
     */
    public static void setDisplayLog(boolean log) {
        Lumberjack.displayLog = log;
        Lumberjack.log(Level.INFO, "Lumberjack set to " + (log ? "" : "not ") + "display log.");
    }

    /**
     * Set the number of elements to keep in the log at any point in time. Note:
     * setKeepLog needs to be set separately.
     *
     * @param i The maximum number of elements to hold in the log.
     */
    public static void setKeptLogSize(int i) {
        Lumberjack.maxSize = i;
        Lumberjack.log(Level.INFO, "Lumberjack set to keep at most " + Integer.toString(i) + " logs.");
        // The size of the log will be shrunk at the next call to log.
    }

    /**
     * Return the last n messages logged, where n is set by
     * Lumberjack.setKeptLogSize or is 10 by default.
     *
     * @return A List containing the last n messages logged, with the most
     * recent item first.
     */
    public static List<String> getLogs() {
        return log;
    }

    /**
     * Log the given message at the specified level.
     *
     * @param l The level to log at. The log will only keep the message if it is
     * at least as high as the Logger's level (see: setLevel); If the message is
     * kept, it will be printed/kept available for later retrieval.
     *
     * @param s The message to log.
     */
    public static void log(Level l, String s) {
        // Make sure the Level is not NONE.
        if (l.lvl == Level.NONE.lvl) {
            throw new IllegalArgumentException("You can't log something at NONE severity!");
        }

        String hrLog = String.format("[%s]\t%s", l.name, s);

        // Check log severity:
        if (l.lvl >= level.lvl) {

            // Keep the log:
            if (Lumberjack.keepLog) {
                // Remove all elements after the end. This is put in a loop in case
                // the maxSize is changed while the logger has elements.
                while (sz >= maxSize) {
                    log.removeLast();
                    sz--;
                }

                // Add the element to the head:
                log.add(hrLog);
            }

            // Display the log:
            if (Lumberjack.displayLog) {
                System.err.println(hrLog);
            }
        } else {
            // Discard the log
        }
    }

    /**
     * Log an exception at the specified level.
     *
     * @param l The level to log at. The log will only keep the message if it is
     * at least as high as the Logger's level (see: setLevel); If the message is
     * kept, it will be printed/kept available for later retrieval.
     *
     * @param ex The exception to log.
     */
    public static void log(Level l, Exception ex) {
        Lumberjack.log(l, ex.toString());
    }

}
