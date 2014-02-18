/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_provide variant of type Header
 * in the Pyret AST.
 *
 * data s_provide:
 *   s_provide (l :: Loc, block :: Expr)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_provide implements Header {
	// Variables:
	public final Loc l;
	public final Expr block;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_provide(Loc l, Expr block) {
		this.l = l;
		this.block = block;
    }
	
}
