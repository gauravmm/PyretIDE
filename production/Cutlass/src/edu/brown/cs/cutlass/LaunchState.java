/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs.cutlass;

import edu.brown.cs.cutlass.sys.io.AbstractIdentifier;
import edu.brown.cs.cutlass.sys.io.AbstractIdentifierParser;
import edu.brown.cs.cutlass.util.Lumberjack;
import edu.brown.cs.cutlass.util.Lumberjack.Level;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Holds (and handles serializing/unserializing of) the program state at launch.
 * This includes the files currently open. This is an immutable object.
 *
 * @author Gaurav Manek T is an arrPointer
 */
class LaunchState<T extends AbstractIdentifier> {
    // T is an AbstractIdentifer that identifies a unique resource on the 
    // current I/O media (Ex. a path object to a space in disk).

    /**
     *
     *
     */
    private final List<T> openFiles;

    /**
     * Defaults to 0.
     */
    private final int currentTabId;

    /**
     *
     * @param openFiles
     */
    private LaunchState(ArrayList<T> openFiles, int currentTabId) {
        this.openFiles = openFiles;
        this.currentTabId = currentTabId;
    }

    /**
     *
     */
    public LaunchState() {
        openFiles = new ArrayList();
        this.currentTabId = 0;
    }

    /**
     *
     * @return
     */
    public List<T> getOpenFiles() {
        return Collections.unmodifiableList(openFiles);
    }

    @Override
    public String toString() {
        String builtString = "";
        builtString += "currentTab: " + currentTabId;
        for(T t : openFiles){
        	builtString += " File: " + t.toString();
        }
        return builtString;
    }
    
    
    // R is the same type as T, but defined in the static scope.
    
    /**
     * Convert a LaunchState-encoded file into a LaunchState object.
     *
     * Note: If an AbstractIdentifer cannot be parsed, a warning is logged and
     * the function ignores it. File path representation anything we decide on
     * later.
     *
     * @param <R> The underlying type of the I/O system, as defined by the
     * AbstractIdentifier.
     * @param in The List&lt;String&gt;, each element containing one line of the
     * LaunchState file.
     * @param parser A converter that takes an AbstractIdentifier as a String
     * and converts it to an Object.
     * @return A LaunchState containing the state of the program at previous
     * shutdown.
     * @throws IllegalArgumentException If the structure of the file is
     * incorrect.
     */

    public static <R extends AbstractIdentifier> LaunchState<R> fromString(List<String> in, AbstractIdentifierParser<R> parser) throws IllegalArgumentException {
    	//create pieces that will eventually be placed in launch state.
    	ArrayList<R> openFiles0 = new ArrayList<R>();
    	int currentTabID0 = -1; //not instantiated in case it's not found in file.
    	
        //loop through input strings
        for(String line : in){
        	String cutLine = line.trim();
        	if(cutLine.length() == 0 || cutLine.charAt(0) == '#'){
        		// if empty or comment line, do nothing
        	}
        	else{
        		switch(cutLine.substring(0, cutLine.indexOf('\t'))){
        		//Current tab: try to parse it as a number, throw errors if it can't
        		case("CURRENT_TAB"):
        			try{
        				currentTabID0 = Integer.parseInt(cutLine.substring(cutLine.indexOf('\t') + 1));
        			}
        			catch(IllegalArgumentException e){
        				Lumberjack.log(Level.ERROR, "current tab line was formatted wrong.");
        				throw new IllegalArgumentException("Error: the current tab line did "
        						+ "not contain a parseable integer.");
        			}
        			break;
        		//File, try to parse it with the given AIDParser, ignore it if it fails
        		case("FILE"):
        			try{
        				openFiles0.add(parser.parse(cutLine.substring(cutLine.indexOf('\t') + 1)));
        			}
        			catch(IllegalArgumentException e){
        				Lumberjack.log(Level.WARN, "Warning: Could not properly parse "
        						+ "the following line during launchstate creation: " + cutLine);
        			}
        		break;
        		//Default to errors, since we've already checked for blank/commented lines.
        		default:
        			Lumberjack.log(Level.ERROR, "Line did not match any known entry types");
        			throw new IllegalArgumentException("Error: This line did not match any known "
        					+ "entry types.");
        		}
        	}
        }
        
        //instantiation checking code - will grow if LaunchState acquires new features.
        
        if(currentTabID0 < 0 || currentTabID0 >= openFiles0.size()){
        	//yell if we have not set the currently open tab and have files open.
        	throw new IllegalArgumentException("Error: Eithet no open tab or an improper"
        			+ " tab number was given.");
        }
        
        //actual creating and returning of a LaunchState
        return new LaunchState<R>(openFiles0, currentTabID0);
    }

    /**
     * Produce a LaunchState that represents the state of the program at
     * closing.
     *
     * @param <R>
     * @param filesOpen
     * @return
     */
    public static <R extends AbstractIdentifier> LaunchState<R> toState(Collection<R> filesOpen, int tabId) {
    	//instantiate new parts of launchstate
    	ArrayList<R> openFiles0 = new ArrayList<R>();
    	for(R r: filesOpen){
    		// something happens here involving R parsers? Not sure about this abstraction
    		}
    	return new LaunchState<R>(openFiles0, tabId);
    	
    }
    
    
}
