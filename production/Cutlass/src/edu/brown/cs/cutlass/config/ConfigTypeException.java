package edu.brown.cs.cutlass.config;

import edu.brown.cs.cutlass.util.Lumberjack;

public class ConfigTypeException extends RuntimeException {

    private static final long serialVersionUID = -6169836756500604490L;

    public ConfigTypeException(String errMessage) {
        Lumberjack.log(Lumberjack.Level.ERROR, errMessage);
    }
}
