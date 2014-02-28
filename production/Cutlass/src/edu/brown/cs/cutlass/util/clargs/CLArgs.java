/*
 * Project 01 - Autocorrect
 * CSCI 0320, Brown University, Fall 2014
 * Gaurav Manek
 */
package edu.brown.cs.cutlass.util.clargs;

import edu.brown.cs.cutlass.util.Option;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

/**
 * Parses command-line arguments.
 *
 * @author Gaurav Manek
 */
public class CLArgs {

    private final HashMap<CLArg, ArrayList<String>> parsedArgs = new HashMap<>();
    private final ArrayList<String> parsedTopArgs = new ArrayList<>();

    /**
     *
     * @param args The raw arguments.
     * @param clArgs The CLArg instances to parse out of this.
     */
    public CLArgs(String[] args, List<CLArg> clArgs) throws IncorrectCommandlineArgumentException {
        // Prepare the clArgs for lookup:
        HashMap<String, CLArg> lookup = new HashMap<>();
        for (CLArg clA : clArgs) {
            for (String sw : clA.switches) {
                lookup.put(sw, clA);
            }
        }

        for (Iterator<String> it = Arrays.asList(args).iterator(); it.hasNext();) {
            String inp = it.next();

            // If the switch is recognized:
            if (lookup.containsKey(inp)) {
                CLArg clA = lookup.get(inp);
                if (clA.variableArgCount) {
                    throw new UnsupportedOperationException("Variable Argument Counts are not supported yet!");
                } else {
                    int c = clA.argCount;
                    ArrayList<String> inArgs = new ArrayList<>();
                    while (c > 0) {
                        if (it.hasNext()) {
                            inArgs.add(it.next());
                        } else {
                            throw new IncorrectCommandlineArgumentException();
                        }
                        c--;
                    }

                    parsedArgs.put(clA, inArgs);
                }
            } else {
                // Switch is not recognized, put this into the top-level args.
                parsedTopArgs.add(inp);
            }
        }
    }

    /**
     *
     * @param clArg
     * @return
     */
    public boolean hasArg(CLArg clArg) {
        return parsedArgs.containsKey(clArg);
    }

    /**
     *
     * @param clArg
     * @return
     */
    public Option<List<String>> getArg(CLArg clArg) {
        if (hasArg(clArg)) {
            return new Option<>((List<String>) parsedArgs.get(clArg));
        } else {
            return new Option<>();
        }
    }

    /**
     *
     * @return
     */
    public List<String> getTopArgs() {
        return parsedTopArgs;
    }

    @Override
    public String toString() {
        /*
        This code is horriby kludgy and quite a hack. Rewrite this if needed for
        anything other than testing.
        */
        Iterator<Entry<CLArg, ArrayList<String>>> itr = parsedArgs.entrySet().iterator();
        String s = "";
        ArrayList<String> ps = new ArrayList<>();
        while (itr.hasNext()) {
            Entry<CLArg, ArrayList<String>> entry = itr.next();
            ps.add(entry.getKey().text + ":" + entry.getValue() + "");
        }
        String[] outs = ps.toArray(new String[]{});
        Arrays.sort(outs);
        
        return "CLArgs: " + Arrays.asList(outs).toString() + " [" + parsedTopArgs + ']';
    }
}
