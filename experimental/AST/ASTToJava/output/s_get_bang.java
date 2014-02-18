/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_get_bang variant of type Expr
 * in the Pyret AST.
 *
 * data s_get_bang:
 *   s_get_bang (l :: Loc, obj :: Expr, field :: String)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_get_bang implements Expr {
	// Variables:
	public final Loc l;
	public final Expr obj;
	public final String field;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_get_bang(Loc l, Expr obj, String field) {
		this.l = l;
		this.obj = obj;
		this.field = field;
    }
	
}
