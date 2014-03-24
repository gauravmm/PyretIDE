/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.sys.io.laskura;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import edu.brown.cs.cutlass.sys.io.AbstractIOException;
import edu.brown.cs.cutlass.util.Pair;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Gaurav Manek
 */
public class LaskuraClient extends JSONServerClient {

    public enum LaskuraCommand {

        LOGIN("LOGIN"), LOGOUT("LOGOUT"), LIST("LIST"), READ("READ"), WRITE("WRITE"), CREATE_IF("CREATE_IF_NOT_EXISTS"), SAVE_AND_RUN("SAVE_AND_EXECUTE");
        private final String type;

        LaskuraCommand(String _type) {
            type = _type;
        }

        public String getCommandString() {
            return type;
        }
    }

    public LaskuraClient(URL serveraddr) {
        super(serveraddr);
    }

    public String login(String username, String password) throws IOException, AbstractIOException {
        JsonObject rv = this.request(Arrays.asList(
                new Pair<>("action", LaskuraCommand.LOGIN.getCommandString()),
                new Pair<>("username", username),
                new Pair<>("password", password))).getAsJsonObject();
        if (!rv.getAsJsonPrimitive("success").getAsBoolean()) {
            throw new AbstractIOException("Incorrect credentials!");
        }
        return rv.getAsJsonObject().getAsJsonPrimitive("session_id").getAsString();
    }

    public void logout(String sessionid) throws IOException {
        JsonElement rv = this.request(Arrays.asList(
                new Pair<>("action", LaskuraCommand.LOGOUT.getCommandString())));
    }

    public List<String> list(String sessionid) throws IOException {
        JsonElement resp = this.request(Arrays.asList(
                new Pair<>("action", LaskuraCommand.LIST.getCommandString()),
                new Pair<>("sessionid", sessionid)));
        Iterator<JsonElement> itr = resp.getAsJsonObject().getAsJsonArray("files").iterator();
        List<String> rv = new LinkedList<>();
        while (itr.hasNext()) {
            rv.add(itr.next().getAsJsonObject().getAsJsonPrimitive("filename").getAsString());
        }
        return Collections.unmodifiableList(rv);
    }

    public String read(String sessionid, String filename) throws IOException {
        JsonObject resp = this.request(Arrays.asList(
                new Pair<>("action", LaskuraCommand.READ.getCommandString()),
                new Pair<>("sessionid", sessionid),
                new Pair<>("filename", filename))).getAsJsonObject();
        System.err.println(resp.toString());
        if (resp.getAsJsonPrimitive("success").getAsBoolean()) {
            return resp.getAsJsonObject().getAsJsonPrimitive("data").getAsString();
        } else {
            throw new IOException("Cannot read file!");
        }
    }

    public boolean write(String sessionid, String filename, String data) throws IOException {
        JsonElement resp = this.request(Arrays.asList(
                new Pair<>("action", LaskuraCommand.WRITE.getCommandString()),
                new Pair<>("sessionid", sessionid),
                new Pair<>("filename", filename),
                new Pair<>("data", data)));
        return resp.getAsJsonObject().getAsJsonPrimitive("success").getAsBoolean();
    }

    public boolean create_new(String sessionid, String filename) throws IOException {
        JsonElement resp = this.request(Arrays.asList(
                new Pair<>("action", LaskuraCommand.CREATE_IF.getCommandString()),
                new Pair<>("sessionid", sessionid),
                new Pair<>("filename", filename)));
        return resp.getAsJsonObject().getAsJsonPrimitive("success").getAsBoolean();
    }

    public boolean save_execute(String sessionid, String filename) throws IOException {
        JsonElement resp = this.request(Arrays.asList(
                new Pair<>("action", LaskuraCommand.CREATE_IF.getCommandString()),
                new Pair<>("sessionid", sessionid),
                new Pair<>("filename", filename)));
        return resp.getAsJsonObject().getAsJsonPrimitive("success").getAsBoolean();
    }
}
