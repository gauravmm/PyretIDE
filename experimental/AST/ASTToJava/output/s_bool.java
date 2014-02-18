/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_bool variant of type Expr
 * in the Pyret AST.
 *
 * data s_bool:
 *   s_bool (l :: Loc, b :: Bool)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_bool implements Expr {
	// Variables:
	public final Loc l;
	public final Bool b;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_bool(Loc l, Bool b) {
		this.l = l;
		this.b = b;
    }
	
}
