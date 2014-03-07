package edu.brown.cs.cutlass.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.awt.Color;
import java.awt.Dimension;
import java.io.*;

public class ConfigEngine {

    private Properties defaultProps;
    private Properties userProps;
    private final String defaultPath = "defaultProperties.txt"; //change to path of default props file
    private final String userPath;

    public ConfigEngine(String userPath) throws ConfigFileInvalidException {
        this.userPath = userPath;
        defaultProps = new Properties();
        try (FileInputStream inStream = new FileInputStream(defaultPath)) {
            defaultProps.load(inStream);
        } catch (FileNotFoundException e) {
            throw new ConfigFileInvalidException("No configuration file found at " + defaultPath);
        } catch (IOException e) {
            throw new ConfigFileInvalidException("IO error occurred while reading " + defaultPath);
        }

        userProps = new Properties(defaultProps);
        try (FileInputStream inStream = new FileInputStream(userPath)) {
            userProps.load(inStream);
        } catch (FileNotFoundException e) {
            throw new ConfigFileInvalidException("No configuration file found at " + userPath);
        } catch (IOException e) {
            throw new ConfigFileInvalidException("IO error occurred while reading " + userPath);
        }
    }

    public Boolean getBoolean(String key) {
        String value = userProps.getProperty(key);
        if (value == null) {
            throw new ConfigKeyNotFoundException("Key " + key + " does not exist yet");
        }
        if (value.equals("true") || value.equals("false")) {
            boolean ret = Boolean.parseBoolean(value);
            return ret;
        } else {
            throw new ConfigTypeException("Key " + key + " is not associated with a Boolean value");
        }
    }

    public Integer getInteger(String key) {
        String value = userProps.getProperty(key);
        if (value == null) {
            throw new ConfigKeyNotFoundException("Key " + key + " does not exist yet");
        }
        if (value.matches("^\\d+$")) {
            Integer ret = Integer.parseInt(value);
            return ret;
        } else {
            throw new ConfigTypeException("Key " + key + " is not associated with an Integer value");
        }
    }

    public Double getDouble(String key) {
        String value = userProps.getProperty(key);
        if (value == null) {
            throw new ConfigKeyNotFoundException("Key " + key + " does not exist yet");
        }
        if (value.matches("^\\d+$|^\\d+.\\d+$")) {
            Double ret = Double.parseDouble(value);
            return ret;
        } else {
            throw new ConfigTypeException("Key " + key + " is not associated with an Integer value");
        }
    }

    public String getString(String key) {
        String value = userProps.getProperty(key);
        if (value == null) {
            throw new ConfigKeyNotFoundException("Key " + key + " does not exist yet");
        }
        return value;
    }

    public Dimension getDimension(String key) {
        String value = userProps.getProperty(key);
        if (value == null){
            throw new ConfigKeyNotFoundException("Key " + key + " does not exist yet");
        }
        if (value.matches("^Dimension: [\\d+,\\d+]$")) {
            int width = Integer.parseInt(value.substring(12, value.indexOf(",")));
            int height = Integer.parseInt(value.substring(value.indexOf(","+1), value.indexOf("]")));
            return new Dimension(width, height);
        } else {
            throw new ConfigTypeException("Key " + key + " is not associated with a Dimension value");
        }
    }

    public Color getColor(String key) {
        String value = userProps.getProperty(key);
        if (value == null){
            throw new ConfigKeyNotFoundException("Key " + key + " does not exist yet");
        }
        if (value.matches("Color: #[0-9a-fA-F]+")){
            long colorHex = Long.parseLong(value.substring(value.indexOf("#") + 1), 16);
            return new Color((int)colorHex, true);
        } else {
            throw new ConfigTypeException("Key " + key + " is not associated with a Color value");
        }
    }

    public List<String> getList(String key) {
        String value = userProps.getProperty(key);
        if (value == null) {
            throw new ConfigKeyNotFoundException("Key " + key + " does not exist yet");
        }
        String[] valueSplit = value.split(",");
        List<String> ret = new ArrayList<>();
        for (String s : valueSplit) {
            ret.add(s.replace("(0^^^^@", ","));
        }
        return ret;
    }

    public void setBoolean(String key, Boolean value) {
        userProps.setProperty(key, value.toString());
    }

    public void setInteger(String key, Integer value) {
        userProps.setProperty(key, value.toString());
    }

    public void setDouble(String key, Double value) {
        userProps.setProperty(key, value.toString());
    }

    public void setString(String key, String value) {
        userProps.setProperty(key, value);
    }

    public void setDimension(String key, Dimension value) {
        setString(key, "Dimension: [" + value.width + "," + value.height + "]");
    }

    public void setColor(String key, Color value) {
        setString(key, "Color: #" + Integer.toHexString(value.getRGB()));
    }

    public void setList(String key, List<String> value) { //non-empty list
        if (value.isEmpty()) {
            setString(key, "");
            return;
        }
        String write = value.get(0).replace(",", "(0^^^^@");
        for (int i = 1; i < value.size(); i++) {
            write += "," + value.get(i).replace(",", "(0^^^^@");
        }
        setString(key, write);
    }

    public void removeProperty(String key) {
        userProps.remove(key);
    }

    public boolean save() {
        try (FileOutputStream out = new FileOutputStream(userPath)) {
            userProps.store(out, "---No Comment---");
            return true;
        } catch (FileNotFoundException e) {
            throw new ConfigFileInvalidException("No configuration file found at " + userPath);
        } catch (IOException e) {
            throw new ConfigFileInvalidException("IO error occurred while reading " + userPath);
        }
    }

    public static void main(String[] args) throws ConfigFileInvalidException, ConfigTypeException {
        ConfigEngine eng = new ConfigEngine("appProperties");
        System.out.println(eng.getString("user.name"));
        eng.setString("user.name", "cutlass 2.0");
        System.out.println(eng.getString("user.name"));
        List<String> lst = new ArrayList<>();
        lst.add("Hi, my name is");
        lst.add("david");
        eng.setList("nameList", lst);
        for (String s : eng.getList("nameList")) {
            System.out.println(s);
        }
        //eng.setColor("defColor", Color.blue);
        eng.setDimension("dimens", new Dimension(34, 23));
        System.out.println(eng.getColor("defColor"));
        //eng.setString("test1", "i have a \' in this\n\\n\"");
        System.out.println(eng.getString("test1"));
        eng.save();
    }
}
