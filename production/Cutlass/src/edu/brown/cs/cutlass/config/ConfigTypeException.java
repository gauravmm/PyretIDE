package edu.brown.cs.cutlass.config;

public class ConfigTypeException extends RuntimeException {

    private static final long serialVersionUID = -6169836756500604490L;

    public ConfigTypeException(String errMessage) {
        System.out.println(errMessage);
    }
}
