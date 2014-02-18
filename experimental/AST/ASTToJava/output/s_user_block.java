/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_user_block variant of type Expr
 * in the Pyret AST.
 *
 * data s_user_block:
 *   s_user_block (l :: Loc, body :: Expr)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_user_block implements Expr {
	// Variables:
	public final Loc l;
	public final Expr body;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_user_block(Loc l, Expr body) {
		this.l = l;
		this.body = body;
    }
	
}
