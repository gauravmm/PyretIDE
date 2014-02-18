/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_obj variant of type Expr
 * in the Pyret AST.
 *
 * data s_obj:
 *   s_obj (l :: Loc, fields :: List<Member>)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_obj implements Expr {
	// Variables:
	public final Loc l;
	public final List<Member> fields;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_obj(Loc l, List<Member> fields) {
		this.l = l;
		this.fields = fields;
    }
	
}
