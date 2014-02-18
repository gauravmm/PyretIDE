/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_check variant of type Expr
 * in the Pyret AST.
 *
 * data s_check:
 *   s_check (l :: Loc, body :: Expr)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_check implements Expr {
	// Variables:
	public final Loc l;
	public final Expr body;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_check(Loc l, Expr body) {
		this.l = l;
		this.body = body;
    }
	
}
