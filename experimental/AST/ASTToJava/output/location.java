/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the location variant of type Loc
 * in the Pyret AST.
 *
 * data location:
 *   location (file :: String, line :: Any, column :: Any)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class location implements Loc {
	// Variables:
	public final String file;
	public final Any line;
	public final Any column;

	/**
	 * Constructor, initializes all fields.
	 */
	public location(String file, Any line, Any column) {
		this.file = file;
		this.line = line;
		this.column = column;
    }
	
}
