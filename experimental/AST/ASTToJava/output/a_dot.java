/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the a_dot variant of type Ann
 * in the Pyret AST.
 *
 * data a_dot:
 *   a_dot (l :: Loc, obj :: String, field :: String)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class a_dot implements Ann {
	// Variables:
	public final Loc l;
	public final String obj;
	public final String field;

	/**
	 * Constructor, initializes all fields.
	 */
	public a_dot(Loc l, String obj, String field) {
		this.l = l;
		this.obj = obj;
		this.field = field;
    }
	
}
