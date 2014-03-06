package edu.brown.cs.cutlass.config;

import edu.brown.cs.cutlass.util.Lumberjack;

public class ConfigFileInvalidException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ConfigFileInvalidException(String errMessage) {
        Lumberjack.log(Lumberjack.Level.ERROR, errMessage);
    }

}
