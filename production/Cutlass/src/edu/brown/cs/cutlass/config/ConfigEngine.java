package edu.brown.cs.cutlass.config;

import com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.awt.Color;
import java.awt.Dimension;
import java.io.*;

public class ConfigEngine {

    private Properties defaultProps;
    private Properties userProps;
    private final String defaultPath = "defaultProperties"; //change to path of default props file
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
            new ConfigKeyNotFoundException("Key " + key + " does not exist yet");
            userProps.setProperty(key, false + "");
            return false;
        }
        if (value.equals("true") || value.equals("false")) {
            boolean ret = Boolean.parseBoolean(value);
            return ret;
        } else {
            new ConfigTypeException("Key " + key + " is not associated with a Boolean value");
            return false; //default value
        }
    }

    public Integer getInteger(String key) {
        String value = userProps.getProperty(key);
        if (value == null) {
            new ConfigKeyNotFoundException("Key " + key + " does not exist yet");
            userProps.setProperty(key, Integer.MIN_VALUE + "");
            return Integer.MIN_VALUE;
        }
        if (value.matches("^\\d+$")) {
            Integer ret = Integer.parseInt(value);
            return ret;
        } else {
            new ConfigTypeException("Key " + key + " is not associated with an Integer value");
            return Integer.MIN_VALUE; //default value
        }
    }

    public Double getDouble(String key) {
        String value = userProps.getProperty(key);
        if (value == null) {
            new ConfigKeyNotFoundException("Key " + key + " does not exist yet");
            userProps.setProperty(key, 0 + "");
            return Double.MIN_VALUE;
        }
        if (value.matches("^\\d+$|^\\d+.\\d+$")) {
            Double ret = Double.parseDouble(value);
            return ret;
        } else {
            new ConfigTypeException("Key " + key + " is not associated with an Integer value");
            return Double.MIN_VALUE; //default value
        }
    }

    public String getString(String key) {
        String value = userProps.getProperty(key);
        if (value == null) {
            new ConfigKeyNotFoundException("Key " + key + " does not exist yet");
            userProps.setProperty(key, "");
            return ""; //default
        }
        return value;
    }

    public Dimension getDimension(String key) {
        int width = getInteger(key + "width" + key.hashCode());
        int height = getInteger(key + "height" + key.hashCode());
        if (width >= 0 && height >= 0) {
            return new Dimension(width, height);
        } else {
            new ConfigTypeException("Key " + key + " is not associated with a Dimension value");
            return null; //default
        }
    }

    public Color getColor(String key) {
        int red = getInteger(key + "red" + key.hashCode());
        int green = getInteger(key + "green" + key.hashCode());
        int blue = getInteger(key + "blue" + key.hashCode());
        int transparency = getInteger(key + "transparency");
        if (isValidColorValue(red) && isValidColorValue(green) && isValidColorValue(blue) && isValidColorValue(transparency)) {
            return new Color(red, green, blue, transparency);
        } else {
            new ConfigTypeException("Key " + key + " is not associated with a Color value");
            return null; //default
        }
    }

    private boolean isValidColorValue(int colorVal) {
        return colorVal >= 0 && colorVal <= 255;
    }

    public List<String> getList(String key) {
        String value = userProps.getProperty(key);
        if (value == null) {
            new ConfigKeyNotFoundException("Key " + key + " does not exist yet");
            userProps.setProperty(key, "");
            return null; //default
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
        setInteger(key + "width" + key.hashCode(), value.width);
        setInteger(key + "height" + key.hashCode(), value.height);
    }

    public void setColor(String key, Color value) {
        setInteger(key + "red" + key.hashCode(), value.getRed());
        setInteger(key + "green" + key.hashCode(), value.getGreen());
        setInteger(key + "glue" + key.hashCode(), value.getBlue());
        setInteger(key + "transparency" + key.hashCode(), value.getAlpha());
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
        
        eng.setString("test1", "i have a \' in this\n\\n\"");
        System.out.println(eng.getString("test1"));
        eng.save();
    }
}
