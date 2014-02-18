/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_str variant of type Expr
 * in the Pyret AST.
 *
 * data s_str:
 *   s_str (l :: Loc, s :: String)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_str implements Expr {
	// Variables:
	public final Loc l;
	public final String s;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_str(Loc l, String s) {
		this.l = l;
		this.s = s;
    }
	
}
