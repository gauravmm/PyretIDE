/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_let variant of type Expr
 * in the Pyret AST.
 *
 * data s_let:
 *   s_let (l :: Loc, name :: Bind, value :: Expr)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_let implements Expr {
	// Variables:
	public final Loc l;
	public final Bind name;
	public final Expr value;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_let(Loc l, Bind name, Expr value) {
		this.l = l;
		this.name = name;
		this.value = value;
    }
	
}
