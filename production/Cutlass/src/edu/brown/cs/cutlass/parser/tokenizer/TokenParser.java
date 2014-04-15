/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs.cutlass.parser.tokenizer;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Gaurav Manek
 */
public final class TokenParser {

    private static final Pattern tag = Pattern.compile("<(/?)([a-zA-Z0-9]*)(\\b[^>]*)?>");
    
    public static TokenParserOutput parseTokens(String s){
        return parseTokens(s.split("\r?\n"));
    }
    
    public static TokenParserOutput parseTokens(String[] s){
        return parseTokens(Arrays.asList(s));
    }

    public static TokenParserOutput parseTokens(List<String> input) {
        Stack<TokenPairedOpening> pairOpenStart = new Stack<>();
        
        Matcher m = tag.matcher(input);

        pairOpenStart.add(new Pair<>(new XMLRaw(""), 0));
        int idx = 0;
        while (m.find(idx)) {
            String name = m.group(TAG_GROUP_NAME);
            if (m.group(TAG_GROUP_SLASH) == null || m.group(TAG_GROUP_SLASH).isEmpty()) {
                // A tag has been opened. Create a new raw tag:
                XMLRaw xmlRaw = new XMLRaw(name);
                // Add the attributes to this:
                addAttrs(xmlRaw, m.group(TAG_GROUP_ATTR));
                // Add this to the stack:
                pairOpenStart.push(new Pair<>(xmlRaw, m.end()));
            } else if (m.group(TAG_GROUP_SLASH).equals("/")) {
                Pair<XMLRaw, Integer> closingTag = pairOpenStart.pop();
                XMLRaw xmlRaw = closingTag.getKey();
                // Verify that the correct tag is being closed:
                if (!name.equals(xmlRaw.getTag())) {
                    throw new XMLSerializingException("Tag mismatch!");
                }
                // A tag is closed. Add the contents of the current range to the tag.
                xmlRaw.setText(input.substring(closingTag.getValue(), m.start()));
                // Add this to the children of the parent tag:
                pairOpenStart.peek().getKey().add(xmlRaw);
            } else {
                throw new XMLSerializingException("TAG_GROUP_SLASH is matching incorrectly!");
            }
            idx = m.end();
        }

        // Check that everything has been closed:
        Pair<XMLRaw, Integer> rvpair = pairOpenStart.pop();
        if (pairOpenStart.empty() && rvpair.getValue().equals(0)) {
            return rvpair.getKey().toXMLObject();
        } else {
            throw new XMLSerializingException("Not all elements are closed!");
        }
    }

    private static final Pattern attr = Pattern.compile("([a-zA-Z0-9]*)\\s*=\\s*(['\"])([^'\"]*)\\2");
    private static final int ATTR_GROUP_ALL = 0;
    private static final int ATTR_GROUP_NAME = 1;
    private static final int ATTR_GROUP_QUOTE = 2;
    private static final int ATTR_GROUP_VALUE = 3;
    
    private static void addAttrs(XMLRaw xmlRaw, String inp) {
        if (inp == null || inp.isEmpty()) {
            return;
        }
        
        Matcher m = attr.matcher(inp);
        
        int idx = 0;
        while (m.find(idx)) {
            xmlRaw.setAttr(m.group(ATTR_GROUP_NAME), XMLAttributeEscaper.unescape(m.group(ATTR_GROUP_VALUE)));
            idx = m.end();
        }
    }
}
