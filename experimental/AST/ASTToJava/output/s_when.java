/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_when variant of type Expr
 * in the Pyret AST.
 *
 * data s_when:
 *   s_when (l :: Loc, test :: Expr, block :: Expr)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_when implements Expr {
	// Variables:
	public final Loc l;
	public final Expr test;
	public final Expr block;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_when(Loc l, Expr test, Expr block) {
		this.l = l;
		this.test = test;
		this.block = block;
    }
	
}
