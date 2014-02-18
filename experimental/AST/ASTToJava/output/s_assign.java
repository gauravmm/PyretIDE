/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_assign variant of type Expr
 * in the Pyret AST.
 *
 * data s_assign:
 *   s_assign (l :: Loc, id :: String, value :: Expr)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_assign implements Expr {
	// Variables:
	public final Loc l;
	public final String id;
	public final Expr value;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_assign(Loc l, String id, Expr value) {
		this.l = l;
		this.id = id;
		this.value = value;
    }
	
}
