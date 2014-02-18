/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_op variant of type Expr
 * in the Pyret AST.
 *
 * data s_op:
 *   s_op (l :: Loc, op :: String, left :: Expr, right :: Expr)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_op implements Expr {
	// Variables:
	public final Loc l;
	public final String op;
	public final Expr left;
	public final Expr right;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_op(Loc l, String op, Expr left, Expr right) {
		this.l = l;
		this.op = op;
		this.left = left;
		this.right = right;
    }
	
}
