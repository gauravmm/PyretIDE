/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_for_bind variant of type ForBind
 * in the Pyret AST.
 *
 * data s_for_bind:
 *   s_for_bind (l :: Loc, bind :: Bind, value :: Expr)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_for_bind implements ForBind {
	// Variables:
	public final Loc l;
	public final Bind bind;
	public final Expr value;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_for_bind(Loc l, Bind bind, Expr value) {
		this.l = l;
		this.bind = bind;
		this.value = value;
    }
	
}
