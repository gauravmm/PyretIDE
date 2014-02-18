/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_dot variant of type Expr
 * in the Pyret AST.
 *
 * data s_dot:
 *   s_dot (l :: Loc, obj :: Expr, field :: String)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_dot implements Expr {
	// Variables:
	public final Loc l;
	public final Expr obj;
	public final String field;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_dot(Loc l, Expr obj, String field) {
		this.l = l;
		this.obj = obj;
		this.field = field;
    }
	
}
