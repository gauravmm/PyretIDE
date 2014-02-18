/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_app variant of type Expr
 * in the Pyret AST.
 *
 * data s_app:
 *   s_app (l :: Loc, _fun :: Expr, args :: List<Expr>)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_app implements Expr {
	// Variables:
	public final Loc l;
	public final Expr _fun;
	public final List<Expr> args;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_app(Loc l, Expr _fun, List<Expr> args) {
		this.l = l;
		this._fun = _fun;
		this.args = args;
    }
	
}
