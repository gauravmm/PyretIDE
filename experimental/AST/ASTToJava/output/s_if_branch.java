/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_if_branch variant of type IfBranch
 * in the Pyret AST.
 *
 * data s_if_branch:
 *   s_if_branch (l :: Loc, test :: Expr, body :: Expr)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_if_branch implements IfBranch {
	// Variables:
	public final Loc l;
	public final Expr test;
	public final Expr body;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_if_branch(Loc l, Expr test, Expr body) {
		this.l = l;
		this.test = test;
		this.body = body;
    }
	
}
