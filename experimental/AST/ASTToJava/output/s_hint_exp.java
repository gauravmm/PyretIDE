/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_hint_exp variant of type Expr
 * in the Pyret AST.
 *
 * data s_hint_exp:
 *   s_hint_exp (l :: Loc, hints :: List<Hint>, e :: Expr)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_hint_exp implements Expr {
	// Variables:
	public final Loc l;
	public final List<Hint> hints;
	public final Expr e;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_hint_exp(Loc l, List<Hint> hints, Expr e) {
		this.l = l;
		this.hints = hints;
		this.e = e;
    }
	
}
