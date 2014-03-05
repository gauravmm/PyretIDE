package edu.brown.cs.cutlass.config;

public class ConfigKeyNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -7693103574525473135L;

    public ConfigKeyNotFoundException(String errMessage) {
        System.out.println(errMessage);
    }
}
