/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the a_name variant of type Ann
 * in the Pyret AST.
 *
 * data a_name:
 *   a_name (l :: Loc, id :: String)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class a_name implements Ann {
	// Variables:
	public final Loc l;
	public final String id;

	/**
	 * Constructor, initializes all fields.
	 */
	public a_name(Loc l, String id) {
		this.l = l;
		this.id = id;
    }
	
}
