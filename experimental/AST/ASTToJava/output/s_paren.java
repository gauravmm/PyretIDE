/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_paren variant of type Expr
 * in the Pyret AST.
 *
 * data s_paren:
 *   s_paren (l :: Loc, expr :: Expr)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_paren implements Expr {
	// Variables:
	public final Loc l;
	public final Expr expr;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_paren(Loc l, Expr expr) {
		this.l = l;
		this.expr = expr;
    }
	
}
