/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.sys.io.mweserver;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.brown.cs.cutlass.sys.io.AbstractIOException;
import edu.brown.cs.cutlass.util.Lumberjack;
import edu.brown.cs.cutlass.util.Pair;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Gaurav Manek
 */
public class JSONServerClient {

    private final Gson gson = new Gson();
    private final JsonParser jsonParser = new JsonParser();
    private final URL serveraddr;

    public JSONServerClient(URL serveraddr) {
        this.serveraddr = serveraddr;
    }

    protected JsonElement request(List<Pair<String, String>> params) throws IOException {
        JsonObject toSend = new JsonObject();
        if (params != null) {
            for (Pair<String, String> entry : params) {
                toSend.addProperty(entry.getX(), entry.getY());
            }
        }
        String response;
        response = post("json=\"" + toSend.toString() + "\"");

        return jsonParser.parse(response);
    }

    private String post(String contents) throws IOException {
        HttpURLConnection post = (HttpURLConnection) this.serveraddr.openConnection();

        post.setRequestMethod("POST");
        post.setRequestProperty("User-Agent", "Cutlass-Laskura");

        // Send post request
        post.setDoOutput(true);
        try (DataOutputStream output = new DataOutputStream(post.getOutputStream())) {
            output.writeBytes(contents);
            output.flush();
        }

        try (BufferedReader in = new BufferedReader(new InputStreamReader(post.getInputStream()))) {
            StringBuilder rv = new StringBuilder();
            String input;
            while ((input = in.readLine()) != null) {
                rv.append(input).append('\n');
            }
            post.disconnect();
            return rv.toString();
        }
    }

}
