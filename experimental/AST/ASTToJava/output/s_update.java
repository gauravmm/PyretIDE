/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_update variant of type Expr
 * in the Pyret AST.
 *
 * data s_update:
 *   s_update (l :: Loc, super :: Expr, fields :: List<Member>)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_update implements Expr {
	// Variables:
	public final Loc l;
	public final Expr super;
	public final List<Member> fields;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_update(Loc l, Expr super, List<Member> fields) {
		this.l = l;
		this.super = super;
		this.fields = fields;
    }
	
}
