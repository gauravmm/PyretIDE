package edu.brown.cs.cutlass.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.awt.Color;
import java.awt.Dimension;
import java.io.*;

public class ConfigEngine {

    private final Properties userProps;

    /**
     * Private Access Constructor used for fromString.
     *
     * @param cfgFile The contents of the cfgFile, as passed by
     * @throws ConfigFileInvalidException if the configuration file cannot be
     * parsed.
     */
    private ConfigEngine(List<String> cfgFile) throws ConfigFileInvalidException {
        StringBuilder strBld = new StringBuilder();
        userProps = new Properties();
        if (!cfgFile.isEmpty()) {
            strBld.append(cfgFile.get(0));
            for (int index = 1; index < cfgFile.size(); index++) {
                strBld.append("\n");
                strBld.append(cfgFile.get(index));
            }
            String cfgString = strBld.toString();
            try {
                InputStream str = new ByteArrayInputStream(cfgString.getBytes("UTF-8"));
                userProps.load(str);
            } catch (IOException e) {
                throw new ConfigFileInvalidException("An error occurred while reading the configuration file");
            }
        }
    }

    /**
     *
     * @param cfgData
     * @return
     * @throws ConfigFileInvalidException
     */
    public static ConfigEngine fromString(List<String> cfgData) throws ConfigFileInvalidException {
        return new ConfigEngine(cfgData);
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
        if (value == null) {
            throw new ConfigKeyNotFoundException("Key " + key + " does not exist yet");
        }
        if (value.matches("^Dimension: [\\d+,\\d+]$")) {
            int width = Integer.parseInt(value.substring(12, value.indexOf(",")));
            int height = Integer.parseInt(value.substring(value.indexOf("," + 1), value.indexOf("]")));
            return new Dimension(width, height);
        } else {
            throw new ConfigTypeException("Key " + key + " is not associated with a Dimension value");
        }
    }

    public Color getColor(String key) {
        String value = userProps.getProperty(key);
        if (value == null) {
            throw new ConfigKeyNotFoundException("Key " + key + " does not exist yet");
        }
        if (value.matches("Color: #[0-9a-fA-F]+")) {
            long colorHex = Long.parseLong(value.substring(value.indexOf("#") + 1), 16);
            return new Color((int) colorHex, true);
        } else {
            throw new ConfigTypeException("Key " + key + " is not associated with a Color value");
        }
    }

    public List<String> getList(String key) {
        String value = userProps.getProperty(key);
        if (value == null) {
            throw new ConfigKeyNotFoundException("Key " + key + " does not exist yet");
        }
        if (value.charAt(0) == '{' && value.charAt(value.length() - 1) == '}') {
            String[] valueSplit = value.split("(?<!\\\\), ");
            List<String> ret = new ArrayList<>();
            for (String s : valueSplit) {
                ret.add(s.replace("\\,", ","));
            }
            return ret;
        } else {
            throw new ConfigTypeException("Key " + key + " is not associated with a List value");
        }

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
            setString(key, "{}");
            return;
        }
        String write = "{" + value.get(0).replace(",", "\\,");
        for (int i = 1; i < value.size(); i++) {
            write += "," + value.get(i).replace(",", "\\,");
        }
        write += "}";
        setString(key, write);
    }

    public void removeProperty(String key) {
        userProps.remove(key);
    }

    // Serialized output, will be stored in the file:
    @Override
    public String toString() {
        try {
            ByteArrayOutputStream op = new ByteArrayOutputStream();
            userProps.store(op, "");
            String out = new String(op.toByteArray(), "UTF-8");
            return out;
        } catch (IOException ex) {
            //There is, like, no chance of this occurring
            throw new ConfigFileInvalidException("Something went wrong when converting your properties! Sorry.");
        }
    }

}
