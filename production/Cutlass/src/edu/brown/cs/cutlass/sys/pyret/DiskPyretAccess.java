/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.sys.pyret;

import edu.brown.cs.cutlass.sys.io.disk.DiskIdentifier;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author Dilip Arumugam
 */
public class DiskPyretAccess extends AbstractPyretAccess<DiskIdentifier> {
    private static final List<String> raco = new ArrayList<>();
    private static final ProcessBuilder run_build = new ProcessBuilder(raco);
    private static DiskIdentifier identifier;
    private static String temp_file;
    private static Process run_proc;
    private static String raco_path;

    private static final List<String> error_list = Arrays.asList("Avast, there be bugs!", "check-fun:", "check-method:", "apply-fun:", "Arity mismatch", "Expected", "Bad args to prim",
            "brand:", "was not found on", "Cannot lookup mutable field", "Cannot lookup immutable field", "field: expected string, got",
            "Updating non-existent field", "typecheck failed:", "Tried to set value in already-initialized placeholder", "expected Placeholder and got",
            "char-at: Index too large for string", "runtime:", "Division by zero", "has-field:", "substring:", "py-match doesn't work over", "py-match fell through on a",
            "INTERNAL ERROR:", "Runtime link is already set to", "Runtime empty is already set to","This file doesn't look right.","Error in type-checking");
    private static final HashSet<String> error_msgs = new HashSet<>();

    private static final List<PyretOutputValue> out_vals = new ArrayList<>();
    private static final List<PyretOutputValue> err_vals = new ArrayList<>();

    public DiskPyretAccess(DiskIdentifier id, String rp) {
        //System.out.println("New DiskPyretAccess constructed");
        identifier = id;
        error_msgs.addAll(error_list);
        raco_path = rp;
        raco.addAll(Arrays.asList(raco_path, "pyret"));
    }
    
    private boolean containsError(String out){
        boolean err = false;
        for(String s : error_msgs){
            if(out.contains(s)){
                err = true;
                break;
            }
        }
        return err;
    }

    @Override
    public List<PyretOutputValue> getAllOutput(Stream stream) {
        if (stream.equals(AbstractPyretAccess.Stream.STDOUT)) {
            return out_vals;
        }
        if (stream.equals(AbstractPyretAccess.Stream.STDERR)) {
            return err_vals;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public void close() {
        try {
            //kill the process
            if (run_proc != null) {
                run_proc.destroy();
            }
            //delete the temporary file
            raco.clear();
            Files.delete(Paths.get(run_build.directory().getPath() + "/" + temp_file));
        } catch (IOException e) {

        }
    }

    @Override
    protected PyretTerminationValue doInBackground() throws Exception {
        /*
         Use publish(new PyretOutputValue()) to send intermediate data to the listener, and
         this function must return a PyretTerminationValue.
         Feel free to modify the data types as necessary.
         */
        /*  Remember to call:
         *  new PyretOutputValue(AbstractPyretAccess.Stream.STDOUT, "String Here");
         *  when any response is recieved from the code.
         */
        try {
            //Expected that identifier represents full path to user's .arr file
            File user_file = new File(identifier.getId().toString());
            run_build.directory(user_file.getParentFile());
            String usr_file_name = user_file.getName();
            Date date = new Date();
            temp_file = usr_file_name.substring(0, usr_file_name.length() - 4) + "_" + date.toString().replaceAll(" ", "_").replaceAll(":", "-") + ".arr";

            Files.copy(identifier.getId(), Paths.get(user_file.getParent(), temp_file));
            raco.add(temp_file);

            //System.out.println(raco);
            //System.out.println(run_build.command());
            run_proc = run_build.start();
            run_proc.waitFor();

            InputStream output = run_proc.getInputStream();
            int out_size = output.available();
            byte[] out = new byte[out_size];
            output.read(out);
            String out_string = new String(out, "UTF-8").trim();
            //System.out.println(out_string);
            
            InputStream error = run_proc.getErrorStream();
            int err_size = error.available();
            byte[] err_out = new byte[err_size];
            error.read(err_out);
            String err_string = new String(err_out, "UTF-8").trim();
            if (err_string != null && err_string.length() != 0) {
                PyretOutputValue pev = new PyretOutputValue(AbstractPyretAccess.Stream.STDERR, err_string);
                publish(pev);
                err_vals.add(pev);
            }
            
            if (containsError(out_string)) {
                PyretOutputValue pev = new PyretOutputValue(AbstractPyretAccess.Stream.STDERR, out_string);
                publish(pev);
                err_vals.add(pev);
                return new PyretTerminationValue(0);
            }
            if (out_string.contains("Looks shipshape")) {
                PyretOutputValue pov = new PyretOutputValue(AbstractPyretAccess.Stream.STDOUT, out_string);
                publish(pov);
                out_vals.add(pov);
                return new PyretTerminationValue(0);
            } else {
                if (out_string.contains("errno=")) {
                    int index = out_string.lastIndexOf("errno=");
                    int errCode = Integer.parseInt(out_string.substring(index + 6, index + 7));
                    return new PyretTerminationValue(errCode);
                }
                return new PyretTerminationValue(1);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return new PyretTerminationValue(1);
        }
    }
}
