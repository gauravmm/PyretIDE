package edu.brown.cs.cutlass.config;

public class ConfigFileInvalidException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ConfigFileInvalidException(String errMessage) {
        System.out.println(errMessage);
    }

}
