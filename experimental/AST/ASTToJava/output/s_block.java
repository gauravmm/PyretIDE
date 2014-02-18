/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_block variant of type Expr
 * in the Pyret AST.
 *
 * data s_block:
 *   s_block (l :: Loc, stmts :: List<Expr>)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_block implements Expr {
	// Variables:
	public final Loc l;
	public final List<Expr> stmts;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_block(Loc l, List<Expr> stmts) {
		this.l = l;
		this.stmts = stmts;
    }
	
}
