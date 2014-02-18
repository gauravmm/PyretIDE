/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_list variant of type Expr
 * in the Pyret AST.
 *
 * data s_list:
 *   s_list (l :: Loc, values :: List<Expr>)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_list implements Expr {
	// Variables:
	public final Loc l;
	public final List<Expr> values;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_list(Loc l, List<Expr> values) {
		this.l = l;
		this.values = values;
    }
	
}
