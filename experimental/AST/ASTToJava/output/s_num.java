/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_num variant of type Expr
 * in the Pyret AST.
 *
 * data s_num:
 *   s_num (l :: Loc, n :: Number)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_num implements Expr {
	// Variables:
	public final Loc l;
	public final Number n;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_num(Loc l, Number n) {
		this.l = l;
		this.n = n;
    }
	
}
