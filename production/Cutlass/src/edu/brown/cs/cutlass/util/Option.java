/*
 * Project 01 - Autocorrect
 * CSCI 0320, Brown University, Fall 2014
 * Gaurav Manek
 */
package edu.brown.cs.cutlass.util;

import java.util.Objects;

/**
 * Contains a value or no value, throws errors for debugging. This is better
 * than using null to represent the absence of a value because its not default
 * behavior.
 *
 * @author Gaurav Manek
 * @param <T>
 */
public class Option<T> {

    private final T data;
    private final boolean hasData;

    /**
     * Create an Option with data.
     *
     * @param data
     */
    public Option(T data) {
        this.data = data;
        this.hasData = true;
    }

    /**
     * Create an option without data.
     */
    public Option() {
        this.data = null;
        this.hasData = false;
    }
    
    /**
     * Check if the Option contains data.
     *
     * @return True if the option contains data (even if that data is null).
     * False otherwise.
     */
    public boolean hasData() {
        return hasData;
    }

    /**
     * Gets the stored data, or throws a RuntimeException if no such data
     * exists.
     *
     * @return The stored data.
     */
    public T getData() {
        if (hasData) {
            return data;
        } else {
            throw new RuntimeException("Attepted to access data of Option that contains no data.");
        }
    }

    @Override
    public String toString() {
        if(hasData)
            return "<" + data.toString() + ">";
        else
            return "<>";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.data);
        hash = 17 * hash + (this.hasData ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Option<?> other = (Option<?>) obj;
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        if (this.hasData != other.hasData) {
            return false;
        }
        return true;
    }
}
