/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_extend variant of type Expr
 * in the Pyret AST.
 *
 * data s_extend:
 *   s_extend (l :: Loc, super :: Expr, fields :: List<Member>)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_extend implements Expr {
	// Variables:
	public final Loc l;
	public final Expr super;
	public final List<Member> fields;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_extend(Loc l, Expr super, List<Member> fields) {
		this.l = l;
		this.super = super;
		this.fields = fields;
    }
	
}
