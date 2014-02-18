/*
 * Automatically generated file. Do not modify directly.
 * Created 2014-02-17 22:31:02.896000 by Gaurav Manek
 */

package edu.brown.cs.cutlass.pyret.ast;

import java.util.List;

/**
 * This class corresponds to the s_left_app variant of type Expr
 * in the Pyret AST.
 *
 * data s_left_app:
 *   s_left_app (l :: Loc, obj :: Expr, _fun :: Expr, args :: List<Expr>)
 *   ...
 * end
 * 
 * @author Edward Teach
 */
public final class s_left_app implements Expr {
	// Variables:
	public final Loc l;
	public final Expr obj;
	public final Expr _fun;
	public final List<Expr> args;

	/**
	 * Constructor, initializes all fields.
	 */
	public s_left_app(Loc l, Expr obj, Expr _fun, List<Expr> args) {
		this.l = l;
		this.obj = obj;
		this._fun = _fun;
		this.args = args;
    }
	
}
