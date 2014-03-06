package edu.brown.cs.cutlass.config;

import edu.brown.cs.cutlass.util.Lumberjack;

public class ConfigKeyNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -7693103574525473135L;

    public ConfigKeyNotFoundException(String errMessage) {
        Lumberjack.log(Lumberjack.Level.ERROR, errMessage);
    }
}
