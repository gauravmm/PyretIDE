/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_bracket variant of type Expr
 * in the Pyret AST.
 *
 * data s_bracket:
 *   s_bracket (l :: Loc, obj :: Expr, field :: Expr)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_bracket implements Expr {
	// Variables:
	public final Loc l;
	public final Expr obj;
	public final Expr field;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_bracket(Loc l, Expr obj, Expr field) {
		this.l = l;
		this.obj = obj;
		this.field = field;
    }
	
}
